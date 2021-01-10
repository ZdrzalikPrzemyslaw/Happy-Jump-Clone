package tech.szymanskazdrzalik.weather_game.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class OrientationSensorsService {

    private final Context context;
    private float[] mAccelerometerData = new float[3];
    private float[] mGyroscopeData = new float[3];
    private float[] mMagnetometerData = new float[3];
    private float roll;


    private abstract class CustomSensorEventListener implements SensorEventListener {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private SensorEventListener gyroscopeSensorEventListener = new CustomSensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int sensorType = event.sensor.getType();
            if (sensorType == Sensor.TYPE_GYROSCOPE) {
                OrientationSensorsService.this.mGyroscopeData = event.values;
            }
        }
    };

    private SensorEventListener accelerometerSensorEventListener = new CustomSensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int sensorType = event.sensor.getType();
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                OrientationSensorsService.this.mAccelerometerData = event.values.clone();
            }
        }
    };

    private SensorEventListener magnetometerSensorEventListener = new CustomSensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            int sensorType = event.sensor.getType();
            if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
                mMagnetometerData = event.values.clone();
                new Thread(new MakeMove()).start();
            }
        }
    };

    public void registerListeners() {
        SensorManager sensorManager =  (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        sensorManager.registerListener(this.accelerometerSensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this.magnetometerSensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this.gyroscopeSensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterListeners() {
        SensorManager sensorManager =  (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        sensorManager.unregisterListener(this.accelerometerSensorEventListener);
        sensorManager.unregisterListener(this.magnetometerSensorEventListener);
        sensorManager.unregisterListener(this.gyroscopeSensorEventListener);
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

    public float getRollConvertedIntoPlayerPositionChangeCoeff() {
        if ((roll < (Math.PI / 2)) && (roll > (-Math.PI / 2))) {
            return roll;
        }
        if (roll < 0) {
            return (float) (roll + Math.PI);
        }
        return (float) (roll - Math.PI);
    }

    private class MakeMove implements Runnable {
        @Override
        public void run() {
            float[] orientationValues = getOrientationValues();

            OrientationSensorsService.this.roll = orientationValues[2];
        }
    }
}
