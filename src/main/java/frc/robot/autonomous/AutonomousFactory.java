package frc.robot.autonomous;

import java.util.List;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.AutonConstants.LimelightConstants;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.autonomous.LimelightTagGetters.Axis;
import frc.robot.commands.Arm.CollectivePIDBraco;
import frc.robot.commands.Claw.ClawTestCommand;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;

public class AutonomousFactory extends RobotContainer{
    private static Command trajectoryThroughReef(){
        return new PathPlannerAuto(SwerveSubsystem.getAutonomousRoutine()).withTimeout(2.0);
    }
    private static Command alignRobotToTag(Pose2d pose2d){
        return new AutoAlignTag(swerveSubsystem,
        pose2d,
         limelightTagGettersX,
          limelightTagGettersY,
            limelightTagGettersTheta);
    }
    private Command setArmStateWithTimeout(ArmStates targState, List<Braco> bracos, GarraIntake garraIntake){
        return new CollectivePIDBraco(targState, bracos, garraIntake)
                   .withTimeout(
                    LimelightConstants.armTimeout
                   );
    }
    private Command activateIntakeWithTimeout(GarraIntake garraIntake, double timeout){
        return new ClawTestCommand(garraIntake, -0.1).withTimeout(
            LimelightConstants.armTimeout
           );
    }
    public static Command fabricateAutonomous(){
        String path = SwerveSubsystem.getAutonomousRoutine();
        Alliance fieldAttributes = (DriverStation.getAlliance().get() == Alliance.Red)?Alliance.Red:Alliance.Blue;
    return new SequentialCommandGroup(
        new PathPlannerAuto(SwerveSubsystem.getAutonomousRoutine()).withTimeout(2.0),
            new AutoAlignTag(swerve,
                 LimelightConstants.cameraOffsets,
                  limelightTagGettersX, limelightTagGettersY,
                   limelightTagGettersTheta),
                new CollectivePIDBraco(ArmStates.l3, bracos, garraIntake)
                   .withTimeout(
                    LimelightConstants.armTimeout
                   ),
                    new ClawTestCommand(garraIntake, -0.1).withTimeout(
                        LimelightConstants.armTimeout
                       ),
                        new CollectivePIDBraco(ArmStates.guarda, bracos, garraIntake).withTimeout(
                            LimelightConstants.armTimeout
                           )
        );
    }
}
