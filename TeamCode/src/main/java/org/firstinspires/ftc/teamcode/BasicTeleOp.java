package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Basic TeleOp Test", group = "Test")
public class BasicTeleOp extends OpMode {

    @Override
    public void init() {
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
        telemetry.addData("Status", "Running");
        telemetry.addData("Loop Time", System.currentTimeMillis());
        telemetry.update();
    }

    @Override
    public void stop() {
    }
}