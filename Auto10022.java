package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;

public abstract class Auto10022 extends LinearOpMode {

    //Declare Hardware

    //Drivetrain
    DcMotor frontleft, frontright, backleft, backright;

    //Lift
    DcMotor liftMotor;

    //Sensors
    ColorSensor goldSensor;

    //Linear Slide
    DcMotor horizontalSlide, verticalSlide;

    //Intake & Outtake
    DcMotor intakeTubing;
    Servo intakeRotateOne, intakeRotateTwo, outtakeRotate;

    //Variables
    static final double COUNTS_PER_MOTOR_REV = 134.4;
    static final double DRIVE_GEAR_REDUCTION = 1.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    int colorTest = 0;
    int positionTest = 0;

    public void initialize() {

        //Drivetrain Initialization
        frontleft = hardwareMap.dcMotor.get("frontleft");
        frontright = hardwareMap.dcMotor.get("frontright");
        backleft = hardwareMap.dcMotor.get("backleft");
        backright = hardwareMap.dcMotor.get("backright");

        //Reverse Motors and Servos
        frontright.setDirection(DcMotor.Direction.REVERSE);
        backright.setDirection(DcMotor.Direction.REVERSE);

        //Encoders
        frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Sensor Initialization
        goldSensor = hardwareMap.colorSensor.get("goldsensor");

        //Servo Initialization
        intakeRotateOne = hardwareMap.servo.get("intakerotateone");
        intakeRotateTwo = hardwareMap.servo.get("intakerotatetwo");
        intakeTubing = hardwareMap.dcMotor.get("intaketubing");

    }

    public void Forward(double inches, double power){
        if(opModeIsActive())  {

            backleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            backleft.setTargetPosition((int)(-inches * COUNTS_PER_INCH));
            backright.setTargetPosition((int)(-inches * COUNTS_PER_INCH));
            frontleft.setTargetPosition((int)((inches * COUNTS_PER_INCH)));
            frontright.setTargetPosition((int)(inches * COUNTS_PER_INCH));

            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            while(opModeIsActive() && backright.isBusy() && frontright.isBusy() && frontleft.isBusy() && backleft.isBusy()){

                telemetry.addData("Path1",  "Running to %7d :%7d", backleft.getTargetPosition(), backright.getTargetPosition());
                telemetry.addData("Path2",  "Running at %7d :%7d", backleft.getCurrentPosition(), backright.getCurrentPosition());
                telemetry.update();

                backleft.setPower(power);
                backright.setPower(power);
                frontleft.setPower(power);
                frontright.setPower(power);
            }

            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);

            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

    public void driveForward(double distance, double power) {

        int newfrontleftTarget;
        int newfrontrightTarget;
        int newbackleftTarget;
        int newbackrightTarget;

        if (opModeIsActive()) {

            newfrontleftTarget = frontleft.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newfrontrightTarget = frontright.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newbackleftTarget = backleft.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newbackrightTarget = backright.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

            frontleft.setTargetPosition(newfrontleftTarget);
            frontright.setTargetPosition(newfrontrightTarget);
            backleft.setTargetPosition(newbackleftTarget);
            backright.setTargetPosition(newbackrightTarget);

            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(opModeIsActive() && backright.isBusy() && frontright.isBusy() && frontleft.isBusy() && backleft.isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", backleft.getTargetPosition(), backright.getTargetPosition());
                telemetry.addData("Path2",  "Running at %7d :%7d", backleft.getCurrentPosition(), backright.getCurrentPosition());
                telemetry.update();

                backleft.setPower(-power);
                backright.setPower(-power);
                frontleft.setPower(-power);
                frontright.setPower(-power);

            }

            //Stop all motion;
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);


            //Turn off RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

    public void driveBackward(double distance, double power) {

        int newfrontleftTarget;
        int newfrontrightTarget;
        int newbackleftTarget;
        int newbackrightTarget;

        if (opModeIsActive()) {

            newfrontleftTarget = frontleft.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);
            newfrontrightTarget = frontright.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);
            newbackleftTarget = backleft.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);
            newbackrightTarget = backright.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);

            frontleft.setTargetPosition(newfrontleftTarget);
            frontright.setTargetPosition(newfrontrightTarget);
            backleft.setTargetPosition(newbackleftTarget);
            backright.setTargetPosition(newbackrightTarget);

            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(opModeIsActive() && backright.isBusy() && frontright.isBusy() && frontleft.isBusy() && backleft.isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", newbackleftTarget, newbackrightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d", frontleft.getCurrentPosition(), frontright.getCurrentPosition(), backleft.getCurrentPosition(), backright.getCurrentPosition());
                telemetry.update();

