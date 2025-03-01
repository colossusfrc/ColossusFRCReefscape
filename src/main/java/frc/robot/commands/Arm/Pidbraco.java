package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMechanisms.Braco;

public class Pidbraco extends Command{
    private final Braco braco;
    private final double target;
    private final boolean analizePid;
    private final double feedForward;
    public Pidbraco(Braco braco, double target){
        this.braco = braco;
        this.target = target;
        //sobrecarga de método para tirar a potência do braço, se necesário
        this.analizePid = false;
        this.feedForward = 0.0;
        
        addRequirements(this.braco);
    }
    public Pidbraco(Braco braco, double target, double feedForward){
        this.braco = braco;
        this.target = target;
        //sobrecarga de método para tirar a potência do braço, se necesário
        this.analizePid = false;
        this.feedForward = feedForward;
        
        addRequirements(this.braco);
    }
    public Pidbraco(Braco braco, double target, boolean analizePid, double feedForward){
        this.braco = braco;
        this.target = target;
        //sobrecarga de método para tirar a potência do braço, se necesário
        this.analizePid = analizePid;
        this.feedForward = feedForward;
        
        addRequirements(this.braco);
    }
    public Pidbraco(Braco braco, double target, boolean analizePid){
        this.braco = braco;
        this.target = target;

        this.analizePid = analizePid;

        this.feedForward = 0.0;

        addRequirements(this.braco);
    }
    @Override
    public void initialize() {
    }
    @Override
    public void execute() {
        braco.setAbsolutePosition(target, feedForward);
    }

    @Override
    public void end(boolean interrupted) {
        braco.setP();
        braco.stopArm();
    }

    @Override
    public boolean isFinished() {
        return (analizePid)?braco.getPID():false;
    }
}
