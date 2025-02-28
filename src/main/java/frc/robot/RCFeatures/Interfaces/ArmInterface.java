package frc.robot.RCFeatures.Interfaces;

import java.util.Arrays;
import java.util.List;

import frc.robot.RCFeatures.ArmFeatures.StateMachine;
import frc.robot.subsystems.ArmMechanisms.BracoAlto;
import frc.robot.subsystems.ArmMechanisms.BracoBaixo;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

public interface ArmInterface {
     enum ArmStates{
          l1,
          l2,
          l3,
          pega,
          guarda,
          pegaChao,
          algee,
          idle
     };
     static StateMachine stateMachine = new StateMachine();
     static List<Braco> bracos = Arrays.asList(
    new BracoAlto(stateMachine),
     new BracoBaixo(stateMachine));
}
