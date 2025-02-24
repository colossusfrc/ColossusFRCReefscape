package frc.robot.RCFeatures.Interfaces;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.Controle;

public interface IOInterface {
    // Controle de Xbox, troque para o qual sua equipe estará utilizando
    CommandXboxController controleXbox = new CommandXboxController(Controle.xboxControle);
}
