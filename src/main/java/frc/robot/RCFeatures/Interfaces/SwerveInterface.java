package frc.robot.RCFeatures.Interfaces;

import java.io.File;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.subsystems.SwerveSubsystem;

public interface SwerveInterface {
  // Aqui iniciamos o swerve
    SwerveSubsystem swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
}
