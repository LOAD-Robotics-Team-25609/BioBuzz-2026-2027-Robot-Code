package org.firstinspires.ftc.teamcode.LOADCode.Testing_;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "JosephinesIntake", group = "Test")
public class IntakeTest extends OpMode {

    private DcMotor intake;
    private DcMotor transfer;

    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intake");
        transfer = hardwareMap.get(DcMotor.class, "Transfer");

        transfer.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Stick y is negative when pushed forward, so flip it: forward = positive power
        double intakePower = -gamepad1.right_stick_y;   // right stick runs the intake
        double transferPower = -gamepad1.left_stick_y;  // left stick runs the transfer

        intake.setPower(intakePower);
        transfer.setPower(transferPower);

        telemetry.addData("Intake power", "%.2f", intakePower);
        telemetry.addData("Transfer power", "%.2f", transferPower);
        telemetry.update();
    }

    @Override
    public void stop() {
        intake.setPower(0);
        transfer.setPower(0);
    }
}




