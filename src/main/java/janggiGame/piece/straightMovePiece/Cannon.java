package janggiGame.piece.straightMovePiece;

import janggiGame.board.Dot;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cannon extends StraightMovePiece {
    private static final String NAME = "포";

    public Cannon(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public void validateMove(Map<Dot, Piece> routesWithPiece, Piece destinationPiece) {
        validateSameDynasty(destinationPiece);

        if (destinationPiece != null && destinationPiece.getName().equals(NAME)) {
            throw new UnsupportedOperationException("[ERROR] 포는 포를 공격할 수 없습니다.");
        }

        List<Piece> pieces = routesWithPiece.values()
                .stream()
                .filter(Objects::nonNull)
                .toList();

        if (pieces.size() != 1) {
            throw new UnsupportedOperationException("[ERROR] 포는 경로에 단 한개의 기물만 존재해야 합니다.");
        }

        if (pieces.getFirst().getName().equals(NAME)) {
            throw new UnsupportedOperationException("[ERROR] 포끼리 뛰어 넘을 수 없습니다.");
        }
    }


    @Override
    public String getName() {
        return NAME;
    }
}
