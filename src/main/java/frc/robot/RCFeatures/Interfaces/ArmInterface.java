package frc.robot.RCFeatures;

import java.util.Arrays;
import java.util.List;

import frc.robot.subsystems.ArmMechanisms.Braco;
import frc.robot.subsystems.ArmMechanisms.BracoAlto;
import frc.robot.subsystems.ArmMechanisms.BracoBaixo;

public interface ArmInterface {
     List<Braco> bracos = Arrays.asList(
    new BracoAlto(),
     new BracoBaixo());
}
