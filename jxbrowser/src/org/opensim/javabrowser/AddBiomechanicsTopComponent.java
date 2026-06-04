/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/templateTopComponent637.java to edit this template
 */
package org.opensim.javabrowser;

import org.opensim.addBiomech.AddBiomechanicsHandleDownloadJPanel;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.SavePasswordCallback;
import com.teamdev.jxbrowser.browser.callback.StartDownloadCallback;
import com.teamdev.jxbrowser.download.event.DownloadFinished;

import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.*;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.opensim.addBiomech.AddBiomechPrefs;
import org.opensim.view.pub.OpenSimDB;
import org.opensim.view.pub.ViewDB;
import org.opensim.logger.OpenSimLogger;
import org.opensim.utils.TheApp;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.opensim.javabrowser//AddBiomechanics//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "AddBiomechanicsTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED
)
@TopComponent.Registration(
        mode = "editor", 
        openAtStartup = false
)
@ActionID(
        category = "Window", 
        id = "org.opensim.javabrowser.AddBiomechanicsTopComponent"
)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AddBiomechanicsAction",
        preferredID = "AddBiomechanicsTopComponent"
)
@ActionReference(
        path = "Menu/Window",
        position = 333
)
@Messages({
    "CTL_AddBiomechanicsAction=AddBiomechanics",
    "CTL_AddBiomechanicsTopComponent=AddBiomechanics Window",
    "HINT_AddBiomechanicsTopComponent=This is a AddBiomechanics window"
})
public final class AddBiomechanicsTopComponent extends TopComponent {
    Browser browser; 
    public AddBiomechanicsTopComponent() {
        initComponents();
        Engine engine = jxBrowserTopComponent.createJxBrowserEngine();
        browser = engine.newBrowser();
        // Begin hack
//            String hackPath="C:\\Users\\ayman\\Downloads\\Subject01 (Processed and Reviewed).zip"; 
//            AddBiomechanicsHandleDownloadJPanel handlePanel = new AddBiomechanicsHandleDownloadJPanel(hackPath); //fullPath.toString()
//            DialogDescriptor dlg = new DialogDescriptor(handlePanel,"How to handle download", false, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if (e.getSource() == NotifyDescriptor.OK_OPTION){
//                        handlePanel.executeUserAction();
//                    }
//                }
//            });
//            DialogDisplayer.getDefault().createDialog(dlg).setVisible(true);

        // End hack
        browser.set(StartDownloadCallback.class, (params, tell) -> {
            params.download().on(DownloadFinished.class, event ->
                    System.out.println("File downloaded!"));
            Path downloadPath = Paths.get(AddBiomechPrefs.getDownloadsDir());
            Path fullPath = downloadPath.resolve(params.download().target().suggestedFileName());
            tell.download(fullPath);
            OpenSimLogger.logMessage("Downloading finished, file:"+fullPath, 0);
            String hackHackPath="C:\\Users\\ayman\\Downloads\\Subject01 (Processed and Reviewed).zip"; 
            AddBiomechanicsHandleDownloadJPanel handlePanel = new AddBiomechanicsHandleDownloadJPanel(hackHackPath); //fullPath.toString()
            DialogDescriptor dlg = new DialogDescriptor(handlePanel,"How to handle download", false, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == NotifyDescriptor.OK_OPTION){
                        handlePanel.executeUserAction();
                    }
                }
            });
            DialogDisplayer.getDefault().createDialog(dlg).setVisible(true);
        });

        // This clears the cache in the <user-dir>/EmbeddedBrowserCache/Cache
        // folder (asynchronously). Doing so is necessary to ensure that Models
        // reliably show up in the visualizer. It's important to not delete
        // the entire EmbeddedBrowserCache folder, so as to retain user
        // settings for the floor, etc.
        BrowserView view = BrowserView.newInstance(browser);

        jPanel1.add(view);
        browser.set(SavePasswordCallback.class, (params, tell) -> tell.ignore());
        browser.navigation().loadUrl("addbiomechanics.org/login");
        setName(Bundle.CTL_AddBiomechanicsTopComponent());
        setToolTipText(Bundle.HINT_AddBiomechanicsTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AddBiomechanicsTopComponent.class, "AddBiomechanicsTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(AddBiomechanicsTopComponent.class, "AddBiomechanicsTopComponent.jButton1.text")); // NOI18N

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(AddBiomechanicsTopComponent.class, "AddBiomechanicsTopComponent.jScrollPane1.border.title"))); // NOI18N

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Subject 1", "Subject 2", "Subject CP", "Subject CP2", "Running 1", "Walking 2" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(AddBiomechanicsTopComponent.class, "AddBiomechanicsTopComponent.jButton2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(AddBiomechanicsTopComponent.class, "AddBiomechanicsTopComponent.jButton3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(AddBiomechanicsTopComponent.class, "AddBiomechanicsTopComponent.jButton4.text")); // NOI18N

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
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
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }
    
    private static Path createDownloadsDirectory() {
        try {
            return Files.createTempDirectory("Downloads");
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
}
