package frc.robot.autonomous;

import java.util.List;

import com.pathplanner.lib.auto.NamedCommands;
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
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;

public class AutonomousFactory {
    public static boolean stCmd;
    public static Command getAutonomousCommand(){
        NamedCommands.registerCommand("ArmReef", setArmState(ArmStates.l1, 2));
        NamedCommands.registerCommand("Out", actuateClaw(2));
        NamedCommands.registerCommand("Back", setArmState(ArmStates.guarda, 1));
        NamedCommands.registerCommand("reset", alignTagToPosition());
        return pathplannerTrajetory();
    }
    private static Command pathplannerTrajetory(){
        return new PathPlannerAuto(SwerveSubsystem.getAutonomousRoutine());
    }
    public static Command alignTagToPosition(){
        return 
            SwerveSubsystem.getInstance().resetOdometryCommand(
                new Pose2d(AutoInterface.robotPoseDueTag(), SwerveSubsystem.getInstance().getHeading())
          );
    }
    public static Command alignTagToPosition(Pose2d pose){
        return 
            SwerveSubsystem.getInstance().resetOdometryCommand(
                new Pose2d(AutoInterface.robotPoseDueTag(), SwerveSubsystem.getInstance().getHeading())
          );
    }
    private static Command setArmState(ArmStates armStates, double timeout){
        return new CollectivePIDBraco(armStates, ArmInterface.bracos, GarraBase.getInstance());
    }
    private static Command actuateClaw(double timeout){
        return new ClawTestCommand(GarraIntake.getInstance(),()-> -0.1).withTimeout(
                        timeout
                       );
    }
}
