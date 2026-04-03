package domain.rule;

import domain.intersection.Intersection;
import domain.piece.Elephant;
import domain.piece.move.ElephantMoveRule;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElephantMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void should_throw_exception_when_destination_is_same_team() {
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Elephant elephant = new Elephant(Team.CHO);
        Elephant sameTeamPiece = new Elephant(Team.CHO);

        Intersection from = new Intersection(start, elephant);
        Intersection middleIntersection1 = Intersection.empty(middlePoint1);
        Intersection middleIntersection2 = Intersection.empty(middlePoint2);
        Intersection to = new Intersection(end, sameTeamPiece);

        ElephantMoveRule elephantRule = new ElephantMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    elephantRule.validateMoveRule(from, List.of(middleIntersection1, middleIntersection2, to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("상의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void should_throw_exception_when_path_has_obstacle() {
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Elephant elephant = new Elephant(Team.CHO);
        Elephant obstacle = new Elephant(Team.CHO);

        Intersection from = new Intersection(start, elephant);
        Intersection middleIntersection1 = new Intersection(middlePoint1, obstacle);
        Intersection middleIntersection2 = Intersection.empty(middlePoint2);
        Intersection to = Intersection.empty(end);

        ElephantMoveRule elephantMoveRule = new ElephantMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    elephantMoveRule.validateMoveRule(from, List.of(middleIntersection1, middleIntersection2, to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
    }

    @Test
    @DisplayName("상은 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void should_move_elephant_when_no_obstacle_and_destination_is_empty() {
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Elephant elephant = new Elephant(Team.CHO);

        Intersection from = new Intersection(start, elephant);
        Intersection middleIntersection1 = Intersection.empty(middlePoint1);
        Intersection middleIntersection2 = Intersection.empty(middlePoint2);
        Intersection to = Intersection.empty(end);

        ElephantMoveRule elephantMoveRule = new ElephantMoveRule();

        Assertions.assertThatCode(
                        () -> elephantMoveRule.validateMoveRule(from, List.of(middleIntersection1, middleIntersection2, to)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상은 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void should_move_elephant_when_no_obstacle_and_destination_is_opponent() {
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Elephant elephant = new Elephant(Team.CHO);
        Elephant opponent = new Elephant(Team.HAN);

        Intersection from = new Intersection(start, elephant);
        Intersection middleIntersection1 = Intersection.empty(middlePoint1);
        Intersection middleIntersection2 = Intersection.empty(middlePoint2);
        Intersection to = new Intersection(start, opponent);

        ElephantMoveRule elephantMoveRule = new ElephantMoveRule();

        Assertions.assertThatCode(
                        () -> elephantMoveRule.validateMoveRule(from, List.of(middleIntersection1, middleIntersection2, to)))
                .doesNotThrowAnyException();
    }

}
