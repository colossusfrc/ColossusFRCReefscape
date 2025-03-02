// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;
import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import frc.robot.RCFeatures.Interfaces.ArmInterface.ArmStates;
import swervelib.math.Matter;
import swervelib.math.SwerveMath;

/**
 * Classe de constantes
 */
public final class Constants {
  // Aqui temos várias constantes referentes as demais áreas do robô
    
  public static final class Dimensoes {
    // Tempo de loop (sparkMax + normal = 130ms)
    public static final double LOOP_TIME = 0.13;
    // Massa do robô *DEVE SER CONFIGURADO PARA O SEU ROBÔ*
    //precisa ser calculado
    public static final double ROBOT_MASS = 38;
    //Velocidade máxima *DEVE SER CONFIGURADO PARA O SEU ROBÔ*
    //tem que fazer
    public static final double MAX_SPEED = 4;
    //Posição do módulo mais longe *COLOQUE OS MESMOS VALORES DO JSON*
    private static final Translation2d FURTHEST_MODULE_POSE = new Translation2d(11.75, 11.75);
    public static final double MAX_ANGULAR_SPEED = SwerveMath.calculateMaxAngularVelocity(MAX_SPEED, FURTHEST_MODULE_POSE.getX(), FURTHEST_MODULE_POSE.getY());

    //Posições do centro de massa *DEVE SER CONFIGURADO PARA SEU ROBÔ*
    private static final double xMass = 0;
    private static final double yMass = 0;
    private static final double zMass = .08;

    // Centro de massa do chassi
    public static final Matter CHASSIS    = new Matter(new Translation3d(xMass, yMass, (zMass)), ROBOT_MASS);
   }

   public static final class AutonConstants{
    //só mexer aqui
    public static final class LimelightConstants{
      public static final String limelightName = "limelight-one";
      public static final double kpTheta = 0.0;
      public static final double kiTheta = 0.0;
      public static final double kdTheta = 0.0;
      public static final double iThetaRange = 10.0;
      public static final double kpX = 0.0;
      public static final double kiX = 0.0;
      public static final double kdX = 0.0;
      public static final double iXRange = 10.0;
      public static final double kpY = 0.0;
      public static final double kiY = 0.0;
      public static final double kdY = 0.0;
      public static final double iYRange = 10.0;
      public static final double kMaxOutput = 0.0;
      public static final double kMinOutput = -kMaxOutput;

      public static final double armTimeout = 2.0;

      public static final double[] limelightV0 = new double[]{
        0.0,//frente
         0.0,//direita
          0.0,//cima
           0.0,//0.0
            0.0,//pitch
             0.0};//0.0

    }
    private static final String[] labels = {
      "bleu 1",
      "bleu 2",
      "bleu 3",
      "rouge 1",
      "rouge 2",
      "rouge 3"
    };
    private static final Pose2d[] initialPositions = {
      new Pose2d(7.6, 2, new Rotation2d(180)),
      new Pose2d(7.6, 4, new Rotation2d(180)),
      new Pose2d(7.6, 6, new Rotation2d(180)),
      new Pose2d(10, 2, new Rotation2d(0)),
      new Pose2d(10, 4, new Rotation2d(0)),
      new Pose2d(10, 6, new Rotation2d(0))
    };
    private static final Pose2d[] cameraTargetPoses = {
      new Pose2d(5.430, 2.485, new Rotation2d(0.0)),
      new Pose2d(7.6, 4, new Rotation2d(0.0)),
      new Pose2d(7.6, 6, new Rotation2d(0.0)),
      new Pose2d(10, 2, new Rotation2d(0.0)),
      new Pose2d(10, 4, new Rotation2d(0.0)),
      new Pose2d(10, 6, new Rotation2d(0.0))
    };
    private static final Rotation2d[] cameraHeadings = {
      new Rotation2d(120.0),
      new Rotation2d(180.0),
      new Rotation2d(-120.0),
      new Rotation2d(60.0),
      new Rotation2d(0.0),
      new Rotation2d(-60.0)
    };
    private static final int[] tagIds = {
      22,
      21,
      20,
      11,
      10,
      9
    };
    //posicoes iniciais do autonomo
    public static final HashMap<String, Pose2d> initialPositionsByLabels = new HashMap<>();
    //tags a partir da driverstation
    public static final HashMap<String, Integer> tagIdsByLabels = new HashMap<>();
    //targets da camera
    public static final HashMap<String, Pose2d> cameraOffsets = new HashMap<>();
    //posicoes alvo 
    public static final HashMap<String, Rotation2d> cameraTargetHeadings = new HashMap<>();
    static{
      for(int i = 0; i<6; i++){
        initialPositionsByLabels.put(labels[i], initialPositions[i]); 
        tagIdsByLabels.put(labels[i], tagIds[i]);
        cameraOffsets.put(labels[i], cameraTargetPoses[i]);
        cameraTargetHeadings.put(labels[i], cameraHeadings[i]);
      }
    }
   }

