package com.tairanchina.taiheapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class StepService extends Service implements SensorEventListener {
    private static final String TAG = "StepService";
    private SharedPreferences sharedPreferences;
    private static SensorManager sensorManager;
    public StepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("step", MODE_PRIVATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getStepDetector();
            }
        }).start();
    }

    /**
     * 获取传感器实例
     */
    private void getStepDetector() {
        if(sensorManager!=null){
            sensorManager=null;
        }
        //获取传感器管理器的实例
        sensorManager=(SensorManager)this.getSystemService(SENSOR_SERVICE);
        //android4.4以后可以使用计步传感器
        int versionCode= Build.VERSION.SDK_INT;
        if(versionCode>=19){
            addCountStepListener();
        }
    }

    /**
     * 添加传感器监听
     */
    private void addCountStepListener() {
        Sensor countSensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(countSensor!=null){
            sensorManager.registerListener(StepService.this,countSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else if(detectorSensor!=null){
            sensorManager.registerListener(StepService.this,countSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            
        }
    }


    /**
     * 启动加速度传感器计步
     */
    private void addBasePedometerListener(){
        Log.d(TAG, "addBasePedometerListener: 加速度传感器");
    }

    /**
     * Google内置计步器计算步数，因为该方法只有4.4以上的版本才有，低于该版本不能使用该方法，可以使用加速度传感器。
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        int step = (int) event.values[0];
        Log.d(TAG, "*********onSensorChanged**********: "+step);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("steps", step);
        editor.commit();
        /**
         * 发送广播
         */
        Intent intent=new Intent("com.wq.STEP_COUNT");
        intent.putExtra("step",step);
        sendBroadcast(intent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
