package org.doitbetter;

import com.google.gson.annotations.SerializedName;

public class Rule {

    private String name;
    private String description;
    private boolean active;
    @SerializedName("works_over")
    private WorksOver worksOver;
    private int priority;
    @SerializedName("priority-meaning")
    private String priorityMeaning;
    private String context;
    private String url;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public WorksOver getWorksOver() {
        return worksOver;
    }

    public void setWorksOver(WorksOver worksOver) {
        this.worksOver = worksOver;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPriorityMeaning() {
        return priorityMeaning;
    }

    public void setPriorityMeaning(String priorityMeaning) {
        this.priorityMeaning = priorityMeaning;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class WorksOver {
        private boolean method;
        private boolean clazz;

        // Getters and setters
        public boolean isMethod() { return method; }
        public void setMethod(boolean method) { this.method = method; }

        public boolean isClazz() { return clazz; }
        public void setClazz(boolean clazz) { this.clazz = clazz; }
    }
}
