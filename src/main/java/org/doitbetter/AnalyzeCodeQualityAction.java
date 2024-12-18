package org.doitbetter;

import java.util.HashMap;
import java.util.Map;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class AnalyzeCodeQualityAction extends AnAction {
	@Override
	public void actionPerformed(AnActionEvent e) {

		Project project = e.getProject();
		ProjectContextHolder.setCurrentProject(project);

		Map<String, String> requirements = new HashMap<>();
		requirements.put("Unused Private Method", "java");
		if (project != null) {
			(new CodeQualityDetector()).start(project, requirements);
		} else {
			System.err.println("No project found. Ensure you are running this action within a project context.");
		}

	}
}
