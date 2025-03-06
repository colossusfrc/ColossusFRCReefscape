package frc.robot.autonomous;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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
    public static Command getAutonomousCommand(){
        NamedCommands.registerCommand("Level1", setArmState(ArmStates.l1));
        NamedCommands.registerCommand("SendCoral", actuateClaw(1.0));
        NamedCommands.registerCommand("Back", setArmState(ArmStates.guarda));
        NamedCommands.registerCommand("ResTag", alignTagToPosition().withTimeout(1.0));
        NamedCommands.registerCommand("keepCoral", actuateClaw(2.0, 0.05));
        //recursos:
        //armreef: levanta o brao para l1
        //GoOut: atua a garra por 1 segundo
        //back: volta o braço do swerve
        //reset: olha a april tag e reseta a posição da câmera
        return pathplannerTrajetory();
    }
    private static Command pathplannerTrajetory(){
        return new PathPlannerAuto(SwerveSubsystem.getAutonomousPathPlanner());
    }
    public static Command alignTagToPosition(){
        if(!LimelightHelpers.getTV(LimelightConstants.limelightName))return new Command() {};
        return 
            SwerveSubsystem.getInstance().resetOdometryCommand(
                new Pose2d(AutoInterface.robotPoseDueTag(), SwerveSubsystem.getInstance().getHeading())
          );
    }
    public static Command alignTagToPosition(Pose2d pose){
        if(!LimelightHelpers.getTV(LimelightConstants.limelightName))return new Command() {};
        return 
            SwerveSubsystem.getInstance().resetOdometryCommand(
                new Pose2d(AutoInterface.robotPoseDueTag(), SwerveSubsystem.getInstance().getHeading())
          );
    }
    private static Command setArmState(ArmStates armStates){
        return new CollectivePIDBraco(armStates, ArmInterface.bracos, GarraBase.getInstance()).withTimeout(3.0);
    }
    private static Command actuateClaw(double timeout){
        return new ClawTestCommand(GarraIntake.getInstance(),()-> -0.1).withTimeout(
                        timeout
                       );
    }
    private static Command actuateClaw(double timeout, double power){
        return new ClawTestCommand(GarraIntake.getInstance(),()-> power).withTimeout(
                        timeout
                       );
    }
}
