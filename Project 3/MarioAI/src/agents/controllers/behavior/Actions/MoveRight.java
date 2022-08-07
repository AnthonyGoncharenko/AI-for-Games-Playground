package agents.controllers.behavior.Actions;

import de.novatec.marioai.tools.MarioAiAgent;

public class MoveRight implements Task {
    @Override
    public State run(MarioAiAgent b) {
        b.moveRight();
        return State.SUCCESS;
    }
}
