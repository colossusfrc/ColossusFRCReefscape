package frc.robot.subsystems.ArmMechanisms;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.ArmUtility;
import frc.robot.Constants.ArmUtility.ArmConstants;
import frc.robot.RCFeatures.ArmFeatures.StateMachine;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

/**
 * BracoBaixo
 */
public class BracoBaixo extends Braco{

  //private SparkMax motorAuxiliar = new SparkMax(12, MotorType.kBrushless);

  private double lastValue = 0.0;

  private double currentValue;

  private double rotations = 0.0;

  private final Supplier<Double> getTreatedMotion;

  private final double initialComplementarAngle = 14.0;

  private static BracoBaixo instance;
    public static synchronized BracoBaixo getInstance(){
        if(instance==null){
        instance = new BracoBaixo(StateMachine.getInstance());
        }
        return instance;
    }

    private BracoBaixo(StateMachine stateMachine){
    super(ArmConstants.id,
    ArmConstants.idEncoder,
     ArmConstants.conversionFactor,
     stateMachine);
    super.pidController.setTolerance(2);
    getTreatedMotion = () -> {
        currentValue = -super.enCycleAdv.get();

        if(lastValue>0.9 && currentValue< 0.1){
            rotations++;
        }else if(lastValue<0.1 && currentValue>0.9){
            rotations--;
        }

        lastValue = currentValue;

        return currentValue + rotations + ArmConstants.offset;
    };
  }
  @Override
  protected void motorConfig() {
      super.motorConfig();
      config.inverted(true);
  }
  @Override
  public void periodic() {
      super.periodic();
      SmartDashboard.putNumber("Value Baixo", super.enCycleAdv.get());
      SmartDashboard.putNumber("treated value", getTreatedMotion.get());
      SmartDashboard.putNumber("Absolute angle braco Baixo", getAbsoluteAngle());
  
      currentValue = getTreatedMotion.get();
  }

  @Override
  public double getAbsolutePosition() {
    double value = getTreatedMotion.get()*conversionFactor*360;
    return (value<=360.0)?value:value-360.0;
  }
  @Override
  public void setArm(double power) {
    super.setArm(-power);
    //this.motorAuxiliar.set(power);
  }

  @Override
  public double getAbsoluteAngle() {
    double angle;
    angle =  getAbsolutePosition() + initialComplementarAngle;
    while(Math.abs(angle)>360)angle -= Math.signum(angle)*360;
    while(Math.abs(angle)>180)angle = -Math.signum(angle)*(360-Math.abs(angle));
    return angle;
  }

  @Override
  public void setAbsolutePosition(double position, double feedForward) {
    double nonTreatedPower = pidController.calculate(getAbsoluteAngle(), position);

    double treatedPower = (Math.abs(nonTreatedPower)>ArmUtility.ArmConstants.kMaxOutput)?Math.signum(nonTreatedPower)*ArmConstants.kMaxOutput:nonTreatedPower;

    setArm(treatedPower+feedForward);

  }
  @Override
  public boolean getHasChangedPID() {
    return false;
  }
  @Override
  public void setP() {
    return;
  }
}