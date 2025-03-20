package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Piece;
import java.util.Map;

public class JanggiGame {

    private final JanggiBoard janggiBoard;

    public JanggiGame(BoardGenerator boardGenerator) {
        this.janggiBoard = new JanggiBoard(boardGenerator);
    }


    public void move(Position startPosition, Position targetPosition) {

    }

    public Map<Position, Piece> getBoardState() {
        return janggiBoard.getBoard();
    }
}
