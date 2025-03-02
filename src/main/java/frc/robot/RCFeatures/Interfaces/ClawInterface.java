package frc.robot.RCFeatures.Interfaces;

import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;

public interface ClawInterface {
    static GarraIntake garraIntake = GarraIntake.getInstance();
    static GarraBase garraBase = GarraBase.getInstance();
}
