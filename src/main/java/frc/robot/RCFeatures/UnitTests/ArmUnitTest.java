package frc.robot.RCFeatures.UnitTests;

import java.util.List;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.RCFeatures.ArmFeatures.ArmCommandSelector;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import frc.robot.commands.Arm.Pidbraco;
import frc.robot.subsystems.ArmMechanisms.Braco;

public class ArmUnitTest{
    private final CommandXboxController controleXbox;
    private final List<Braco> bracos;

    public ArmUnitTest(CommandXboxController controleXbox, List<Braco> bracos){
        this.controleXbox = controleXbox;
        this.bracos = bracos;

        armRoutineManager();
    }
    private void armRoutineManager(){
      bracos.get(0).setDefaultCommand(
        new Pidbraco(bracos.get(0), Constants.ArmUtility.ArmPositions.armPositions.get(ArmStates.guarda)[0])
      );
      bracos.get(1).setDefaultCommand(
        new Pidbraco(bracos.get(1), Constants.ArmUtility.ArmPositions.armPositions.get(ArmStates.guarda)[1])
      );
        treeUnitTest();
    }
    private void doubleArmTest(){
      controleXbox.x().toggleOnTrue(
        new Pidbraco(bracos.get(0), -90)
      );
      controleXbox.a().toggleOnTrue(
        new Pidbraco(bracos.get(0), 90));
      controleXbox.b().toggleOnTrue(
        new Pidbraco(bracos.get(1), 45)
      );
    }
    private void lowArmUnitTest(){
      controleXbox.a().toggleOnTrue(
        new Pidbraco(bracos.get(1), 25));
    controleXbox.x().toggleOnTrue(
        new Pidbraco(bracos.get(1), 45));
    controleXbox.b().toggleOnTrue(
        new Pidbraco(bracos.get(1), 60));
    controleXbox.y().toggleOnTrue(
        new Pidbraco(bracos.get(1), 75));
    }
    private void highArmUnitTest(){
      controleXbox.a().toggleOnTrue(
        new Pidbraco(bracos.get(0), 90));
    controleXbox.x().toggleOnTrue(
        new Pidbraco(bracos.get(0), 53));
    controleXbox.y().toggleOnTrue(
        new Pidbraco(bracos.get(0), 13));
    controleXbox.start().toggleOnTrue(
        new Pidbraco(bracos.get(0), -55));
    }
    private void armUnitTest(){
    controleXbox.a().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), 15),
        new Pidbraco(bracos.get(1), 90)
      ));
    controleXbox.b().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), -105),
        new Pidbraco(bracos.get(1), 15)
      )
    );
    controleXbox.x().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), 45),
        new Pidbraco(bracos.get(1), 90)
      ));
    controleXbox.y().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), -80),
        new Pidbraco(bracos.get(1), 45)
      ));
    controleXbox.start().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), 90),
        new Pidbraco(bracos.get(1), 15)
      ));
  }
  private void treeUnitTest(){
    controleXbox.a().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.l1, bracos)
    );
    controleXbox.x().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.l2, bracos)
    );
    controleXbox.y().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.pega, bracos)
    );
    controleXbox.b().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.l3, bracos)
    );
    controleXbox.start().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.guarda, bracos)
    );
    controleXbox.back().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.pegaChao, bracos)
    );
  }
}