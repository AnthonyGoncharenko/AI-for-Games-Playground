package agents.controllers.behavior.Conditions;

import agents.controllers.behavior.Actions.Task;
import de.novatec.marioai.tools.MarioAiAgent;

public class IsCoinAbove implements Task {
    @Override
    public State run(MarioAiAgent b) {
        return (b.isEnemyAhead()) ? State.SUCCESS : State.FAILED;
    }
}
