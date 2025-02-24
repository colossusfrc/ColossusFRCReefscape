package frc.robot.RCFeatures.UnitTests;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Constants.Controle;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveUnitTest {
    private final SwerveSubsystem swerve;
    private final CommandXboxController controleXbox;
    public SwerveUnitTest(SwerveSubsystem swerve, CommandXboxController controleXbox){
        this.swerve = swerve;
        this.controleXbox = controleXbox;
          swerveUnitTestHeadingJoysticjInputs();
    }
    private void swerveUnitTestHeading(){
        controleXbox.povDown().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
          ()->0.0, 
        ()->0.0, 
        ()->0.0, 
        ()->90));
        controleXbox.povUp().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
          ()->0.0, 
        ()->0.0, 
        ()->0.0, 
        ()->-90.0));
        controleXbox.povRight().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
          ()->0.0, 
        ()->0.0, 
        ()->90.0, 
        ()->0.0));
        controleXbox.povLeft().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
          ()->0.0, 
        ()->0.0, 
        ()->-90.0, 
        ()->0.0));
    }
    private void swerveUnitTestHeadingJoysticjInputs(){
        controleXbox.povDown().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit, Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit, Constants.Controle.DEADBAND), 
        ()->0.0, 
        ()->90));
        controleXbox.povUp().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit, Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit, Constants.Controle.DEADBAND), 
        ()->0.0, 
        ()->-90.0));
        controleXbox.povRight().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit, Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit, Constants.Controle.DEADBAND), 
        ()->90.0, 
        ()->0.0));
        controleXbox.povLeft().toggleOnTrue(swerve.driveCommandAlinharComJoystick(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit, Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit, Constants.Controle.DEADBAND), 
        ()->-90.0, 
        ()->0.0));
    }
}