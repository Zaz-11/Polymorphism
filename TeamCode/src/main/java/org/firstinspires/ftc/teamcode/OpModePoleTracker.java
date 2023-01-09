package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.PipePoleTracker.getBoxBL_X;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getBoxBL_Y;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getBoxHeight;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getBoxWidth;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getCenterX;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getHighestX;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getHighestY;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getLevel1Assigment;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getLevel2Assigment;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getLevel2Capable;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getLevelString;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getLowestX;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getLowestY;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getMinRectHeight;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getMinRectWidth;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getPercentColor;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getRectHeight;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getRectWidth;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getXResolution;
import static org.firstinspires.ftc.teamcode.PipePoleTracker.getYResolution;

import static org.firstinspires.ftc.teamcode.Variables.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name="PoleTracker", group="A")
public class OpModePoleTracker extends DriveMethods {
    String level = "one";
    int levelCounter = 1;

    //The unit here is pixels
    int targetX;
    double errorX;
    double divderX = 100;
    double alignPowerAdded;



    //The unit here is boxes
    int targetDistance;

    @Override
    public void runOpMode(){

        initMotorsBlue();
        calibrateNavXIMU();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);


        PipePoleTracker pipePoleTracker = new PipePoleTracker(level);
        camera.setPipeline(pipePoleTracker);


        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(640,480, OpenCvCameraRotation.UPRIGHT);


            }
            @Override
            public void onError(int errorCode)
            {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
        waitForStart();

        targetX = getXResolution()/2; //<-- this SHOULD be the resolution at level1 (check-able)
        level1Aligned = false;
        level2Aligned = false;
        level3Aligned = false;
//        isIMURecorded = false;
        visionAutoActivated = false;

        while(opModeIsActive()){


            //Button triggers
            if(gamepad2.a && getLevel2Capable()){
//                levelCounter = 2;
                visionAutoActivated = true;


            }

            if(gamepad2.x){
                levelCounter = 1;
            }


            errorX = targetX - getCenterX();

            alignPowerAdded = errorX/divderX;

            if(visionAutoActivated){
                if(levelCounter == 1 && Math.abs(errorX) < 32){//TODO will need to add distance condition
                    level1Aligned = true;
                    levelCounter = 2;
                    //Robot is in front of pole well enough, entering level2...
                }

                if(levelCounter == 1 && level1Aligned == false){
//            motorFL.setPower(alignPowerAdded);
//            motorBL.setPower(alignPowerAdded);
//            motorFR.setPower(-alignPowerAdded);
//            motorBR.setPower(-alignPowerAdded);
                }

                if(levelCounter == 2 && Math.abs(errorX) < 8){ //TODO will need to add distance condition
                    level2Aligned = true;
                    //get IMU heading
                    isIMURecorded = true; // honestly wholly redundant
                    levelCounter = 3;
                }


                if(levelCounter == 2 && level2Aligned == false){ //TODO feed different inputs into this to make more aggresive
//            motorFL.setPower(alignPowerAdded);
//            motorBL.setPower(alignPowerAdded);
//            motorFR.setPower(-alignPowerAdded);
//            motorBR.setPower(-alignPowerAdded);
                }

                if (levelCounter == 3 && getPercentColor() < 10){
                    level3Aligned = true;
                }

                if(levelCounter == 3 && getPercentColor() >= 10){
                    //Slide go up <-- Honestly just use a consistent power for ease
                }


            }



            if(level.equals("two") && Math.abs(errorX) < 8){
                level2Aligned = true;
            }



            if(level2Aligned == false){
//                motorFL.setPower(alignPowerAdded);
//            motorBL.setPower(alignPowerAdded);
//            motorFR.setPower(-alignPowerAdded);
//            motorBR.setPower(-alignPowerAdded);

            }




            if(levelCounter == 1){
                level = "one";
            }

            if(levelCounter == 2){
                level = "two";
            }

            if(levelCounter == 3){
                level = "three";
            }




            telemetry.addLine("Current Level: " + getLevelString());
            telemetry.addLine("Level1 Assigment: " + getLevel1Assigment());
            telemetry.addLine("Level2 Assignment : " + getLevel2Assigment());
            telemetry.addLine("X_resolution: " + getXResolution());
            telemetry.addLine("Y_resolution: " + getYResolution());
            telemetry.addLine("Level 2 Capable?: " + getLevel2Capable());
            telemetry.addLine("# of Nonzeros: " + getPercentColor());
            telemetry.addLine("Focus Rectangle Width " + getRectWidth());
            telemetry.addLine("Focus Rectangle Height: " + getRectHeight());
            telemetry.addLine("Rectangle Min Width: " + getMinRectWidth());
            telemetry.addLine("Rectangle Min Height: " + getMinRectHeight());
            telemetry.addLine("Box Width: " + getBoxWidth());
            telemetry.addLine("Box Height: " + getBoxHeight());
            telemetry.addLine("Center X: " + getCenterX());
            telemetry.addLine("LowestX: " + getLowestX());
            telemetry.addLine("HighestX: " + getHighestX());
            telemetry.addLine("LowestY: " + getLowestY());
            telemetry.addLine("HighestY: " + getHighestY());
//            telemetry.addLine("Box_BL_x: " + getBoxBL_X());
//            telemetry.addLine("Box_BL_y: " + getBoxBL_Y());
            telemetry.addLine("targetX: " + targetX);
            telemetry.addLine("centerX: " + getCenterX());
            telemetry.addLine("errorX: " + errorX);
            telemetry.addLine("Power Applied: " + alignPowerAdded);
            telemetry.addLine("level1Aligned?: " + level1Aligned);
            telemetry.addLine("level2Aligned?: " + level2Aligned);
            telemetry.addLine("Activated?: " + visionAutoActivated);








            telemetry.update();

            pipePoleTracker = new PipePoleTracker(level);
            camera.setPipeline(pipePoleTracker);
        }
    }
}
