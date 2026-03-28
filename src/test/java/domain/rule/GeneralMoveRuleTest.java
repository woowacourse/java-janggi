package domain.rule;

import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.move.rule.GeneralMoveRule;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GeneralMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, general);
        Intersection to = new Intersection(end, sameTeamPiece);

        GeneralMoveRule generalMoveRule = new GeneralMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    generalMoveRule.checkMoveRule(from, List.of(to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("장군은 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGeneralWhenDestinationIsEmpty() {
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);

        Intersection from = new Intersection(start, general);
        Intersection to = Intersection.empty(end);

        GeneralMoveRule generalMoveRule = new GeneralMoveRule();

        Assertions.assertThat(generalMoveRule.checkMoveRule(from, List.of(to)))
                .isTrue();
    }

    @Test
    @DisplayName("장군은 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGeneralWhenDestinationIsOpponent() {
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);
        Piece opponent = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, general);
        Intersection to = new Intersection(end, opponent);

        GeneralMoveRule generalMoveRule = new GeneralMoveRule();

        Assertions.assertThat(generalMoveRule.checkMoveRule(from, List.of(to)))
                .isTrue();
    }

}
