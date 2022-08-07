package agents.controllers.behavior.Composites;

import agents.BTMarioAgent;
import agents.controllers.behavior.Actions.Task;
import de.novatec.marioai.tools.MarioAiAgent;

import java.util.List;

public class Sequence extends Composite {
    public Sequence(List<Task> children) {
        super(children);
    }

    @Override
    public State run(MarioAiAgent b) {
        for (Task t: this.tasks) {
            Task.State state = t.run(b);
            if (state == State.FAILED) {
                return state;
            }
        }
        return State.SUCCESS;
    }
}
