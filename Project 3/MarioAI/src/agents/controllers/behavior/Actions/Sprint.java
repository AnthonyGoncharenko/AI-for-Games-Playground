package agents.controllers.behavior.Actions;

import de.novatec.marioai.tools.MarioAiAgent;

public class Sprint implements Task{
    @Override
    public State run(MarioAiAgent b) {
        b.sprint();
        return State.SUCCESS;
    }
}
