package frc.robot.subsystems.ArmMechanisms;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmUtility.ClawConstants;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Garra;

public class GarraBase extends Garra{
    private final DutyCycleEncoder encoderAbsoluto;
    private final double conversionFactor = 1.0;
    private final double initialComplementarAngle = 0.0;
    private static GarraBase instance;
    private final PIDController pidGarraBase;
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
        SmartDashboard.putNumber("posicao encoder absoluto garra base", getAbsoluteAngle());
    }
    @Override
    public double getAbsolutePosition() { 
        double value = this.encoderAbsoluto.get()*conversionFactor*360;
        return value;
    }
    @Override
    public double getAbsoluteAngle() {double angle;
        angle = getAbsolutePosition() + initialComplementarAngle;
        while(Math.abs(angle)>360)angle -= Math.signum(angle)*360;
        while(Math.abs(angle)>180)angle = -Math.signum(angle)*(360-Math.abs(angle));
        return angle+ClawConstants.encoderOffset;
    }

    @Override
    public double getError() { return this.pidGarraBase.getError(); }

    @Override
    public double getIncrementalPosition() { return getPosition(); }

    @Override
    public double getIncrementalAngle() { return getPosition(); }

    @Override
    public boolean getPID() { return this.pidGarraBase.atSetpoint(); }

    @Override
    public void setAbsolutePosition(double target, double feedForward) { 
        double nonTreatedPower = pidGarraBase.calculate(getAbsoluteAngle(), target);

        double treatedPower = (Math.abs(nonTreatedPower)>ClawConstants.kMaxPower)?Math.signum(nonTreatedPower)*ClawConstants.kMaxPower:nonTreatedPower;
        
        setArm(treatedPower+feedForward);
    }

    @Override
    public void stopArm() { setPower(0.0); }

    @Override
    public void setArm(double power) { setPower(power);}

    @Override
    public void setReference(double position) { setAbsolutePosition(position, 0.0);}
}
