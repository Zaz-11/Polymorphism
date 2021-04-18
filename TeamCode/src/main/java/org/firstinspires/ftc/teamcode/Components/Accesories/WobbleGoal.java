/**
 * This is the program for moving the wobble goal arm up and down
 * @author Sid
 * @version 2.0
 * @since 2020-11-08
 * @status teleop working
 */

package org.firstinspires.ftc.teamcode.Components.Accesories;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleGoal {

    public enum Position {
        REST, GRAB, RAISE, RUN, DROP, AutoGRAB, DRIVETOWALL, AutoRAISE
    }

    //declaring the op mode
    private LinearOpMode op;

    protected DcMotor wobbleGoalMotor = null;

    protected Servo wobbleGoalServo = null;

    protected Servo wobbleGoalServoClaw = null;

    private final int ticksForREST = 50;
    private final int ticksForGRAB = 750;
    private final int ticksForRAISE = 300;
    private final int ticksForDriveToWall = 50;
    private final int TicksForAutonomousDrop = 800;
    private final int ticksForAutonomousRaise = 700;
    private final double wobbleGoalSpeed = 0.4;
    private final double wobbleGoalSpeedDrop = 0.5;

    public WobbleGoal(LinearOpMode opMode) {
        //setting the opmode
        this.op = opMode;

        //getting the motor & servo from the hardware map
        wobbleGoalMotor = (DcMotor) opMode.hardwareMap.get("wobbleGoalMotor");
        wobbleGoalServoClaw = op.hardwareMap.servo.get("wobbleGoalServoClaw");
        wobbleGoalMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wobbleGoalMotor.setDirection(DcMotor.Direction.FORWARD);
        wobbleGoalMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobbleGoalMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        closeWobbleGoalClaw();
        goToPosition(Position.REST);
        opMode.sleep(500);

    }

    //tells motor to go to a specified position based on ticks(i)
    public void goToPosition(Position p) {
        int i = 0;
        if (p == Position.REST) {
            i = ticksForREST;
        } else if (p == Position.GRAB) {
            i = ticksForGRAB;
        } else if (p == Position.RAISE) {
            i = ticksForRAISE;
        } else if (p == Position.AutoRAISE) {
            i = ticksForAutonomousRaise;
        }
        else if (p == Position.DRIVETOWALL){
            i = ticksForDriveToWall;
        } else if (p == Position.DROP) {
            i = TicksForAutonomousDrop;
        }
        else {
            op.telemetry.addData("IQ Lvl", "0.00");
            op.telemetry.update();
            op.sleep(2000);
        }
        op.sleep(1000);
        if (p == Position.DROP) {
            wobbleGoalMotor.setPower(wobbleGoalSpeedDrop);
        } else {
            wobbleGoalMotor.setPower(wobbleGoalSpeed);
        }

        wobbleGoalMotor.setTargetPosition(i);
        wobbleGoalMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        op.sleep(1000);
        op.telemetry.addData("Wobble Goal", "Position:" + wobbleGoalMotor.getCurrentPosition() + "-->" + i);
        op.telemetry.update();
//        op.sleep(2000);
    }

    public void printCurrentLocation(){
        op.telemetry.addData("Wobble Goal ", "Current Position:" + wobbleGoalMotor.getCurrentPosition());
        op.telemetry.update();
//        op.sleep(2000);
    }

    //stops the motor
    public void stop() {
        wobbleGoalMotor.setPower(0);
    }
    public void moveWobbleGoalServo (boolean direction){
        if (direction){
            wobbleGoalServo.setPosition(1.0);
        } else {
            wobbleGoalServo.setPosition(0.3);
        }
        op.telemetry.addData(" Wobble Goal Position: ", direction);
        op.telemetry.update();
        op.sleep(500);
    }
    // moves the wobble goal servo
    public void openWobbleGoalClaw() {
            wobbleGoalServoClaw.setPosition(0.2);
            op.sleep(200);
            op.telemetry.addData(" Wobble Goal Claw: ", "closed");
            op.telemetry.update();

    }
    public void  closeWobbleGoalClaw() {
            wobbleGoalServoClaw.setPosition(1);
            op.sleep(200);
            op.telemetry.addData(" Wobble Goal Claw: ", "closed");
            op.telemetry.update();
    }
}

