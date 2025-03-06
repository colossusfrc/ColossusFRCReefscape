package frc.robot.autonomous;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.AutonConstants.LimelightConstants;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;

public class LimelightTagGetters extends SubsystemBase{
    private PIDController pid;
    public enum Axis{x, y, theta};
    private Alliance fieldAttributes;
    private Axis axis;
    public LimelightTagGetters(Axis axis){
        this.axis = axis;
        this.fieldAttributes = DriverStation.getAlliance().get();
        this.pid = new PIDController(0.012, 0, 0);
    }
    public double getPose(){
        LimelightHelpers.SetFiducialIDFiltersOverride(LimelightConstants.limelightName, new int[]{AutonConstants.tagIdsByLabels.get(SwerveSubsystem.getAutonomousRoutine())});
        if(!LimelightHelpers.getTV(LimelightConstants.limelightName))return 1.0;
        if(axis == Axis.x){
            return LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(LimelightConstants.limelightName).pose.getX()+0.212397;
        }else if(axis == Axis.y){
            return LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(LimelightConstants.limelightName).pose.getY()-0.2635;
        }else{ 
            return LimelightHelpers.getTX(LimelightConstants.limelightName);
        }
        
    }
    public double getOutput(double target){
        return this.pid.calculate(this.getPose(), target);
    }
    public boolean getPid(){
        return this.pid.atSetpoint();
    }
    @Override
    public void periodic() {
        SmartDashboard.putNumber(axis.toString()+" pose", getPose());
        SmartDashboard.putNumber("tag id", LimelightHelpers.getFiducialID(LimelightConstants.limelightName));
    }
}