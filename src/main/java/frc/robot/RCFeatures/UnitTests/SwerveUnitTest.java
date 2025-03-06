package frc.robot.RCFeatures.UnitTests;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Constants.AutonConstants;
import frc.robot.Constants.Controle;
import frc.robot.RCFeatures.Interfaces.AutoInterface;
import frc.robot.autonomous.AlignTagSwerve;
import frc.robot.autonomous.AutoAlignTag;
import frc.robot.autonomous.AutonomousFactory;
import frc.robot.subsystems.SwerveMechanisms.SwerveSubsystem;

public class SwerveUnitTest {
    private final SwerveSubsystem swerve;
    private final CommandXboxController controleXbox;
    public SwerveUnitTest(SwerveSubsystem swerve, CommandXboxController controleXbox){
        this.swerve = swerve;
        this.controleXbox = controleXbox;
          alignTagHeading();
    }
    private void alignTagHeading(){
      controleXbox.povUp().toggleOnTrue(
        new AlignTagSwerve(
        () -> MathUtil.applyDeadband(controleXbox.getRawAxis(1)*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(controleXbox.getRawAxis(0)*Controle.limit.get(), Constants.Controle.DEADBAND),
        new Pose2d(0.0, 0.0, new Rotation2d(0.0)),
        swerve,
        AutoInterface.limelightTagGettersTheta));
    }
    private void alignTagCommand(){
      controleXbox.povUp().toggleOnTrue(AutonomousFactory.alignTagToPosition(AutonConstants.cameraOffsets.get(SwerveSubsystem.getAutonomousRoutine())));
    controleXbox.povDown().onTrue(SwerveSubsystem.getInstance().resetOdometryCommand(
      new Pose2d(AutoInterface.robotPoseDueTag(),
      AutonConstants.cameraTargetHeadings.get(SwerveSubsystem.getAutonomousRoutine()))));
    }
    private void swerveUnitTestHeading(){
        controleXbox.povDown().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
          ()->0.0, 
        ()->0.0, 
        ()->0.0, 
        ()->60));
        controleXbox.povUp().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
          ()->0.0, 
        ()->0.0, 
        ()->0.0, 
        ()->-60.0));
    }
    private void swerveUnitTestHeadingJoysticjInputs(){
        controleXbox.povDown().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit.get(), Constants.Controle.DEADBAND), 
        ()->0.0, 
        ()->90));
        controleXbox.povUp().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit.get(), Constants.Controle.DEADBAND), 
        ()->0.0, 
        ()->-90.0));
        controleXbox.povRight().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit.get(), Constants.Controle.DEADBAND), 
        ()->90.0, 
        ()->0.0));
        controleXbox.povLeft().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit.get(), Constants.Controle.DEADBAND), 
        ()->-90.0, 
        ()->0.0));
    }
}