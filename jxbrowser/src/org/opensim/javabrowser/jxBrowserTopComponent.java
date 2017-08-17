/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opensim.javabrowser;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.BrowserType;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.util.Observable;
import java.util.Observer;
import java.util.prefs.Preferences;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.modules.Places;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.opensim.utils.TheApp;
import org.opensim.view.ObjectSetCurrentEvent;
import org.opensim.view.pub.OpenSimDB;
import org.opensim.view.pub.ViewDB;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.opensim.javabrowser//jxBrowser//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "jxBrowserTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "org.opensim.javabrowser.jxBrowserTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_jxBrowserAction",
        preferredID = "jxBrowserTopComponent"
)
@Messages({
    "CTL_jxBrowserAction=Visualizer",
    "CTL_jxBrowserTopComponent=Visualizer Window",
    "HINT_jxBrowserTopComponent=This is a Visualizer window"
})
public final class jxBrowserTopComponent extends TopComponent implements Observer {
    Browser browser; 
    BrowserView view; 

    public jxBrowserTopComponent() {
        initComponents();
        String useGPU = "On";        
        String savedGPU = Preferences.userNodeForPackage(TheApp.class).get("GPU Acceleration", useGPU);
        Preferences.userNodeForPackage(TheApp.class).put("GPU Acceleration", savedGPU);
        String useLightWeight = "Off";        
        String savedLightWeight = Preferences.userNodeForPackage(TheApp.class).get("LightWeight Browser", useLightWeight);
        Preferences.userNodeForPackage(TheApp.class).put("LightWeight Browser", savedLightWeight);

        BrowserContextParams bcp = new BrowserContextParams(
            Places.getUserDirectory() + "/EmbeddedBrowserCache");
        BrowserContext browserContext = new BrowserContext(bcp);
        if (savedGPU.equalsIgnoreCase("off")){
            BrowserPreferences.setChromiumSwitches("--disable-gpu");
        }
        if (savedLightWeight.equalsIgnoreCase("on"))
            browser = new Browser(BrowserType.LIGHTWEIGHT, browserContext);
        else 
            browser = new Browser(BrowserType.HEAVYWEIGHT, browserContext);
        // This clears the cache in the <user-dir>/EmbeddedBrowserCache/Cache
        // folder (asynchronously). Doing so is necessary to ensure that Models
        // reliably show up in the visualizer. It's important to not delete
        // the entire EmbeddedBrowserCache folder, so as to retain user
        // settings for the floor, etc.
        browser.getCacheStorage().clearCache();
        view = new BrowserView(browser);
        jPanel1.add(view);
        ViewDB.startVisualizationServer();
        OpenSimDB.getInstance().addObserver(this);
        browser.loadHTML("<html><body></body></html>");
        jPanel1.validate();
         
        setName(Bundle.CTL_jxBrowserTopComponent());
        setToolTipText(Bundle.HINT_jxBrowserTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        jPanel1.setLayout(new java.awt.BorderLayout());
        jScrollPane.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ObjectSetCurrentEvent){
            ObjectSetCurrentEvent ev = (ObjectSetCurrentEvent) arg;
            browser.loadURL("http://localhost:8002/threejs/editor/index.html");
            //JSValue window = browser.executeJavaScriptAndReturnValue("window");
            //window.asObject().setProperty("myObject", ViewDB.getInstance().getCurrentJson());
            OpenSimDB.getInstance().deleteObserver(this);
        }
     }
}
