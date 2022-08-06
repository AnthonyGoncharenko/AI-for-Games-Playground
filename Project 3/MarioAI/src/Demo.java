import de.novatec.marioai.tools.*;

public class Demo extends MarioAiAgent {

    @Override
    public String getName() {
        return "Demo Agent";
    }

    @Override
    public MarioInput doAiLogic() {
        moveRight();
        return getMarioInput();
    }

    public static void main(String[] args) {
        MarioAiRunner runner = new MarioAiRunnerBuilder()
                .addAgent(new Demo())
                .setLevelConfig(LevelConfig.LEVEL_1)
                .construct();

        runner.run();
    }
}

