package frc.robot.subsystems.ArmMechanisms;

import java.util.function.Supplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmUtility.ArmConstants;
import frc.robot.Constants.ArmUtility.ClawConstants;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Garra;

public class GarraBase extends Garra{
    private final DutyCycleEncoder encoderAbsoluto;
    private final double conversionFactor = 1.0;
    private final double initialComplementarAngle = 0.0;
    private static GarraBase instance;
    private final PIDController pidGarraBase;
     private double lastValue = 0.5;

  private double currentValue = 0.5;

  private double rotations = 0.0;

  private final Supplier<Double> getTreatedMotion;
    public static synchronized GarraBase getInstance(){
        if(instance==null){
        instance = new GarraBase();
        }
        return instance;
    }

    private GarraBase(){
        super(ClawConstants.baseId);
        this.encoderAbsoluto = new DutyCycleEncoder(ClawConstants.absoluteEncoderId);
        this.pidGarraBase = new PIDController(ClawConstants.kp, ClawConstants.ki, ClawConstants.kd);    
        getTreatedMotion = () -> {
        currentValue = encoderAbsoluto.get();

        if(lastValue>0.9 && currentValue< 0.1){
            rotations++;
        }else if(lastValue<0.1 && currentValue>0.9){
            rotations--;
        }

        lastValue = currentValue;

        return (currentValue + rotations + ClawConstants.encoderOffset);
    };
    }
    @Override
    protected void pidConfig() {
    }
    @Override
    public void periodic() {
        SmartDashboard.putNumber("posicao encoder garr base", this.encoderAbsoluto.get());
        SmartDashboard.putNumber("posicao encoder incremental garra base", getIncrementalAngle());
        SmartDashboard.putNumber("erro brscobase", pidGarraBase.getError());
        SmartDashboard.putNumber("power base", getPower());
    }
    @Override
    public double getAbsolutePosition() { 
        double value = getTreatedMotion.get()*conversionFactor*360;
        return (value<=360.0)?value:value-360.0;
    }
    @Override
    public double getAbsoluteAngle() {
        double angle;
    angle =  getAbsolutePosition() + initialComplementarAngle;
    while(Math.abs(angle)>360)angle -= Math.signum(angle)*360;
    //while(Math.abs(angle)>180)angle = -Math.signum(angle)*(360-Math.abs(angle));
    return angle;
    }
    public double getIncremental(){
        double value = this.relativeEncoder.getPosition()*conversionFactor;
        return (value<=360.0)?value:value-360.0;
    }
@Override
    public double getIncrementalAngle() {
        double angle;
        angle =  getIncremental();
        while(Math.abs(angle)>360)angle -= Math.signum(angle)*360;
        return angle;
    }
    @Override
    public double getError() { return this.pidGarraBase.getError(); }

    @Override
    public double getIncrementalPosition() { return getPosition(); }

    @Override
    public boolean getPID() { return this.pidGarraBase.atSetpoint(); }

    @Override
    public void setAbsolutePosition(double target, double feedForward) { 
        double nonTreatedPower = pidGarraBase.calculate(getIncrementalAngle(), target);

        double treatedPower = (Math.abs(nonTreatedPower)>ClawConstants.kMaxPower)?Math.signum(nonTreatedPower)*ClawConstants.kMaxPower:nonTreatedPower;
        
        setArm(treatedPower+feedForward);
    }

    @Override
    public void stopArm() { setPower(0.0); }

    @Override
    public void setArm(double power) { motor.set(power);}

    @Override
    public void setReference(double position) { setAbsolutePosition(position, 0.0);}
}
