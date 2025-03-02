package frc.robot.subsystems.ArmMechanisms;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmUtility.ClawConstants;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Garra;

public class GarraBase extends Garra{

    private static GarraBase instance;
    public static synchronized GarraBase getInstance(){
        if(instance==null){
        instance = new GarraBase();
        }
        return instance;
    }

    private GarraBase(){
        super(ClawConstants.baseId);
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
