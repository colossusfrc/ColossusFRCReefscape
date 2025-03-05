package frc.robot.autonomous;

import java.util.HashMap;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.AutonConstants.LimelightConstants;

public class LimelightTagGetters extends SubsystemBase{
    private PIDController pid;
    public enum Axis{x, y, theta};
    private Function<Double, Double> pidGetter;
    private Alliance fieldAttributes;
    private Axis axis;
    private static HashMap<Axis, PIDController> getPid = new HashMap<>();
        static private HashMap<Axis, Double> getIzone = new HashMap<>();
        static{
            LimelightHelpers.setCameraPose_RobotSpace(AutonConstants.LimelightConstants.limelightName, AutonConstants.LimelightConstants.limelightV0[0], AutonConstants.LimelightConstants.limelightV0[1], AutonConstants.LimelightConstants.limelightV0[2], AutonConstants.LimelightConstants.limelightV0[3], AutonConstants.LimelightConstants.limelightV0[4], AutonConstants.LimelightConstants.limelightV0[5]);
            getPid.put(Axis.x, new PIDController(LimelightConstants.kpX, LimelightConstants.kiX, LimelightConstants.kdX));
        getPid.put(Axis.y, new PIDController(LimelightConstants.kpY, LimelightConstants.kiY, LimelightConstants.kdY));
        getPid.put(Axis.theta, new PIDController(LimelightConstants.kpTheta, LimelightConstants.kiTheta, LimelightConstants.kdTheta));
        getIzone.put(Axis.x, LimelightConstants.iXRange);
        getIzone.put(Axis.y, LimelightConstants.iYRange);
        getIzone.put(Axis.theta, LimelightConstants.iThetaRange);
    }
    public LimelightTagGetters(Alliance fieldAttributes, Axis axis, int tagId){
        LimelightHelpers.SetFiducialIDFiltersOverride(LimelightConstants.limelightName, new int[]{tagId});
        this.pid = getPid.get(axis);
        this.pid.setIZone(getIzone.get(axis));
        this.pid.setTolerance(getIzone.get(axis));
        this.fieldAttributes = fieldAttributes;
        this.axis = axis;
        pidGetter = (setPoint)->{
            boolean isVisible = LimelightHelpers.getTV(LimelightConstants.limelightName);
            return (isVisible)?getPid(getAxis(fieldAttributes, axis), setPoint):0.0;};
        LimelightHelpers.setPriorityTagID(LimelightConstants.limelightName, tagId);
    }
    private static Pose2d getPose(Alliance fieldAttributes){
        return (fieldAttributes==Alliance.Red)?
        LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(LimelightConstants.limelightName).pose
        :LimelightHelpers.getBotPoseEstimate_wpiRed_MegaTag2(LimelightConstants.limelightName).pose;
    }
    private static Double getAxis(Alliance fieldAttributes, Axis axis){
        boolean isVisible = LimelightHelpers.getTV(LimelightConstants.limelightName);
        if(!isVisible)return 0.0;
        try{ switch (axis) {
                case theta:return LimelightHelpers.getRawFiducials(LimelightConstants.limelightName)[0].txnc;
                case x:return getPose(fieldAttributes).getX();
                default:return getPose(fieldAttributes).getY();
                }
        }catch(ArrayIndexOutOfBoundsException e){}
        return getPose(fieldAttributes).getY();
    }
    private Double getPid(double measurement,double setPoint){ 
        Double output = this.pid.calculate(measurement, setPoint);
        return 
        (Math.abs(output)>=LimelightConstants.kMaxOutput)?
        Math.signum(output)*LimelightConstants.kMaxOutput:
        output;
    }

    public DoubleSupplier getOutput(Double alvo){
        return ()-> this.pidGetter.apply(alvo);
    }
    public boolean getPid(){
        return Math.abs(this.pid.getError())<LimelightConstants.iXRange;
    }
    public double getValue(){ return getAxis(fieldAttributes, axis); }
    @Override
    public void periodic() {
        try{
            SmartDashboard.putNumber(axis.toString()+" value", getValue());
            SmartDashboard.putBoolean(axis.toString()+" pid", getPid());
            SmartDashboard.putNumber(axis+" erro", this.pid.getError());
        }catch(NullPointerException e){e.printStackTrace();}
        
    }
}
