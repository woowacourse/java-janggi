package domain.rule;

import domain.intersection.Intersection;
import domain.piece.Chariot;
import domain.piece.move.ChariotMoveRule;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChariotMoveRuleTest {

    final Point start = new Point(0, 0);
    final Point middlePoint1 = new Point(1, 0);
    final Point middlePoint2 = new Point(2, 0);
    final Point middlePoint3 = new Point(3, 0);
    final Point middlePoint4 = new Point(4, 0);
    final Point middlePoint5 = new Point(5, 0);
    final Point middlePoint6 = new Point(6, 0);
    final Point middlePoint7 = new Point(7, 0);
    final Point middlePoint8 = new Point(8, 0);
    final Point end = new Point(9, 0);

    final Intersection intersection1 = Intersection.empty(middlePoint1);
    final Intersection intersection2 = Intersection.empty(middlePoint2);
    final Intersection intersection3 = Intersection.empty(middlePoint3);
    final Intersection intersection4 = Intersection.empty(middlePoint4);
    final Intersection intersection5 = Intersection.empty(middlePoint5);
    final Intersection intersection6 = Intersection.empty(middlePoint6);
    final Intersection intersection7 = Intersection.empty(middlePoint7);
    final Intersection intersection8 = Intersection.empty(middlePoint8);

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void should_throw_exception_when_destination_is_same_team() {
        Team sameTeam = Team.CHO;

        Intersection from = new Intersection(start, new Chariot(sameTeam));
        Intersection to = new Intersection(end, new Chariot(sameTeam));

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    chariotMoveRule.validateMoveRule(from, List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            intersection5,
                            intersection6,
                            intersection7,
                            intersection8,
                            to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("차의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void should_throw_exception_when_path_has_obstacle() {
        Team sameTeam = Team.CHO;

        Intersection from = new Intersection(start, new Chariot(sameTeam));
        Intersection obstacle = new Intersection(middlePoint7, new Chariot(sameTeam));
        Intersection to = Intersection.empty(end);

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    chariotMoveRule.validateMoveRule(from, List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            intersection5,
                            intersection6,
                            obstacle,
                            intersection8,
                            to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
    }

    @Test
    @DisplayName("차는 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void should_move_chariot_when_no_obstacle_and_destination_is_empty() {
        Intersection from = new Intersection(start, new Chariot(Team.CHO));
        Intersection to = Intersection.empty(end);

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThatCode(
                        () -> chariotMoveRule.validateMoveRule(from, List.of(intersection1,
                                intersection2,
                                intersection3,
                                intersection4,
                                intersection5,
                                intersection6,
                                intersection7,
                                intersection8,
                                to)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("차는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void should_move_chariot_when_no_obstacle_and_destination_is_opponent() {
        Intersection from = new Intersection(start, new Chariot(Team.CHO));
        Intersection to = new Intersection(end, new Chariot(Team.HAN));

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThatCode(
                        () -> chariotMoveRule.validateMoveRule(from, List.of(intersection1,
                                intersection2,
                                intersection3,
                                intersection4,
                                intersection5,
                                intersection6,
                                intersection7,
                                intersection8,
                                to)))
                .doesNotThrowAnyException();
    }

}
