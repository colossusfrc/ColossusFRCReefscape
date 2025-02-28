// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Controle;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RCFeatures.Interfaces.ArmInterface;
import frc.robot.RCFeatures.Interfaces.ClawInterface;
import frc.robot.RCFeatures.Interfaces.IOInterface;
import frc.robot.RCFeatures.Interfaces.SwerveInterface;
import frc.robot.RCFeatures.UnitTests.ArmUnitTest;
import frc.robot.RCFeatures.UnitTests.ClawUnitTest;
import frc.robot.RCFeatures.UnitTests.SwerveUnitTest;
import frc.robot.autonomous.AutonomousFactory;
import frc.robot.subsystems.SwerveSubsystem;

public class RobotContainer implements IOInterface,
 ArmInterface,
  SwerveInterface,
    ClawInterface
   {
  public RobotContainer() {
    super();
    // Configure the trigger bindings
    configureBindings();
  }

  // Função onde os eventos (triggers) são configurados
  private void configureBindings() {
    // Definimos o comando padrão como a tração
    swerve.setDefaultCommand(
      swerve.driveCommand(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> -controleXbox.getRightX()*Controle.limit.get()));
    //testes unitários do swerve
    new SwerveUnitTest(swerve, controleXbox);
    //testes unitparios do braço
    new ArmUnitTest(controleXbox, bracos, stateMachine, garraBase);
    //testes unitarios da garra
    new ClawUnitTest(garraIntake, garraBase, controleXbox);
  }


  // Define os motores como coast ou brake
  public void setMotorBrake(boolean brake) {
    swerve.setMotorBrake(brake);
  }

  public Command getAutonomousCommand()
  {
    return swerve.getAutonomousCommand(SwerveSubsystem.getAutonomousRoutine(), true);
  }
  public Command getSequentialAutonomousCommand(){
    return AutonomousFactory.getAutonomousCommand(
      bracos,
       swerve,
        garraIntake,
         garraBase);
  }
  

}
