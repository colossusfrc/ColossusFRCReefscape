package frc.robot.RCFeatures.UnitTests;

import java.util.List;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
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
        new Pidbraco(bracos.get(1), 80));
    controleXbox.x().toggleOnTrue(
        new Pidbraco(bracos.get(1), 45));
    controleXbox.y().toggleOnTrue(
        new Pidbraco(bracos.get(1), 15));
    }
    private void highArmUnitTest(){
      controleXbox.a().toggleOnTrue(
        new Pidbraco(bracos.get(0), 90));
    controleXbox.x().toggleOnTrue(
        new Pidbraco(bracos.get(0), 45));
    controleXbox.y().toggleOnTrue(
        new Pidbraco(bracos.get(0), -80));
    controleXbox.start().toggleOnTrue(
        new Pidbraco(bracos.get(0), 90));
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
      ArmCommandSelector.getArmCommand(ArmStates.l1, bracos, true)
    );
    controleXbox.x().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.l2, bracos, true)
    );
    controleXbox.y().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.pega, bracos, true)
    );
    controleXbox.b().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.l3, bracos, true)
    );
    controleXbox.start().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.guarda, bracos, true)
    );
    controleXbox.back().toggleOnTrue(
      ArmCommandSelector.getArmCommand(ArmStates.pegaChao, bracos, true)
    );
  }
}