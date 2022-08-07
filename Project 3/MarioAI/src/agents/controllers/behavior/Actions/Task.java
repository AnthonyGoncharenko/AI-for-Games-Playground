package agents.controllers.behavior.Actions;

import de.novatec.marioai.tools.MarioAiAgent;

public interface Task {
    enum State {SUCCESS, FAILED}

    State run(MarioAiAgent b);
}
