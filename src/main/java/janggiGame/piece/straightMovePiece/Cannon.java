package janggiGame.piece.straightMovePiece;

import janggiGame.position.Position;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cannon extends StraightMovePiece {

    public Cannon(Dynasty dynasty) {
        super(dynasty, Type.CANNON);
    }

    @Override
    public void validateMove(Map<Position, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        if (destinationPiece != null && destinationPiece.getType() == Type.CANNON) {
            throw new UnsupportedOperationException("[ERROR] 포는 포를 공격할 수 없습니다.");
        }

        List<Piece> pieces = routesWithPiece.values()
                .stream()
                .filter(Objects::nonNull)
                .toList();

        if (pieces.size() != 1) {
            throw new UnsupportedOperationException("[ERROR] 포는 경로에 단 한개의 기물만 존재해야 합니다.");
        }

        if (pieces.getFirst().getType() == Type.CANNON) {
            throw new UnsupportedOperationException("[ERROR] 포끼리 뛰어 넘을 수 없습니다.");
        }
    }
}
