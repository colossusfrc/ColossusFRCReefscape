package frc.robot.RCFeatures.Interfaces;

import frc.robot.Constants.AutonConstants;
import frc.robot.autonomous.LimelightTagGetters;
import frc.robot.autonomous.LimelightTagGetters.Axis;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;

public interface AutoInterface {
   static LimelightTagGetters limelightTagGettersX = new LimelightTagGetters(DriverStation.getAlliance().get(), Axis.x, AutonConstants.tagIdsByLabels.get(SwerveSubsystem.getAutonomousRoutine()));
   static LimelightTagGetters limelightTagGettersY = new LimelightTagGetters(DriverStation.getAlliance().get(), Axis.y, AutonConstants.tagIdsByLabels.get(SwerveSubsystem.getAutonomousRoutine()));
   static LimelightTagGetters limelightTagGettersTheta = new LimelightTagGetters(DriverStation.getAlliance().get(), Axis.theta, AutonConstants.tagIdsByLabels.get(SwerveSubsystem.getAutonomousRoutine()));
   public static Translation2d robotPoseDueTag(){
    return new Translation2d(limelightTagGettersX.getValue(), limelightTagGettersY.getValue());
   }
}
