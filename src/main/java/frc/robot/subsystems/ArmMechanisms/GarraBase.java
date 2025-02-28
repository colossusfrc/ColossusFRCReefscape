package frc.robot.subsystems.ArmMechanisms;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Garra;

public class GarraBase extends Garra{
    public GarraBase(){
        super(11);
    }
    @Override
    protected void pidConfig() {
        config.closedLoop
            .pidf(0.04, 0.0, 0.0, 0.0)
            .iZone(0.0)
            .maxOutput(1.0)
            .minOutput(-1.0);
    }
    @Override
    public void periodic() {
        SmartDashboard.putNumber("posicao encoder garr base", getPosition());
    }
}
