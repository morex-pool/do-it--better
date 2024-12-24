package org.doitbetter;

import java.util.HashMap;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

public class DoItBetterToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        IssuePanel issuePanel = new IssuePanel(new HashMap<>()); // Initialize with empty issues
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(issuePanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
