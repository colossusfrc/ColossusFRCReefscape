package frc.robot.autonomous;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Function;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants.AutonConstants.LimelightConstants;

public class LimelightTagGetters {
    private PIDController pid;
    public enum Alliance{red, blue};
    public enum Axis{x, y, theta};
    private Function<Double, Double> pidGetter;
    public LimelightTagGetters(Alliance fieldAttributes, Axis axis, int tagId){
        switch (axis) {
            case theta:
            pid = new PIDController(LimelightConstants.kpTheta, LimelightConstants.kiTheta, LimelightConstants.kdTheta);
            pid.setIZone(LimelightConstants.iThetaRange);
            break;
            case x:
            pid = new PIDController(LimelightConstants.kpX, LimelightConstants.kiX, LimelightConstants.kdX);
            pid.setIZone(LimelightConstants.iXRange);
            break;
            case y:
            pid = new PIDController(LimelightConstants.kpY, LimelightConstants.kiY, LimelightConstants.kdY);
            pid.setIZone(LimelightConstants.iYRange);
            break;
        }
        pidGetter = (setPoint)->{
            return getPid(getAxis(fieldAttributes, axis), setPoint);};
        LimelightHelpers.setPriorityTagID(LimelightConstants.limelightName, tagId);
    }
    private Pose2d getPose(Alliance fieldAttributes){
        return (fieldAttributes==Alliance.red)?
        LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(LimelightConstants.limelightName).pose
        :LimelightHelpers.getBotPoseEstimate_wpiRed_MegaTag2(LimelightConstants.limelightName).pose;
    }
    private Double getAxis(Alliance fieldAttributes, Axis axis){
        switch (axis) {
            case theta:
            return getPose(fieldAttributes).getRotation().getDegrees();
            case x:
            return getPose(fieldAttributes).getX();
            case y:
            return getPose(fieldAttributes).getY();
        }
        return (axis==Axis.x)?getPose(fieldAttributes).getX():getPose(fieldAttributes).getY();
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
    public BooleanSupplier getPid(){
        return ()->this.pid.atSetpoint();
    }
}