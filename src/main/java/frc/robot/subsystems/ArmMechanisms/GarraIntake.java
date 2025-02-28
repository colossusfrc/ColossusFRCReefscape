package frc.robot.subsystems.ArmMechanisms;

import frc.robot.subsystems.ArmMechanisms.Superclasses.Garra;

public class GarraIntake extends Garra{
    public GarraIntake(){
        super(13);
    }
    @Override
    protected void pidConfig() {
        config.closedLoop
            .pidf(1.0, 0.0, 0.0, 0.01)
            .iZone(0.0)
            .maxOutput(1.0)
            .minOutput(-1.0);
    }
}
