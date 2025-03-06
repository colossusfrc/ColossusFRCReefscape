package frc.robot.RCFeatures.UnitTests;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ArmUtility.ArmPositions;
import frc.robot.Constants.ArmUtility.ClawConstants;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Claw.ClawCommand;
import frc.robot.commands.Claw.ClawTestCommand;
import frc.robot.subsystems.ArmMechanisms.GarraBase;
import frc.robot.subsystems.ArmMechanisms.GarraIntake;

public class ClawUnitTest {
    private final GarraIntake garraIntake;
    private final GarraBase garraBase;
    private final CommandXboxController contrle;

    public ClawUnitTest(GarraIntake garraIntake, GarraBase garraBase, CommandXboxController contrle){
        this.garraIntake = garraIntake;
        this.garraBase = garraBase;
        this.contrle = contrle;

        unitTestClaw();
    }

    private void unitTestClaw(){
        garraIntake.setDefaultCommand(new ClawTestCommand(garraIntake, ClawConstants.feedForward));
        garraBase.setDefaultCommand(new ClawCommand(garraBase, ArmPositions.armPositions.get(ArmStates.guarda)[2]));
        contrle.leftBumper().whileTrue(new ClawTestCommand(garraIntake, ()->ClawConstants.clawDrop));
        contrle.rightBumper().whileTrue(new ClawTestCommand(garraIntake, ()->ClawConstants.clawReceive));
    }
}
