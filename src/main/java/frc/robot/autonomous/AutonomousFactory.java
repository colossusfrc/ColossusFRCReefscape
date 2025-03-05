package frc.robot.autonomous;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
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
        
        pulbishToPathPlanner();

        return pathplannerRoutine(SwerveSubsystem.getAutonomousPathPlanner());
    }
    private static void pulbishToPathPlanner(){
        NamedCommands.registerCommand("AlignTagReef", alignTagToPosition(AutonConstants.cameraOffsets.get(SwerveSubsystem.getAutonomousRoutine())));
        NamedCommands.registerCommand("SetArmStateReef", setArmState(ArmStates.l3, LimelightConstants.armTimeout));
        NamedCommands.registerCommand("Drop", actuateClaw(LimelightConstants.armTimeout));
        NamedCommands.registerCommand("BackArm", setArmState(ArmStates.guarda, LimelightConstants.armTimeout));
        NamedCommands.registerCommand("AlgeeArm", setArmState(ArmStates.pegaAlgeeL2, LimelightConstants.armTimeout));
        NamedCommands.registerCommand("KeepAlgee", actuateClaw(LimelightConstants.armTimeout, 0.3));
        NamedCommands.registerCommand("ArmProcessor", setArmState(ArmStates.pegaAlgeeChao, LimelightConstants.armTimeout));
        NamedCommands.registerCommand("ArmProcessor", setArmState(ArmStates.pegaAlgeeChao, LimelightConstants.armTimeout));
        NamedCommands.registerCommand("CathCoral", setArmState(ArmStates.pega, LimelightConstants.armTimeout));
    }
    private static Command pathplannerRoutine(String trajectory){
        return new PathPlannerAuto(trajectory);
    }
    private static Command pathplannerTrajetoryWithTimeout(String trajectory, double timeout){
        return new PathPlannerAuto(SwerveSubsystem.getAutonomousRoutine()).withTimeout(timeout);
    }
    public static Command alignTagToPosition(Pose2d pose){
        return new AutoAlignTag(SwerveSubsystem.getInstance(),
        pose,
         AutoInterface.limelightTagGettersX, AutoInterface.limelightTagGettersY,
          AutoInterface.limelightTagGettersTheta);
          //.andThen(SwerveSubsystem.getInstance().resetOdometryCommand(new Pose2d(AutoInterface.robotPoseDueTag(),AutonConstants.cameraTargetHeadings.get(SwerveSubsystem.getAutonomousRoutine()))));
    }
    private static Command setArmState(ArmStates armStates, double timeout){
        return new CollectivePIDBraco(armStates, ArmInterface.bracos, GarraIntake.getInstance())
        .withTimeout(
         timeout
        );
    }
    private static Command actuateClaw(double timeout){
        return new ClawTestCommand(GarraIntake.getInstance(), -0.1).withTimeout(
                        timeout
        );
    }
    private static Command actuateClaw(double timeout, double power){
            return new ClawTestCommand(GarraIntake.getInstance(), power).withTimeout(
                            timeout
    );
    }

}