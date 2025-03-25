package game.domain.piece;

import game.domain.board.BoardLocation;
import java.util.List;

public class DefaultPiece extends Piece {

    public DefaultPiece(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation target) {
        throw new IllegalStateException("[ERROR] 실행할 수 없습니다.");
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation target) {
        throw new IllegalStateException("[ERROR] 실행할 수 없습니다.");
    }

    @Override
    public void validateArrival(List<Piece> pathPiece) {
        throw new IllegalStateException("[ERROR] 실행할 수 없습니다.");
    }

    @Override
    public void validateKillable(Piece destinationPiece) {
        throw new IllegalStateException("[ERROR] 실행할 수 없습니다.");
    }

    @Override
    public PieceType getType() {
        return PieceType.DEFAULT;
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
