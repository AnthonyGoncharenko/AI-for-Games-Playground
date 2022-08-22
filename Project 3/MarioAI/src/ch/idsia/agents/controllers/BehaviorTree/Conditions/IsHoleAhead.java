package ch.idsia.agents.controllers.BehaviorTree.Conditions;

import ch.idsia.agents.controllers.BehaviorTree.Actions.Task;
import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

public class IsHoleAhead implements Task {
    @Override
    public Task.State run(BehaviorTreeAgent b) {
        return (b.isHoleAhead()) ? State.SUCCESS : State.FAILED;
    }
}
