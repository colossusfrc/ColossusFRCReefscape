package frc.robot.subsystems.ArmMechanisms;
import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmUtility;
import frc.robot.Constants.ArmUtility.HighArmConstants;

public class BracoAlto extends Braco{

    private double lastValue = 0.0;

    private double currentValue;

    private double rotations = 0.0;

    private final Supplier<Double> getTreatedMotion;

    private final double offset = -0.709;

    private final double initialComplementarAngle = 92.3;

    public BracoAlto() {
        super(9, 0, 1.0);
        
        getTreatedMotion = () -> {
            currentValue = super.enCycleAdv.get();

            if(lastValue>0.9 && currentValue< 0.1){
                rotations++;
            }else if(lastValue<0.1 && currentValue>0.9){
                rotations--;
            }

            lastValue = currentValue;
    
            return super.enCycleAdv.get() + rotations + offset;
        };

        this.pidController.setPID(HighArmConstants.kP, HighArmConstants.kI, HighArmConstants.kD);
    }
    
    @Override
    public double getAbsolutePosition() {
        double value = getTreatedMotion.get()*conversionFactor*360;
        return value;
    }
    @Override
    protected void motorConfig() {
        super.motorConfig();
        super.
            config.
                inverted(false);
    }
    @Override
    public double getAbsoluteAngle() {
        double angle;
        angle = getAbsolutePosition() + initialComplementarAngle;
        while(Math.abs(angle)>360)angle -= Math.signum(angle)*360;
        while(Math.abs(angle)>180)angle = -Math.signum(angle)*(360-Math.abs(angle));
        return angle+offset;
    }

    @Override
    public void periodic() {
        super.periodic();
        SmartDashboard.putNumber("Value alto", super.enCycleAdv.get());
        SmartDashboard.putNumber("Absolute angle braco alto", getAbsoluteAngle());
        SmartDashboard.putNumber("pid", pidController.getP());
        SmartDashboard.putNumber("Power braco alto", super.motor.get());

        currentValue = getTreatedMotion.get();
    }
 
    @Override
    public void setAbsolutePosition(double position) {
        double nonTreatedPower = pidController.calculate(getAbsoluteAngle(), position);

        double treatedPower = (Math.abs(nonTreatedPower)>ArmUtility.HighArmConstants.kMaxOutput)?Math.signum(nonTreatedPower)*HighArmConstants.kMaxOutput:nonTreatedPower;
        
        treatedPower-= Math.cos(getAbsoluteAngle()) * HighArmConstants.kFF;

        treatedPower = ((Math.abs(super.pidController.getPositionError())>=180)&&(getAbsoluteAngle()>0))?-Math.abs(treatedPower):treatedPower;
        setArm(treatedPower);
    }

    @Override
    public String getName() {
        return this.getSubsystem();
    }
}
