package ch.idsia.agents.controllers.BehaviorTree.Conditions;


import ch.idsia.agents.controllers.BehaviorTree.Actions.Task;
import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

public class CanShoot implements Task {
    @Override
    public State run(BehaviorTreeAgent b) {
        return (b.canShoot()) ? State.SUCCESS : State.FAILED;
    }
}
