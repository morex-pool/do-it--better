package org.doitbetter;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

public class NotificationUtil {
    public static void showErrorNotification(Project project, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("RulesManagerNotificationGroup")
                .createNotification(content, NotificationType.ERROR)
                .notify(project);
    }

    public static void showWarningNotification(Project project, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("RulesManagerNotificationGroup")
                .createNotification(content, NotificationType.WARNING)
                .notify(project);
    }
}
