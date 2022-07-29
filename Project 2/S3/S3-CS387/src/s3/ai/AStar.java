package s3.ai;


import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

record Node(double x, double y, double g, double h, Optional<Node> parent) {
    public Double getNodeWeight() {
        return g() + h();
    }

    public void print() {
        System.out.println("Node(" + x() + ", " + y() + ", " + g() + ", " + h() + ")");
    }
}

public class AStar {

    private final double x;
    private final double y;
    private final double goal_x;
    private final double goal_y;
    private final S3 game;
    private final Pair<Double, Double> LEFT = new Pair<>(-1.0, 0.0);
    private final Pair<Double, Double> RIGHT = new Pair<>(1.0, 0.0);
    private final Pair<Double, Double> DOWN = new Pair<>(0.0, -1.0);
    private final Pair<Double, Double> UP = new Pair<>(0.0, 1.0);
    private final Pair<Double, Double> UP_RIGHT = new Pair<>(1.0, 1.0);
    private final Pair<Double, Double> UP_LEFT = new Pair<>(-1.0, 1.0);
    private final Pair<Double, Double> DOWN_RIGHT = new Pair<>(1.0, -1.0);
    private final Pair<Double, Double> DOWN_LEFT = new Pair<>(-1.0, -1.0);
    private final S3PhysicalEntity moveableEntity;
    private ArrayList<Double> WEIGHTS;
    ArrayList<Pair<Double, Double>> possibleMoves = new ArrayList<>(Arrays.asList(UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT));
    private boolean[][] CLOSED, OPENHASH;


    public AStar(double start_x, double start_y, double goal_x, double goal_y, S3PhysicalEntity i_entity, S3 the_game) {
        this.x = start_x;
        this.y = start_y;
        this.goal_x = goal_x;
        this.goal_y = goal_y;
        this.game = the_game;
        this.moveableEntity = (S3PhysicalEntity) i_entity.clone();
    }

    public List<Pair<Double, Double>> computePath() {
        ArrayList<Node> OPEN;
        int idx;
        Node node;
        OPEN = new ArrayList<>(List.of(new Node(this.x, this.y, 0, heuristic(this.x, this.y), Optional.empty())));
        OPENHASH = new boolean[game.getMap().getWidth()][game.getMap().getHeight()];
        CLOSED = new boolean[game.getMap().getWidth()][game.getMap().getHeight()];
        WEIGHTS = new ArrayList<>(List.of(OPEN.get(0).getNodeWeight()));
        OPENHASH[(int) this.x][(int) this.y] = true;
        System.out.println("START: (" + this.x + ", " + this.y + ") -- GOAL = (" + goal_x + " " + goal_y + ")");
        while (OPEN.size() != 0) {
            idx = getIdxOfNodeWithLowestWeight();
            node = OPEN.remove(idx);
            WEIGHTS.remove(idx);

            node.print();
            if (isAtGoal(node)) {
                System.out.println("END");
                return backtrack(node);
            }
            CLOSED[(int) node.x()][(int) node.y()] = true;
            for (Node child : getChildrenNodes(node)) {
                OPEN.add(child);
                OPENHASH[(int) child.x()][(int) child.y()] = true;
                WEIGHTS.add(child.getNodeWeight());
            }
        }
        return null;
    }

    protected Double heuristic(double x, double y) {
//        return Math.sqrt(Math.pow(goal_x - x,2) + Math.pow(goal_y - y,2));
        return Math.abs(goal_x - x) + Math.abs(goal_y - y);
    }

    public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y, S3PhysicalEntity i_entity, S3 the_game) {
        AStar a = new AStar(start_x, start_y, goal_x, goal_y, i_entity, the_game);
        List<Pair<Double, Double>> path = a.computePath();
        if (path != null) return path.size();
        return -1;
    }

    public boolean isAtGoal(Node node) {
        return node.x() == this.goal_x && node.y() == this.goal_y;
    }

    protected int getIdxOfNodeWithLowestWeight() {
        int idx = 0;
        double min = WEIGHTS.get(idx);
        // Find the smallest weighted node such that it is 1 + the current nodes g and its parent is the current node.
        for (int i = 1; i < WEIGHTS.size(); i++) {
            if (WEIGHTS.get(i) < min) {
                min = WEIGHTS.get(i);
                idx = i;
            }
        }
        return idx;

    }

    public ArrayList<Node> getChildrenNodes(Node node) {
        // For each possible move, add the current nodes position
        // Filter so that the move is not out of bounds
        // Filter so that the move does not hit any collision boxes
        // Filter so that the move is neither in the OPEN nor CLOSED list
        // For each of these legal moves, create the new Node with the new x/y locations, with a g_n+1 = g_n + 1,  heuristic, and parent node
        return possibleMoves.stream().map(move -> new Pair<>(node.x() + move.m_a, node.y() + move.m_b))
                .filter(newLoc -> 0 < newLoc.m_a && newLoc.m_a < this.game.getMap().getWidth() && 0 < newLoc.m_b && newLoc.m_b < this.game.getMap().getHeight())
                .filter(newLoc -> {
                    moveableEntity.setX((int) (double) newLoc.m_a);
                    moveableEntity.setY((int) (double) newLoc.m_b);
                    return this.game.anyLevelCollision(moveableEntity) == null;
                })
                .filter(newLoc -> !OPENHASH[(int) (double) newLoc.m_a][(int) (double) newLoc.m_b])
                .filter(newLoc -> !CLOSED[(int) (double) newLoc.m_a][(int) (double) newLoc.m_b])
                .map(legalMove -> new Node(legalMove.m_a, legalMove.m_b, node.g() + 1, heuristic(legalMove.m_a, legalMove.m_b), Optional.of(node)))
                .collect(Collectors.toCollection(ArrayList::new));


    }

    public List<Pair<Double, Double>> backtrack(Node finalNode) {
        List<Pair<Double, Double>> backTrack = new ArrayList<>();
        List<Pair<Double, Double>> res = new ArrayList<>();
        Optional<Node> node = Optional.of(finalNode);
        while (node.isPresent()) {
            if (node.get().x() == this.x && node.get().y() == this.y) break;
            backTrack.add(new Pair<>(node.get().x(), node.get().y()));
            node = node.get().parent();
        }
        for (int i = backTrack.size() - 1; i >= 0; i--) {
            res.add(backTrack.get(i));
        }
//        System.out.println("BACKTRACK LENGTH: " + res.size());
//        for (Pair<Double, Double> resNode : res) {
//            System.out.print("NODE(" + resNode.m_a + "," + resNode.m_b + ") ");
//        }
//        System.out.println();
        return res;
    }

}
