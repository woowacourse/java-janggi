package janggiGame.testhelper;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.character.PieceType;
import java.util.ArrayList;
import java.util.List;

public class FakePiece extends Piece {
    // 해당 구현체는 추상 클래스인 Piece 테스트를 위한 구현체이다.

    public FakePiece(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public List<Position> getIntermediatePoints(Position origin, Position destination) {
        List<Position> intermediatePoints = new ArrayList<>();
        intermediatePoints.add(Position.of(0, 8));
        return intermediatePoints;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}
