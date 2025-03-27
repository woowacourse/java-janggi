package domain.piece;

import domain.pattern.Direction;
import domain.pattern.Path;
import domain.piece.state.MovedCannon;

public class Cannon extends Piece {
    public Cannon(Side side) {
        super(7, side, new Path(Direction.createChariotOrCannonPatternMap()), new MovedCannon());
    }

    @Override
    public void capture(Piece piece) {
        if (piece.getPieceSymbol().equals(PieceSymbol.CANNON)) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.CANNON;
    }
}
