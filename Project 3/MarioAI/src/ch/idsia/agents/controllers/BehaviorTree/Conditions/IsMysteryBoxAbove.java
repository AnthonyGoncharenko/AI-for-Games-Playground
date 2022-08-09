package ch.idsia.agents.controllers.BehaviorTree.Conditions;

import ch.idsia.agents.controllers.BehaviorTree.Actions.Task;
import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

public class IsMysteryBoxAbove implements Task {
    @Override
    public Task.State run(BehaviorTreeAgent b) {
        return (b.isQuestionBrickAbove()) ? State.SUCCESS : State.FAILED;
    }
}
