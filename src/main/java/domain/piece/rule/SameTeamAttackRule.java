package domain.piece.rule;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Optional;

public class SameTeamAttackRule implements Rule {

    @Override
    public void validate(List<Position> path, List<Piece> piecesInPath, Piece selectedPiece,
                         Optional<Piece> targetPiece) {
        if (targetPiece.isPresent() && targetPiece.get().isTeam(selectedPiece)) {
            throw new IllegalArgumentException("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
        }
    }
}
