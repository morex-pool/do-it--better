package org.doitbetter;


import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.TabbedPaneWrapper;

public class RulesConfigurable implements Configurable, Disposable {
    private JPanel mainPanel;
    private TabbedPaneWrapper tabbedPane;
    private RulesListPanel rulesListPanel;
    private RulesImportExportPanel importExportPanel;

    @Override
    public String getDisplayName() {
        return "Rules Manager";
    }

    /*
    Add to dispose() if:
        You have event listeners: Unregister any listeners added during the lifecycle.
        You use threads or executors: Shut them down to avoid memory leaks.
        You work with disposable IntelliJ components: Ensure their dispose() method is called.
     */
    @Override
    public void dispose() {
        // Additional cleanup, if required
        if (rulesListPanel != null) {
            rulesListPanel.dispose();
        }
        if (importExportPanel != null) {
            importExportPanel.dispose();
        }
    }

    @Override
    public JComponent createComponent() {
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new TabbedPaneWrapper(this); // `this` is a Disposable

        // First Tab: Rules List
        rulesListPanel = new RulesListPanel();
        tabbedPane.addTab("Rules List", rulesListPanel);

        // Second Tab: Import/Export
        importExportPanel = new RulesImportExportPanel(rulesListPanel);
        tabbedPane.addTab("Import/Export", importExportPanel);

        mainPanel.add(tabbedPane.getComponent(), BorderLayout.CENTER);
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        return rulesListPanel.isModified() || importExportPanel.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        rulesListPanel.apply();
        importExportPanel.apply();
    }

    @Override
    public void reset() {
        rulesListPanel.reset();
        importExportPanel.reset();
    }
}
