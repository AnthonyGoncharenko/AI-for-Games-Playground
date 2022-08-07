package agents;

import agents.controllers.behavior.Actions.Task;
import de.novatec.marioai.tools.MarioAiAgent;
import de.novatec.marioai.tools.MarioInput;

public class BTMarioAgent extends MarioAiAgent {

    Task root;

    public BTMarioAgent(Task root) {
        this.root = root;
    }

    @Override
    public String getName() {
        return "BTMarioAgent";
    }

    @Override
    public MarioInput doAiLogic() {
        root.run(this);
        return getMarioInput();
    }

}