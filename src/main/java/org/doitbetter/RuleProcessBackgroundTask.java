package org.doitbetter;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

public abstract class RuleProcessBackgroundTask extends Task.Backgroundable {
    public RuleProcessBackgroundTask(Project project, String title) {
        super(project, title, true);
    }

    @Override
    public void run(ProgressIndicator indicator) {
        try {
            indicator.setText("Processing rules...");
            indicator.setFraction(0.0);

            // Start processing
            doProcess(indicator);

        } catch (Exception e) {
            ProjectContextHolder.showErrorNotification("Processing failed: " + e.getMessage());
        }
    }

    protected abstract void doProcess(ProgressIndicator indicator) throws Exception;
}
