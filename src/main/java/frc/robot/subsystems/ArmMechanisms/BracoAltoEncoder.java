package frc.robot.subsystems.ArmMechanisms;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmUtility;
import frc.robot.Constants.ArmUtility.ArmConstants;
import frc.robot.RCFeatures.ArmFeatures.StateMachine;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

@Deprecated
public class BracoAltoEncoder extends Braco{

    private final double initialComplementarAngle = 92.3;

    public BracoAltoEncoder(StateMachine stateMachine) {
        super(9, 15.0, stateMachine);
        getTreatedMotion = () -> 0.0;
        super.encoder.setPosition(0.0);
    }

    @Override
    public void periodic() {
        super.treatBoundariesIncremental();
        SmartDashboard.putNumber("Angle position "+getName(), getAbsoluteAngle());
        SmartDashboard.putNumber("Erro", getError());
        SmartDashboard.putNumber("power ", super.motor.get());
    }

    @Override
    public double getAbsolutePosition() {
        return -encoder.getPosition()+initialComplementarAngle;
    }

    @Override
    public double getAbsoluteAngle() {
        double angle;
        angle = getAbsolutePosition();
        while(Math.abs(angle)>360)angle -= Math.signum(angle)*360;
        while(Math.abs(angle)>180)angle = -Math.signum(angle)*(360-Math.abs(angle));
        return angle;
    }

    @Override
    public void setAbsolutePosition(double target, double feedForward) {
        double nonTreatedPower = pidController.calculate(getAbsoluteAngle(), target);

        double treatedPower = (Math.abs(nonTreatedPower)>ArmUtility.ArmConstants.kMaxOutput)?Math.signum(nonTreatedPower)*ArmConstants.kMaxOutput:nonTreatedPower;
        
        //treatedPower-= Math.cos(getAbsoluteAngle()) * ArmConstants.kFF;

        treatedPower = ((Math.abs(super.pidController.getPositionError())>=180)&&(getAbsoluteAngle()>0))?-Math.abs(treatedPower):treatedPower;
        setArm(-treatedPower-feedForward);
    }
    
    @Override
    public String getName() {
        return this.getSubsystem();
    }
}