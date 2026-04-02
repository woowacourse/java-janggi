package domain.move.rule;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.intersection.palace.*;
import domain.move.directions.Vector;
import domain.move.directions.exception.DirectionException;
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

import static domain.move.directions.exception.DirectionError.INVALID_DIRECTION;
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
        Intersection sameTeamDestination = new Intersection(end, new Piece(sameTeam, PieceType.CHARIOT));

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
                sameTeamDestination)
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
    void chariotCanMoveWhenNoObstacleAndDestinationIsEmpty() {
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
        // given
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

    @Test
    @DisplayName("차가 좌하궁성 안에 있으면, 오른쪽 위(↗,↗)로 두칸 움직일 수 있다.")
    void chariotCanMoveRightUpTwiceWhenChariotInLeftBottomPalace() {
        // given
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Point leftBottomPoint = new Point(9, 3);
        Point centerPoint = leftBottomPoint.next(Vector.RIGHT_UP);
        Point rightTopPoint = centerPoint.next(Vector.RIGHT_UP);

        Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPoint, chariot);
        Intersection centerPalace = RightTopPalace.empty(centerPoint);
        Intersection rightTopPalace = RightTopPalace.empty(rightTopPoint);
        Intersection expected = new RightTopPalace(rightTopPoint, chariot);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                leftBottomPalace,
                centerPalace,
                rightTopPalace
        )));

        // when
        janggiBoard.tryToMove(leftBottomPoint, rightTopPoint, Team.CHO);

        // then
        Assertions.assertThat(janggiBoard.findIntersection(rightTopPoint))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("차가 좌하궁성 안에 있으면, 오른쪽 위(↗)로 한 칸 움직일 수 있다.")
    void chariotCanMoveRightUpWhenChariotInLeftBottomPalace() {
        // given
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Point leftBottomPoint = new Point(9, 3);
        Point centerPoint = new Point(8, 4);

        Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPoint, chariot);
        Intersection centerPalace = RightTopPalace.empty(centerPoint);
        Intersection expected = new RightTopPalace(centerPoint, chariot);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                leftBottomPalace,
                centerPalace
        )));

        // when
        janggiBoard.tryToMove(leftBottomPoint, centerPoint, Team.CHO);

        // then
        Assertions.assertThat(janggiBoard.findIntersection(centerPoint))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("차가 가운데 궁성 안에 있으면, 오른쪽 위(↗)로 한 칸 움직일 수 있다.")
    void chariotCanMoveRightUpWhenChariotInCenterPalace() {
        // given
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Point centerPoint = new Point(8, 4);
        Point rightTopPoint = centerPoint.next(Vector.RIGHT_UP);

        Intersection centerPalace = new CenterPalace(centerPoint, chariot);
        Intersection rightTopPalace = RightTopPalace.empty(rightTopPoint);
        Intersection expected = new RightTopPalace(rightTopPoint, chariot);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                centerPalace,
                rightTopPalace
        )));

        // when
        janggiBoard.tryToMove(centerPoint, rightTopPoint, Team.CHO);

        // then
        Assertions.assertThat(janggiBoard.findIntersection(rightTopPoint))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("차가 가운데 궁성 안에서 대각선으로 두 칸 움직이려 하면 예외가 발생한다. 테스트 방향(↗,↗)")
    void shouldThrowExceptionTryToMoveChariotToRightTopFromCenterPalace() {
        // given
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Point centerPoint = new Point(8, 4);
        Point rightTopPoint = centerPoint.next(Vector.RIGHT_UP);
        Point twiceLeftUpPoint = rightTopPoint.next(Vector.RIGHT_UP);

        Intersection centerPalace = new CenterPalace(centerPoint, chariot);
        Intersection rightTopPalace = RightTopPalace.empty(rightTopPoint);
        Intersection twiceLeftTopIntersection = NormalIntersection.empty(twiceLeftUpPoint);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                centerPalace,
                rightTopPalace,
                twiceLeftTopIntersection
        )));

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(centerPoint, twiceLeftUpPoint, Team.CHO))
                .isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }

    @Test
    @DisplayName("차가 좌하궁성에 있을 때, 왼쪽 위(↖)로 움직이려하면 예외가 발생한다. ")
    void shouldThrowExceptionTryToMoveChariotFromLeftBottomToLeftUp() {
        // given
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Point leftBottomPoint = new Point(9, 3);
        Point leftUpPoint = leftBottomPoint.next(Vector.LEFT_UP);

        Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPoint, chariot);
        Intersection leftUpNormalIntersection = new Intersection(leftUpPoint, Piece.none());

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                leftBottomPalace,
                leftUpNormalIntersection
        )));

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(leftBottomPoint, leftUpPoint, Team.CHO))
                .isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }

    @Test
    @DisplayName("차가 일반 궁성에 있을때, 대각선으로 움직이려 하면 예외가 발생한다. 테스트 방향 (↗)")
    void shouldThrowExceptionTryToMoveChariotToDiagonalFromNormalPalace() {
        // given
        Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

        Point normalLeftPalacePoint = new Point(8, 3);
        Point normalTopPalacePoint = normalLeftPalacePoint.next(Vector.RIGHT_UP);

        Intersection normalLeftPalace = new NormalPalace(normalLeftPalacePoint, chariot);
        Intersection normalTopPalace = new NormalPalace(normalTopPalacePoint, Piece.none());

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                normalLeftPalace,
                normalTopPalace
        )));

        // when & then
        Assertions.assertThatThrownBy(() -> {
                    janggiBoard.tryToMove(normalLeftPalacePoint, normalTopPalacePoint, Team.CHO);
                }).isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }

}
