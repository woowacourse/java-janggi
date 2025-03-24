package janggi.controller;

public class GameStatus {

    private boolean isPlaying = true;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void stopPlaying() {
        isPlaying = false;
    }
}
