// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Controle;
import frc.robot.commands.Arm.Pidbraco;
import frc.robot.commands.Arm.SimpleArmCommand;
import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.ArmMechanisms.Braco;
import frc.robot.subsystems.ArmMechanisms.BracoAlto;
import frc.robot.subsystems.ArmMechanisms.BracoBaixo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // Aqui iniciamos o swerve
  private SwerveSubsystem swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));

  // Controle de Xbox, troque para o qual sua equipe estará utilizando
  private CommandXboxController controleXbox = new CommandXboxController(Controle.xboxControle);

  private final List<Braco> bracos = Arrays.asList(
    new BracoAlto(),
     new BracoBaixo());

  public RobotContainer() {
    // Definimos o comando padrão como a tração
    swerve.setDefaultCommand(swerve.driveCommand(
      () -> MathUtil.applyDeadband(-controleXbox.getLeftY()*Controle.limit, Constants.Controle.DEADBAND),
      () -> MathUtil.applyDeadband(-controleXbox.getLeftX()*Controle.limit, Constants.Controle.DEADBAND),
      () -> -controleXbox.getRightX()*Controle.limit));

    // Configure the trigger bindings
    configureBindings();
  }

  // Função onde os eventos (triggers) são configurados
  private void configureBindings() {
    controleXbox.a().onTrue(swerve.driveCommandAlinharComJoystick(
    () -> MathUtil.applyDeadband(controleXbox.getLeftY(), Constants.Controle.DEADBAND),
    () -> MathUtil.applyDeadband(controleXbox.getLeftX(), Constants.Controle.DEADBAND),
    () -> controleXbox.getRightX(),
    () -> controleXbox.getRightY()));


    if(!Robot.isReal()){
      //controleXbox.start().onTrue(Commands.runOnce(() -> swerve.resetOdometry(new Pose2d(3, 3, new Rotation2d()))));
    }

    armRoutineManager();
  }


  // Define os motores como coast ou brake
  public void setMotorBrake(boolean brake) {
    swerve.setMotorBrake(brake);
  }

  private void armRoutineManager(){
    bracos.forEach(
      b->b.setDefaultCommand(
      (b.getLastTarget()!=null)?
      new Pidbraco(b, b.getLastTarget()):
      new SimpleArmCommand(b, 0.0)));

    armUnitTest();
  }
  private void armUnitTest(){
    controleXbox.a().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), 90),
        new Pidbraco(bracos.get(1), 90)
      ));
    controleXbox.x().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), 45),
        new Pidbraco(bracos.get(1), 90)
      ));
    controleXbox.y().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), -80),
        new Pidbraco(bracos.get(1), 45)
      ));
    controleXbox.start().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), 90),
        new Pidbraco(bracos.get(1), 25)
      ));
  }
}
