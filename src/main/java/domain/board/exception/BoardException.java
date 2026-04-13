package domain.board.exception;

import common.exception.JanggiException;

public class BoardException extends JanggiException {

    public BoardException(String errorMessage) {
        super(errorMessage);
    }

}
