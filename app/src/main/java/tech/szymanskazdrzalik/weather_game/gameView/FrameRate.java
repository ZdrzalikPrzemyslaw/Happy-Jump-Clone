package tech.szymanskazdrzalik.weather_game.gameView;

public class FrameRate {
    private static long start = 0, diff, wait;
    public static void capFrameRate(long fps) throws InterruptedException {
        FrameRate.wait = 1000 / fps;
        FrameRate.diff = System.currentTimeMillis() - start;
        if (diff < wait) {
            Thread.sleep(wait - diff);
        }
        start = System.currentTimeMillis();
    }
}
