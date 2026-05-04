/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.opensim.javabrowser.addBiomech;

import org.opensim.utils.TheApp;
import static org.opensim.utils.TheApp.getCurrentVersionPreferences;

/**
 *
 * @author ayman
 */
public final class AddBiomechPrefs {
    public static String getDownloadsDir() {
        return getCurrentVersionPreferences().get("AddBiomechDownloadFolder", TheApp.getInstallDir()+"/"+"AddBiomechDownloads");
    }
}
