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
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.directions.exception.DirectionError.INVALID_DIRECTION;
import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;

class SoliderMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, soldier);
        Intersection sameTeamDestination = new Intersection(end, sameTeamPiece);

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

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, soldier);
        Intersection destination = new Intersection(end, anotherTeamPiece);
        Intersection expected = new Intersection(end, soldier);
        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));

        // when
        janggiBoard.tryToMove(start, end, Team.HAN);
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

        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, soldier);
        Intersection destination = new Intersection(end, anotherTeamPiece);
        Intersection expected = new Intersection(end, soldier);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));

        // when
        janggiBoard.tryToMove(start, end, Team.CHO);
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

        Team sameTeam = Team.CHO;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection destination = NormalIntersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                destination))
        );

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(start, end, sameTeam))
                .isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }

    @Test
    @DisplayName("한 진영 졸이 올라가려고 하면 예외가 발생한다.")
    void shouldThrowExceptionWhenSoliderOfHanMoveUp() {
        // given
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Team sameTeam = Team.HAN;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new NormalIntersection(start, soldier);
        Intersection destination = NormalIntersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                destination))
        );

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(start, end, sameTeam))
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
            Team han = Team.HAN;
            Piece solider = new Piece(han, PieceType.SOLDIER);

            Point leftTopPoint = new Point(7, 3);
            Point centerPoint = leftTopPoint.next(Vector.RIGHT_DOWN);

            Intersection leftBottomPalace = new LeftTopPalace(leftTopPoint, solider);
            Intersection centerPalace = CenterPalace.empty(centerPoint);
            Intersection expected = new CenterPalace(centerPoint, solider);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    leftBottomPalace,
                    centerPalace
            )));

            // when
            janggiBoard.tryToMove(leftTopPoint, centerPoint, han);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(한)은 우상(7, 5)에서 가운데(8, 4)로 이동할 수 있다.")
        void soliderOfHanCanMoveLeftDownOnceWhenSoliderInRightTopPalace() {
            // given
            Team han = Team.HAN;
            Piece solider = new Piece(han, PieceType.SOLDIER);

            Point rightTopPoint = new Point(7, 5);
            Point centerPoint = rightTopPoint.next(Vector.LEFT_DOWN);

            Intersection leftBottomPalace = new RightTopPalace(rightTopPoint, solider);
            Intersection centerPalace = CenterPalace.empty(centerPoint);
            Intersection expected = new CenterPalace(centerPoint, solider);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    leftBottomPalace,
                    centerPalace
            )));

            // when
            janggiBoard.tryToMove(rightTopPoint, centerPoint, han);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(한)은 가운데(8, 4)에서 좌하(9, 3)로 이동할 수 있다.")
        void soliderOfHanCanMoveLeftDownOnceWhenSoliderInCenterPalace() {
            // given
            Team han = Team.HAN;
            Piece solider = new Piece(han, PieceType.SOLDIER);

            Point centerPoint = new Point(8, 4);
            Point leftBottomPoint = centerPoint.next(Vector.LEFT_DOWN);

            Intersection centerPalace = new CenterPalace(centerPoint, solider);
            Intersection leftBottomPalace = LeftBottomPalace.empty(leftBottomPoint);
            Intersection expected = new LeftBottomPalace(leftBottomPoint, solider);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    leftBottomPalace
            )));

            // when
            janggiBoard.tryToMove(centerPoint, leftBottomPoint, han);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftBottomPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(한)은 가운데(8, 4)에서 우하(9, 5)에서 으로 이동할 수 있다.")
        void soliderOfHanCanMoveRightDownOnceWhenSoliderInCenterPalace() {
            // given
            Team han = Team.HAN;
            Piece solider = new Piece(han, PieceType.SOLDIER);

            Point centerPoint = new Point(8, 4);
            Point rightBottomPoint = centerPoint.next(Vector.RIGHT_DOWN);

            Intersection centerPalace = new CenterPalace(centerPoint, solider);
            Intersection leftBottomPalace = RightBottomPalace.empty(rightBottomPoint);
            Intersection expected = new RightBottomPalace(rightBottomPoint, solider);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    leftBottomPalace
            )));

            // when
            janggiBoard.tryToMove(centerPoint, rightBottomPoint, han);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightBottomPoint))
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
            Team han = Team.HAN;
            Piece solider = new Piece(han, PieceType.SOLDIER);

            Point leftBottomPoint = new Point(9, 3);
            Point centerPoint = leftBottomPoint.next(Vector.RIGHT_UP);

            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPoint, solider);
            Intersection centerPalace = CenterPalace.empty(centerPoint);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    leftBottomPalace,
                    centerPalace
            )));

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(leftBottomPoint, centerPoint, han))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(한)이 우하(9, 5)에서 가운데(8, 4)으로 이동할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfHanMoveFromRightBottomPalaceToCenterPalace() {
            // given
            Team han = Team.HAN;
            Piece solider = new Piece(han, PieceType.SOLDIER);

            Point rightBottomPoint = new Point(9, 5);
            Point centerPoint = rightBottomPoint.next(Vector.LEFT_UP);

            Intersection rightBottomPalace = new RightBottomPalace(rightBottomPoint, solider);
            Intersection centerPalace = CenterPalace.empty(centerPoint);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    rightBottomPalace,
                    centerPalace
            )));

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(rightBottomPoint, centerPoint, han))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(한)이 가운데(8, 4)에서 좌상(7, 3)으로 이동할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfHanMoveFromCenterPalaceToLeftTopPalace() {
            // given
            Team han = Team.HAN;
            Piece solider = new Piece(han, PieceType.SOLDIER);

            Point centerPoint = new Point(8, 4);
            Point leftTopPoint = centerPoint.next(Vector.LEFT_UP);

            Intersection centerPalace = new CenterPalace(centerPoint, solider);
            Intersection leftTopPalace = LeftTopPalace.empty(leftTopPoint);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    leftTopPalace
            )));

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(centerPoint, leftTopPoint, han))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(한)이 가운데(8, 4)에서 우상(7, 5)으로 이동할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfHanMoveFromCenterPalaceToRightTopPalace() {
            // given
            Team han = Team.HAN;
            Piece solider = new Piece(han, PieceType.SOLDIER);

            Point centerPoint = new Point(8, 4);
            Point rightUpPoint = centerPoint.next(Vector.RIGHT_UP);

            Intersection centerPalace = new CenterPalace(centerPoint, solider);
            Intersection rightTopPalace = RightTopPalace.empty(rightUpPoint);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    rightTopPalace
            )));

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(centerPoint, rightUpPoint, han))
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
            Team cho = Team.CHO;
            Piece solider = new Piece(cho, PieceType.SOLDIER);

            Point leftBottomPoint = new Point(2, 3);
            Point centerPoint = leftBottomPoint.next(Vector.RIGHT_UP);

            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPoint, solider);
            Intersection centerPalace = CenterPalace.empty(centerPoint);
            Intersection expected = new CenterPalace(centerPoint, solider);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    leftBottomPalace,
                    centerPalace
            )));

            // when
            janggiBoard.tryToMove(leftBottomPoint, centerPoint, cho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(초)은 우하(2, 5)에서 가운데(1, 4)로 이동할 수 있다.")
        void soliderOfChoCanMoveLeftDownOnceWhenSoliderInRightTopPalace() {
            // given
            Team cho = Team.CHO;
            Piece solider = new Piece(cho, PieceType.SOLDIER);

            Point rightBottomPoint = new Point(2, 5);
            Point centerPoint = rightBottomPoint.next(Vector.LEFT_UP);

            Intersection rightBottomPalace = new RightBottomPalace(rightBottomPoint, solider);
            Intersection centerPalace = CenterPalace.empty(centerPoint);
            Intersection expected = new CenterPalace(centerPoint, solider);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    rightBottomPalace,
                    centerPalace
            )));

            // when
            janggiBoard.tryToMove(rightBottomPoint, centerPoint, cho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(초)은 가운데(1, 4)에서 좌상(0, 3)로 이동할 수 있다.")
        void soliderOfChoCanMoveLeftDownOnceWhenSoliderInCenterPalace() {
            // given
            Team cho = Team.CHO;
            Piece solider = new Piece(cho, PieceType.SOLDIER);

            Point centerPoint = new Point(1, 4);
            Point leftTopPoint = centerPoint.next(Vector.LEFT_UP);

            Intersection centerPalace = new CenterPalace(centerPoint, solider);
            Intersection leftTopPalace = LeftTopPalace.empty(leftTopPoint);
            Intersection expected = new LeftTopPalace(leftTopPoint, solider);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    leftTopPalace
            )));

            // when
            janggiBoard.tryToMove(centerPoint, leftTopPoint, cho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftTopPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("졸병(초)은 가운데(1, 4)에서 우상(0, 5)에서 으로 이동할 수 있다.")
        void soliderOfChoCanMoveRightDownOnceWhenSoliderInCenterPalace() {
            // given
            Team cho = Team.CHO;
            Piece solider = new Piece(cho, PieceType.SOLDIER);

            Point centerPoint = new Point(1, 4);
            Point rightTopPoint = centerPoint.next(Vector.RIGHT_UP);

            Intersection centerPalace = new CenterPalace(centerPoint, solider);
            Intersection rightTopPalace = RightTopPalace.empty(rightTopPoint);
            Intersection expected = new RightTopPalace(rightTopPoint, solider);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    rightTopPalace
            )));

            // when
            janggiBoard.tryToMove(centerPoint, rightTopPoint, cho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPoint))
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
            Team cho = Team.CHO;
            Piece solider = new Piece(cho, PieceType.SOLDIER);

            Point leftTopPoint = new Point(0, 3);
            Point centerPoint = leftTopPoint.next(Vector.RIGHT_DOWN);

            Intersection leftTopPalace = new LeftTopPalace(leftTopPoint, solider);
            Intersection centerPalace = CenterPalace.empty(centerPoint);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    leftTopPalace,
                    centerPalace
            )));

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(leftTopPoint, centerPoint, cho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(초)은 우상(0, 5)에서 가운데(1, 4)로 이동하면 예외가 발생한다.")
        void shouldThrowExceptionWhenSoliderOfChoMoveFromRightTopToCenter() {
            // given
            Team cho = Team.CHO;
            Piece solider = new Piece(cho, PieceType.SOLDIER);

            Point rightTopPoint = new Point(0, 5);
            Point centerPoint = rightTopPoint.next(Vector.LEFT_DOWN);

            Intersection rightTopPalace = new RightTopPalace(rightTopPoint, solider);
            Intersection centerPalace = CenterPalace.empty(centerPoint);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    rightTopPalace,
                    centerPalace
            )));

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(rightTopPoint, centerPoint, cho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(초)은 가운데(1, 4)에서 좌하(0, 3)로 이동하면 예외가 발생한다.")
        void soliderOfChoCanMoveLeftDownOnceWhenSoliderInCenterPalace() {
            // given
            Team cho = Team.CHO;
            Piece solider = new Piece(cho, PieceType.SOLDIER);

            Point centerPoint = new Point(1, 4);
            Point leftBottomPoint = centerPoint.next(Vector.LEFT_DOWN);

            Intersection centerPalace = new CenterPalace(centerPoint, solider);
            Intersection leftBottomPalace = LeftBottomPalace.empty(leftBottomPoint);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    leftBottomPalace
            )));

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(centerPoint, leftBottomPoint, cho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("졸병(초)은 가운데(1, 4)에서 우하(2, 5)로 이동하면 예외가 발생한다.")
        void soliderOfChoCanMoveRightDownOnceWhenSoliderInCenterPalace() {
            // given
            Team cho = Team.CHO;
            Piece solider = new Piece(cho, PieceType.SOLDIER);

            Point centerPoint = new Point(1, 4);
            Point rightBottomPoint = centerPoint.next(Vector.RIGHT_DOWN);

            Intersection centerPalace = new CenterPalace(centerPoint, solider);
            Intersection rightBottomPalace = RightBottomPalace.empty(rightBottomPoint);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    rightBottomPalace
            )));

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(centerPoint, rightBottomPoint, cho))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

    }

}
