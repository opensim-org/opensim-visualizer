/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.opensim.addBiomech;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> segments = new ArrayList<>();
    private List<Path> markerDataPaths = new ArrayList<>();
    private List<Path> motionPaths = new ArrayList<>();
    private List<String> grfDataPaths = new ArrayList<>();
    private boolean stitched = false;

    void addMarkerDataPath(Path path) {
        markerDataPaths.add(path);
    }

    void addSegment(String segmentNumber) {
        segments.add(segmentNumber);
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
}
