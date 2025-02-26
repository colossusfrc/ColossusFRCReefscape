package frc.robot.subsystems.ArmMechanisms;

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
    private final SparkMax motor = new SparkMax(11, MotorType.kBrushless);

    private final SparkMaxConfig config = new SparkMaxConfig();

    private final RelativeEncoder relativeEncoder;

    private final SparkClosedLoopController sparkPid;

    public Garra(){
        pidConfig();

        motorConfig();

        this.relativeEncoder = motor.getEncoder();

        this.sparkPid = motor.getClosedLoopController();

        this.relativeEncoder.setPosition(0.0);

        setBrake(true);
    }

    private final void motorConfig(){
        config.smartCurrentLimit(40)
        .idleMode(IdleMode.kBrake);
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    private final void pidConfig(){
        config.closedLoop
            .pidf(3.0, 0.0, 0.0, 0.0)
            .iZone(0.0)
            .maxOutput(1.0)
            .minOutput(-1.0);
    }

    public double getPosition(){ return relativeEncoder.getPosition(); }

    public double getPower(){ return motor.get(); }

    public void setReference(double position){ this.sparkPid.setReference(position, ControlType.kPosition); }

    public void setPower(double power){ this.motor.set(power); }

    public void setBrake(boolean brake){
        config.idleMode((brake)?IdleMode.kBrake:IdleMode.kCoast);
        motor.configureAsync(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("power", motor.get());
        SmartDashboard.putNumber("Posicao Garra", getPosition());
    }
}

