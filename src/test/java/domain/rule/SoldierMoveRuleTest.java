package domain.rule;

import domain.intersection.Intersection;
import domain.piece.Soldier;
import domain.piece.move.SoldierMoveRule;
import domain.piece.move.Vector;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoldierMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void should_throw_exception_when_destination_is_same_team() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Soldier soldier = new Soldier(sameTeam);
        Soldier sameTeamPiece = new Soldier(sameTeam);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, sameTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    soldierMoveRule.validateMoveRule(from, List.of(to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("한 진영인 졸의 직진 방향은 내려감이다.")
    void should_move_down_for_han_soldier() {
        Point start = new Point(0, 0);
        Point end = start.next(Vector.DOWN);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Soldier soldier = new Soldier(sameTeam);
        Soldier anotherTeamPiece = new Soldier(anotherTeam);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        Assertions.assertThatCode(() -> soldierMoveRule.validateMoveRule(from, List.of(to)))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("한 진영인 졸의 직진 방향은 올라감이다.")
    void should_move_up_for_cho_soldier() {
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Soldier soldier = new Soldier(sameTeam);
        Soldier anotherTeamPiece = new Soldier(anotherTeam);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        Assertions.assertThatCode(() -> soldierMoveRule.validateMoveRule(from, List.of(to)))
                .doesNotThrowAnyException();

    }

}
