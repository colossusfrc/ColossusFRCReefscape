package frc.robot.subsystems.ArmMechanisms;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmUtility;
import frc.robot.Constants.ArmUtility.ArmConstants;

public class BracoAlto extends Braco{

    private double lastValue = 0.0;

    private double currentValue;

    private double rotations = 0.0;

    private final Supplier<Double> getTreatedMotion;

    private final double offset = -0.817;

    private final double initialComplementarAngle = 92.3;

    public BracoAlto() {
        super(9, 0, .43907793633369923161361141602634);
        
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
    }
    
    @Override
    public double getAbsolutePosition() {
        double value = getTreatedMotion.get()*conversionFactor*360;
        return value;
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
        SmartDashboard.putNumber("AUx", rotations);

        currentValue = getTreatedMotion.get();
    }
 
    @Override
    public void setAbsolutePosition(double position) {
        double nonTreatedPower = pidController.calculate(getAbsoluteAngle(), position);

        double treatedPower = (Math.abs(nonTreatedPower)>ArmUtility.ArmConstants.kMaxOutput)?Math.signum(nonTreatedPower)*ArmConstants.kMaxOutput:nonTreatedPower;
        
        treatedPower = ((Math.abs(super.pidController.getPositionError())>=180)&&(getAbsoluteAngle()>0))?-Math.abs(treatedPower):treatedPower;
    
        setArm(treatedPower);
    }

    @Override
    public String getName() {
        return this.getSubsystem();
    }
}
