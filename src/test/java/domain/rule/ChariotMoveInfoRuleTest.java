package domain.rule;

import domain.intersection.Intersection;
import domain.move.ChariotMoveRule;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChariotMoveInfoRuleTest {
    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void should_throw_exception_when_destination_is_same_team() {
        Point start = new Point(0, 0);
        Point end = new Point(9, 0);
        Team sameTeam = Team.CHO;

        Intersection from = new Intersection(start, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection to = new Intersection(end, new Piece(sameTeam, PieceType.CHARIOT));

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    chariotMoveRule.validateMoveRule(from, List.of(to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 팀의 위치로 이동할 수 없습니다.");
    }
}
