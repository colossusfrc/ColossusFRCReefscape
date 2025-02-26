package frc.robot.RCFeatures.UnitTests;

import frc.robot.commands.Claw.ClawCommand;
import frc.robot.subsystems.ArmMechanisms.Garra;

public class ClawUnitTest {
    private final Garra garra;

    public ClawUnitTest(Garra garra){
        this.garra = garra;

        unitTestClaw();
    }

    private void unitTestClaw(){
        garra.setDefaultCommand(new ClawCommand(garra, 0.0));
    }
}
