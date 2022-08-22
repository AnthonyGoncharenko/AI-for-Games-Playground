package ch.idsia.agents.controllers.BehaviorTree.Conditions;

import ch.idsia.agents.controllers.BehaviorTree.Actions.Task;
import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

public class IsCarrying implements Task {
    @Override
    public Task.State run(BehaviorTreeAgent b) {
        return b.isCarrying() ? State.SUCCESS : State.FAILED;
    }
}
