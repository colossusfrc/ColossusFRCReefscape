package frc.robot.RCFeatures.ArmFeatures;

import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;

public class StateMachine {
    @Deprecated
    private ArmStates anterior;
    private ArmStates atual;

    public StateMachine(){
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
        ArmStates estadoFinal = stateCondiitons(novoEstado);
        if (estadoFinal != atual) {
            anterior = atual;
            atual = estadoFinal;
        }
    }
    //condições de passagem dos estados
    private ArmStates stateCondiitons(ArmStates hipotheticalState) {
        if((atual==ArmStates.pega||atual == ArmStates.pegaAlgeeL2)&&hipotheticalState!=ArmStates.guarda)return ArmStates.guarda;
        return hipotheticalState;
    }
}

