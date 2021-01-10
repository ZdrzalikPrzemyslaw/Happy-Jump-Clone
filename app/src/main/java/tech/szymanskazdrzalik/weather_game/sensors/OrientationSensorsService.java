package tech.szymanskazdrzalik.weather_game.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class OrientationSensorsService implements SensorEventListener {


    private final static double HORIZONTAL_PITCH_MAX = 0.5;
    private final static double HORIZONTAL_PITCH_MIN = -0.5;

    private final static double changeColourAzimuthBreakpoint1 = 0.25;
    private final static double changeColourAzimuthBreakpoint2 = 1.25;
    private final static double changeColourAzimuthBreakpoint3 = 1.75;
    private final static double changeColourAzimuthBreakpoint4 = 2.75;

    private float[] mAccelerometerData = new float[3];
    private float[] mGyroscopeData = new float[3];
    private float[] mMagnetometerData = new float[3];
    private final Context context;
    public interface OnOrientationEventListener {
        public void onOrientationChangeEvent();
    }

    private OnOrientationEventListener onOrientationEventListener;

    public void setOnOrientationEventListener(OnOrientationEventListener onOrientationEventListener) {
        this.onOrientationEventListener = onOrientationEventListener;
    }

    /**
     * Class constructor.
     *
     * @param context context from activity.
     */
    public OrientationSensorsService(Context context) {
        this.context = context;
    }

    /**
     * @return {@link SensorManager#getOrientation(float[], float[])} with a rotation matrix optained from
     * {@link SensorManager#getRotationMatrix(float[], float[], float[], float[])}
     */
    private float[] getOrientationValues() {
        float[] rotationMatrix = new float[9];
        boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,
                null, mAccelerometerData, mMagnetometerData);
        float[] orientationValues = new float[3];
        if (rotationOK) {
            SensorManager.getOrientation(rotationMatrix, orientationValues);
        }
        return orientationValues;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_GYROSCOPE:
                this.mGyroscopeData = event.values;
                break;
            case Sensor.TYPE_ACCELEROMETER:
                this.mAccelerometerData = event.values.clone();
                new Thread(new MakeMove()).start();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnetometerData = event.values.clone();
                new Thread(new MakeMove()).start();
                break;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class MakeMove implements Runnable {

        @Override
        public void run() {
            try {
                float[] orientationValues = getOrientationValues();


                float azimuth = orientationValues[0];
                float pitch = orientationValues[1];
                float roll = orientationValues[2];
                System.out.println("Azimuth " + azimuth + " pitch " + pitch + " roll " + roll);

//                if (mGyroscopeData[0] > MIN_GYRO_VALUE_VERTICAL && pitch < -MIN_PITCH) {
//                    boardActivityListener.callback(context.getString(R.string.MoveDown));
//                } else if (mGyroscopeData[0] < -MIN_GYRO_VALUE_VERTICAL && pitch > MIN_PITCH) {
//                    boardActivityListener.callback(context.getString(R.string.MoveUP));
//                } else if (mGyroscopeData[1] > MIN_GYRO_VALUE_HORIZONTAL && roll > MIN_ROLL) {
//                    boardActivityListener.callback(context.getString(R.string.MoveRight));
//                } else if (mGyroscopeData[1] < -MIN_GYRO_VALUE_HORIZONTAL && roll < -MIN_ROLL) {
//                    boardActivityListener.callback(context.getString(R.string.MoveLeft));
//                }
            } finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
