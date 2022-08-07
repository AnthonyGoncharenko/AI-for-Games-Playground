package agents.controllers.behavior.Composites;

import agents.BTMarioAgent;
import agents.controllers.behavior.Actions.Task;
import de.novatec.marioai.tools.MarioAiAgent;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Task {
    protected List<Task> tasks;

    public Composite(List<Task> children) {
        this.tasks = (children == null) ? new ArrayList<>() : children;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public State run(MarioAiAgent b) {
        return null;
    }

    public void addTask(Task t) {
        this.tasks.add(t);
    }

}
