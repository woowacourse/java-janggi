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
        if (piece.isMovable(current, destination)) {
            List<BoardLocation> allPath = piece.createAllPath(current, destination);
            if (piece.isCannon()){
                choBoard.validateCannon(allPath, destination);
                hanBoard.validateCannon(allPath, destination);
            }
            choBoard.validateAllyMove(allPath, destination);
            hanBoard.validateEnemyMove(allPath);
            hanBoard.removeIfHas(destination);
            choBoard.move(current, destination);
        }
    }

    public void moveHanPiece(BoardLocation current, BoardLocation destination) {
        Piece piece = choBoard.findByLocation(current);
        if (piece.isMovable(current, destination)) {
            List<BoardLocation> allPath = piece.createAllPath(current, destination);
            if (piece.isCannon()){
                choBoard.validateCannon(allPath, destination);
                hanBoard.validateCannon(allPath, destination);
            }
            hanBoard.validateAllyMove(allPath, destination);
            choBoard.validateEnemyMove(allPath);
            choBoard.removeIfHas(destination);
            hanBoard.move(current, destination);
        }
    }
}
