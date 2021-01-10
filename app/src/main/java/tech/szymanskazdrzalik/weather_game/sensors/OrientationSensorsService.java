package tech.szymanskazdrzalik.weather_game.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class OrientationSensorsService implements SensorEventListener {

    private final Context context;
    private float[] mAccelerometerData = new float[3];
    private float[] mGyroscopeData = new float[3];
    private float[] mMagnetometerData = new float[3];
    private OnOrientationEventListener onOrientationEventListener;
    private float roll;

    /**
     * Class constructor.
     *
     * @param context context from activity.
     */
    public OrientationSensorsService(Context context) {
        this.context = context;
    }

    public void setOnOrientationEventListener(OnOrientationEventListener onOrientationEventListener) {
        this.onOrientationEventListener = onOrientationEventListener;
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

    public float getChange() {
        if ((roll < (Math.PI / 2)) && (roll > (-Math.PI / 2))) {
            return roll;
        }
        if (roll < 0) {
            return (float) (roll + Math.PI);
        }
        return (float) (roll - Math.PI);
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
//                new Thread(new MakeMove()).start();
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


    public interface OnOrientationEventListener {
        void onOrientationChangeEvent();
    }

    private class MakeMove implements Runnable {

        @Override
        public void run() {
            float[] orientationValues = getOrientationValues();

            OrientationSensorsService.this.roll = orientationValues[2];
            System.out.println("roll " + roll);

        }


    }


}
