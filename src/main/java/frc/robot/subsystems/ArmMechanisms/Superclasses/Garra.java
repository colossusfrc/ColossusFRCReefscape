package frc.robot.subsystems.ArmMechanisms.Superclasses;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Garra extends SubsystemBase{
    protected final SparkMax motor; //11

    //protected final SparkMax motorAlto;//13

    protected final SparkMaxConfig config = new SparkMaxConfig();

    protected final RelativeEncoder relativeEncoder;

    protected final SparkClosedLoopController sparkPid;

    public Garra(int id){

        this.motor = new SparkMax(id, MotorType.kBrushless);

        pidConfig();

        motorConfig();

        this.relativeEncoder = motor.getEncoder();

        this.sparkPid = motor.getClosedLoopController();

        this.relativeEncoder.setPosition(0.0);

        setBrake(true);
    }

    protected void motorConfig(){
        config.smartCurrentLimit(40)
        .idleMode(IdleMode.kCoast)
        .encoder
            .positionConversionFactor(1.0);
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    protected void pidConfig(){
        config.closedLoop
            .pidf(0.3, 0.0, 0.0, 0.0)
            .iZone(0.0)
            .maxOutput(1.0)
            .minOutput(-1.0);
    }

    public void setBrake(boolean brake){
        config.idleMode((brake)?IdleMode.kBrake:IdleMode.kCoast);
        motor.configureAsync(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }


    public double getPosition(){ return relativeEncoder.getPosition(); }

    public double getPower(){ return motor.get(); }

    public void setReference(double position){ this.sparkPid.setReference(position, ControlType.kPosition); }

    public void setPower(double power){ this.motor.set(power); }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("power", motor.get());
        SmartDashboard.putNumber("Posicao Garra", getPosition());
    }
    
    public void resetEncoder(){
        relativeEncoder.setPosition(0.0);
    }
}

