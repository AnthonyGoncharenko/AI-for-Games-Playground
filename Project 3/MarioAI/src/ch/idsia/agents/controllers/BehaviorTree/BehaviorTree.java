package ch.idsia.agents.controllers.BehaviorTree;

import ch.idsia.agents.controllers.BehaviorTree.Actions.*;
import ch.idsia.agents.controllers.BehaviorTree.Composites.Selector;
import ch.idsia.agents.controllers.BehaviorTree.Composites.Sequence;
import ch.idsia.agents.controllers.BehaviorTree.Conditions.*;

import java.util.Arrays;

public class BehaviorTree {
    protected Task BehaviorTree;

    public BehaviorTree() {
        Selector danger = new Selector(Arrays.asList(new IsObstacleAhead(), new IsHoleAhead(), new IsEnemyAhead()));
        Selector jumpBranch = new Selector(Arrays.asList(danger, new IsCoinAbove(), new IsMysteryBoxAbove()));
        Sequence jump = new Sequence(Arrays.asList(jumpBranch, new Jump()));
        Sequence carrying = new Sequence(Arrays.asList(new MoveRight(), new Shoot()));
        Sequence moveRightAndJump = new Sequence(Arrays.asList(new MoveRight(), jump));
        Sequence inAir = new Sequence(Arrays.asList(new InAir(), new MoveDown()));
        Sequence enemyKiller = new Sequence(Arrays.asList(new IsEnemyAhead(), new MoveLeft(), new Shoot(), new MoveRight()));

        this.BehaviorTree = new Sequence(Arrays.asList(moveRightAndJump, enemyKiller, carrying, inAir));
    }

    public Task getBehaviorTree() {
        return BehaviorTree;
    }
}
