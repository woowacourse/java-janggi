package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;
import java.util.function.Function;

public class EmptyPiece extends Piece {

    private EmptyPiece() {
        super(Team.NONE, List.of());
    }

    private static class LazyHolder {
        private static final EmptyPiece INSTANCE = new EmptyPiece();
    }

    public static EmptyPiece getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public void validateMove(Position source, Position target, Function<Position, Piece> pieceAt) {
        throw new IllegalArgumentException("빈 기물을 선택했습니다. 아군 기물을 선택해 주세요.");
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public double score() {
        return 0.0;
    }
}
