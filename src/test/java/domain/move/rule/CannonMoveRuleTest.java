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
import fixture.JanggiBoardFixture;
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.directions.exception.DirectionError.INVALID_DIRECTION;
import static domain.move.path.exception.PathError.*;

class CannonMoveRuleTest {

    final Piece cannon = new Piece(Team.CHO, PieceType.CANNON);

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

        Intersection origin = new NormalIntersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection onlyObstacleIntersection = new NormalIntersection(middlePoint5, new Piece(sameTeam, PieceType.CANNON));
        Intersection sameTeamDestination = new NormalIntersection(end, new Piece(sameTeam, PieceType.CHARIOT));

        // when
        CannonMoveRule cannonMoveRule = new CannonMoveRule();
        Path sameTeamPath = new Path(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                onlyObstacleIntersection,
                intersection6,
                intersection7,
                intersection8,
                sameTeamDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> cannonMoveRule.validateMoveRule(sameTeamPath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("포의 이동경로 장애물이 포이면 예외가 발생한다.")
    void shouldThrowExceptionWhenObstacleIsCannon() {
        // given
        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;

        Intersection origin = new NormalIntersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection cannonObstacle = new NormalIntersection(middlePoint7, new Piece(anotherTeam, PieceType.CANNON));
        Intersection destination = NormalIntersection.empty(end);

        // when
        CannonMoveRule cannonMoveRule = new CannonMoveRule();
        Path obstaclePath = new Path(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                intersection5,
                intersection6,
                cannonObstacle,
                intersection8,
                destination)
        );

        // then
        Assertions.assertThatThrownBy(() -> cannonMoveRule.validateMoveRule(obstaclePath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNON_CANNOT_JUMP_CANNON.getMessage());
    }

    @Test
    @DisplayName("포는 두개의 장애물을 넘어갈 때 예외가 발생한다.")
    void shouldThrowExceptionWhenCannonJumpTwoObstacle() {
        // given
        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;

        Intersection origin = new NormalIntersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection obstacle1 = new NormalIntersection(middlePoint5, new Piece(anotherTeam, PieceType.CHARIOT));
        Intersection obstacle2 = new NormalIntersection(middlePoint6, new Piece(anotherTeam, PieceType.CHARIOT));
        Intersection destination = NormalIntersection.empty(end);

        // when
        CannonMoveRule cannonMoveRule = new CannonMoveRule();
        Path twoObstaclePath = new Path(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                obstacle1,
                obstacle2,
                intersection7,
                intersection8,
                destination)
        );

        // then
        Assertions.assertThatThrownBy(() -> cannonMoveRule.validateMoveRule(twoObstaclePath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNON_MUST_JUMP_ONE_PIECE.getMessage());
    }

    @Test
    @DisplayName("포의 경로에 장애물이 없을 때 예외가 발생한다.")
    void shouldThrowExceptionWhenCannonPathDoesntObstacle() {
        // given
        Team sameTeam = Team.CHO;

        Intersection origin = new NormalIntersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection destination = NormalIntersection.empty(end);

        // when
        CannonMoveRule cannonMoveRule = new CannonMoveRule();
        Path noObstaclePath = new Path(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                intersection5,
                intersection6,
                intersection7,
                intersection8,
                destination)
        );

        // then
        Assertions.assertThatThrownBy(() -> cannonMoveRule.validateMoveRule(noObstaclePath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNON_MUST_JUMP_ONE_PIECE.getMessage());
    }

    @Test
    @DisplayName("포가 포를 공격할때 예외가 발생한다.")
    void shouldThrowExceptionWhenCannonAttackCannon() {
        // given
        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;

        Intersection origin = new NormalIntersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection obstacle = new NormalIntersection(middlePoint6, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection cannonDestination = new NormalIntersection(end, new Piece(anotherTeam, PieceType.CANNON));

        // when
        CannonMoveRule cannonMoveRule = new CannonMoveRule();
        Path cannonDesitnationPath = new Path(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                intersection4,
                intersection5,
                obstacle,
                intersection7,
                intersection8,
                cannonDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> cannonMoveRule.validateMoveRule(cannonDesitnationPath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNON_CANNOT_ATTACK_CANNON.getMessage());
    }

    @Test
    @DisplayName("포는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void cannonCanMoveWhenNoObstacleAndDestinationIsOpponent() {
        // given
        Piece obstacle = new Piece(Team.HAN, PieceType.CHARIOT);

        Intersection origin = new NormalIntersection(start, cannon);
        Intersection obstacleIntersection = new NormalIntersection(middlePoint4, obstacle);
        Intersection opponentDestination = new NormalIntersection(end, obstacle);
        Intersection expected = new NormalIntersection(end, cannon);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                obstacleIntersection,
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

    @Test
    @DisplayName("포는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void cannonCanMoveWhenNoObstacleAndDestinationIsEmpty() {
        // given
        Piece obstacle = new Piece(Team.HAN, PieceType.CHARIOT);

        Intersection origin = new NormalIntersection(start, cannon);
        Intersection obstacleIntersection = new NormalIntersection(middlePoint4, obstacle);
        Intersection opponentDestination = NormalIntersection.empty(end);
        Intersection expected = new NormalIntersection(end, cannon);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                intersection1,
                intersection2,
                intersection3,
                obstacleIntersection,
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
    @DisplayName("포의 궁성 내 대각선 이동 테스트")
    class CannonPalaceDiagonalMoveTest {

        @Test
        @DisplayName("포가 좌하(9, 3)에서 우상(7, 5)으로 이동할 수 있다.")
        void cannonCanMoveRightUpTwiceWhenCannonInLeftBottomPalace() {
            // given
            Piece obstacle = new Piece(Team.CHO, PieceType.SOLDIER);

            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, cannon);
            Intersection centerPalaceHasObstacle = new CenterPalace(centerPointCho, obstacle);
            Intersection expected = new RightTopPalace(rightTopPointCho, cannon);

            JanggiBoard janggiBoard = JanggiBoardFixture
                    .generate(leftBottomPalace, centerPalaceHasObstacle, rightTopCho);

            // when
            janggiBoard.processTurn(leftBottomPointCho, rightTopPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("포가 좌상(7, 3)에서 우하(9, 5)로 이동할 수 있다.")
        void cannonCanMoveRightDownTwiceWhenCannonInLeftTopPalace() {
            // given
            Piece obstacle = new Piece(Team.CHO, PieceType.SOLDIER);

            Intersection leftTopPalace = new LeftTopPalace(leftTopPointCho, cannon);
            Intersection centerPalaceHasObstacle = new CenterPalace(centerPointCho, obstacle);
            Intersection expected = new RightBottomPalace(rightBottomPointCho, cannon);

            JanggiBoard janggiBoard = JanggiBoardFixture
                    .generate(leftTopPalace, centerPalaceHasObstacle, rightBottomCho);

            // when
            janggiBoard.processTurn(leftTopPointCho, rightBottomPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightBottomPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("포가 우상(7, 5)에서 좌하(9, 3)로 이동할 수 있다.")
        void cannonCanMoveLeftDownTwiceWhenCannonInRightTopPalace() {
            // given
            Piece obstacle = new Piece(Team.CHO, PieceType.SOLDIER);

            Intersection rightTopPalace = new RightTopPalace(rightTopPointCho, cannon);
            Intersection centerPalaceHasObstacle = new CenterPalace(centerPointCho, obstacle);
            Intersection expected = new LeftBottomPalace(leftBottomPointCho, cannon);

            JanggiBoard janggiBoard = JanggiBoardFixture
                    .generate(rightTopPalace, centerPalaceHasObstacle, leftBottomCho);

            // when
            janggiBoard.processTurn(rightTopPointCho, leftBottomPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftBottomPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("포가 우하(9, 5)에서 좌상(7, 3)으로 이동할 수 있다.")
        void cannonCanMoveLeftUpTwiceWhenCannonInRightBottomPalace() {
            // given
            Piece obstacle = new Piece(Team.CHO, PieceType.SOLDIER);

            Intersection rightBottomPalace = new RightBottomPalace(rightBottomPointCho, cannon);
            Intersection centerPalaceHasObstacle = new CenterPalace(centerPointCho, obstacle);
            Intersection expected = new LeftTopPalace(leftTopPointCho, cannon);

            JanggiBoard janggiBoard = JanggiBoardFixture
                    .generate(rightBottomPalace, centerPalaceHasObstacle, leftTopCho);

            // when
            janggiBoard.processTurn(rightBottomPointCho, leftTopPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftTopPointCho))
                    .isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("포의 궁성 내 대각선 이동 예외 테스트")
    class CannonMoveExceptionInPalaceTest {


        @Test
        @DisplayName("좌하궁성(9, 3)에서 우상궁성(7, 5)으로 이동 시, 장애물이 없으면 예외가 발생한다.")
        void cannonCanNotMoveRightUpTwiceWhenCannonInLeftBottomPalaceButNoObstacle() {
            // given
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, cannon);
            Intersection emptyCenterPalace = CenterPalace.empty(centerPointCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, emptyCenterPalace, rightTopCho);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftBottomPointCho, rightTopPointCho))
                    .isInstanceOf(PathException.class)
                    .hasMessage(CANNON_MUST_JUMP_ONE_PIECE.getMessage());
        }

        @Test
        @DisplayName("좌하궁성(9, 3)에서 가운데(8, 4)로 이동 시, 예외가 발생한다.")
        void cannonCanNotMoveRightUpOnceWhenCannonInLeftBottomPalace() {
            // given
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, cannon);
            Intersection emptyCenterPalace = RightTopPalace.empty(centerPointCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, emptyCenterPalace);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftBottomPointCho, centerPointCho))
                    .isInstanceOf(PathException.class)
                    .hasMessage(CANNON_MUST_JUMP_ONE_PIECE.getMessage());
        }

        @Test
        @DisplayName("포가 우하(9, 5)에서 일반 격자점 우상(7, 7)으로 대각선 이동할 수 없다.")
        void cannonCanNotMoveRightUpTwiceWhenCannonInRightBottomPalace() {
            // given
            Piece obstacle = new Piece(Team.CHO, PieceType.SOLDIER);

            Point rightUpPoint = rightBottomPointCho.next(Vector.RIGHT_UP);
            Point destinationPoint = rightUpPoint.next(Vector.RIGHT_UP);

            Intersection rightBottomPalace = new RightBottomPalace(rightBottomPointCho, cannon);
            Intersection hasObstacleButNormalIntersection = new NormalIntersection(rightUpPoint, obstacle);
            Intersection normalDestination = NormalIntersection.empty(destinationPoint);
            JanggiBoard janggiBoard = JanggiBoardFixture
                    .generate(rightBottomPalace, hasObstacleButNormalIntersection, normalDestination);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(rightBottomPointCho, destinationPoint))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

    }

}
