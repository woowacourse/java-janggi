package domain;

import dto.BoardDTO;
import java.util.List;

public class JanggiGame {

    private static final int MAX_ROW = 9;
    private static final int MIN_ROW = 0;
    private static final int MAX_COL = 8;
    private static final int MIN_COL = 0;

    private final Board board;
    private GameStatus gameStatus;

    public JanggiGame(Board board) {
        this.board = board;
    }

//    public List<BoardDTO> pieceInfo() {
//        if (gameStatus.equals(GameStatus.GREEN_PLAYER_TURN)) {
//            return board.redPieceInfo();
//        }
//
//        return board.redPieceInfo();
//    }

    private void validOutOfRange(Position targetPosition) {
        if (targetPosition.row() > MAX_ROW || targetPosition.row() < MIN_ROW || targetPosition.col() > MAX_COL
                || targetPosition.col() < MIN_COL) {
            throw new IllegalArgumentException();
        }
    }

}
