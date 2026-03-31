package domain.move.rule;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import java.util.List;
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;
import static domain.move.path.exception.PathError.CANNOT_MOVE_PATH_HAS_OBSTACLE;

class ChariotMoveRuleTest {

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
        // given
        Team sameTeam = Team.CHO;

        Intersection origin = new Intersection(start, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection sameTeamIntersection = new Intersection(end, new Piece(sameTeam, PieceType.CHARIOT));

        // when
        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();
        Path sameTeamPath = new Path(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                intersection5,
                intersection6,
                intersection7,
                intersection8,
                sameTeamIntersection)
        );

        // then
        Assertions.assertThatThrownBy(() -> chariotMoveRule.validateMoveRule(sameTeamPath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("차의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void shouldThrowExceptionWhenPathHasObstacle() {
        // given
        Team sameTeam = Team.CHO;

        Intersection origin = new Intersection(start, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection obstacle = new Intersection(middlePoint7, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection destination = Intersection.empty(end);

        // when
        ChariotMoveRule chariotMoveRule = new ChariotMoveRule();
        Path obstaclePath = new Path(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                intersection5,
                intersection6,
                obstacle,
                intersection8,
                destination)
        );

        // then
        Assertions.assertThatThrownBy(() -> chariotMoveRule.validateMoveRule(obstaclePath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_PATH_HAS_OBSTACLE.getMessage());
    }

    @Test
    @DisplayName("차는 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void chariotCanMove_WhenNoObstacle_AndDestinationIsEmpty() {
        // given
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Intersection origin = new Intersection(start, chariot);
        Intersection emptyDestination = Intersection.empty(end);
        Intersection expected = new Intersection(end, chariot);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                intersection5,
                intersection6,
                intersection7,
                intersection8,
                emptyDestination)));

        // when
        janggiBoard.tryToMove(start, end, Team.CHO);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("차는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void chariotCanMoveWhenNoObstacleAndDestinationIsOpponent() {
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);
        Piece opponentChariot = new Piece(Team.HAN, PieceType.CHARIOT);

        Intersection origin = new Intersection(start, chariot);
        Intersection opponentDestination = new Intersection(end, opponentChariot);
        Intersection expected = new Intersection(end, chariot);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                intersection5,
                intersection6,
                intersection7,
                intersection8,
                opponentDestination)));

        // when
        janggiBoard.tryToMove(start, end, Team.CHO);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
