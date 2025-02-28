package frc.robot.autonomous;

import java.util.List;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.AutonConstants.LimelightConstants;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.autonomous.LimelightTagGetters.Axis;
import frc.robot.commands.Arm.CollectivePIDBraco;
import frc.robot.commands.Claw.ClawTestCommand;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.ArmMechanisms.Braco;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;

public class AutonomousFactory {
    public static Command getAutonomousCommand(
    List<Braco> bracos,
     SwerveSubsystem swerveSubsystem,
      GarraIntake garraIntake,
       GarraBase garraBase){
        String path = SwerveSubsystem.getAutonomousRoutine();
        Alliance fieldAttributes = (DriverStation.getAlliance().get() == Alliance.Red)?
        Alliance.Red:
        Alliance.Blue;
        LimelightTagGetters limelightTagGettersX = new LimelightTagGetters(fieldAttributes, Axis.x, AutonConstants.tagIdsByLabels.get(path));
         LimelightTagGetters limelightTagGettersY = new LimelightTagGetters(fieldAttributes, Axis.y, AutonConstants.tagIdsByLabels.get(path));
          LimelightTagGetters limelightTagGettersTheta = new LimelightTagGetters(fieldAttributes, Axis.theta, AutonConstants.tagIdsByLabels.get(path));
    return new SequentialCommandGroup(
        new PathPlannerAuto(SwerveSubsystem.getAutonomousRoutine()),
            new AutoAlignTag(swerveSubsystem,
                 LimelightConstants.cameraOffsets,
                  limelightTagGettersX, limelightTagGettersY,
                   limelightTagGettersTheta),
                new CollectivePIDBraco(ArmStates.l3, bracos, garraIntake)
                   .withTimeout(
                    LimelightConstants.armTimeout
                   ),
                    new ClawTestCommand(garraIntake, -0.1),
                        new CollectivePIDBraco(ArmStates.guarda, bracos, garraIntake)
        );
    }
}
