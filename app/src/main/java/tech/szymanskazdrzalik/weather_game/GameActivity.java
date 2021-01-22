package tech.szymanskazdrzalik.weather_game;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

    public void pauseButtonOnClick(View v) {
        binding.gameView.togglePauseGame();
        if (binding.gameView.isPaused()) {
            runOnUiThread(() -> binding.pauseButton.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.pause_on)));
        } else {
            runOnUiThread(() -> binding.pauseButton.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.pause_off)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.gameView.resume();
        binding.gameView.setGameOverListener(() -> {
            // TODO: 22.01.2021 Zrobic to inacsej
            runOnUiThread(GameActivity.this::recreate);
        });
        binding.gameView.setScoreListener(() -> runOnUiThread(() -> binding.textViewScore.setText(String.format("%d", (int) (binding.gameView.getScore())))));
    }

    public void onStartGameButtonOnClick(View v) {
        binding.gameView.startGame();
        binding.pauseButton.setVisibility(View.VISIBLE);
        binding.startGameButton.setVisibility(View.INVISIBLE);
    }

    public interface GameOverListener {
        void onGameOver();
    }

    public interface ScoreListener {
        void onScoreChange();
    }
}
