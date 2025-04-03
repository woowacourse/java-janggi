package domain.piece.rule;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Optional;

public class JumpOverOnePieceRule implements Rule {

    @Override
    public void validate(List<Position> path, List<Piece> piecesInPath, Piece selectedPiece,
                         Optional<Piece> targetPiece) {
        if (piecesInPath.size() != 1) {
            throw new IllegalArgumentException("포는 다른 말 하나를 뛰어넘어야 합니다.");
        }
        if (selectedPiece.isSameType(piecesInPath.getFirst())) {
            throw new IllegalArgumentException("포는 포끼리 건너뛸 수 없습니다.");
        }
        if (targetPiece.isPresent() && targetPiece.get().isSameType(selectedPiece)) {
            throw new IllegalArgumentException("포는 포끼리 잡을 수 없습니다");
        }
    }
}
