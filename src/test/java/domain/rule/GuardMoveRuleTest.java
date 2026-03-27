package domain.rule;

import domain.intersection.Intersection;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Soldier;
import domain.piece.Team;
import domain.piece.move.GeneralMoveRule;
import domain.piece.move.GuardMoveRule;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GuardMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Guard guard = new Guard(sameTeam);
        Soldier sameTeamPiece = new Soldier(sameTeam);

        Intersection from = new Intersection(start, guard);
        Intersection to = new Intersection(end, sameTeamPiece);

        GuardMoveRule guardMoveRule = new GuardMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    guardMoveRule.checkMoveRule(from, List.of(to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("사는 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGuardWhenDestinationIsEmpty() {
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Guard guard = new Guard(sameTeam);

        Intersection from = new Intersection(start, guard);
        Intersection to = Intersection.empty(end);

        GuardMoveRule guardMoveRule = new GuardMoveRule();

        Assertions.assertThat(guardMoveRule.checkMoveRule(from, List.of(to)))
                .isTrue();
    }

    @Test
    @DisplayName("사는 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGuardWhenDestinationIsOpponent() {
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Guard guard = new Guard(sameTeam);
        Soldier opponent = new Soldier(anotherTeam);

        Intersection from = new Intersection(start, guard);
        Intersection to = new Intersection(end, opponent);

        GuardMoveRule guardMoveRule = new GuardMoveRule();

        Assertions.assertThat(guardMoveRule.checkMoveRule(from, List.of(to)))
                .isTrue();
    }

}
