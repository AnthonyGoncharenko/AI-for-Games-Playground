package ch.idsia.agents.controllers.BehaviorTree.Composites;

import ch.idsia.agents.controllers.BehaviorTree.Actions.Task;
import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

import java.util.List;

public class Selector extends Composite {
    public Selector(List<Task> children) {
        super(children);
    }

    @Override
    public State run(BehaviorTreeAgent b) {
        for (Task t: this.tasks) {
            Task.State state = t.run(b);
            if (state == State.SUCCESS) {
                return state;
            }
        }
        return State.FAILED;
    }
}