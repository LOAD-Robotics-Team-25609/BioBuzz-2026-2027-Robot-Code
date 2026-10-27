package org.firstinspires.ftc.teamcode.BiobuzzTest;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "shooterTest", group = "Test")
public class shooterTest extends OpMode {

    private DcMotorEx Shooter;
    private static final double LAUNCHER_TICKS_PER_REV = 28; // Bare goBILDA motor, no gearbox
    private static final double LAUNCHER_TARGET_RPM = 3000; // TUNE HERE cuz u will forget, this number is in RPM
    private static final double LAUNCHER_TARGET_VELOCITY = LAUNCHER_TARGET_RPM * LAUNCHER_TICKS_PER_REV / 60.0; // encoder ticks/per sec, calculated from the RPM above
    private static final double LAUNCHER_VELOCITY_TOLERANCE = 50; // Ticks
    private static final double FLYWHEEL_GEAR_RATIO = 2.4; // Flywheel turns 3.75x slower than the motor

    private boolean shooterOn = false;
    @Override
    public void init() {

       Shooter = hardwareMap.get(DcMotorEx.class, "Shooter");

        Shooter.setDirection(DcMotor.Direction.REVERSE);
       Shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }
    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        if (gamepad1.yWasPressed()) {
            shooterOn = !shooterOn;   // Each press toggles on/off hopefully
        }

        Shooter.setVelocity(shooterOn ? LAUNCHER_TARGET_VELOCITY : 0);

        boolean readyToShoot = shooterOn //Whatever happens, dont get rid of this
                && Math.abs(Shooter.getVelocity() - LAUNCHER_TARGET_VELOCITY) < LAUNCHER_VELOCITY_TOLERANCE;

        telemetry.addData("Shooter On", shooterOn);
        telemetry.addData("Shooter Ready", readyToShoot);
        telemetry.addData("Shooter Velocity", Shooter.getVelocity()); //future reference, always leave telemetry on the bottom
        telemetry.addData("Shooter RPM", "%.0f", Shooter.getVelocity() * 60.0 / LAUNCHER_TICKS_PER_REV);
        telemetry.addData("Target RPM", "%.0f", LAUNCHER_TARGET_RPM);
        telemetry.addData("Flywheel RPM", "%.0f", Shooter.getVelocity() * 60.0 / LAUNCHER_TICKS_PER_REV / FLYWHEEL_GEAR_RATIO);
    }
}
