package frc.robot.RCFeatures.Interfaces;

import java.util.Arrays;
import java.util.List;

import frc.robot.RCFeatures.ArmFeatures.StateMachine;
import frc.robot.subsystems.ArmMechanisms.BracoBaixo;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

public interface ArmInterface {
     static enum ArmStates{
          l1,
          l2,
          l3,
          pega,
          guarda,
          cage,
          pegaAlgeeL2,
          pegaAlgeeL3
     };
     static StateMachine stateMachine = StateMachine.getInstance();
     static List<Braco> bracos = Arrays.asList(
     BracoBaixo.getInstance());
}
