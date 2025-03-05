package frc.robot.autonomous;

import java.util.List;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.AutonConstants.LimelightConstants;
import frc.robot.RCFeatures.Interfaces.ArmInterface;
import frc.robot.RCFeatures.Interfaces.AutoInterface;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Arm.CollectivePIDBraco;
import frc.robot.commands.Claw.ClawTestCommand;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;

public class AutonomousFactory {
    public static Command getAutonomousCommand(){
        return new SequentialCommandGroup(
        pathplannerTrajetoryWithTimeout(SwerveSubsystem.getAutonomousRoutine(), 2.0),
            alignTagToPosition(AutonConstants.cameraOffsets.get(SwerveSubsystem.getAutonomousRoutine())).andThen(
                SwerveSubsystem.getInstance().resetOdometryCommand(new Pose2d(AutoInterface.robotPoseDueTag(), AutonConstants.cameraTargetHeadings.get(SwerveSubsystem.getAutonomousRoutine())))),
                    setArmState(ArmStates.l3, LimelightConstants.armTimeout),
                        actuateClaw(LimelightConstants.armTimeout),
                            setArmState(ArmStates.guarda, LimelightConstants.armTimeout)
        );
    }
    private static Command pathplannerTrajetoryWithTimeout(String trajectory, double timeout){
        return new PathPlannerAuto(SwerveSubsystem.getAutonomousRoutine()).withTimeout(timeout);
    }
    public static Command alignTagToPosition(Pose2d pose){
        return new AutoAlignTag(SwerveSubsystem.getInstance(),
        pose,
         AutoInterface.limelightTagGettersX, AutoInterface.limelightTagGettersY,
          AutoInterface.limelightTagGettersTheta);
    }
    private static Command setArmState(ArmStates armStates, double timeout){
        return new CollectivePIDBraco(ArmStates.l3, ArmInterface.bracos, GarraIntake.getInstance())
        .withTimeout(
         timeout
        );
    }
    private static Command actuateClaw(double timeout){
        return new ClawTestCommand(GarraIntake.getInstance(), -0.1).withTimeout(
                        timeout
                       );
    }
}
