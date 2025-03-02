package frc.robot.subsystems.ArmMechanisms.Superclasses;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmUtility;

import java.util.function.Supplier;

import frc.robot.RCFeatures.ArmFeatures.StateMachine;
import frc.robot.subsystems.ArmMechanisms.Interfaces.Clever;

public abstract class Braco extends SubsystemBase implements Clever{

  protected final SparkMax motor;

  protected final SparkMaxConfig config;

  protected final RelativeEncoder encoder;

  protected final PIDController pidController;

  protected double conversionFactor = 1.0;

  protected final DutyCycleEncoder enCycleAdv;

  protected final StateMachine stateMachine;

  protected Supplier<Double> getTreatedMotion;
  
  private Braco(int id, StateMachine stateMachine) {
    this.motor = new SparkMax(id, MotorType.kBrushless);

    config = new SparkMaxConfig();

    motorConfig();
   
    this.enCycleAdv = null;

    this.encoder = motor.getEncoder();

    incrementalEncoderConfig();

    this.pidController = new PIDController(ArmUtility.ArmConstants.kP, ArmUtility.ArmConstants.kI, ArmUtility.ArmConstants.kD);

    buildConfig();

    this.stateMachine = stateMachine;
  }
  //one id for each engine
  public Braco(int idEngine, int idDutyCycleEncoder, StateMachine stateMachine){
    this.motor = new SparkMax(idEngine, MotorType.kBrushless);

    config = new SparkMaxConfig();

    motorConfig();

    this.enCycleAdv = new DutyCycleEncoder(idDutyCycleEncoder);

    this.encoder = motor.getEncoder();

    incrementalEncoderConfig();

    this.pidController = new PIDController(ArmUtility.ArmConstants.kP, ArmUtility.ArmConstants.kI, ArmUtility.ArmConstants.kD);

    buildConfig();

    this.stateMachine = stateMachine;
  }
  //one id for all engines, different conversion factors
  public Braco(int id, double conversionFactor, StateMachine stateMachine){
    this(id, stateMachine);
    this.conversionFactor = conversionFactor;
    incrementalEncoderConfig();
    buildConfig();
  }
  //one id for each engine, different conversion factors
  public Braco(int id, int idDutyCycleEncoder, double conversionFactor, StateMachine stateMachine){
    this(id, idDutyCycleEncoder, stateMachine);
    this.conversionFactor = conversionFactor;
  }

  protected void motorConfig(){
    this.config
      .smartCurrentLimit(40)
      .idleMode(IdleMode.kCoast);
  }

  private final void incrementalEncoderConfig(){
    this.config
      .encoder
      .positionConversionFactor(conversionFactor);
  }

  private final void buildConfig(){ this.motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters); }

  @Override
  public void periodic() {
    treatBoundariesIncremental();
    SmartDashboard.putNumber("Error "+getName(), pidController.getPositionError());
    SmartDashboard.putNumber("valor", conversionFactor);
    SmartDashboard.putString("past state", stateMachine.getAnterior().toString());
    SmartDashboard.putString("current state", stateMachine.getAtual().toString());
    SmartDashboard.putNumber("Amperagem "+getName(), motor.getOutputCurrent());
  }

  protected void treatBoundariesIncremental(){
    double angle = encoder.getPosition();
    if(angle>360)encoder.setPosition(angle-360);
    if(Math.abs(angle)>360)encoder.setPosition(angle-Math.signum(angle)*360);
    if(Math.abs(angle)>180)encoder.setPosition(-Math.signum(angle)*(360-Math.abs(angle)));
  }

  public void setArm(double power){
    motor.set(power);
  }
  //method to quicly stop and brake the engine
  public void stopArm() {
    motor.set(0.0);
  }
  public void setIdleMode(IdleMode idleMode){
    motor.configureAsync(
      new SparkMaxConfig()
      .idleMode(idleMode),
       ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);
  }
  @Override
  public boolean getPID() {
      return pidController.atSetpoint();
  }
  @Override
  public double getIncrementalPosition() {
    double angle = encoder.getPosition();
    angle*=360;
    return 
    (angle<=360)?angle:angle-360;
  }
  @Override
  public double getIncrementalAngle() {
    double angle;

    angle = getIncrementalPosition();
   
    if(Math.abs(angle)>360)angle -= Math.signum(angle)*360;
    if(Math.abs(angle)>180)angle = -Math.signum(angle)*(360-Math.abs(angle));
    return 
    angle;
  }
  @Override
  public double getError() {
    return pidController.getPositionError();
  }
  public void resetIntegrator(){
    pidController.reset();
  }
  public abstract boolean getHasChangedPID();
  public abstract void setP();
}
