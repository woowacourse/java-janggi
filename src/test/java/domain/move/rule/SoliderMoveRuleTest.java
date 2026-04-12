package domain.move.rule;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.intersection.palace.*;
import domain.move.directions.exception.DirectionException;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.move.directions.Vector;
import domain.point.Point;
import fixture.JanggiBoardFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.directions.exception.DirectionError.INVALID_DIRECTION;
import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;

class SoliderMoveRuleTest {

    final Team teamHan = Team.HAN;
    final Team teamCho = Team.CHO;

    final Piece soliderHan = new Piece(Team.HAN, PieceType.SOLDIER);
    final Piece soliderCho = new Piece(Team.CHO, PieceType.SOLDIER);

    final Point centerPointHan = new Point(1, 4);
    final Point leftTopPointHan = new Point(0, 3);
    final Point leftBottomPointHan = new Point(2, 3);
    final Point rightTopPointHan = new Point(0, 5);
    final Point rightBottomPointHan = new Point(2, 5);

    final Point centerPointCho = new Point(8, 4);
    final Point leftTopPointCho = new Point(7, 3);
    final Point leftBottomPointCho = new Point(9, 3);
    final Point rightTopPointCho = new Point(7, 5);
    final Point rightBottomPointCho = new Point(9, 5);

    final Intersection centerHan = CenterPalace.empty(centerPointHan);
    final Intersection leftTopHan = LeftTopPalace.empty(leftTopPointHan);
    final Intersection leftBottomHan = LeftBottomPalace.empty(leftBottomPointHan);
    final Intersection rightTopHan = RightTopPalace.empty(rightTopPointHan);
    final Intersection rightBottomHan = RightBottomPalace.empty(rightBottomPointHan);

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

