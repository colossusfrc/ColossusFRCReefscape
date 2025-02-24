package frc.robot.RCFeatures;

import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Arm.Pidbraco;
import frc.robot.commands.Arm.SimpleArmCommand;
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
    bracos.forEach(
      b->b.setDefaultCommand(
      (b.getLastTarget()!=null)?
      new Pidbraco(b, b.getLastTarget()):
      new SimpleArmCommand(b, 0.0)));

        armUnitTest();
  }
    private void armUnitTest(){
    controleXbox.a().toggleOnTrue(
      new ParallelCommandGroup(
        new Pidbraco(bracos.get(0), 90),
        new Pidbraco(bracos.get(1), 90)
      ));
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
}