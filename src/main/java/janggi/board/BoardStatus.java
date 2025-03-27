package janggi.board;

import janggi.piece.Side;

public enum BoardStatus {

    CHO_TURN("초나라 차례", Side.CHO),
    HAN_TURN("한나라 차례", Side.HAN),
    CHO_WIN("초나라 승리!", Side.CHO),
    HAN_WIN("한나라 승리!", Side.HAN),
    ;

    private final String message;
    private final Side side;

    BoardStatus(final String message, final Side side) {
        this.message = message;
        this.side = side;
    }

    public String getMessage() {
        return message;
    }

    public Side getSide() {
        return side;
    }

}
