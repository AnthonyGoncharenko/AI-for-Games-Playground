package agents.controllers.behavior.Actions;

import de.novatec.marioai.tools.MarioAiAgent;

public class Jump implements Task {
    @Override
    public State run(MarioAiAgent b) {
        b.jump();
        return State.SUCCESS;
    }
}