    // Contem a porta em que o controle está
    public static class Controle {
      public static Supplier<Double> limit = ()->0.8;
      public static final double maxLimit = 0.8;
      public static final double minLimit = 0.6;
      // Porta do controle
      public static final int xboxControle = 0;
      public static final int xboxControlePiloto2 = 1;
      // Deadband do controle
      public static final double DEADBAND = 0.2;
    }

    public static final class SwerveConfigs {
      // variável que ativa o PID para controlar a orientação do robô (PID tunado no json)
      public static final boolean headingCorrection = true;
      // true para correção de aceleração
      public static final boolean accelCorrection = false;
      // constante para diminuir o input do joystick (0 < multiplicadorRotacional <= 1)
      public static final double multiplicadorRotacional = 0.8;
      // constante para diminuir o input do joystick (y)
      public static final double multiplicadorTranslacionalY = 0.7;
      // constante para diminuir o input do joystick (x)
      public static final double multiplicadorTranslacionalX = 0.7;
      public static final double TURN_CONSTANT = 0.75;
      // variável que ativa a coração para resolver problema de "skew"
      public static final boolean usarCorrecaoDesvioVelocidadeAngular = false;
      // constante que corrije o skew (de -0.15 - 0.15) ESSA VARIÁVEL DEVE SER TUNADA PARA SEU ROBÔ
      public static final double coeficienteCorecaoAngVel = 4;
    }
    public static final class ArmUtility{
      //constants do braço baixi
      public static final class ArmConstants {
        public static final int id = 10;
        public static final int idEncoder = 1;
        public static final double conversionFactor = 0.4285706;
        public static final double offset = 0.779;
        public static final double kP = 0.04;
        public static final double kI = 0.01;
        public static final double kD = 0.00036;
        public static final double kIz = 0.0;
        public static final double kFF = 0.0;
        public static final double kMaxOutput = 1.0;
        public static final double kMinOutput = -kMaxOutput;
      }
      //constantes do braço alto
      public static final class HighArmConstants {
        public static final int id = 9;
        public static final int idEncoder = 0;
        public static final double offset = -0.709;
        public static final double kP = 0.01;
        public static final double kPguardaPega = 0.006;
        public static final double kI = 0.0000;
        public static final double kD = 0.0;
        public static final double kIz = 0.0;
        public static final double kFF = 0.0;
        public static final double kMaxOutput = 1.0;
        public static final double kMinOutput = -kMaxOutput;
      }
      public static final class ClawConstants{
        public static final double clawReceive = 0.2;
        public static final double clawDrop = -0.1;
        public static final int baseId = 11;
        public static final int intakeId = 13;
      }
      public static final class ArmPositions{
        public static double armFeedForward = -0.1;
          @SuppressWarnings({ "rawtypes", "unchecked" })
          //ArmState->{posicaoBracoAlto, posicaoBracoBaixo, posicaoGarra}
          public static HashMap<ArmStates, Double[]> armPositions = new HashMap(8);
          static{
              armPositions.put(ArmStates.guarda, new Double[]{75.0, 29.0, 0.0});
              armPositions.put(ArmStates.pegaAlgeeL3, new Double[]{-25.0, 67.0, -9.5});
              armPositions.put(ArmStates.l1, new Double[]{50.0, 30.0, -8.7});
              armPositions.put(ArmStates.l2, new Double[]{25.0, 35.0, -8.6});
              armPositions.put(ArmStates.pega, new Double[]{15.7, 29.0, -4.7});
              armPositions.put(ArmStates.pegaAlgeeChao, new Double[]{72.0, 31.4, -8.5});
              armPositions.put(ArmStates.l3, new Double[]{-10.0, 46.5, -9.35});
              armPositions.put(ArmStates.pegaAlgeeL2, new Double[]{30.6, 19.906, -13.286});
          }
        }
    }
}
