package frc.robot.RCFeatures.ArmFeatures;

import java.util.List;
import java.util.Objects;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Arm.CollectivePIDBraco;
import frc.robot.subsystems.ArmMechanisms.Braco;

public abstract class ArmCommandSelector {
    public static Command getArmCommand(ArmStates armState, List<Braco> bracos){
        if(Braco.getLastPositionTarget()==ArmStates.pega&&
            armState != ArmStates.guarda){
            return new CollectivePIDBraco(ArmStates.guarda, bracos);
        }
            return new CollectivePIDBraco(armState, bracos);
        
    }
    /*public static Command getArmCommand(ArmStates armState, List<Braco> braco){
        Double[] positions = ArmPositions.armPositions.get(armState);
        Command armPerformance = null;
        switch (armState) {
            case guarda:
                armPerformance = new ParallelCommandGroup(
                new Pidbraco(braco.get(0), positions[0]),
                new Pidbraco(braco.get(1), positions[1])
                );
                break;
            case idle:
                armState = ArmStates.guarda;
                break;
            case l1:
                armPerformance = new ParallelCommandGroup(
                      new Pidbraco(braco.get(0),positions[0]),
                      new Pidbraco(braco.get(1), positions[1])
                    );
                break;
            case l2:
                armPerformance = new ParallelCommandGroup(
                      new Pidbraco(braco.get(0), positions[0]),
                      new Pidbraco(braco.get(1), positions[1])
                    );
                break;
            case l3:
                armPerformance = new ParallelCommandGroup(
                    new Pidbraco(braco.get(0), positions[0]),
                    new Pidbraco(braco.get(1), positions[1])
                );
                
                break;
            case pega:
                armPerformance = new ParallelCommandGroup(
                    new Pidbraco(braco.get(0), positions[0]),
                    new Pidbraco(braco.get(1), positions[1])
                );
                break;
            case pegaChao:
                armPerformance = new ParallelCommandGroup(
                    new Pidbraco(braco.get(0), positions[0]),
                    new Pidbraco(braco.get(1), positions[1])
                );
                break;
            default:
                armState = ArmStates.guarda;
        }

        return armPerformance;
    }*/
}
