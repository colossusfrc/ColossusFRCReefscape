package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.Braco;

public class Pidbraco extends Command{
    private final Braco braco;
    private final double target;
    private final boolean analizePid;
    public Pidbraco(Braco braco, double target){
        this.braco = braco;
        this.target = target;
        //sobrecarga de método para tirar a potência do braço, se necesário
        this.analizePid = false;
        
        addRequirements(this.braco);
    }
    public Pidbraco(Braco braco, double target, boolean analizePid){
        this.braco = braco;
        this.target = target;

        this.analizePid = analizePid;

        addRequirements(this.braco);
    }
    @Override
    public void execute() {
        braco.setAbsolutePosition(target);
    }

    @Override
    public void end(boolean interrupted) {
        braco.stopArm();
    }

    @Override
    public boolean isFinished() {
        return (analizePid)?braco.getPID():false;
    }
}
