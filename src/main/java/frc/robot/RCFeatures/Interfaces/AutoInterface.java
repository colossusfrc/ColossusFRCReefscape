package frc.robot.RCFeatures.Interfaces;

import frc.robot.Constants.AutonConstants;
import frc.robot.autonomous.LimelightTagGetters;
import frc.robot.autonomous.LimelightTagGetters.Axis;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;

public interface AutoInterface {
   static LimelightTagGetters limelightTagGettersX = new LimelightTagGetters(Axis.x);
   static LimelightTagGetters limelightTagGettersY = new LimelightTagGetters(Axis.y);
   static LimelightTagGetters limelightTagGettersTheta = new LimelightTagGetters(Axis.theta);
   public static Translation2d robotPoseDueTag(){
    return new Translation2d(limelightTagGettersX.getPose(), limelightTagGettersY.getPose());
   }
}
