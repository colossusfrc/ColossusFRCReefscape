package frc.robot.autonomous;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
public class Camera implements Runnable{
    private final UsbCamera camera;
    public final CvSink cvSink;
    public final CvSource cvSource;
    public Camera(String name){
        this.camera = CameraServer.startAutomaticCapture();
        this.camera.setResolution(640, 480);

        cvSink = CameraServer.getVideo();
        cvSource = CameraServer.putVideo(name, 640, 480);
        if(name=="camera 2"){
            new Thread(this).start();
        }
    }
    @Override
    public void run() {
        Mat frame = new Mat();
        
        cvSink.grabFrame(frame);
        
        Core.rotate(frame, frame, Core.ROTATE_90_CLOCKWISE);

        cvSource.putFrame(frame);
    }
}


