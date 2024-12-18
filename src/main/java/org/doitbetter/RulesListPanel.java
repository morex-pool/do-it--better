package org.doitbetter;


import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.intellij.ui.table.JBTable;

public class RulesListPanel extends JPanel {
    private JBTable rulesTable;
    private DefaultTableModel tableModel;
    private List<Rule> rules;
    private JButton addButton; // Declare as a class-level variable
    private JButton disableButton; // Declare as a class-level variable

    public RulesListPanel() {
        setLayout(new BorderLayout());

        // Initialize table model
        String[] columnNames = {"Name", "Description", "Active", "Priority", "Context"};
        tableModel = new DefaultTableModel(columnNames, 0);
        rulesTable = new JBTable(tableModel);

        // Load existing rules
        loadRules();

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(rulesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Add buttons panel
        JPanel buttonsPanel = new JPanel();
        addButton = new JButton("Add Rule");
        disableButton = new JButton("Disable Rule");

        addButton.addActionListener(e -> addNewRule());
        disableButton.addActionListener(e -> disableSelectedRule());

        buttonsPanel.add(addButton);
        buttonsPanel.add(disableButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void loadRules() {
        rules = RulesManager.getInstance().getRules();
        tableModel.setRowCount(0);
        for (Rule rule : rules) {
            tableModel.addRow(new Object[]{
                    rule.getName(),
                    rule.getDescription(),
                    rule.isActive(),
                    rule.getPriority(),
                    rule.getContext()
            });
        }
    }

    private void addNewRule() {
        RuleEditDialog dialog = new RuleEditDialog(null);
        if (dialog.showAndGet()) {
            Rule newRule = dialog.getRule();
            RulesManager.getInstance().addRule(newRule);
            loadRules();
        }
    }

    private void disableSelectedRule() {
        int selectedRow = rulesTable.getSelectedRow();
        if (selectedRow >= 0) {
            Rule selectedRule = rules.get(selectedRow);
            selectedRule.setActive(false);
            RulesManager.getInstance().updateRule(selectedRule);
            loadRules();
        }
    }

    public boolean isModified() {
        // Implement modification check logic
        return false;
    }

    public void apply() {
        // Save changes
    }

    public void reset() {
        loadRules();
    }

    public void dispose() {
        for (ActionListener listener : addButton.getActionListeners()) {
            addButton.removeActionListener(listener);
        }
        for (ActionListener listener : disableButton.getActionListeners()) {
            disableButton.removeActionListener(listener);
        }
    }
}
