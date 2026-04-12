package domain.game.exception;

import domain.exception.JanggiException;

public class GameIdAlreadyExistsException extends JanggiException {

    public GameIdAlreadyExistsException(GameErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
