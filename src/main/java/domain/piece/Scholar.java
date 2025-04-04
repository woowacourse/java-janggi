package domain.piece;

import domain.board.BoardLocation;
import domain.board.BoardVector;
import domain.board.Palace;
import java.util.Collections;
import java.util.List;

public class Scholar extends Piece {

    public Scholar(Team team) {
        super(team, new Score(3));
    }

    @Override
    protected void validateArrival(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        validateInPalace(current, destination);

        if (boardVector.isNotAxis() && canNotMoveDiagonal(current, destination, boardVector)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
        }
    }

    @Override
    protected List<BoardLocation> extractIntermediatePath(BoardLocation current, BoardLocation destination) {
        return Collections.emptyList();
    }

    @Override
    protected void validateMovePath(List<Piece> pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public PieceType getType() {
        return PieceType.SCHOLAR;
    }

    private void validateInPalace(BoardLocation current, BoardLocation destination) {
        Palace palace = team.getPalace();
        palace.validateInPalace(current, destination);
    }

    private boolean canNotMoveDiagonal(BoardLocation current, BoardLocation destination, BoardVector boardVector) {
        return Palace.isNotDiagonalMoveAllowed(current, destination) || boardVector.isNotDiagonal();
    }
}
