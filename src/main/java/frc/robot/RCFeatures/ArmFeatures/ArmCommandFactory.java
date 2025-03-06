package frc.robot.RCFeatures.ArmFeatures;

import java.util.List;
import java.util.Map;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Arm.CollectivePIDBraco;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

public abstract class ArmCommandFactory {
    public static boolean stCmd = false;
    public static Command getArmCommand(ArmStates armState, List<Braco> bracos, StateMachine stateMachine, GarraBase garra){
        stCmd = true;
        Command selectCommand = new SelectCommand<>(Map.ofEntries(
            Map.entry(ArmStates.l1, new CollectivePIDBraco(ArmStates.l1, bracos, garra)),
            Map.entry(ArmStates.l2, new CollectivePIDBraco(ArmStates.l2, bracos, garra)),
            Map.entry(ArmStates.l3, new CollectivePIDBraco(ArmStates.l3, bracos, garra)),
            Map.entry(ArmStates.pega, new CollectivePIDBraco(ArmStates.pega, bracos, garra)),
            Map.entry(ArmStates.cage, new CollectivePIDBraco(ArmStates.cage, bracos, garra)),
            Map.entry(ArmStates.guarda, new CollectivePIDBraco(ArmStates.guarda, bracos, garra)),
            Map.entry(ArmStates.pegaAlgeeL2, new CollectivePIDBraco(ArmStates.pegaAlgeeL2, bracos, garra)),
            Map.entry(ArmStates.pegaAlgeeL3, new CollectivePIDBraco(ArmStates.pegaAlgeeL3, bracos, garra))
        ), ()->{
            stateMachine.setEstado(armState);
            return stateMachine.getAtual();
        });
        return selectCommand;
    }
}
