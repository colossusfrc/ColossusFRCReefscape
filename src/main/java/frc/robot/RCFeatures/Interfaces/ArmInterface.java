package frc.robot.RCFeatures.Interfaces;

import java.util.Arrays;
import java.util.List;

import frc.robot.subsystems.ArmMechanisms.Braco;
import frc.robot.subsystems.ArmMechanisms.BracoAlto;
import frc.robot.subsystems.ArmMechanisms.BracoBaixo;

public interface ArmInterface {
     static List<Braco> bracos = Arrays.asList(
    new BracoAlto(),
     new BracoBaixo());
     enum ArmStates{
          l1,
          l2,
          l3,
          pega,
          guarda,
          pegaChao,
          idle
     };
}
