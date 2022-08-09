package ch.idsia.agents.controllers.BehaviorTree;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.agents.controllers.BehaviorTree.Actions.Task;
import ch.idsia.agents.controllers.BehaviorTree.Composites.Composite;
import ch.idsia.benchmark.mario.environments.Environment;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;

public class BehaviorTreeAgent extends BasicMarioAIAgent implements Agent {
    public int trueJumpCounter = 0;
    public int trueSpeedCounter = 0;
    Task behaviorTree;
    Document doc;
    final String XML_SOURCE = "src/ch/idsia/agents/controllers/BehaviorTree/MarioAIBehaviorTree.xml";

    public BehaviorTreeAgent() {
        super("BehaviorTreeAgent");
        reset();
    }

    public void reset() {
        action = new boolean[Environment.numberOfKeys];
        trueJumpCounter = 0;
        trueSpeedCounter = 0;
    }

    public void readXML() {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            BehaviorTreeHandler b = new BehaviorTreeHandler();
            saxParser.parse(new File(XML_SOURCE), b);
            this.behaviorTree = b.getBehaviorTree();
            System.out.println(this.behaviorTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean[] getAction() {
        this.behaviorTree.run(this);
        if (this.trueJumpCounter > 16) {
            this.reset();
        }
        return this.action;
    }

    public void setMarioInput(int marioKeyDown, boolean b) {
        this.action[marioKeyDown] = b;
    }

    public boolean getMarioInput(int marioKeySpeed) {
        return this.action[marioKeySpeed];
    }

    public boolean isCarrying() {
        return this.isMarioCarrying;
    }

    public boolean canShoot() {
        return this.isMarioAbleToShoot;
    }

    public boolean isOnGround() {
        return this.isMarioOnGround;
    }

    public boolean isHoleAhead() {
        int x = this.marioEgoRow, y = this.marioEgoCol;
        return (getReceptiveFieldCellValue(x + 2, y + 1) == 0 &&
                getReceptiveFieldCellValue(x + 1, y + 1) == 0);
    }

    public boolean isEnemyAhead() {
        int x = this.marioEgoRow, y = this.marioEgoCol;
        return (this.getEnemiesCellValue(x, y + 1) != 0 ||
                this.getEnemiesCellValue(x, y + 2) != 0 ||
                this.getEnemiesCellValue(x, y + 3) != 0 ||
                this.getEnemiesCellValue(x, y + 4) != 0 ||
                this.getEnemiesCellValue(x, y + 5) != 0 ||
                this.getEnemiesCellValue(x + 1, y + 1) != 0 ||
                this.getEnemiesCellValue(x + 2, y + 2) != 0 ||
                this.getEnemiesCellValue(x + 3, y + 3) != 0 ||
                this.getEnemiesCellValue(x + 4, y + 4) != 0 ||
                this.getEnemiesCellValue(x - 1, y + 1) != 0 ||
                this.getEnemiesCellValue(x - 2, y + 2) != 0 ||
                this.getEnemiesCellValue(x - 3, y + 3) != 0 ||
                this.getEnemiesCellValue(x - 4, y + 4) != 0);
    }

    public boolean isQuestionBrickAbove() {
        int x = this.marioEgoRow, y = this.marioEgoCol;
        int BOX = -24;
        return this.getReceptiveFieldCellValue(x + 1, y) == BOX ||
                this.getReceptiveFieldCellValue(x + 2, y) == BOX ||
                this.getReceptiveFieldCellValue(x + 3, y) == BOX ||
                this.getReceptiveFieldCellValue(x + 4, y) == BOX ||
                this.getReceptiveFieldCellValue(x + 5, y) == BOX;
    }

    public boolean isBrickAhead() {
        int x = this.marioEgoRow;
        int y = this.marioEgoCol;
        return this.getReceptiveFieldCellValue(x, y + 1) != 0 ||
                this.getReceptiveFieldCellValue(x, y + 2) != 0 ||
                this.getReceptiveFieldCellValue(x, y + 3) != 0 ||
                this.getReceptiveFieldCellValue(x, y + 4) != 0 ||
                this.getReceptiveFieldCellValue(x, y + 5) != 0;
    }

    private static class BehaviorTreeHandler extends DefaultHandler {
        ArrayList<Composite> compList = new ArrayList<>();
        public Composite root;
        String taskName;
        TaskFactory taskFactory = new TaskFactory();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equals("Sequence") || qName.equals("Selector")) {
                this.root = (Composite) taskFactory.getTask(qName);
                this.compList.add(this.root);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equals("Sequence") || qName.equals("Selector")) {
                int size = this.compList.size();
                if (size > 1) {
                    this.compList.get(size - 2).addTask(this.root);
                    this.compList.remove(size - 1);
                    this.root = this.compList.get(size - 1 - 1);
                }
            } else {
                this.root.addTask(taskFactory.getTask(this.taskName));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            this.taskName = new String(ch, start, length);
        }

        public Task getBehaviorTree() {
            return this.root;
        }
    }
}
