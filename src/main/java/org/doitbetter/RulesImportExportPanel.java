package org.doitbetter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;

public class RulesImportExportPanel extends JPanel {
    private JTextField importedFilePathField;
    private RulesListPanel rulesListPanel;
    private boolean modified = false;
    private JButton importButton; // Declare as a class-level variable
    private JButton exportButton; // Declare as a class-level variable

    public RulesImportExportPanel(RulesListPanel rulesListPanel) {
        this.rulesListPanel = rulesListPanel;
        setLayout(new BorderLayout());

        // Import section
        JPanel importPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        importedFilePathField = new JTextField(30);
        importedFilePathField.setEditable(false);

        importButton = new JButton("Import JSON");
        importButton.addActionListener(e -> importJsonFile());

        importPanel.add(new JLabel("Imported File:"));
        importPanel.add(importedFilePathField);
        importPanel.add(importButton);

        // Export section
        exportButton = new JButton("Export JSON");
        exportButton.addActionListener(e -> exportJsonFile());

        JPanel exportPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exportPanel.add(exportButton);

        // Add panels to main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(importPanel);
        mainPanel.add(exportPanel);

        add(mainPanel, BorderLayout.NORTH);
    }

    private void importJsonFile() {
        FileChooserDescriptor descriptor = new FileChooserDescriptor(
                true, false, false, false, false, false
        )
                .withFileFilter(file -> file.getName().endsWith(".json"));

        VirtualFile selectedFile = FileChooser.chooseFile(descriptor, null, null);

        if (selectedFile != null) {
            try {
                File file = new File(selectedFile.getPath());
                RulesManager.getInstance().importRulesFromJson(file);

                importedFilePathField.setText(file.getAbsolutePath());
                rulesListPanel.reset(); // Refresh rules list
                modified = true;
            } catch (Exception e) {
                ProjectContextHolder.showErrorNotification(
                        "Failed to import JSON: " + e.getMessage()
                );
            }
        }
    }

    private void exportJsonFile() {
        FileChooserDescriptor descriptor = new FileChooserDescriptor(
                false, true, false, false, false, false
        );

        VirtualFile selectedDir = FileChooser.chooseFile(descriptor, null, null);

        if (selectedDir != null) {
            try {
                File file = new File(selectedDir.getPath(), "rules_export.json");
                RulesManager.getInstance().exportRulesToJson(file);

                ProjectContextHolder.showErrorNotification(
                        "Rules exported successfully to: " + file.getAbsolutePath()
                );
            } catch (Exception e) {
                ProjectContextHolder.showErrorNotification(
                        "Failed to export JSON: " + e.getMessage()
                );
            }
        }
    }

    public boolean isModified() {
        return modified;
    }

    public void apply() {
        modified = false;
    }

    public void reset() {
        importedFilePathField.setText("");
        modified = false;
    }

    public void dispose() {
        for (ActionListener listener : importButton.getActionListeners()) {
            importButton.removeActionListener(listener);
        }
        for (ActionListener listener : exportButton.getActionListeners()) {
            exportButton.removeActionListener(listener);
        }
    }
}
