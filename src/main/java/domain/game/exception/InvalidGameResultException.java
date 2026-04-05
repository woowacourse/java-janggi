package domain.game.exception;

import domain.exception.JanggiException;

public class InvalidGameResultException extends JanggiException {

    public InvalidGameResultException(GameErrorMessage errorMessage) {
        super(errorMessage.message());
    }
}
