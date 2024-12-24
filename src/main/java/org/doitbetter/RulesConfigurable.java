package org.doitbetter;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.TabbedPaneWrapper;

public class RulesConfigurable implements Configurable, Disposable {
    private JPanel mainPanel;
    private TabbedPaneWrapper tabbedPane;
    private RulesListPanel rulesListPanel;
    private RulesImportExportPanel importExportPanel;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Do It Better";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (rulesListPanel == null) {
            rulesListPanel = new RulesListPanel();
        }
        return rulesListPanel;
    }

    @Override
    public boolean isModified() {
        return rulesListPanel != null && rulesListPanel.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        if (rulesListPanel != null) {
            rulesListPanel.apply();
        }
    }

    @Override
    public void reset() {
        if (rulesListPanel != null) {
            rulesListPanel.reset();
        }
    }

    @Override
    public void disposeUIResources() {
        rulesListPanel = null;
    }

    @Override
    public void dispose() {
        disposeUIResources();
    }

}
