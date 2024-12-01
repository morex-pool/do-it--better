package org.doitbetter;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiMethod;

public class IssuePanel extends JPanel {

	private JPanel explanationPanel; // Panel to hold the explanation
	private JSplitPane splitPane;

	public IssuePanel(Map<PsiMethod, String> issues) {
		setLayout(new BorderLayout());

		// Left panel for issues list
		JPanel issuesListPanel = new JPanel();
		issuesListPanel.setLayout(new BoxLayout(issuesListPanel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(issuesListPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		explanationPanel = new JPanel();
		explanationPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		explanationPanel.setLayout(new BorderLayout());
		explanationPanel.setVisible(false);

		// Create JSplitPane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, explanationPanel);
		splitPane.setOneTouchExpandable(true); // Add a one-touch button to expand/collapse the divider

		// Set the divider location to 50% of the total width
		splitPane.setResizeWeight(0.5);
		splitPane.setDividerLocation(0.5);

		add(splitPane, BorderLayout.CENTER);

		if (issues.isEmpty()) {
			return;
		}

		for (Map.Entry<PsiMethod, String> issue : issues.entrySet()) {
			String className = issue.getValue();
			PsiMethod method = issue.getKey();
			String linkText = String.format("%s.%s", className, method.getName());

			VirtualFile virtualFile = method.getContainingFile().getVirtualFile();
			if (virtualFile == null) {
				issuesListPanel.add(new JLabel("Cannot locate the file of " + linkText));
				continue;
			}

			// Create a clickable link to navigate to issue
			JPanel issueItemPanel = new JPanel(new BorderLayout());
			issueItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			issueItemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					try {
						// Clear any previous content in the explanation panel
						explanationPanel.removeAll();

						// Add the explanation for this issue
						JTextArea explanationText = new JTextArea(getIssueExplanation(method));
						explanationText.setEditable(false);
						explanationText.setWrapStyleWord(true);
						explanationText.setLineWrap(true);
						explanationPanel.add(new JScrollPane(explanationText), BorderLayout.CENTER);
						explanationPanel.setVisible(true);

						// Set the divider location to 1000 pixels from the left
						splitPane.setDividerLocation(splitPane.getWidth() - 1000);

						// Navigate to the method
						OpenFileDescriptor descriptor = new OpenFileDescriptor(method.getProject(), virtualFile,
								method.getTextOffset());
						descriptor.navigate(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});

			// Add the link
			JLabel linkLabel = new JLabel("<html><u>" + linkText + "</u></html>");
			issueItemPanel.add(linkLabel, BorderLayout.WEST);

			issuesListPanel.add(issueItemPanel);
			issuesListPanel.add(Box.createVerticalStrut(5));
		}

		// Initially set the explanation panel to not be visible
		explanationPanel.setVisible(false);

		// Set the divider location after the panel is shown to ensure correct sizing
		SwingUtilities.invokeLater(() -> {
			splitPane.setDividerLocation(0.5); // Set to 50% initially
		});
	}

	// Method to get explanation for an issue, replace with actual logic
	private String getIssueExplanation(PsiMethod method) {
		return "This is an example explanation for the issue related to the method " + method.getName() + ".";
	}
}
