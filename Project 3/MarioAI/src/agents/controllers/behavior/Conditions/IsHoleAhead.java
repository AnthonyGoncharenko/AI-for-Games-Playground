package agents.controllers.behavior.Conditions;

import agents.controllers.behavior.Actions.Task;
import de.novatec.marioai.tools.MarioAiAgent;

public class IsHoleAhead implements Task {
    @Override
    public State run(MarioAiAgent b) {
        return (b.isHoleAhead()) ? State.SUCCESS : State.FAILED;
    }
}
