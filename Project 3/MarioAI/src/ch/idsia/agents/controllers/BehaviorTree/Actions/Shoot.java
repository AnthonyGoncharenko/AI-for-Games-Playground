package ch.idsia.agents.controllers.BehaviorTree.Actions;

import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

import static ch.idsia.benchmark.mario.environments.Environment.MARIO_KEY_SPEED;

public class Shoot implements Task {
    @Override
    public State run(BehaviorTreeAgent b) {

        if (b.canShoot()) {
            b.setMarioInput(MARIO_KEY_SPEED, true);
            b.trueSpeedCounter++;
            return State.SUCCESS;
        }
        return State.FAILED;
    }
}
