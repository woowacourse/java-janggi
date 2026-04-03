package domain.rule;

import domain.intersection.Intersection;
import domain.piece.Horse;
import domain.piece.move.HorseMoveRule;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HorseMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void should_throw_exception_when_destination_is_same_team() {
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Horse horse = new Horse(Team.CHO);
        Horse sameTeamPiece = new Horse(Team.CHO);

        Intersection from = new Intersection(start, horse);
        Intersection middleIntersection = Intersection.empty(middlePoint);
        Intersection to = new Intersection(end, sameTeamPiece);

        HorseMoveRule horseRule = new HorseMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    horseRule.validateMoveRule(from, List.of(middleIntersection, to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("마의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void should_throw_exception_when_path_has_obstacle() {
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Horse horse = new Horse(Team.CHO);
        Horse obstacle = new Horse(Team.CHO);

        Intersection from = new Intersection(start, horse);
        Intersection middleIntersection = new Intersection(middlePoint, obstacle);
        Intersection to = Intersection.empty(end);

        HorseMoveRule horseRule = new HorseMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    horseRule.validateMoveRule(from, List.of(middleIntersection, to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
    }

    @Test
    @DisplayName("마는 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void should_move_horse_when_no_obstacle_and_destination_is_empty() {
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Horse horse = new Horse(Team.CHO);

        Intersection from = new Intersection(start, horse);
        Intersection middleIntersection = Intersection.empty(middlePoint);
        Intersection to = Intersection.empty(end);

        HorseMoveRule horseRule = new HorseMoveRule();

        Assertions.assertThatCode(() -> horseRule.validateMoveRule(from, List.of(middleIntersection, to)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("마는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void should_move_horse_when_no_obstacle_and_destination_is_opponent() {
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Horse horse = new Horse(Team.CHO);
        Horse opponent = new Horse(Team.HAN);

        Intersection from = new Intersection(start, horse);
        Intersection middleIntersection = Intersection.empty(middlePoint);
        Intersection to = new Intersection(start, opponent);

        HorseMoveRule horseRule = new HorseMoveRule();

        Assertions.assertThatCode(() -> horseRule.validateMoveRule(from, List.of(middleIntersection, to)))
                .doesNotThrowAnyException();
    }


}
