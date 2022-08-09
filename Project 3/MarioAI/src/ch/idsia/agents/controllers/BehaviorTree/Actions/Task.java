package ch.idsia.agents.controllers.BehaviorTree.Actions;


import ch.idsia.agents.controllers.BehaviorTree.BehaviorTreeAgent;

public interface Task {
    enum State {SUCCESS, FAILED}

    State run(BehaviorTreeAgent b);
}
