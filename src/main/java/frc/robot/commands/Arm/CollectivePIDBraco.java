package frc.robot.commands.Arm;

import frc.robot.Constants;
import frc.robot.Constants.Controle;
import frc.robot.Constants.ArmUtility.ArmConstants;
import frc.robot.Constants.ArmUtility.ArmPositions;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Claw.ClawCommand;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.Superclasses.Braco;

import java.util.List;
import java.util.ResourceBundle.Control;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class CollectivePIDBraco extends Command {
    private final ArmStates armState;
    private final List<Braco> bracos;
    private final GarraBase garra;
    private boolean pid = false;
    private double time;
    public CollectivePIDBraco(ArmStates armState, List<Braco> bracos, GarraBase garra){
        this.armState  = armState;
        this.bracos = bracos;
        this.garra = garra;
        addRequirements(this.bracos.get(0), this.garra);
    }
    

    @Override
    public void initialize() {
        time = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        //diminui a potencia se !guarda
        Constants.Controle.limit = ()->((armState==ArmStates.guarda)||(armState==ArmStates.l1)||(armState==ArmStates.cage)?
            Controle.maxLimit:
            Controle.minLimit);
        Constants.Controle.turnLimit = ()->((armState==ArmStates.guarda)||(armState==ArmStates.l1)||(armState==ArmStates.cage)?
            Controle.turn:
            Controle.minTurn);
        if (armState == ArmStates.guardaL3&&(Timer.getFPGATimestamp()-time)<1) {
            
        }else if(armState == ArmStates.guardaL3){
            new Pidbraco(this.bracos.get(0), ArmPositions.armPositions.get(armState)[0]).execute();
        }else if((armState==ArmStates.startL3||armState==ArmStates.pega||armState==ArmStates.l1)&&(pid==false)){
            new Pidbraco(this.bracos.get(0), ArmPositions.armPositions.get(armState)[0]).execute();
        }else{
            new Pidbraco(this.bracos.get(0), ArmPositions.armPositions.get(armState)[0]).execute();
            //GARRA[2]
            new ClawCommand(this.garra, ArmPositions.armPositions.get(armState)[2]).execute();
        }
        pid = this.bracos.get(0).getPID();
    }

    @Override
    public void end(boolean interrupted) {
        Constants.Controle.limit = ()->Controle.maxLimit;
        Constants.Controle.turnLimit = ()->Controle.turn;
        ArmConstants.maxOutputFn = ()-> ArmConstants.kMaxOutput;
        pid = false;
     }

    @Override
    public boolean isFinished() { return false; }
}
