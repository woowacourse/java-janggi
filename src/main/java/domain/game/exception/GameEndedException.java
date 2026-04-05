package domain.game.exception;

import domain.exception.JanggiException;

public class GameEndedException extends JanggiException {

    public GameEndedException(GameErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
