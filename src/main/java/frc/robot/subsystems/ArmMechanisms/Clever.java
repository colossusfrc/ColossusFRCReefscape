package frc.robot.subsystems.ArmMechanisms;
public interface Clever {

    double getAbsolutePosition();
    double getAbsoluteAngle();
    double getError();

    double getIncrementalPosition();
    double getIncrementalAngle();

    boolean getPID();

    void setAbsolutePosition(double target);
    void stopArm();
    void setArm(double power);
}
