package frc.robot.RCFeatures.ArmFeatures;

import frc.robot.Constants.ArmUtility.ArmConstants;
import frc.robot.Constants.ArmUtility.ClawConstants;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import edu.wpi.first.wpilibj.Timer;

public class StateMachine {
    @Deprecated
    private ArmStates anterior;
    private ArmStates atual;
    private boolean pidSpecialities = false;
    private static StateMachine instance;

    public static StateMachine getInstance(){
        if(instance == null){
            instance = new StateMachine();
        }
        return instance;
    }

    private StateMachine(){
        this.anterior = ArmStates.guarda;
        this.atual = ArmStates.guarda;
    }

    public ArmStates getAnterior() { 
        return this.anterior; 
    }
    public ArmStates getAtual() { 
        return this.atual; 
    }

    public void setEstado(ArmStates novoEstado) {
        ArmStates estadoFinal = stateConditions(novoEstado);
        if (estadoFinal != atual) {
            anterior = atual;
            atual = estadoFinal;
        }
    }

    private ArmStates stateConditions(ArmStates hipotheticalState) {
        this.pidSpecialities = (atual == ArmStates.pega && hipotheticalState == ArmStates.guarda);

        if(hipotheticalState == ArmStates.cage){
            ClawConstants.clawPower = 0.0;
        } else if(atual == ArmStates.l3 && hipotheticalState == ArmStates.guarda){
            new Thread(
                ()->{
                    double zeroTIme = Timer.getFPGATimestamp();
                    while(Timer.getFPGATimestamp()-zeroTIme<2){
                        ClawConstants.clawPower = -0.3;
                    }
                    ClawConstants.clawPower = 0.05;
                }
            ).start();
            return ArmStates.guardaL3;
        } else if (atual == ArmStates.l3 && hipotheticalState == ArmStates.pega) {
            new Thread(
                ()->{
                    double zeroTIme = Timer.getFPGATimestamp();
                    while(Timer.getFPGATimestamp()-zeroTIme<2){
                        ClawConstants.clawPower = -0.3;
                    }
                    ClawConstants.clawPower = 0.05;
                }
            ).start();
        } else if(hipotheticalState == ArmStates.guarda && atual == ArmStates.cage) {
            ArmConstants.maxOutputFn = ()->0.5;
        } else {
            ClawConstants.clawPower = 0.05;
        }

        return hipotheticalState;
    }

    public boolean getPidSpecialitites(){ return pidSpecialities; }
}
