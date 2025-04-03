package domain.piece.rule;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SameTeamAttackRuleTest {

    @Test
    void 같은_팀_말이_있는_칸으로_할_수_없다() {
        // given
        SameTeamAttackRule rule = new SameTeamAttackRule();
        List<Position> path = List.of(new Position(2, 1));
        List<Piece> piecesInPath = List.of();
        Piece piece = new Piece(Team.CHO, PieceType.CHA, new Position(1, 1));
        Piece targetPiece = new Piece(Team.CHO, PieceType.PAWN, new Position(3, 1));
        // when & then
        Assertions.assertThatThrownBy(() -> rule.validate(path, piecesInPath, piece, Optional.of(targetPiece)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치는 아군의 말이 있으므로 이동 불가능 합니다.");
    }

}