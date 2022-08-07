import agents.BTMarioAgent;
import agents.controllers.behavior.Actions.Task;
import de.novatec.marioai.tools.LevelConfig;
import de.novatec.marioai.tools.MarioAiAgent;
import de.novatec.marioai.tools.MarioAiRunner;
import de.novatec.marioai.tools.MarioAiRunnerBuilder;

public class Main {

    public static void main(String[] args) {
        MarioAiAgent agent;
        MarioAiRunner runner;
        Task root;

        root = new BehaviorTree().getBehaviorTree();
        agent = new BTMarioAgent(root);
        runner = new MarioAiRunnerBuilder()
                .addAgent(agent)
                .setLevelConfig(LevelConfig.LEVEL_5)
                .construct();

        runner.run();

    }
}
