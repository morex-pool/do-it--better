package org.doitbetter;

import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.*;
import java.awt.*;

public class RuleEditDialog extends DialogWrapper {
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JCheckBox activeCheckBox;
    private JCheckBox methodCheckBox;
    private JCheckBox classCheckBox;
    private JSpinner prioritySpinner;
    private JTextField contextField;
    private JTextField urlField;

    private Rule rule;

    public RuleEditDialog(Rule existingRule) {
        super(true);
        setTitle(existingRule == null ? "Add New Rule" : "Edit Rule");
        this.rule = existingRule != null ? existingRule : new Rule();

        init();
        initFields();
    }

    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField(rule.getName());
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea(rule.getDescription());
        panel.add(new JScrollPane(descriptionArea));

        panel.add(new JLabel("Active:"));
        activeCheckBox = new JCheckBox("", rule.isActive());
        panel.add(activeCheckBox);

        panel.add(new JLabel("Works Over:"));
        JPanel worksOverPanel = new JPanel();
        methodCheckBox = new JCheckBox("Method", rule.getWorksOver().isMethod());
        classCheckBox = new JCheckBox("Class", rule.getWorksOver().isClazz());
        worksOverPanel.add(methodCheckBox);
        worksOverPanel.add(classCheckBox);
        panel.add(worksOverPanel);

        panel.add(new JLabel("Priority:"));
        prioritySpinner = new JSpinner(new SpinnerNumberModel(rule.getPriority(), 1, 10, 1));
        panel.add(prioritySpinner);

        panel.add(new JLabel("Context:"));
        contextField = new JTextField(rule.getContext());
        panel.add(contextField);

        panel.add(new JLabel("URL:"));
        urlField = new JTextField(rule.getUrl());
        panel.add(urlField);

        return panel;
    }

    private void initFields() {
        if (rule != null) {
            nameField.setText(rule.getName());
            descriptionArea.setText(rule.getDescription());
            activeCheckBox.setSelected(rule.isActive());
            methodCheckBox.setSelected(rule.getWorksOver().isMethod());
            classCheckBox.setSelected(rule.getWorksOver().isClazz());
            prioritySpinner.setValue(rule.getPriority());
            contextField.setText(rule.getContext());
            urlField.setText(rule.getUrl());
        }
    }

    public Rule getRule() {
        rule.setName(nameField.getText());
        rule.setDescription(descriptionArea.getText());
        rule.setActive(activeCheckBox.isSelected());

        Rule.WorksOver worksOver = new Rule.WorksOver();
        worksOver.setMethod(methodCheckBox.isSelected());
        worksOver.setClazz(classCheckBox.isSelected());
        rule.setWorksOver(worksOver);

        rule.setPriority((Integer) prioritySpinner.getValue());
        rule.setContext(contextField.getText());
        rule.setUrl(urlField.getText());

        return rule;
    }
}
