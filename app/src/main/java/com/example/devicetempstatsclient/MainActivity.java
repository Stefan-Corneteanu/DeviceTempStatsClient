package com.example.devicetempstatsclient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

//TODO: get the images with the devicetempstats native service on an emulator
import devicetempstats.service.api;

public class MainActivity extends AppCompatActivity {

    private TextView cpuText;
    private TextView gpuText;
    private TextView ambientText;

    private TextView avgCpuText;
    private TextView avgGpuText;
    private TextView avgAmbientText;

    private TextView maxCpuText;
    private TextView maxGpuText;
    private TextView maxAmbientText;

    private Handler handler;

    private Runnable temperatureRunnable;

    private final long awaitTimeInMillis = 1000;

    private DeviceTempStatsService dts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize textviews
        cpuText = (TextView) findViewById(R.id.cpu);
        gpuText = (TextView) findViewById(R.id.gpu);
        ambientText = (TextView) findViewById(R.id.ambient);

        avgCpuText = (TextView) findViewById(R.id.avgCpu);
        avgGpuText = (TextView) findViewById(R.id.avgGpu);
        avgAmbientText = (TextView) findViewById(R.id.avgAmbient);

        maxCpuText = (TextView) findViewById(R.id.maxCpu);
        maxGpuText = (TextView) findViewById(R.id.maxGpu);
        maxAmbientText = (TextView) findViewById(R.id.maxAmbient);

        //initialize service
        dts = new DeviceTempStatsService();

        //initialize Handler and Looper
        handler = new Handler(Looper.getMainLooper());
        temperatureRunnable = new Runnable() {
            @Override
            public void run() {
                UpdateTemperatureTextViews();
                handler.postDelayed(this, awaitTimeInMillis);
            }
        };

        //first run of the temperatureRunnable
        handler.post(temperatureRunnable);
    }

    private void UpdateTemperatureTextViews(){
        int cpuTemp = dts.getCpuTemperature();
        int gpuTemp = dts.getGpuTemperature();
        int ambientTemp = dts.getAmbientTemperature();

        int avgCpuTemp = dts.getAverageCpuTemperature();
        int avgGpuTemp = dts.getAverageGpuTemperature();
        int avgAmbientTemp = dts.getAverageAmbientTemperature();

        int maxCpuTemp = dts.getMaxCpuTemperature();
        int maxGpuTemp = dts.getMaxGpuTemperature();
        int maxAmbientTemp = dts.getMaxAmbientTemperature();

        cpuText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.cpu_temperature), cpuTemp));
        gpuText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.gpu_temperature), gpuTemp));
        ambientText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.ambient_temperature), ambientTemp));

        avgCpuText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.average_cpu_temperature), avgCpuTemp));
        avgGpuText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.average_gpu_temperature), avgGpuTemp));
        avgAmbientText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.average_ambiental_temperature), avgAmbientTemp));

        maxCpuText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.maximum_cpu_temperature), maxCpuTemp));
        maxGpuText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.maximum_gpu_temperature), maxGpuTemp));
        maxAmbientText.setText(String.format(Locale.getDefault(),"%s %d", getString(R.string.maximum_ambiental_temperature), maxAmbientTemp));
    }
}