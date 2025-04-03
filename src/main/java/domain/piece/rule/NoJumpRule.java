package domain.piece.rule;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Optional;

public class NoJumpRule implements Rule {

    @Override
    public void validate(List<Position> path, List<Piece> piecesInPath, Piece selectedPiece,
                         Optional<Piece> targetPiece) {
        if (!piecesInPath.isEmpty()) {
            throw new IllegalArgumentException("다른 말이 존재해서 해당 좌표로 갈 수가 없습니다.");
        }
    }
}
