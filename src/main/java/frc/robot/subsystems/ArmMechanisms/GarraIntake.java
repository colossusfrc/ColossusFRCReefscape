package frc.robot.subsystems.ArmMechanisms;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmUtility.ClawConstants;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Garra;

public class GarraIntake extends Garra{

    private static GarraIntake instance;
    public static synchronized GarraIntake getInstance(){
        if(instance==null){
        instance = new GarraIntake();
        }
        return instance;
        
    }

    private GarraIntake(){
        super(ClawConstants.intakeId);
    }
    @Override
    protected void pidConfig() {
        config.closedLoop
            .pidf(1.0, 0.0, 0.0, 0.01)
            .iZone(0.0)
            .maxOutput(1.0)
            .minOutput(-1.0);
    }
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Voltagem", motor.getOutputCurrent());
        SmartDashboard.putNumber("power", motor.get());
        SmartDashboard.putNumber("clawPower", ClawConstants.clawPower);
    }
    public double getVoltage(){
        return this.motor.getOutputCurrent();
    }
}
