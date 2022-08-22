package ch.idsia.agents.controllers.BehaviorTree.Composites;

import ch.idsia.agents.controllers.BehaviorTree.Actions.Task;
import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

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
    public State run(BehaviorTreeAgent b) {
        return null;
    }

    public void addTask(Task t) {
        this.tasks.add(t);
    }

}
