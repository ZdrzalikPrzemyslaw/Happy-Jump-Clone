package tech.szymanskazdrzalik.weather_game;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tech.szymanskazdrzalik.weather_game.databinding.ActivityGameBinding;
import tech.szymanskazdrzalik.weather_game.gameView.GameView;

public class GameActivity extends AppCompatActivity {

    ActivityGameBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.gameView.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.gameView.resume();
    }
}
