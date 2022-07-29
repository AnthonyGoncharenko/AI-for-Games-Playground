package controllers;

import Vector.Vector;
import engine.Car;
import engine.Game;
import engine.Marker;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SeekController extends Controller {
    protected final Car target;
    double alpha, projection, normalProjection, speed;
    Marker marker;
    protected final double MAX_ACCELERATION = 10;
    protected final double MAX_SPEED = 250;

    public SeekController(Car target) {
        this.target = target;
    }

    protected Vector seek(Vector subject, Vector target) {
        return target.elementWiseSubtraction(subject).normalize();
    }

    protected Vector pursue(Car subject, double maxTime) {
        Vector subjectPosition = new Vector(subject.getX(), subject.getY());
        Vector targetPosition = new Vector(target.getX(), target.getY());
        double time = targetPosition
                .elementWiseSubtraction(subjectPosition)
                .multiplyConstant(1 / MAX_SPEED).magnitude();
        if (time > maxTime) time = maxTime;
        Vector targetVelocity = new Vector(Math.cos(target.getAngle()), Math.sin(target.getAngle()));
        Vector prediction = targetPosition.elementWiseAddition(targetVelocity.multiplyConstant(time));
        return seek(subjectPosition, prediction);
    }

    protected Vector flee(Vector subject, Vector target) {
        return target.elementWiseSubtraction(subject).normalize().multiplyConstant(-1);
    }

    @Override
    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        Vector velocity, normalVelocity;
// Zero out the control variables for the update frame
        controlVariables[VARIABLE_STEERING] = 0;
        controlVariables[VARIABLE_THROTTLE] = 0;
        controlVariables[VARIABLE_BRAKE] = 0;
        Vector subjectPosition = new Vector(subject.getX(), subject.getY());
        Vector targetPosition = new Vector(target.getX(), target.getY());
// Find the Acceleration vector from the subject to the target.
//        Vector A = pursue(subject, delta_t);
        Vector A = seek(subjectPosition, targetPosition);

        alpha = subject.getAngle();

        velocity = new Vector(Math.cos(alpha), Math.sin(alpha));
        normalVelocity = new Vector(-Math.sin(alpha), Math.cos(alpha));
// Calculate the projection of A onto the unit axis of the subject with respect to the X-axis
        projection = A.dotProduct(velocity);
// Calculate the projection of A onto the unit axis of the subject with respect to the Y-axis
        normalProjection = A.dotProduct(normalVelocity);
// Sets the throttle and brake depending on whether projection is positive or negative.
        controlVariables[VARIABLE_THROTTLE] = max(projection, 0);
        controlVariables[VARIABLE_BRAKE] = -min(projection, 0);
        controlVariables[VARIABLE_STEERING] = normalProjection;

    }
}
