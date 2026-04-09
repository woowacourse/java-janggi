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

class GuardMoveRuleTest {

    final Team teamHan = Team.HAN;
    final Team teamCho = Team.CHO;

    final Piece guard = new Piece(teamCho, PieceType.GUARD);

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
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.CHO;
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, guard);
        Intersection sameTeamDestination = new NormalIntersection(end, sameTeamPiece);

        // when
        GuardMoveRule guardMoveRule = new GuardMoveRule();
        Path sameTeamPath = new Path(List.of(
                origin,
                sameTeamDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> guardMoveRule.validateMoveRule(sameTeamPath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("사는 도착지에 상대팀이 없으면 이동할 수 있다.")
    void canMoveGuardWhenDestinationIsEmpty() {
        // given
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.CHO;

        Intersection origin = new LeftTopPalace(start, guard);
        Intersection emptyDestination = NormalPalace.empty(end);
        Intersection expected = new NormalPalace(end, guard);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                emptyDestination
        )));

        // when
        janggiBoard.processTurn(start, end);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("사는 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGuardWhenDestinationIsOpponent() {
        // given
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;

        Piece opponent = new Piece(anotherTeam, PieceType.GUARD);

        Intersection origin = new LeftTopPalace(start, guard);
        Intersection opponentDestination = new NormalPalace(end, opponent);
        Intersection expected = new NormalPalace(end, guard);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                opponentDestination
        )));

        // when
        janggiBoard.processTurn(start, end);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Nested
    @DisplayName("사의 궁성 내 대각선 이동 테스트")
    class GuardPalaceDiagonalMoveTest {

        @Test
        @DisplayName("사는 좌하(9, 3)에서 가운데(8, 4)으로 이동할 수 있다.")
        void guardCanMoveFromLeftBottomPalaceToCenterPalace() {
            // given
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, guard);
            Intersection expected = new CenterPalace(centerPointCho, guard);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, centerCho);

            // when
            janggiBoard.processTurn(leftBottomPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("사는 좌상(7, 3)에서 가운데(8, 4)으로 이동할 수 있다.")
        void guardCanMoveFromLeftUpPalaceToCenterPalace() {
            // given
            Intersection leftBottomPalace = new LeftTopPalace(leftTopPointCho, guard);
            Intersection expected = new CenterPalace(centerPointCho, guard);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, centerCho);

            // when
            janggiBoard.processTurn(leftTopPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("사는 우하(9, 5)에서 가운데(8, 4)으로 이동할 수 있다.")
        void guardCanMoveFromRightBottomPalaceToCenterPalace() {
            // given
            Intersection rightBottomPalace = new RightBottomPalace(rightBottomPointCho, guard);
            Intersection expected = new CenterPalace(centerPointCho, guard);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(rightBottomPalace, centerCho);

            // when
            janggiBoard.processTurn(rightBottomPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("사는 우상(7, 5)에서 가운데(8, 4)으로 이동할 수 있다.")
        void guardCanMoveFromRightTopPalaceToCenterPalace() {
            // given
            Intersection rightTopPalace = new RightTopPalace(rightTopPointCho, guard);
            Intersection expected = new CenterPalace(centerPointCho, guard);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(rightTopPalace, centerCho);

            // when
            janggiBoard.processTurn(rightTopPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("사는 가운데(8, 4)에서 좌하(9, 3)에서 으로 이동할 수 있다.")
        void guardCanMoveFromCenterPalaceToLeftBottomPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, guard);
            Intersection expected = new LeftBottomPalace(leftBottomPointCho, guard);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, leftBottomCho);

            // when
            janggiBoard.processTurn(centerPointCho, leftBottomPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftBottomPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("사는 가운데(8, 4)에서 좌상(7, 3)에서 으로 이동할 수 있다.")
        void guardCanMoveFromCenterPalaceToLeftTopPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, guard);
            Intersection expected = new LeftTopPalace(leftTopPointCho, guard);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, leftTopCho);

            // when
            janggiBoard.processTurn(centerPointCho, leftTopPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftTopPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("사는 가운데(8, 4)에서 우하(9, 5)에서 으로 이동할 수 있다.")
        void guardCanMoveFromCenterPalaceToRightBottomPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, guard);
            Intersection expected = new RightBottomPalace(rightBottomPointCho, guard);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, rightBottomCho);

            // when
            janggiBoard.processTurn(centerPointCho, rightBottomPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightBottomPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("사는 가운데(8, 4)에서 우상(7, 5)에서 으로 이동할 수 있다.")
        void guardCanMoveFromCenterPalaceToRightTopPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, guard);
            Intersection expected = new RightTopPalace(rightTopPointCho, guard);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, rightTopCho);

            // when
            janggiBoard.processTurn(centerPointCho, rightTopPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPointCho))
                    .isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("사의 궁성 내 대각선 이동 예외 테스트")
    class GuardExceptionInPalaceTest {

        @Test
        @DisplayName("사가 왼쪽 일반 궁성(8,3)에서 위쪽 일반 궁성(7,4)으로 이동 하려 할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenGuardMoveFromLeftNormalPalaceToTopNormalPalace() {
            // given
            Point leftPalacePoint = new Point(8, 3);
            Point topPalacePoint = leftPalacePoint.next(Vector.RIGHT_UP);

            Intersection leftPalace = new NormalPalace(leftPalacePoint, guard);
            Intersection topPalace = NormalPalace.empty(topPalacePoint);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftPalace, topPalace);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftPalacePoint, topPalacePoint))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("사가 궁성을 나가려고 하는 경우, 예외가 발생한다.")
        void shouldThrowExceptionWhenGuardGoOutPalace() {
            // given
            Point leftPalacePoint = new Point(8, 3);
            Point outOfPalacePoint = leftPalacePoint.next(Vector.LEFT);

            Intersection leftPalace = new NormalPalace(leftPalacePoint, guard);
            Intersection outOfPalace = NormalIntersection.empty(outOfPalacePoint);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftPalace, outOfPalace);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftPalacePoint, outOfPalacePoint))
                    .isInstanceOf(PathException.class)
                    .hasMessage(GUARD_CANNOT_GO_OUT_PALACE.getMessage());
        }

    }

}
