package frc.robot.subsystems.ArmMechanisms;

import java.util.Deque;
import java.util.ArrayDeque;

public interface Clever {
    Deque<Double> positions = new ArrayDeque<>();

    Double getLastTarget();

    double getAbsolutePosition();
    double getAbsoluteAngle();
    double getError();

    double getIncrementalPosition();
    double getIncrementalAngle();

    boolean getPID();

    void setAbsolutePosition(double target);
    void setLastTarget(Double target);
    void stopArm();
    void setArm(double power);
}
