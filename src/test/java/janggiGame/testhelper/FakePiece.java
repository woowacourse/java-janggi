package janggiGame.testhelper;

import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.PieceType;
import java.util.ArrayList;
import java.util.List;

public class FakePiece extends Piece {
    // 해당 구현체는 추상 클래스인 Piece 테스트를 위한 구현체이다.

    public FakePiece(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Dot> getIntermediatePoints(Dot origin, Dot destination) {
        List<Dot> intermediatePoints = new ArrayList<>();
        intermediatePoints.add(Dot.of(0, 8));
        return intermediatePoints;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
