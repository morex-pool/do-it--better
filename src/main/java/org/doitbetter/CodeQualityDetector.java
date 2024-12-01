package org.doitbetter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import groovy.util.logging.Log4j;

@Log4j
public class CodeQualityDetector {

	public void start(Project project, Map<String, String> requirements) {

		DumbService.getInstance(project).runWhenSmart(() -> {

			PsiManager psiManager = PsiManager.getInstance(project);
			GlobalSearchScope projectScope = GlobalSearchScope.projectScope(project);
			ToolWindow toolWindow = getToolWindow("Analyze Code Quality", project);

			for (Map.Entry<String, String> requirement : requirements.entrySet()) {

				Collection<VirtualFile> files = FilenameIndex.getAllFilesByExt(project, requirement.getValue(),
						projectScope);

				Map<PsiMethod, String> issues = new HashMap<>();
				for (VirtualFile file : files) {
					if (file.getName().endsWith(".java")) {
						PsiJavaFile javaFile = (PsiJavaFile) psiManager.findFile(file);
						if (javaFile != null) {
							for (PsiClass psiClass : javaFile.getClasses()) {
								String className = psiClass.getQualifiedName();
								for (PsiMethod method : psiClass.getMethods()) {
									String methodName = method.getName();
									if (method.hasModifierProperty(PsiModifier.PRIVATE)) {
										Collection<PsiReference> references = ReferencesSearch.search(method,
												GlobalSearchScope.projectScope(project)).findAll();
										if (references == null || references.size() == 0 || references.isEmpty()) {
											issues.put(method, className);
										}
									}
								}
							}
						}
					}
				}
				displayUnusedMethodWarning(toolWindow, requirement.getKey(), issues);
			}
		});
	}

	private void displayUnusedMethodWarning(ToolWindow toolWindow, String panelTitle, Map<PsiMethod, String> issues) {
		ApplicationManager.getApplication().invokeLater(() -> {
			ContentManager contentManager = toolWindow.getContentManager();
			ContentFactory contentFactory = ContentFactory.getInstance();
			Content content = contentFactory.createContent(new IssuePanel(issues), panelTitle, false);
			contentManager.addContent(content);
			toolWindow.show();
		});
	}

	private ToolWindow getToolWindow(String toolWindowName, Project project) {
		ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
		ToolWindow toolWindow = toolWindowManager.getToolWindow(toolWindowName);
		if (toolWindow == null) {
			toolWindow = toolWindowManager.registerToolWindow(toolWindowName, true, ToolWindowAnchor.BOTTOM);
		}
		return toolWindow;
	}
}
