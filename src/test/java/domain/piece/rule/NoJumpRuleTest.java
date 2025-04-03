package domain.piece.rule;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NoJumpRuleTest {

    @Test
    void 경로에_다른_말이있으면_예외를_발생시킨다() {
        // given
        NoJumpRule rule = new NoJumpRule();
        List<Position> path = List.of(new Position(2, 1));
        List<Piece> piecesInPath = List.of(new Piece(Team.HAN, PieceType.CHA, new Position(2, 1)));
        Piece piece = new Piece(Team.CHO, PieceType.PO, new Position(1, 1));
        // when & then
        Assertions.assertThatThrownBy(() -> rule.validate(path, piecesInPath, piece, Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 말이 존재해서 해당 좌표로 갈 수가 없습니다.");
    }

}