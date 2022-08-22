package ch.idsia.agents.controllers.BehaviorTree.Actions;

import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

import static ch.idsia.benchmark.mario.environments.Environment.MARIO_KEY_SPEED;

public class Walk implements Task{
    @Override
    public State run(BehaviorTreeAgent b) {
        if (b.getMarioInput(MARIO_KEY_SPEED)){
            b.setMarioInput(MARIO_KEY_SPEED, false);
        }
        return State.SUCCESS;
    }
}
