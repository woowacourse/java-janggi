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

import fixture.JanggiBoardFixture;
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static domain.move.directions.exception.DirectionError.INVALID_DIRECTION;
import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;
import static domain.move.path.exception.PathError.CANNOT_MOVE_PATH_HAS_OBSTACLE;

class ChariotMoveRuleTest {

    final Piece chariot = new Piece(Team.CHO, PieceType.CHARIOT);

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

    final Intersection intersection1 = NormalIntersection.empty(middlePoint1);
    final Intersection intersection2 = NormalIntersection.empty(middlePoint2);
    final Intersection intersection3 = NormalIntersection.empty(middlePoint3);
    final Intersection intersection4 = NormalIntersection.empty(middlePoint4);
    final Intersection intersection5 = NormalIntersection.empty(middlePoint5);
    final Intersection intersection6 = NormalIntersection.empty(middlePoint6);
    final Intersection intersection7 = NormalIntersection.empty(middlePoint7);
    final Intersection intersection8 = NormalIntersection.empty(middlePoint8);

    final Point centerPointCho = new Point(8, 4);
    final Point leftTopPointCho = new Point(7, 3);
    final Point leftBottomPointCho = new Point(9, 3);
    final Point rightTopPointCho = new Point(7, 5);
    final Point rightBottomPointCho = new Point(9, 5);

    final Intersection centerCho = CenterPalace.empty(centerPointCho);
    final Intersection leftTopCho = LeftTopPalace.empty(leftTopPointCho);
    final Intersection leftBottomCho = LeftBottomPalace.empty(leftBottomPointCho);
    final Intersection rightTopCho = RightTopPalace.empty(rightTopPointCho);
    final Intersection rightBottomCho = RightBottomPalace.empty(rightBottomPointCho);

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        // given
        Team sameTeam = Team.CHO;

        Intersection origin = new NormalIntersection(start, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection sameTeamDestination = new NormalIntersection(end, new Piece(sameTeam, PieceType.CHARIOT));

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

        Intersection origin = new NormalIntersection(start, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection obstacle = new NormalIntersection(middlePoint7, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection destination = NormalIntersection.empty(end);

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
        Intersection origin = new NormalIntersection(start, chariot);
        Intersection emptyDestination = NormalIntersection.empty(end);
        Intersection expected = new NormalIntersection(end, chariot);

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
        janggiBoard.processTurn(start, end);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("차는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void chariotCanMoveWhenNoObstacleAndDestinationIsOpponent() {
        // given
        Piece opponentChariot = new Piece(Team.HAN, PieceType.CHARIOT);

        Intersection origin = new NormalIntersection(start, chariot);
        Intersection opponentDestination = new NormalIntersection(end, opponentChariot);
        Intersection expected = new NormalIntersection(end, chariot);

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
        janggiBoard.processTurn(start, end);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Nested
    @DisplayName("차의 궁성 내 대각선 이동 테스트")
    class ChariotPalaceDiagonalMoveTest {

        @Test
        @DisplayName("차는 좌하궁성(9,3)에서 우상궁성(7,5)로 움직일 수 있다.(↗,↗)")
        void chariotCanMoveRightUpTwiceWhenChariotInLeftBottomPalace() {
            // given
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, chariot);
            Intersection expected = new RightTopPalace(rightTopPointCho, chariot);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, centerCho, rightTopCho);

            // when
            janggiBoard.processTurn(leftBottomPointCho, rightTopPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("차는 좌하궁성(9,3)에서 가운데(8,4)로 움직일 수 있다.(↗)")
        void chariotCanMoveRightUpWhenChariotInLeftBottomPalace() {
            // given
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, chariot);
            Intersection expected = new CenterPalace(centerPointCho, chariot);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, centerCho);

            // when
            janggiBoard.processTurn(leftBottomPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("차는 가운데(8,4)에서 우상 궁성(7,5)로 움직일 수 있다.(↗)")
        void chariotCanMoveRightUpWhenChariotInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, chariot);
            Intersection expected = new RightTopPalace(rightTopPointCho, chariot);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, rightTopCho);

            // when
            janggiBoard.processTurn(centerPointCho, rightTopPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPointCho))
                    .isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("차의 궁성 내 대각선 이동 예외 테스트")
    class GeneralMoveExceptionInPalaceTest {

        @Test
        @DisplayName("차는 가운데(8,4)에서 대각선으로 두 칸 움직이면 예외가 발생한다. 테스트 방향(↗,↗)")
        void shouldThrowExceptionprocessTurnChariotToRightTopFromCenterPalace() {
            // given
            Point outOtLeftUpPoint = rightTopPointCho.next(Vector.RIGHT_UP);

            Intersection centerPalace = new CenterPalace(centerPointCho, chariot);
            Intersection twiceLeftTopIntersection = NormalIntersection.empty(outOtLeftUpPoint);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, rightTopCho, twiceLeftTopIntersection);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(centerPointCho, outOtLeftUpPoint))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("차가 좌하궁성에 있을 때, 왼쪽 위(↖)로 움직이려하면 예외가 발생한다. ")
        void shouldThrowExceptionprocessTurnChariotFromLeftBottomToLeftUp() {
            // given
            Point leftBottomPoint = new Point(9, 3);
            Point leftUpPoint = leftBottomPoint.next(Vector.LEFT_UP);

            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPoint, chariot);
            Intersection leftUpNormalIntersection = new NormalIntersection(leftUpPoint, Piece.none());
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, rightTopCho, leftUpNormalIntersection);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftBottomPoint, leftUpPoint))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("차가 일반 궁성에 있을때, 대각선으로 움직이려 하면 예외가 발생한다. 테스트 방향 (↗)")
        void shouldThrowExceptionprocessTurnChariotToDiagonalFromNormalPalace() {
            // given
            Point normalLeftPalacePoint = new Point(8, 3);
            Point normalTopPalacePoint = normalLeftPalacePoint.next(Vector.RIGHT_UP);

            Intersection normalLeftPalace = new NormalPalace(normalLeftPalacePoint, chariot);
            Intersection normalTopPalace = new NormalPalace(normalTopPalacePoint, Piece.none());
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(normalLeftPalace, normalTopPalace);

            // when & then
            Assertions.assertThatThrownBy(() -> {
                        janggiBoard.processTurn(normalLeftPalacePoint, normalTopPalacePoint);
                    }).isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

    }

}