        Piece soldier = new Piece(teamHan, PieceType.SOLDIER);
        Piece sameTeamPiece = new Piece(teamHan, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection sameTeamDestination = new NormalIntersection(end, sameTeamPiece);

        SoliderMoveRule soliderMoveRule = new SoliderMoveRule();

        // when
        Path path = new Path(List.of(
                origin,
                sameTeamDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> soliderMoveRule.validateMoveRule(path))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("한 진영인 졸의 직진 방향은 내려감이다.")
    void straightOfHanSoliderIsDown() {
        // given
        Point start = new Point(0, 0);
        Point end = start.next(Vector.DOWN);

        Piece soldier = new Piece(teamHan, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(teamCho, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection destination = new NormalIntersection(end, anotherTeamPiece);
        Intersection expected = new NormalIntersection(end, soldier);
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                teamHan,
                origin, destination
        );

        // when
        janggiBoard.processTurn(start, end);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("초 진영인 졸의 직진 방향은 올라감이다.")
    void straightOfCHOSoliderIsUp() {
        // given
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Piece soldier = new Piece(teamCho, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(teamHan, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection destination = new NormalIntersection(end, anotherTeamPiece);
        Intersection expected = new NormalIntersection(end, soldier);

        JanggiBoard janggiBoard = JanggiBoardFixture.generate(origin, destination);

        // when
        janggiBoard.processTurn(start, end);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("초 진영 졸이 내려가려고 하면 예외가 발생한다.")
    void shouldThrowExceptionWhenSoliderOfChoMoveDown() {
        // given
        Point start = new Point(1, 0);
        Point end = start.next(Vector.DOWN);
        Piece soldier = new Piece(teamCho, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection destination = NormalIntersection.empty(end);
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(origin, destination);

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(start, end))
                .isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }

    @Test
    @DisplayName("한 진영 졸이 올라가려고 하면 예외가 발생한다.")
    void shouldThrowExceptionWhenSoliderOfHanMoveUp() {
        // given
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);
        Piece soldier = new Piece(teamHan, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection destination = NormalIntersection.empty(end);
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                teamHan,
                origin, destination
        );

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(start, end))
                .isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }

    @Nested
    @DisplayName("졸병(한) 궁성 내 대각선 정상 이동 테스트")
    class SoliderOfHanMoveInPalaceTest {

        @Test
        @DisplayName("졸병(한)은 좌상(7, 3)에서 가운데(8, 4)로 이동할 수 있다.")
        void soliderOfHanCanMoveRightDownOnceWhenSoliderInLeftTopPalace() {
            // given
            Intersection leftTopPalace = new LeftTopPalace(leftTopPointCho, soliderHan);
            Intersection expected = new CenterPalace(centerPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    leftTopPalace, centerCho
            );

            // when
            janggiBoard.processTurn(leftTopPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(한)은 우상(7, 5)에서 가운데(8, 4)로 이동할 수 있다.")
        void soliderOfHanCanMoveLeftDownOnceWhenSoliderInRightTopPalace() {
            // given
            Intersection rightTopPalace = new RightTopPalace(rightTopPointCho, soliderHan);
            Intersection expected = new CenterPalace(centerPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    rightTopPalace, centerCho
            );

            // when
            janggiBoard.processTurn(rightTopPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(한)은 가운데(8, 4)에서 좌하(9, 3)로 이동할 수 있다.")
        void soliderOfHanCanMoveLeftDownOnceWhenSoliderInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, soliderHan);
            Intersection expected = new LeftBottomPalace(leftBottomPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    centerPalace, leftBottomCho
            );

            // when
            janggiBoard.processTurn(centerPointCho, leftBottomPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftBottomPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(한)은 가운데(8, 4)에서 우하(9, 5)에서 으로 이동할 수 있다.")
        void soliderOfHanCanMoveRightDownOnceWhenSoliderInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, soliderHan);
            Intersection expected = new RightBottomPalace(rightBottomPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    centerPalace, rightBottomCho
            );

            // when
            janggiBoard.processTurn(centerPointCho, rightBottomPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightBottomPointCho))
                    .isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("졸병(한) 이동 예외 테스트")
    class SoliderOfHanExceptionInPalaceTest {

        @Test
        @DisplayName("졸병(한)이 좌하(9, 3)에서 가운데(8, 4)으로 이동할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfHanMoveFromLeftBottomPalaceToCenterPalace() {
            // given
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    leftBottomPalace, centerCho
            );

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftBottomPointCho, centerPointCho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(한)이 우하(9, 5)에서 가운데(8, 4)으로 이동할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfHanMoveFromRightBottomPalaceToCenterPalace() {
            // given
            Intersection rightBottomPalace = new RightBottomPalace(rightBottomPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    rightBottomPalace, centerCho
            );

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(rightBottomPointCho, centerPointCho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(한)이 가운데(8, 4)에서 좌상(7, 3)으로 이동할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfHanMoveFromCenterPalaceToLeftTopPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    centerPalace, leftTopCho
            );

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(centerPointCho, leftTopPointCho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(한)이 가운데(8, 4)에서 우상(7, 5)으로 이동할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfHanMoveFromCenterPalaceToRightTopPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    centerPalace, rightTopCho
            );

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(centerPointCho, rightTopPointCho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(한)이 좌하(9, 3)에서 우상(7, 5)으로 이동할 경우 예외가 발생한다. (두칸 이동)")
        void shouldThrowExceptionWhenSoliderOfHanMoveFromLeftBottomPalaceToRightTop() {
            // given
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, soliderHan);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(
                    teamHan,
                    leftBottomPalace, centerCho
            );

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftBottomPointCho, rightTopPointCho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

    }

    @Nested
    @DisplayName("졸병(초) 궁성 내 대각선 정상 이동 테스트")
    class SoliderOfChoMoveInPalaceTest {

        @Test
        @DisplayName("졸병(초)은 좌하(2, 3)에서 가운데(1, 4)로 이동할 수 있다.")
        void soliderOfChoCanMoveRightUpOnceWhenSoliderInLeftTopPalace() {
            // given
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointHan, soliderCho);
            Intersection expected = new CenterPalace(centerPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, centerHan);

            // when
            janggiBoard.processTurn(leftBottomPointHan, centerPointHan);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointHan))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(초)은 우하(2, 5)에서 가운데(1, 4)로 이동할 수 있다.")
        void soliderOfChoCanMoveLeftDownOnceWhenSoliderInRightTopPalace() {
            // given
            Intersection rightBottomPalace = new RightBottomPalace(rightBottomPointHan, soliderCho);
            Intersection expected = new CenterPalace(centerPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(rightBottomPalace, centerHan);

            // when
            janggiBoard.processTurn(rightBottomPointHan, centerPointHan);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointHan))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(초)은 가운데(1, 4)에서 좌상(0, 3)로 이동할 수 있다.")
        void soliderOfChoCanMoveLeftDownOnceWhenSoliderInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointHan, soliderCho);
            Intersection expected = new LeftTopPalace(leftTopPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, leftTopHan);

            // when
            janggiBoard.processTurn(centerPointHan, leftTopPointHan);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftTopPointHan))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(초)은 가운데(1, 4)에서 우상(0, 5)에서 으로 이동할 수 있다.")
        void soliderOfChoCanMoveRightDownOnceWhenSoliderInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointHan, soliderCho);
            Intersection expected = new RightTopPalace(rightTopPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, rightTopHan);

            // when
            janggiBoard.processTurn(centerPointHan, rightTopPointHan);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPointHan))
                    .isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("졸병(초) 궁성 내 대각선 이동 예외 테스트")
    class SoliderOfChoExceptionInPalaceTest {

        @Test
        @DisplayName("졸병(초)은 좌상(0, 3)에서 가운데(1, 4)로 이동하면 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfChoMoveFromLeftTopToCenter() {
            // given
            Intersection leftTopPalace = new LeftTopPalace(leftTopPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftTopPalace, centerHan);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftTopPointHan, centerPointHan))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(초)은 우상(0, 5)에서 가운데(1, 4)로 이동하면 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfChoMoveFromRightTopToCenter() {
            // given
            Intersection rightTopPalace = new RightTopPalace(rightTopPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(rightTopPalace, centerHan);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(rightTopPointHan, centerPointHan))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(초)은 가운데(1, 4)에서 좌하(0, 3)로 이동하면 예외가 발생한다.")
        void soliderOfChoCanMoveLeftDownOnceWhenSoliderInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, leftBottomHan);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(centerPointHan, leftBottomPointHan))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(초)은 가운데(1, 4)에서 우하(2, 5)로 이동하면 예외가 발생한다.")
        void soliderOfChoCanMoveRightDownOnceWhenSoliderInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, rightBottomHan);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(centerPointHan, rightBottomPointHan))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(초)은 우상(0, 5)에서 좌하(2, 3)로 이동하면 예외가 발생한다. (대각선 두칸 이동)")
        void shouldThrowExceptionWhenSoliderOfChoMoveFromRightTopToLeftBottom() {
            // given
            Intersection rightTopPalace = new RightTopPalace(rightTopPointHan, soliderCho);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(rightTopPalace, centerHan);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(rightTopPointHan, leftBottomPointHan))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

    }

}
