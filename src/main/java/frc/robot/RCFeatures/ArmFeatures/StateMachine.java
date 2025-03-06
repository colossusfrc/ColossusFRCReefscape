package frc.robot.RCFeatures.ArmFeatures;

import frc.robot.Constants.ArmUtility.ClawConstants;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import edu.wpi.first.wpilibj.Timer;

public class StateMachine {
    @Deprecated
    private ArmStates anterior;
    private ArmStates atual;
    private boolean pidSpecialities = false;
    private static StateMachine instance;

    private Timer intakeTimer = new Timer();
    private boolean intakeActive = false;

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

        /*if (atual == ArmStates.l3 && hipotheticalState == ArmStates.guarda) {
            ClawConstants.clawPower = 0.05; 
            
            if (!intakeActive) {
                intakeTimer.reset();
                intakeTimer.start();
                intakeActive = true;
            }

            if (intakeTimer.get() < 2.0) {
                ClawConstants.clawPower = -0.3;
            } else {
                ClawConstants.clawPower = 0.0;
                intakeActive = false;
            }
        } else {
            ClawConstants.clawPower = 0.05;
        }*/

        if(hipotheticalState == ArmStates.cage){
            ClawConstants.clawPower = 0.0;
        }

        return hipotheticalState;
    }

    public boolean getPidSpecialitites(){ return pidSpecialities; }
}
