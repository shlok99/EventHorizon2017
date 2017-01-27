package org.firstinspires.ftc.robotcontroller.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity.TAG;

/**
 * Created by andrew.keenan on 1/11/2017.
 */
@Autonomous(name="AJ Camera Test", group="Camera")
public class CameraTest extends LinearOpMode {

    private Camera mCamera;
    public CameraPreview preview;
    public Bitmap image;
    private int width;
    private int height;
    private YuvImage yuvImage = null;
    private int looped = 0;
    private String data;
    ElapsedTime timer = new ElapsedTime(), timer2 = new ElapsedTime();

    public void runOpMode() {
        waitForStart();
        mCamera = ((FtcRobotControllerActivity) hardwareMap.appContext).mCamera;
//i need to init the camera and also get the instance of the camera        //on pic take protocol
        telemetry.addData("camera","initingcameraPreview");
        ((FtcRobotControllerActivity) hardwareMap.appContext).initCameraPreview(mCamera, this);
        //wait, because I have handler wait three seconds b4 it'll take a picture, in initCamera
        timer2.reset();
        int timeItTakes = (int)(timer2.time() * 1000);
        sleep(CameraPreview.RETRIEVE_FILE_TIME - timeItTakes);
        //now we are going to retreive the image and convert it to bitmap
        SharedPreferences prefs = hardwareMap.appContext.getApplicationContext().getSharedPreferences(
                "com.quan.companion", Context.MODE_PRIVATE);
        String path = prefs.getString(CameraPreview.pictureImagePathSharedPrefsKeys, "No path found");
        Log.e("path",path);
        telemetry.addData("image",path);
        //debug stuff - telemetry.addData("camera", "path: " + path);
        File imgFile = new File(path);
        image = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        Log.e("image",image.toString());
    }
}