                backleft.setPower(power);
                backright.setPower(power);
                frontleft.setPower(power);
                frontright.setPower(power);

            }

            // Stop all motion;
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);


            // Turn off RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

    public void strafeRight(double distance, double power) {

        int newfrontleftTarget;
        int newfrontrightTarget;
        int newbackleftTarget;
        int newbackrightTarget;

        if (opModeIsActive()) {

            newfrontleftTarget = frontleft.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);
            newfrontrightTarget = frontright.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newbackleftTarget = backleft.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newbackrightTarget = backright.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);

            frontleft.setTargetPosition(newfrontleftTarget);
            frontright.setTargetPosition(newfrontrightTarget);
            backleft.setTargetPosition(newbackleftTarget);
            backright.setTargetPosition(newbackrightTarget);

            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(opModeIsActive() && backright.isBusy() && frontright.isBusy() && frontleft.isBusy() && backleft.isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", backleft.getTargetPosition(), backright.getTargetPosition());
                telemetry.addData("Path2",  "Running at %7d :%7d", backleft.getCurrentPosition(), backright.getCurrentPosition());
                telemetry.update();

                backleft.setPower(power);
                backright.setPower(power);
                frontleft.setPower(power);
                frontright.setPower(power);

            }

            //Stop all motion;
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);


            //Turn off RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

    public void strafeLeft(double distance, double power) {

        int newfrontleftTarget;
        int newfrontrightTarget;
        int newbackleftTarget;
        int newbackrightTarget;

        if (opModeIsActive()) {

            newfrontleftTarget = frontleft.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newfrontrightTarget = frontright.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);
            newbackleftTarget = backleft.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);
            newbackrightTarget = backright.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

            frontleft.setTargetPosition(newfrontleftTarget);
            frontright.setTargetPosition(newfrontrightTarget);
            backleft.setTargetPosition(newbackleftTarget);
            backright.setTargetPosition(newbackrightTarget);

            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(opModeIsActive() && backright.isBusy() && frontright.isBusy() && frontleft.isBusy() && backleft.isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", backleft.getTargetPosition(), backright.getTargetPosition());
                telemetry.addData("Path2",  "Running at %7d :%7d", backleft.getCurrentPosition(), backright.getCurrentPosition());
                telemetry.update();

                backleft.setPower(power);
                backright.setPower(power);
                frontleft.setPower(power);
                frontright.setPower(power);

            }

            // Stop all motion;
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);


            // Turn off RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

    public void rotateRight(double distance, double power) {

        int newfrontleftTarget;
        int newfrontrightTarget;
        int newbackleftTarget;
        int newbackrightTarget;

        if (opModeIsActive()) {

            newfrontleftTarget = frontleft.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newfrontrightTarget = frontright.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);
            newbackleftTarget = backleft.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);
            newbackrightTarget = backright.getCurrentPosition() - (int)(distance * COUNTS_PER_INCH);

            frontleft.setTargetPosition(newfrontleftTarget);
            frontright.setTargetPosition(newfrontrightTarget);
            backleft.setTargetPosition(newbackleftTarget);
            backright.setTargetPosition(newbackrightTarget);

            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(opModeIsActive() && backright.isBusy() && frontright.isBusy() && frontleft.isBusy() && backleft.isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", backleft.getTargetPosition(), backright.getTargetPosition());
                telemetry.addData("Path2",  "Running at %7d :%7d", backleft.getCurrentPosition(), backright.getCurrentPosition());
                telemetry.update();

                backleft.setPower(power);
                backright.setPower(power);
                frontleft.setPower(power);
                frontright.setPower(power);

            }

            // Stop all motion;
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);


            // Turn off RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

    public void rotateLeft(double distance, double power) {

        int newfrontleftTarget;
        int newfrontrightTarget;
        int newbackleftTarget;
        int newbackrightTarget;

        if (opModeIsActive()) {

            newfrontleftTarget = frontleft.getCurrentPosition() - (int) (distance * COUNTS_PER_INCH);
            newfrontrightTarget = frontright.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
            newbackleftTarget = backleft.getCurrentPosition() - (int) (distance * COUNTS_PER_INCH);
            newbackrightTarget = backright.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);

            frontleft.setTargetPosition(newfrontleftTarget);
            frontright.setTargetPosition(newfrontrightTarget);
            backleft.setTargetPosition(newbackleftTarget);
            backright.setTargetPosition(newbackrightTarget);

            frontleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontright.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backleft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backright.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(opModeIsActive() && backright.isBusy() && frontright.isBusy() && frontleft.isBusy() && backleft.isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", backleft.getTargetPosition(), backright.getTargetPosition());
                telemetry.addData("Path2",  "Running at %7d :%7d", backleft.getCurrentPosition(), backright.getCurrentPosition());
                telemetry.update();

                backleft.setPower(power);
                backright.setPower(power);
                frontleft.setPower(power);
                frontright.setPower(power);

            }

            // Stop all motion;
            frontleft.setPower(0);
            frontright.setPower(0);
            backleft.setPower(0);
            backright.setPower(0);


            // Turn off RUN_TO_POSITION
            frontleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

    public void landRobot(double distance, double power) {

        int newliftmotorTarget;

        while (opModeIsActive()) {

            newliftmotorTarget = liftMotor.getCurrentPosition() + (int)(distance * COUNTS_PER_INCH);

            liftMotor.setTargetPosition(newliftmotorTarget);

            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            while(opModeIsActive() && liftMotor.isBusy()) {

                telemetry.addData("Path1",  "Running to %7d :%7d", liftMotor.getTargetPosition());
                telemetry.addData("Path2",  "Running at %7d :%7d", liftMotor.getCurrentPosition());
                telemetry.update();

                liftMotor.setPower(power);

            }

            // Stop all motion;
            liftMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

    //Position 0: Middle Sample
    //Position 1: Right Sample
    //Position 2: Left Sample

    public void detectColor() {

        if (positionTest == 0) {

            if (goldSensor.green() < 30) {

                //Push Gold Cube
                driveForward(5.0, 0.5);
                driveBackward(5.0, 0.5);

            }

            else {

                driveBackward(8.0, 0.5);
                positionTest = 1;

            }

        }

        if (positionTest == 1) {

            //Move to right sample
            rotateRight(60, 0.5);
            driveForward(14.0, 0.5);
            rotateLeft(60, 0.5);

            if (goldSensor.green() < 30) {

                //Push Gold Cube
                driveForward(5.0, 0.5);
                driveBackward(5.0, 0.5);

            }

            else {

                driveBackward(8.0, 0.5);
                positionTest = 2;

            }

        }

        if (positionTest == 2) {

            //Move to left sample
            rotateLeft(60, 0.5);
            driveForward(29.5, 0.5);
            rotateRight(60, 0.5);

            //Push Gold Cube
            driveForward(8.0, 0.5);
            driveBackward(8.0, 0.5);

        }

    }

    public void repositionRobotDepot() {

        if (positionTest == 0) {

            rotateRight(60, 0.5); //90 Degrees
            driveBackward(39.0, 0.5);
            rotateLeft(30, 0.5); //45 Degrees

        }

        if (positionTest == 1) {

            rotateRight(60, 0.5); //90 Degrees
            driveBackward(24.5, 0.5);
            rotateLeft(30, 0.5); //45 Degrees

        }

        if (positionTest == 2) {

            rotateRight(60, 0.5); //90 Degrees
            driveBackward(54.0, 0.5);
            rotateLeft(30, 0.5); //45 Degrees

        }

    }

    public void repositionRobotCrater() {

        if (positionTest == 0) {

            rotateLeft(60, 0.5); //90 Degrees
            driveForward(41.0, 0.5);
            rotateLeft(30, 0.5); //45 Degrees

        }

        if (positionTest == 1) {

            rotateLeft(60, 0.5); //90 Degrees
            driveForward(60.0, 0.5);
            rotateLeft(30, 0.5); //45 Degrees

        }

        if (positionTest == 2) {

            rotateLeft(60, 0.5); //90 Degrees
            driveBackward(24.0, 0.5);
            rotateLeft(30, 0.5); //45 Degrees

        }

    }

    public void outtakeMarker(double position, int time) {

        intakeRotateOne.setPosition(position);
        intakeRotateTwo.setPosition(position);
        sleep(time);
        intakeTubing.setPower(1);
        sleep(time);
        intakeRotateOne.setPosition(0);
        intakeRotateTwo.setPosition(0);
        sleep(time);

    }

    public void intakeMinerals(int time) {

        horizontalSlide.setPower(1);
        sleep(time);
        intakeTubing.setPower(1);
        sleep(1000000);

    }

}
