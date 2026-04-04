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
    @DisplayName("한 진영 졸은 아래쪽으로 한 칸 전진할 수 있다.")
    void should_allow_downward_move_for_han_soldier() {
        Point start = new Point(0, 0);
        Point end = start.next(Vector.DOWN);

        Soldier soldier = new Soldier(Team.HAN);
        Soldier anotherTeamPiece = new Soldier(Team.CHO);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        List<Point> possiblePoints = soldierMoveRule.findPossiblePoints(from, to);
        Assertions.assertThat(possiblePoints).containsExactly(end);
    }

    @Test
    @DisplayName("초 진영 졸은 위쪽으로 한 칸 전진할 수 있다.")
    void should_allow_upward_move_for_cho_soldier() {
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Soldier soldier = new Soldier(Team.CHO);
        Soldier anotherTeamPiece = new Soldier(Team.HAN);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        List<Point> possiblePoints = soldierMoveRule.findPossiblePoints(from, to);
        Assertions.assertThat(possiblePoints).containsExactly(end);
    }

    @Test
    @DisplayName("한 진영 졸은 위쪽으로 전진할 수 없다.")
    void should_reject_upward_move_for_han_soldier() {
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Soldier soldier = new Soldier(Team.HAN);
        Soldier anotherTeamPiece = new Soldier(Team.CHO);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        Assertions.assertThatThrownBy(() -> soldierMoveRule.findPossiblePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("초 진영 졸은 아래쪽으로 전진할 수 없다.")
    void should_reject_downward_move_for_cho_soldier() {
        Point start = new Point(0, 0);
        Point end = start.next(Vector.DOWN);

        Soldier soldier = new Soldier(Team.CHO);
        Soldier anotherTeamPiece = new Soldier(Team.HAN);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        Assertions.assertThatThrownBy(() -> soldierMoveRule.findPossiblePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
