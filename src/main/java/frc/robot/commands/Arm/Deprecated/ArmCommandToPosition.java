// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm.Deprecated;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

/** An example command that uses an example subsystem. */
@Deprecated
public class ArmCommandToPosition extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Braco arm;
  private final double power;
  private final double targAngle;
  private final boolean analyzePID;
  public PIDController PIDArm;

  public ArmCommandToPosition(
  Braco arm,
   double targAngle,
   double power,
   boolean analyzePID) {
    this.arm = arm;
    this.targAngle = targAngle;
    this.power = power;
    this.analyzePID = analyzePID;
    PIDArm = new PIDController(0.025 , 0.0, 0.0);
    PIDArm.setTolerance(0.1);
    addRequirements(arm);
  }

  @Override
  public void initialize() {
    PIDArm.enableContinuousInput(0, 360);
    PIDArm.reset();
    PIDArm.setSetpoint(targAngle);
  }
  @Override
  public void execute() {
    double appliedPower = PIDArm.calculate(arm.getIncrementalAngle(), targAngle);
    appliedPower=(Math.abs(appliedPower)>power)?Math.signum(appliedPower)*power:appliedPower;
    //appliedPower*=((Math.signum(appliedPower)<0&&arm.getAbsoluteAngle()<-30.0)||(Math.signum(appliedPower)>0&&arm.getAbsoluteAngle()>145.0))?-1.0:1.0;
    arm.setArm(appliedPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.stopArm();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   if(!analyzePID){
    return false;
   }else{
    return PIDArm.atSetpoint();
   }
  }
  public boolean pid(){
    return PIDArm.atSetpoint();
  }
}