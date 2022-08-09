package ch.idsia.agents.controllers.BehaviorTree.Conditions;

import ch.idsia.agents.controllers.BehaviorTree.Actions.Task;
import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

public class InAir implements Task {
    @Override
    public Task.State run(BehaviorTreeAgent b) {
        return !b.isOnGround() ? State.SUCCESS : State.FAILED;
    }
}
