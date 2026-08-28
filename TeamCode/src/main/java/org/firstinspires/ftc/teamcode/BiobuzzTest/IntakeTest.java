package org.firstinspires.ftc.teamcode.BiobuzzTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Intake Test")
public class IntakeTest extends LinearOpMode {

    private DcMotor intakeMotor;

    @Override
    public void runOpMode() {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.right_trigger > 0.1) {
                intakeMotor.setPower(1.0);
            }
                intakeMotor.setPower(0);
            }
        }
    }







