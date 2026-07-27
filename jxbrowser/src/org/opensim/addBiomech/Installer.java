/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/moduleInstall.java to edit this template
 */
package org.opensim.addBiomech;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // TODO
    }
@Override
    public boolean closing() {
        // Return false to CANCEL shutdown (e.g., unsaved data)
        return true; // Allow exit
    }

    @Override
    public void close() {
        // Called when shutdown is confirmed — do your cleanup here
        deleteTempFiles();
    }

    private void deleteTempFiles() {
        Path downloadPath = Paths.get(AddBiomechPrefs.getDownloadsDir());
        File tempDir = new File(downloadPath.toString());
        deleteRecursively(tempDir);
    }

    private void deleteRecursively(File f) {
        if (f.isDirectory()) {
            for (File child : f.listFiles()) {
                deleteRecursively(child);
            }
        }
        f.delete();
    }
}
