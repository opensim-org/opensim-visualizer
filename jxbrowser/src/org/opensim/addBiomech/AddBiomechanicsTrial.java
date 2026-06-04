/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.opensim.addBiomech;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Exceptions;
import org.opensim.modeling.STOFileAdapter;
import org.opensim.modeling.StdVectorString;
import org.opensim.modeling.Storage;
import org.opensim.modeling.TRCFileAdapter;
import org.opensim.modeling.TableUtilities;
import org.opensim.modeling.TimeSeriesTable;
import org.opensim.modeling.TimeSeriesTableVec3;

/**
 *
 * @author ayman
 * 
 * Class abstracting an AddBiomechanicsTrial downloaded from the website, serves as a way to group trial-specific files sprinkled
 * around the download zip.
 */
public class AddBiomechanicsTrial {

    public AddBiomechanicsTrial(String trialName){
        this.name = trialName;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the markerDataPaths
     */
    public List<Path> getMarkerDataPaths() {
        return markerDataPaths;
    }


    /**
     * @return the grfDataPaths
     */
    public List<String> getGrfDataPaths() {
        return grfDataPaths;
    }

    
    public boolean needStitching() {
        return stitched==false && segments.size()>1;
    }
    private String name;
    private final List<String> segments = new ArrayList<>();
    private final List<Path> markerDataPaths = new ArrayList<>();
    private final List<String> grfDataPaths = new ArrayList<>();
    private final boolean stitched = false;

    void addMarkerDataPath(Path path) {
        markerDataPaths.add(path);
        // if grf exists add it to grfDataPaths
        String idFolder = path.toFile().getParentFile().getPath().concat("/../ID/");
        String candidateGrfFile = idFolder+"/"+name+"_segment_0_grf.mot";
        if (new File(candidateGrfFile).exists())
            grfDataPaths.add(candidateGrfFile);
    }

    void addSegment(String segmentNumber) {
        segments.add(segmentNumber);
    }
    public String getDetails() {
        return ("Trial:"+name+", Segments:"+segments.toString());
    }
    @Override
    public String toString() {
        return name;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return toString().equals(o.toString());
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    public String getStitchedIK() {
        if (markerDataPaths.isEmpty())
            return null;
        String ikFolder = markerDataPaths.get(0).toFile().getParentFile().getPath().concat("/../IK");
        String firstIKMotFilename = ikFolder+"/"+name+"_segment_0_ik.mot";
        
        if (needStitching()){
            Storage results = null;
            String origName = "";
            boolean inDegrees = false;
            try {
               results = new Storage(firstIKMotFilename);
               origName = results.getName();
               inDegrees = results.isInDegrees();
            } catch (IOException ex) {
               Exceptions.printStackTrace(ex);
            }
            // Free, will use TimeSeriesTables and only convert final table to Storage
            TimeSeriesTable tally = new TimeSeriesTable(firstIKMotFilename);
            // For now assume segments are numbered 0..segments.size()-1
            for (int segNumber=1; segNumber < segments.size(); segNumber++){
                TimeSeriesTable tableToAppend = new TimeSeriesTable(getSegmentFileName(firstIKMotFilename, segNumber));
                TimeSeriesTable appended = TableUtilities.concatenateTable(tally, tableToAppend);
                tally = appended;
            }
            int numCols = (int) tally.getNumColumns();
            int numRows = (int) tally.getNumRows();
            tally.addTableMetaDataString("name", origName+"_Stitched");
            tally.addTableMetaDataString("nRows",    String.valueOf(numRows));
            tally.addTableMetaDataString("nColumns", String.valueOf(numCols + 1));
            tally.addTableMetaDataString("inDegrees", inDegrees?"yes":"No");
            String resultFile = getSegmentFileName(firstIKMotFilename, -1);
            STOFileAdapter.write(tally, resultFile);
            return resultFile;
        }
        else
            return firstIKMotFilename;    
    }
    public String getStitchedMarkerData() {
        if (markerDataPaths.isEmpty())
            return null;
        String mdFolder = markerDataPaths.get(0).toFile().getParentFile().getPath().concat("/../MarkerData");
        String firstTrcFilename = mdFolder+"/"+name+"_segment_0.trc";
        // meta data needed for trc files
        if (needStitching()){
            // Free, will use TimeSeriesTables and only convert final table to Storage
            TimeSeriesTableVec3 tally = new TimeSeriesTableVec3(firstTrcFilename);
            // Keep metadataKeys from first table to be used later
            StdVectorString metadataKeys = tally.getTableMetaDataKeys();
            StdVectorString metadataValues = new StdVectorString();
            // First line of the stream is the header.
            for (String metadata1 : metadataKeys) {
                metadataValues.add(tally.getTableMetaDataString(metadata1));
            }

            // For now assume segments are numbered 0..segments.size()-1
            for (int segNumber=1; segNumber < segments.size(); segNumber++){
                TimeSeriesTableVec3 tableToAppend = new TimeSeriesTableVec3(getSegmentFileName(firstTrcFilename, segNumber));
                TimeSeriesTableVec3 appended = TableUtilities.concatenateTableVec3(tally, tableToAppend);
                tally = appended;
            }
            // copy meta data back into result
            for (int i=0; i< metadataKeys.size(); i++) {
                tally.addTableMetaDataString(metadataKeys.get(i), metadataValues.get(i));
            }
            String resultFile = getSegmentFileName(firstTrcFilename, -1);
            //tally.addTableMetaDataString(name, name);
            TRCFileAdapter.write(tally, resultFile);
            return resultFile;
        }
        else
            return firstTrcFilename;            
    }
    public String getStitchedGrf() {
        if (grfDataPaths.isEmpty()) {
            return null;
        }
        String idFolder = markerDataPaths.get(0).toFile().getParentFile().getPath().concat("/../ID");
        String firstGrfFilename = idFolder + "/" + name + "_segment_0_grf.mot";

        if (needStitching()) {
            Storage results = null;
            String origName = "";
            boolean inDegrees = false;
            try {
                results = new Storage(firstGrfFilename);
                origName = results.getName();
                inDegrees = results.isInDegrees();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            // Free, will use TimeSeriesTables and only convert final table to Storage
            TimeSeriesTable tally = new TimeSeriesTable(firstGrfFilename);
            // For now assume segments are numbered 0..segments.size()-1
            for (int segNumber = 1; segNumber < segments.size(); segNumber++) {
                TimeSeriesTable tableToAppend = new TimeSeriesTable(getSegmentFileName(firstGrfFilename, segNumber));
                TimeSeriesTable appended = TableUtilities.concatenateTable(tally, tableToAppend);
                tally = appended;
            }
            int numCols = (int) tally.getNumColumns();
            int numRows = (int) tally.getNumRows();
            tally.addTableMetaDataString("name", origName + "_Stitched");
            tally.addTableMetaDataString("nRows", String.valueOf(numRows));
            tally.addTableMetaDataString("nColumns", String.valueOf(numCols + 1));
            tally.addTableMetaDataString("inDegrees", inDegrees ? "yes" : "No");
            String resultFile = getSegmentFileName(firstGrfFilename, -1);
            STOFileAdapter.write(tally, resultFile);
            return resultFile;
        } else {
            return firstGrfFilename;
        }
    }

    private String getSegmentFileName(String firstSegmentFilename, int segNumber) {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         if (segNumber == -1)
             return (firstSegmentFilename.replace("_0", "_all"));
         return (firstSegmentFilename.replace("_0", "_"+String.valueOf(segNumber)));
    }
    
 }
