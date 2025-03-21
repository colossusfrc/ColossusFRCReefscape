// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Controle;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RCFeatures.Interfaces.ArmInterface;
import frc.robot.RCFeatures.Interfaces.AutoInterface;
import frc.robot.RCFeatures.Interfaces.ClawInterface;
import frc.robot.RCFeatures.Interfaces.IOInterface;
import frc.robot.RCFeatures.Interfaces.SwerveInterface;
import frc.robot.RCFeatures.UnitTests.ArmUnitTest;
import frc.robot.RCFeatures.UnitTests.ClawUnitTest;
import frc.robot.RCFeatures.UnitTests.SwerveUnitTest;
import frc.robot.autonomous.AutonomousFactory;

public class RobotContainer implements IOInterface,
 ArmInterface,
  SwerveInterface,
    ClawInterface,
      AutoInterface
   {

  /*Camera camera = new Camera("camera 1");
  Camera camera2 = new Camera("camera 2");*/

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
        () -> MathUtil.applyDeadband(-controleXbox.getRawAxis(1)*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> MathUtil.applyDeadband(-controleXbox.getRawAxis(0)*Controle.limit.get(), Constants.Controle.DEADBAND),
        () -> -controleXbox.getRawAxis(4)*Controle.turnLimit.get()));
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
   return AutonomousFactory.getAutonomousCommand();
  }
}
 
