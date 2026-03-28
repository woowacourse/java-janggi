package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.move.rule.ChariotMoveRule;
import domain.point.Point;
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
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        Team sameTeam = Team.CHO;

        Intersection from = new Intersection(start, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection to = new Intersection(end, new Piece(sameTeam, PieceType.CHARIOT));

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    chariotMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            intersection5,
                            intersection6,
                            intersection7,
                            intersection8,
                            to)));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("차의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void shouldThrowExceptionWhenPathHasObstacle() {
        Team sameTeam = Team.CHO;

        Intersection from = new Intersection(start, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection obstacle = new Intersection(middlePoint7, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection to = Intersection.empty(end);

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    chariotMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            intersection5,
                            intersection6,
                            obstacle,
                            intersection8,
                            to)));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
    }

    @Test
    @DisplayName("차는 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void chariotCanMove_WhenNoObstacle_AndDestinationIsEmpty() {
        Intersection from = new Intersection(start, new Piece(Team.CHO, PieceType.CHARIOT));
        Intersection to = Intersection.empty(end);

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThat(
                chariotMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                        intersection2,
                        intersection3,
                        intersection4,
                        intersection5,
                        intersection6,
                        intersection7,
                        intersection8,
                        to))))
                .isTrue();
    }

    @Test
    @DisplayName("차는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void chariotCanMoveWhenNoObstacleAndDestinationIsOpponent() {
        Intersection from = new Intersection(start, new Piece(Team.CHO, PieceType.CHARIOT));
        Intersection to = new Intersection(end, new Piece(Team.HAN, PieceType.CHARIOT));

        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();

        Assertions.assertThat(
                        chariotMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                                intersection2,
                                intersection3,
                                intersection4,
                                intersection5,
                                intersection6,
                                intersection7,
                                intersection8,
                                to))))
                .isTrue();
    }

}
