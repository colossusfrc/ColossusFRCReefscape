package frc.robot.commands.Arm;

import frc.robot.Constants;
import frc.robot.Constants.Controle;
import frc.robot.Constants.ArmUtility.ArmPositions;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Claw.ClawCommand;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;

public class CollectivePIDBraco extends Command {
    private final ArmStates armState;
    private final List<Braco> bracos;
    private final GarraBase garra;
    public CollectivePIDBraco(ArmStates armState, List<Braco> bracos, GarraBase garra){
        this.armState  = armState;
        this.bracos = bracos;
        this.garra = garra;
        addRequirements(this.bracos.get(0), this.garra);
    }
    

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        //diminui a potencia se !guarda
        Constants.Controle.limit = ()->((armState==ArmStates.guarda)||(armState==ArmStates.l1)||(armState==ArmStates.cage)?
            Controle.maxLimit:
            Controle.minLimit);
        //controle do BRAÇO ALTO[0]
        /*new Pidbraco(this.bracos.get(0), ArmPositions.armPositions.get(armState)[0]).execute();*/
        //controle do BRAÇO BAIXO[1]
        new Pidbraco(this.bracos.get(0), ArmPositions.armPositions.get(armState)[0]).execute();
         //GARRA[2]
        new ClawCommand(this.garra, ArmPositions.armPositions.get(armState)[2]).execute();
    }

    @Override
    public void end(boolean interrupted) {
        Constants.Controle.limit = ()->Controle.maxLimit;
     }

    @Override
    public boolean isFinished() { return false; }
}
