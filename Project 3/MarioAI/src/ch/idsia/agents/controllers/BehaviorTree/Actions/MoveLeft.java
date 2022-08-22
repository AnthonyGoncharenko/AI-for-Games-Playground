package ch.idsia.agents.controllers.BehaviorTree.Actions;

import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

import static ch.idsia.benchmark.mario.environments.Environment.MARIO_KEY_LEFT;

public class MoveLeft implements Task{
    @Override
    public State run(BehaviorTreeAgent b) {
        b.setMarioInput(MARIO_KEY_LEFT, true);
        return State.SUCCESS;
    }
}
