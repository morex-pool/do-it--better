package org.doitbetter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;

@State(
        name = "RulesManagerSettings",
        storages = @Storage("rulesmanager.xml")
)
public class RulesManager implements PersistentStateComponent<RulesManager.State> {
    private State myState = new State();

    public static RulesManager getInstance() {
        return ApplicationManager.getApplication().getService(RulesManager.class);
    }

    public List<Rule> getRules() {
        return myState.rules;
    }

    public void addRule(Rule rule) {
        myState.rules.add(rule);
    }

    public void updateRule(Rule rule) {
        // Find and update existing rule
        for (int i = 0; i < myState.rules.size(); i++) {
            if (myState.rules.get(i).getName().equals(rule.getName())) {
                myState.rules.set(i, rule);
                break;
            }
        }
    }

    public void importRulesFromJson(File jsonFile) {
        try (Reader reader = new FileReader(jsonFile)) {
            Gson gson = new GsonBuilder().create();
            State importedState = gson.fromJson(reader, State.class);
            myState.rules = importedState.rules;
        } catch (Exception ex) {
            ex.printStackTrace();
            ProjectContextHolder.showErrorNotification("Error: " + ex.getMessage());
        }
    }

    public void exportRulesToJson(File jsonFile) {
        try (Writer writer = new FileWriter(jsonFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(myState, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
            ProjectContextHolder.showErrorNotification("Error: " + ex.getMessage());
        }
    }

    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(State state) {
        myState = state;
    }

    public static class State {
        public List<Rule> rules = new ArrayList<>();
    }
}
