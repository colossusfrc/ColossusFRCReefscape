package frc.robot.subsystems.ArmMechanisms;

import java.util.Deque;
import java.util.ArrayDeque;

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

import org.dyn4j.exception.NullElementException;

import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;

public abstract class Braco extends SubsystemBase implements Clever{

  protected final SparkMax motor;

  protected final SparkMaxConfig config;

  protected final RelativeEncoder encoder;

  protected final PIDController pidController;

  protected double conversionFactor = 1.0;

  protected final DutyCycleEncoder enCycleAdv;

  protected static final Deque<ArmStates> positions = new ArrayDeque<>();

  protected static final Deque<ArmStates> lastPositions = new ArrayDeque<>();

  protected Supplier<Double> getTreatedMotion;

  static{
    positions.add(ArmStates.guarda);
    lastPositions.add(ArmStates.guarda);
  }
  //uses the closedLoopPIDFCOntroller
  public Braco(int id) {
    this.motor = new SparkMax(id, MotorType.kBrushless);

    config = new SparkMaxConfig();

    motorConfig();
   
    this.enCycleAdv = null;

    this.encoder = motor.getEncoder();

    incrementalEncoderConfig();

    this.pidController = new PIDController(ArmUtility.ArmConstants.kP, ArmUtility.ArmConstants.kI, ArmUtility.ArmConstants.kD);

    buildConfig();
  }
  //one id for each engine
  public Braco(int idEngine, int idDutyCycleEncoder){
    this.motor = new SparkMax(idEngine, MotorType.kBrushless);

    config = new SparkMaxConfig();

    motorConfig();

    this.enCycleAdv = new DutyCycleEncoder(idDutyCycleEncoder);

    this.encoder = motor.getEncoder();

    incrementalEncoderConfig();

    this.pidController = new PIDController(ArmUtility.ArmConstants.kP, ArmUtility.ArmConstants.kI, ArmUtility.ArmConstants.kD);

    buildConfig();
  }
  //one id for all engines, different conversion factors
  public Braco(int id, double conversionFactor){
    this(id);
    this.conversionFactor = conversionFactor;
    incrementalEncoderConfig();
    buildConfig();
  }
  //one id for each engine, different conversion factors
  public Braco(int id, int idDutyCycleEncoder, double conversionFactor){
    this(id, idDutyCycleEncoder);
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
    try{
    SmartDashboard.putString("Current State", (positions.peekLast().toString()!=null)?positions.peekLast().toString():"");
    SmartDashboard.putString("Last State", (positions.peekLast().toString()!=null)?lastPositions.peekLast().toString():"");
    }catch(NullElementException e){}
    catch(NullPointerException l){}
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
  public static void setLastTarget(ArmStates target){
    positions.add(target);
  }
  public static void setLastPositionTarget(ArmStates target){
    lastPositions.add(target);
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
  public static ArmStates getLastTarget() {
    return positions.peekLast();
  }
  public static ArmStates getLastPositionTarget() {
    return lastPositions.peekLast();
  }
}
