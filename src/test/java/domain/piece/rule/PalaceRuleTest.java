package domain.piece.rule;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PalaceRuleTest {

    @Test
    void 경로에_다른_말이있으면_예외를_발생시킨다() {
        // given
        PalaceRule rule = new PalaceRule();
        List<Position> path = List.of(new Position(2, 3));
        List<Piece> piecesInPath = List.of();
        Piece piece = new Piece(Team.CHO, PieceType.GUNG, new Position(2, 4));
        // when & then
        Assertions.assertThatThrownBy(() -> rule.validate(path, piecesInPath, piece, Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("궁 내부에서만 이동할 수 있습니다.");
    }

}