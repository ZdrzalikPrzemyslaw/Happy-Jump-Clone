package tech.szymanskazdrzalik.weather_game;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import tech.szymanskazdrzalik.weather_game.databinding.ActivitySplashScreenBinding;
import tech.szymanskazdrzalik.weather_game.game.entities.PlatformEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PlayerEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.PresentEntity;
import tech.szymanskazdrzalik.weather_game.game.entities.SnowballEntity;


public class SplashActivity extends AppCompatActivity {

    private final static int SPLASH_TIME_OUT = 1500;
    private final Runnable loadRunnable = () -> {
        PlatformEntity.init(getResources());
        SnowballEntity.init(getResources());
        PresentEntity.init(getResources());
        PlayerEntity.init(getResources());
        // TODO: 16.12.2020 Jesli cos ladujemy to tutaj
    };

    ActivitySplashScreenBinding binding;
    private Thread loadThread;
    private final Runnable waifForLoadRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                loadThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(() -> new Handler().postDelayed(() -> {
                        startGameActivity();
                }, SPLASH_TIME_OUT));
            }
        }
    };

    private void startGameActivity() {
        Intent intent = new Intent(SplashActivity.this, GameActivity.class);
        startActivity(intent);
        // TODO: 16.12.2020 Override transition
        finish();
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.loadThread = new Thread(this.loadRunnable);
        this.loadThread.start();
        new Thread(this.waifForLoadRunnable).start();
    }

}
