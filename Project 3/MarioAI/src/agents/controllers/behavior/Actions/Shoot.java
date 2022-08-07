package agents.controllers.behavior.Actions;

import de.novatec.marioai.tools.MarioAiAgent;

public class Shoot implements Task {
    @Override
    public State run(MarioAiAgent b) {

        if (b.mayShoot()) {
            b.shoot();
            return State.SUCCESS;
        }
        return State.FAILED;
    }
}
