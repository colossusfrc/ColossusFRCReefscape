package frc.robot.commands.Arm;

import frc.robot.Constants.ArmUtility.ArmPositions;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.subsystems.ArmMechanisms.Braco;

import java.util.List;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj2.command.Command;

public class CollectivePIDBraco extends Command {
    private final ArmStates armState;
    private final List<Braco> bracos;

    public CollectivePIDBraco(ArmStates armState, List<Braco> bracos){
        this.armState = armState;
        this.bracos = bracos;
        addRequirements(bracos.get(0), bracos.get(1));
    }

    @Override
    public void initialize() {
        Braco.setLastTarget(armState);
    }

    @Override
    public void execute() {
        new Pidbraco(this.bracos.get(0), ArmPositions.armPositions.get(armState)[0]).execute();
        new Pidbraco(this.bracos.get(1), ArmPositions.armPositions.get(armState)[1]).execute();
    }

    @Override
    public void end(boolean interrupted) {
        Braco.setLastPositionTarget(armState);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
