package domain.piece;

import domain.board.BoardLocation;
import domain.board.BoardVector;
import domain.board.Palace;
import java.util.Collections;
import java.util.List;

public class King extends Piece {

    public King(Team team, Score score) {
        super(team, score);
    }

    @Override
    protected void validateArrival(BoardLocation current, BoardLocation destination) {
        BoardVector boardVector = BoardVector.between(current, destination);
        validateInPalace(current, destination);

        if (boardVector.isAxis()) {
            return;
        }
        if (Palace.isDiagonalMoveAllowed(current, destination) && boardVector.isQuadrant()) {
            return;
        }
        throw new IllegalArgumentException("[ERROR] 해당 기물은 목표 위치로 이동할 수 없습니다");
    }

    @Override
    protected List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return Collections.emptyList();
    }

    @Override
    protected void validateMovePath(List<Piece> pathPiece) {
        if (!pathPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 도착지로 이동할 수 없습니다.");
        }
    }

    @Override
    public boolean isStoppedGameIfDie() {
        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    private void validateInPalace(BoardLocation current, BoardLocation destination) {
        Palace palace = team.getPalace();
        palace.validateInPalace(current, destination);
    }
}
