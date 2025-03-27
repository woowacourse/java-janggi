package domain.piece;

import domain.board.Axis;
import domain.board.BoardLocation;
import domain.board.BoardVector;
import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    protected void validateArrival(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        if (boardVector.isNotAxis()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }
    }

    @Override
    protected List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);

        Axis quadrant = Axis.findQuadrant(boardVector);
        return quadrant.createAllPath(current, boardVector);
    }

    @Override
    protected void validateMovePath(List<Piece> pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }
}
