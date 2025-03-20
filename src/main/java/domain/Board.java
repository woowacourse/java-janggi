package domain;

import domain.piece.Piece;
import java.util.List;

public class Board {

    private final ChoBoard choBoard;
    private final HanBoard hanBoard;

    public Board(ChoBoard choBoard, HanBoard hanBoard) {
        this.choBoard = choBoard;
        this.hanBoard = hanBoard;
    }

    public void moveChoPiece(BoardLocation current, BoardLocation destination) {
        Piece piece = choBoard.findByLocation(current);
        List<BoardLocation> allPath = piece.createAllPath(current, destination);
    }

    public void moveHanPiece(BoardLocation current, BoardLocation destination) {

    }
}
