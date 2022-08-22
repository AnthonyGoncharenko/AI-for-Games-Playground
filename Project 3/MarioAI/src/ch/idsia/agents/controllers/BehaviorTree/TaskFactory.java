package ch.idsia.agents.controllers.BehaviorTree;

import ch.idsia.agents.controllers.BehaviorTree.Actions.*;
import ch.idsia.agents.controllers.BehaviorTree.Composites.Selector;
import ch.idsia.agents.controllers.BehaviorTree.Composites.Sequence;
import ch.idsia.agents.controllers.BehaviorTree.Conditions.*;

public class TaskFactory {
    public Task getTask(String taskName){
        Task res;
        switch (taskName){
//            Composites
            case "Sequence": res = new Sequence(null); break;
            case "Selector": res = new Selector(null); break;
//            Actions
            case "Jump": res = new Jump(); break;
            case "MoveDown": res = new MoveDown(); break;
            case "MoveLeft": res = new MoveLeft(); break;
            case "MoveRight": res = new MoveRight(); break;
            case "Shoot": res = new Shoot(); break;
            case "Sprint": res = new Sprint(); break;
            case "Walk" : res = new Walk(); break;

//            Conditions
            case "CanShoot" : res = new CanShoot(); break;
            case "InAir": res = new InAir(); break;
            case "IsCarrying": res = new IsCarrying(); break;
            case "IsCoinAbove": res = new IsCoinAbove(); break;
            case "IsEnemyAhead": res = new IsEnemyAhead(); break;
            case "IsHoleAhead" : res = new IsHoleAhead(); break;
            case "IsMysteryBoxAbove" : res = new IsMysteryBoxAbove(); break;
            case "IsObstacleAhead" : res = new IsObstacleAhead(); break;
            default: throw new IllegalArgumentException(taskName + " is not a proper task class");
        }
        return res;

    }
}
