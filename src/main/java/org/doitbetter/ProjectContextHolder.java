package org.doitbetter;

import com.intellij.openapi.project.Project;

public class ProjectContextHolder {
    private static Project currentProject;

    public static void setCurrentProject(Project project) {
        currentProject = project;
    }

    public static Project getCurrentProject() {
        return currentProject;
    }

    public static void showErrorNotification(String content) {
        if (currentProject != null) {
            NotificationUtil.showErrorNotification(currentProject, content);
        }
    }

    public static void showWarningNotification(String content) {
        if (currentProject != null) {
            NotificationUtil.showWarningNotification(currentProject, content);
        }
    }
}
