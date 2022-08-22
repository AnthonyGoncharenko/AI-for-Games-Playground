package controllers;

import Vector.Vector;
import engine.Car;
import engine.Game;
import engine.GameObject;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ArriveController extends Controller {
    protected final GameObject target;
    double alpha, projection, speed, normalProjection;
    protected final double MAX_ACCELERATION = 10;
    protected final double MAX_SPEED = 250;

    public ArriveController(GameObject target) {
        this.target = target;
    }

    protected Vector arrive(Car subject, double deltaTime) {
        double targetRadius = 2, slowRadius = 200;
        double Dx = target.getX() - subject.getX();
        double Dy = target.getY() - subject.getY();
        Vector D = new Vector(Dx, Dy);
        double dist = D.magnitude();
        if (dist < targetRadius) return new Vector(0.0, 0.0);
        double targetSpeed = (dist > slowRadius) ? MAX_SPEED : MAX_SPEED * dist / slowRadius;
        Vector targetVelocity = D.normalize().multiplyConstant(targetSpeed);
        double Vx = Math.cos(subject.getAngle());
        double Vy = Math.sin(subject.getAngle());
        Vector A = targetVelocity.elementWiseSubtraction(new Vector(Vx, Vy).multiplyConstant(subject.getSpeed() / (deltaTime * 100)));
        if (A.magnitude() > MAX_ACCELERATION) A = A.normalize().multiplyConstant(MAX_ACCELERATION);
        return A;
    }

    @Override
    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        Vector velocity, normalVelocity, subjectPosition, targetPosition;
        controlVariables[VARIABLE_STEERING] = 0;
        controlVariables[VARIABLE_THROTTLE] = 0;
        controlVariables[VARIABLE_BRAKE] = 0;

        speed = subject.getSpeed();
        alpha = subject.getAngle();

        subjectPosition = new Vector(subject.getX(), subject.getY());
        targetPosition = new Vector(target.getX(), target.getY());

        velocity = new Vector(Math.cos(alpha), Math.sin(alpha));
        normalVelocity = new Vector(-Math.sin(alpha), Math.cos(alpha));

        Vector A = this.arrive(subject, delta_t);

        projection = A.dotProduct(velocity);
        normalProjection = A.dotProduct(normalVelocity);

        Vector D = targetPosition.elementWiseSubtraction(subjectPosition);
        Vector DN = D.normalize();

        double angleDifference;
        if (D.get(0) == 0) {
            angleDifference = Math.asin(DN.get(1));
        } else {
            angleDifference = Math.atan(D.get(1) / D.get(0));
            if (D.get(0) < 0) angleDifference += Math.PI;
        }


        angleDifference -= alpha;
        angleDifference %= (2 * Math.PI);

        if (angleDifference > Math.PI) {
            angleDifference -= (2 * Math.PI);
        } else if (angleDifference <= -1 * Math.PI) {
            angleDifference += (2 * Math.PI);
        }
        controlVariables[VARIABLE_THROTTLE] = speed < Math.pow(2, -10) ? 1 : max(projection, 0);
        controlVariables[VARIABLE_BRAKE] = -min(projection, 0);
        controlVariables[VARIABLE_STEERING] = normalProjection;

        if (Math.abs(angleDifference) > 0 && Math.abs(angleDifference) <= Math.PI / 2) {
            controlVariables[VARIABLE_STEERING] = angleDifference;
        } else if (Math.abs(angleDifference) > Math.PI / 2) {
            controlVariables[VARIABLE_THROTTLE] = 1;
            controlVariables[VARIABLE_STEERING] = Math.abs(angleDifference);
        } else {
            controlVariables[VARIABLE_THROTTLE] = 1;
        }
    }
}
