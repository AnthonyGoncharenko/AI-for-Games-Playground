import agents.controllers.behavior.Actions.*;
import agents.controllers.behavior.Composites.Selector;
import agents.controllers.behavior.Composites.Sequence;
import agents.controllers.behavior.Conditions.*;

import java.util.Arrays;

public class BehaviorTree {
    protected Task BehaviorTree;

    public BehaviorTree() {
        Selector danger = new Selector(Arrays.asList(new IsObstacleAhead(), new IsHoleAhead(), new IsEnemyAhead()));
        Selector jumpBranch = new Selector(Arrays.asList(danger, new IsCoinAbove(), new IsMysteryBoxAbove()));
        Sequence jump = new Sequence(Arrays.asList(jumpBranch, new Jump()));
        Sequence moveRightAndJump = new Sequence(Arrays.asList(new Sprint(), new MoveRight(), jump));
        Sequence enemyKiller = new Sequence(Arrays.asList(new IsEnemyAhead(), new Shoot()));
        this.BehaviorTree = new Sequence(Arrays.asList(moveRightAndJump, enemyKiller));
    }

    public Task getBehaviorTree() {
        return BehaviorTree;
    }
}
