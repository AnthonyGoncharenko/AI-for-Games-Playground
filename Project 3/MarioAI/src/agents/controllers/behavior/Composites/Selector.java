package agents.controllers.behavior.Composites;

import agents.BTMarioAgent;
import agents.controllers.behavior.Actions.Task;
import de.novatec.marioai.tools.MarioAiAgent;

import java.util.List;

public class Selector extends Composite {
    public Selector(List<Task> children) {
        super(children);
    }

    @Override
    public State run(MarioAiAgent b) {
        for (Task t: this.tasks) {
            Task.State state = t.run(b);
            if (state == State.SUCCESS) {
                return state;
            }
        }
        return State.FAILED;
    }
}