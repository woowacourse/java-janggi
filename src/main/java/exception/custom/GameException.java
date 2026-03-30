package exception.custom;

import exception.GameErrorMessage;

public class GameException extends RuntimeException{
    private final String message;

    public GameException() {
        this.message = GameErrorMessage.DEFAULT_GAME_ERROR.getMessage();
    }

    protected GameException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
