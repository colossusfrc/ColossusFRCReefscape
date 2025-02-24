// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Controle;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RCFeatures.Interfaces.ArmInterface;
import frc.robot.RCFeatures.Interfaces.IOInterface;
import frc.robot.RCFeatures.Interfaces.SwerveInterface;
import frc.robot.RCFeatures.UnitTests.ArmUnitTest;
import frc.robot.RCFeatures.UnitTests.SwerveUnitTest;

public class RobotContainer implements IOInterface, ArmInterface, SwerveInterface{
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  // Função onde os eventos (triggers) são configurados
  private void configureBindings() {
    // Definimos o comando padrão como a tração
    swerve.setDefaultCommand(
      swerve.driveCommand(
        () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit, Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit, Constants.Controle.DEADBAND),
        () -> -controleXbox.getRightX()*Controle.limit));
    //testes unitários do swerve
    new SwerveUnitTest(swerve, controleXbox);
    //testes unitparios do braço
    new ArmUnitTest(controleXbox, bracos);
  }


  // Define os motores como coast ou brake
  public void setMotorBrake(boolean brake) {
    swerve.setMotorBrake(brake);
  }

  public Command getAutonomousCommand()
  {
    //return swerve.getAutonomousCommand(swerve.getAutonomousRoutine(), true);
    return null;
  }
  

}
