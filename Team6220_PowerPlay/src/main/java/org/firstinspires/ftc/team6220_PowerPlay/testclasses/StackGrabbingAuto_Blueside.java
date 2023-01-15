package org.firstinspires.ftc.team6220_PowerPlay.testclasses;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.team6220_PowerPlay.Constants;

@Disabled
@Autonomous(name = "StackGrabbingAuto_Blue", group = "Test")
public class StackGrabbingAuto_Blueside extends ConeDetection {

    int stackHeight = 4;
    int[] lowerBlue = {100, 150, 20};
    int[] upperBlue = {140, 255, 255};

    @Override
    public void runOpMode() throws InterruptedException
    {
        initialize();
        detectGrab(lowerBlue,upperBlue);
        servoGrabber.setPosition(Constants.GRABBER_CLOSE_POSITION);
        driveSlidesAutonomous(Constants.SLIDE_LOW);
        waitForStart();
    }}














































































