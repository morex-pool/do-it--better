package org.doitbetter;

import java.util.HashMap;
import java.util.Map;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class AnalyzeCodeQualityAction extends AnAction {
	public void actionPerformed(AnActionEvent e) {
		Project project = e.getProject();
		if (project == null) {
			return;
		}
		ProjectContextHolder.setCurrentProject(project);
		Map<String, String> requirements = new HashMap<>();
		requirements.put("Unused Private Method", "java");
		ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Do-It-Better");
		if (toolWindow != null) {
			toolWindow.show(null);
		} else {
			NotificationUtil.showWarningNotification(project, "Do-It-Better tool window not found");
		}
		new CodeQualityDetector().start(project, requirements);
	}
}
