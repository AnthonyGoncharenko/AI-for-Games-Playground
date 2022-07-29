package controllers;

import Vector.Vector;
import engine.*;

import static java.lang.Math.*;

public class WallAvoidanceController extends SeekController {
    protected final double MAX_ACCELERATION = 10;
    protected final double MAX_SPEED = 250;
    double alpha, projection, normalProjection;
    static RotatedRectangle r;
    static Marker marker;
    Car subject;
    Vector unitVelocity, turningVector, subjectPosition;

    public WallAvoidanceController(Car target) {
        super(target);
    }


    protected Vector rayCast(Game game, double alpha, double distance) {
        Vector line = new Vector(Math.cos(alpha), Math.sin(alpha));
        Vector newPosition = subjectPosition.elementWiseAddition(line.multiplyConstant(distance));

        r.setX(newPosition.get(0));
        r.setY(newPosition.get(1));
        r.setAng(alpha);

        for (GameObject o2 : game.getM_objects()) {
            if (o2.collision(r) && o2 != subject) return new Vector(o2.getX(), o2.getY());
        }
        return null;
    }


    @Override
    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        Vector As, Aw, targetPosition, newPosition;
        this.subject = subject;
        Vector leftRayCast = null, centerRayCast = null, rightRayCast = null;

        alpha = subject.getAngle();
        speed = subject.getSpeed();
//        System.out.println("speed: " + speed);
        subjectPosition = new Vector(subject.getX(), subject.getY());
        targetPosition = new Vector(target.getX(), target.getY());
        r = new RotatedRectangle(subject.getX(), subject.getY(), subject.m_img.getWidth(), subject.m_img.getHeight(), alpha);
        marker = (Marker) game.getMarker();
        As = seek(subjectPosition, targetPosition);

        unitVelocity = new Vector(Math.cos(alpha), Math.sin(alpha));
        turningVector = new Vector(-Math.sin(alpha), Math.cos(alpha));

        double line = (speed < 0) ? alpha + PI : alpha;

//        double line = alpha;
        double castLength = 55, moveLengthL = castLength, moveLengthC = castLength, moveLengthR = castLength;

        for (double distance = 1; distance < castLength; distance++) {
//            if (distance + 1 == castLength) System.out.println("left");
            if (leftRayCast == null) {
                leftRayCast = rayCast(game, line - Math.PI / 4, distance);
                moveLengthL = (leftRayCast != null) ? distance : moveLengthL;
            }


//            if (distance + 1 == castLength) System.out.println("center");
            if (centerRayCast == null) {
                centerRayCast = rayCast(game, line, distance);
                moveLengthC = (centerRayCast != null) ? distance : moveLengthC;
            }


//            if (distance + 1 == castLength) System.out.println("right");
            if (rightRayCast == null) {
                rightRayCast = rayCast(game, line + Math.PI / 4, distance);
                moveLengthR = (rightRayCast != null) ? distance : moveLengthR;
            }

        }
//        System.out.println("DONE!!!");
//        System.out.print("moveLengthL, moveLengthC, moveLengthR: ");
//        new Vector(moveLengthL, moveLengthC, moveLengthR).print();
        double min = min(min(moveLengthL, moveLengthC), moveLengthR);
        Vector newAngle = new Vector(Math.cos(line), Math.sin(line)).multiplyConstant(castLength);
//        System.out.print("newAngle");
//        newAngle.print();
        newPosition = (min < castLength) ? subjectPosition.elementWiseSubtraction(newAngle) : targetPosition;

//        System.out.print("newPos: ");
//        newPosition.print();
        marker.setX(newPosition.get(0));
        marker.setY(newPosition.get(1));

        Aw = seek(subjectPosition, newPosition);

//        System.out.print("As: ");
//        As.print();
        double scalar = 0.6;
//        System.out.println("scalar: " + scalar);
//        Vector Ac = As.elementWiseAddition(Aw).normalize();
        Vector Ac = As.multiplyConstant(scalar).elementWiseAddition(Aw.multiplyConstant(1 - scalar)).normalize();

//        System.out.print("As+Aw: ");
//        Ac.print();
        projection = Ac.dotProduct(unitVelocity);
        normalProjection = Ac.dotProduct(turningVector);
//        System.out.println("projection: " + projection);
//        System.out.println("normalProjection: " + normalProjection);
        controlVariables[VARIABLE_THROTTLE] = max(projection, 0);
        controlVariables[VARIABLE_BRAKE] = -min(projection, 0);
        controlVariables[VARIABLE_STEERING] = normalProjection;

    }

}
