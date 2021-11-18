package org.firstinspires.ftc.teamcode.FreightFrenzy_2021.gary;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "GTeleOp_Intake_Spin", group = "Linear OpMode")
public class TeleOp_Intake_Spin extends LinearOpMode {
    private DcMotor Intake = null;
    private DcMotor Spin = null;


    @Override
    public void runOpMode(){
        Intake  = hardwareMap.get(DcMotor.class, "Intake");
        Spin = hardwareMap.get(DcMotor.class, "Spin");

        Intake.setDirection(DcMotor.Direction.REVERSE); //anticlockwise
        Spin.setDirection(DcMotor.Direction.FORWARD); //clockwise
        Intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Spin.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double IntakePower = 0; //setting initial power
        double SpinPower = 0; //setting initial power
        double B_P = 1;
        double IntakeDirection = gamepad1.left_stick_y;
        double SpinDirection = gamepad1.right_stick_y;




        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.left_bumper == true){
                while(gamepad1.left_bumper){
                }
                B_P = B_P + 0.1;
            }else if(gamepad1.right_bumper == true){
                while(gamepad1.right_bumper){
                }
                B_P = B_P -0.1;
            }


            IntakePower = Range.clip(B_P * (IntakeDirection), -1.0, 1.0);
            SpinPower = Range.clip(B_P * (SpinDirection), -1.0, 1.0);

            Intake.setPower(IntakePower);
            Spin.setPower(SpinPower);

            telemetry.addData("The power: ", B_P);
            telemetry.update();






        }

    }
}
