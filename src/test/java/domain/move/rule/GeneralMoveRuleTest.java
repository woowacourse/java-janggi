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
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.directions.exception.DirectionError.INVALID_DIRECTION;
import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;
import static domain.move.path.exception.PathError.GENERAL_CANNOT_GO_OUT_PALACE;

class GeneralMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        // given
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, general);
        Intersection sameTeamDestination = new Intersection(end, sameTeamPiece);

        // when
        GeneralMoveRule generalMoveRule = new GeneralMoveRule();
        Path sameTeamPath = new Path(List.of(
                origin,
                sameTeamDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> generalMoveRule.validateMoveRule(sameTeamPath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("장군은 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGeneralWhenDestinationIsEmpty() {
        // given
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);

        Intersection origin = new Intersection(start, general);
        Intersection emptyDestination = Intersection.empty(end);
        Intersection expected = new Intersection(end, general);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                emptyDestination
        )));

        // when
        janggiBoard.tryToMove(start, end, sameTeam);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("장군은 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGeneralWhenDestinationIsOpponent() {
        // given
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Piece general = new Piece(sameTeam, PieceType.GENERAL);
        Piece opponent = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, general);
        Intersection opponentDestination = new Intersection(end, opponent);
        Intersection expected = new Intersection(end, general);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                opponentDestination
        )));

        // when
        janggiBoard.tryToMove(start, end, sameTeam);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Nested
    @DisplayName("장군의 궁성 내 대각선 이동 테스트")
    class GeneralPalaceDiagonalMoveTest {

        @Test
        @DisplayName("장군은 좌하(9, 3)에서 가운데(8, 4)으로 이동할 수 있다.")
        void generalCanMoveRightUpOnceWhenGeneralInLeftBottomPalace() {
            // given
            Piece general = new Piece(Team.CHO, PieceType.GENERAL);

            Point leftBottomPoint = new Point(9, 3);
            Point centerPoint = leftBottomPoint.next(Vector.RIGHT_UP);

            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPoint, general);
            Intersection centerPalace = CenterPalace.empty(centerPoint);
            Intersection expected = new CenterPalace(centerPoint, general);

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
        @DisplayName("장군은 좌상(7, 3)에서 가운데(8, 4)으로 이동할 수 있다.")
        void generalCanMoveRightDownOnceWhenGeneralInLeftTopPalace() {
            // given
            Piece general = new Piece(Team.CHO, PieceType.GENERAL);

            Point leftTopPoint = new Point(7, 3);
            Point centerPoint = leftTopPoint.next(Vector.RIGHT_DOWN);

            Intersection leftBottomPalace = new LeftTopPalace(leftTopPoint, general);
            Intersection centerPalace = CenterPalace.empty(centerPoint);
            Intersection expected = new CenterPalace(centerPoint, general);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    leftBottomPalace,
                    centerPalace
            )));

            // when
            janggiBoard.tryToMove(leftTopPoint, centerPoint, Team.CHO);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 우하(9, 5)에서 가운데(8, 4)으로 이동할 수 있다.")
        void generalCanMoveLeftUpOnceWhenGeneralInRightBottomPalace() {
            // given
            Piece general = new Piece(Team.CHO, PieceType.GENERAL);

            Point rightBottomPoint = new Point(7, 3);
            Point centerPoint = rightBottomPoint.next(Vector.LEFT_UP);

            Intersection leftBottomPalace = new RightBottomPalace(rightBottomPoint, general);
            Intersection centerPalace = CenterPalace.empty(centerPoint);
            Intersection expected = new CenterPalace(centerPoint, general);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    leftBottomPalace,
                    centerPalace
            )));

            // when
            janggiBoard.tryToMove(rightBottomPoint, centerPoint, Team.CHO);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 우상(7, 5)에서 가운데(8, 4)으로 이동할 수 있다.")
        void generalCanMoveLeftDownOnceWhenGeneralInRightTopPalace() {
            // given
            Piece general = new Piece(Team.CHO, PieceType.GENERAL);

            Point rightTopPoint = new Point(7, 5);
            Point centerPoint = rightTopPoint.next(Vector.LEFT_DOWN);

            Intersection leftBottomPalace = new RightTopPalace(rightTopPoint, general);
            Intersection centerPalace = CenterPalace.empty(centerPoint);
            Intersection expected = new CenterPalace(centerPoint, general);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    leftBottomPalace,
                    centerPalace
            )));

            // when
            janggiBoard.tryToMove(rightTopPoint, centerPoint, Team.CHO);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 가운데(8, 4)에서 좌하(9, 3)에서 으로 이동할 수 있다.")
        void generalCanMoveLeftDownOnceWhenGeneralInCenterPalace() {
            // given
            Piece general = new Piece(Team.CHO, PieceType.GENERAL);

            Point centerPoint = new Point(8, 4);
            Point rightTopPoint = centerPoint.next(Vector.LEFT_DOWN);

            Intersection centerPalace = new CenterPalace(centerPoint, general);
            Intersection leftBottomPalace = LeftBottomPalace.empty(rightTopPoint);
            Intersection expected = new LeftBottomPalace(rightTopPoint, general);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    leftBottomPalace
            )));

            // when
            janggiBoard.tryToMove(centerPoint, rightTopPoint, Team.CHO);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 가운데(8, 4)에서 좌상(7, 3)에서 으로 이동할 수 있다.")
        void generalCanMoveLeftUpOnceWhenGeneralInCenterPalace() {
            // given
            Piece general = new Piece(Team.CHO, PieceType.GENERAL);

            Point centerPoint = new Point(8, 4);
            Point rightTopPoint = centerPoint.next(Vector.LEFT_UP);

            Intersection centerPalace = new CenterPalace(centerPoint, general);
            Intersection leftTopPalace = LeftTopPalace.empty(rightTopPoint);
            Intersection expected = new LeftTopPalace(rightTopPoint, general);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    leftTopPalace
            )));

            // when
            janggiBoard.tryToMove(centerPoint, rightTopPoint, Team.CHO);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 가운데(8, 4)에서 우하(9, 5)에서 으로 이동할 수 있다.")
        void generalCanMoveRightDownOnceWhenGeneralInCenterPalace() {
            // given
            Piece general = new Piece(Team.CHO, PieceType.GENERAL);

            Point centerPoint = new Point(8, 4);
            Point rightDownPoint = centerPoint.next(Vector.RIGHT_DOWN);


            Intersection centerPalace = new CenterPalace(centerPoint, general);
            Intersection rightBottomPalace = RightBottomPalace.empty(rightDownPoint);
            Intersection expected = new RightBottomPalace(rightDownPoint, general);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    rightBottomPalace
            )));

            // when
            janggiBoard.tryToMove(centerPoint, rightDownPoint, Team.CHO);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightDownPoint))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 가운데(8, 4)에서 우상(7, 5)에서 으로 이동할 수 있다.")
        void generalCanMoveRightUpOnceWhenGeneralInCenterPalace() {
            // given
            Piece general = new Piece(Team.CHO, PieceType.GENERAL);

            Point centerPoint = new Point(8, 4);
            Point rightDownPoint = centerPoint.next(Vector.RIGHT_UP);


            Intersection centerPalace = new CenterPalace(centerPoint, general);
            Intersection rightTopPalace = RightTopPalace.empty(rightDownPoint);
            Intersection expected = new RightTopPalace(rightDownPoint, general);

            JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                    centerPalace,
                    rightTopPalace
            )));

            // when
            janggiBoard.tryToMove(centerPoint, rightDownPoint, Team.CHO);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightDownPoint))
                    .isEqualTo(expected);
        }

    }

    @Test
    @DisplayName("장군이 왼쪽 일반 궁성(8,3)에서 위쪽 일반 궁성(7,4)으로 이동 하려 할 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenGeneralMoveFromLeftNormalPalaceToTopNormalPalace() {
        // given
        Piece general = new Piece(Team.CHO, PieceType.GENERAL);

        Point leftPalacePoint = new Point(8, 4);
        Point topPalacePoint = leftPalacePoint.next(Vector.RIGHT_UP);


        Intersection leftPalace = new NormalPalace(leftPalacePoint, general);
        Intersection topPalace = NormalPalace.empty(topPalacePoint);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                leftPalace,
                topPalace
        )));

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(leftPalacePoint, topPalacePoint, Team.CHO))
                .isInstanceOf(DirectionException.class)
                .hasMessage(INVALID_DIRECTION.getMessage());
    }

    @Test
    @DisplayName("장군이 궁성을 나가려고 하는 경우, 예외가 발생한다.")
    void shouldThrowExceptionWhenGeneralGoOutPalace() {
        // given
        Piece general = new Piece(Team.CHO, PieceType.GENERAL);

        Point leftPalacePoint = new Point(8, 4);
        Point outOfPalacePoint = leftPalacePoint.next(Vector.LEFT);


        Intersection leftPalace = new NormalPalace(leftPalacePoint, general);
        Intersection outOfPalace = NormalIntersection.empty(outOfPalacePoint);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                leftPalace,
                outOfPalace
        )));

        // when & then
        Assertions.assertThatThrownBy(() -> janggiBoard.tryToMove(leftPalacePoint, outOfPalacePoint, Team.CHO))
                .isInstanceOf(PathException.class)
                .hasMessage(GENERAL_CANNOT_GO_OUT_PALACE.getMessage());
    }


}
