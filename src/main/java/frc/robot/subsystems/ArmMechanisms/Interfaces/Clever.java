package frc.robot.subsystems.ArmMechanisms.Interfaces;
public interface Clever {

    double getAbsolutePosition();
    double getAbsoluteAngle();
    double getError();

    double getIncrementalPosition();
    double getIncrementalAngle();

    boolean getPID();

    void setAbsolutePosition(double target, double feedForward);
    void stopArm();
    void setArm(double power);
}
