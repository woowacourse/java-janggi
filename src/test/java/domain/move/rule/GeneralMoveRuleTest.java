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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.directions.exception.DirectionError.INVALID_DIRECTION;
import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;
import static domain.move.path.exception.PathError.GENERAL_CANNOT_GO_OUT_PALACE;

class GeneralMoveRuleTest {

    final Team teamHan = Team.HAN;
    final Team teamCho = Team.CHO;

    final Piece general = new Piece(teamCho, PieceType.GENERAL);

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
        Piece sameTeamPiece = new Piece(teamCho, PieceType.SOLDIER);

        Intersection origin = new CenterPalace(centerPointCho, general);
        Intersection sameTeamDestination = new LeftTopPalace(leftTopPointCho, sameTeamPiece);

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
        Point end = centerPointCho.next(Vector.DOWN);

        Intersection origin = new CenterPalace(centerPointCho, general);
        Intersection emptyDestination = NormalPalace.empty(end);
        Intersection expected = new NormalPalace(end, general);
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(origin, emptyDestination);

        // when
        janggiBoard.processTurn(centerPointCho, end);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("장군은 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGeneralWhenDestinationIsOpponent() {
        // given
        Point end = centerPointCho.next(Vector.DOWN);

        Piece opponent = new Piece(teamHan, PieceType.SOLDIER);

        Intersection origin = new CenterPalace(centerPointCho, general);
        Intersection opponentDestination = new NormalPalace(end, opponent);
        Intersection expected = new NormalPalace(end, general);
        JanggiBoard janggiBoard = JanggiBoardFixture.generate(origin, opponentDestination);

        // when
        janggiBoard.processTurn(centerPointCho, end);
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
            Intersection leftBottomPalace = new LeftBottomPalace(leftBottomPointCho, general);
            Intersection expected = new CenterPalace(centerPointCho, general);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftBottomPalace, centerCho);

            // when
            janggiBoard.processTurn(leftBottomPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 좌상(7, 3)에서 가운데(8, 4)으로 이동할 수 있다.")
        void generalCanMoveRightDownOnceWhenGeneralInLeftTopPalace() {
            // given
            Intersection leftTopPalace = new LeftTopPalace(leftTopPointCho, general);
            Intersection expected = new CenterPalace(centerPointCho, general);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftTopPalace, centerCho);

            // when
            janggiBoard.processTurn(leftTopPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 우하(9, 5)에서 가운데(8, 4)으로 이동할 수 있다.")
        void generalCanMoveLeftUpOnceWhenGeneralInRightBottomPalace() {
            // given
            Intersection rightBottomPalace = new RightBottomPalace(rightBottomPointCho, general);
            Intersection expected = new CenterPalace(centerPointCho, general);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(rightBottomPalace, centerCho);

            // when
            janggiBoard.processTurn(rightBottomPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 우상(7, 5)에서 가운데(8, 4)으로 이동할 수 있다.")
        void generalCanMoveLeftDownOnceWhenGeneralInRightTopPalace() {
            // given
            Intersection rightTopPalace = new RightTopPalace(rightTopPointCho, general);
            Intersection expected = new CenterPalace(centerPointCho, general);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(rightTopPalace, centerCho);

            // when
            janggiBoard.processTurn(rightTopPointCho, centerPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(centerPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 가운데(8, 4)에서 좌하(9, 3)에서 으로 이동할 수 있다.")
        void generalCanMoveLeftDownOnceWhenGeneralInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, general);
            Intersection expected = new LeftBottomPalace(leftBottomPointCho, general);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, leftBottomCho);

            // when
            janggiBoard.processTurn(centerPointCho, leftBottomPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftBottomPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 가운데(8, 4)에서 좌상(7, 3)에서 으로 이동할 수 있다.")
        void generalCanMoveLeftUpOnceWhenGeneralInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, general);
            Intersection expected = new LeftTopPalace(leftTopPointCho, general);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, leftTopCho);

            // when
            janggiBoard.processTurn(centerPointCho, leftTopPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(leftTopPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 가운데(8, 4)에서 우하(9, 5)에서 으로 이동할 수 있다.")
        void generalCanMoveRightDownOnceWhenGeneralInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, general);
            Intersection expected = new RightBottomPalace(rightBottomPointCho, general);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, rightBottomCho);

            // when
            janggiBoard.processTurn(centerPointCho, rightBottomPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightBottomPointCho))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("장군은 가운데(8, 4)에서 우상(7, 5)에서 으로 이동할 수 있다.")
        void generalCanMoveRightUpOnceWhenGeneralInCenterPalace() {
            // given
            Intersection centerPalace = new CenterPalace(centerPointCho, general);
            Intersection expected = new RightTopPalace(rightTopPointCho, general);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(centerPalace, rightTopCho);

            // when
            janggiBoard.processTurn(centerPointCho, rightTopPointCho);

            // then
            Assertions.assertThat(janggiBoard.findIntersection(rightTopPointCho))
                    .isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("장군의 궁성 내 대각선 이동 예외 테스트")
    class GeneralMoveExceptionInPalaceTest {

        @Test
        @DisplayName("장군이 왼쪽 일반 궁성(8,3)에서 위쪽 일반 궁성(7,4)으로 이동 하려 할 경우 예외가 발생한다.")
        void shouldThrowExceptionWhenGeneralMoveFromLeftNormalPalaceToTopNormalPalace() {
            // given
            Point leftPalacePoint = new Point(8, 4);
            Point topPalacePoint = leftPalacePoint.next(Vector.RIGHT_UP);

            Intersection leftPalace = new NormalPalace(leftPalacePoint, general);
            Intersection topPalace = NormalPalace.empty(topPalacePoint);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftPalace, topPalace);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftPalacePoint, topPalacePoint))
                    .isInstanceOf(DirectionException.class)
                    .hasMessage(INVALID_DIRECTION.getMessage());
        }

        @Test
        @DisplayName("장군이 궁성을 나가려고 하는 경우, 예외가 발생한다.")
        void shouldThrowExceptionWhenGeneralGoOutPalace() {
            // given

            Point leftPalacePoint = new Point(8, 4);
            Point outOfPalacePoint = leftPalacePoint.next(Vector.LEFT);

            Intersection leftPalace = new NormalPalace(leftPalacePoint, general);
            Intersection outOfPalace = NormalIntersection.empty(outOfPalacePoint);
            JanggiBoard janggiBoard = JanggiBoardFixture.generate(leftPalace, outOfPalace);

            // when & then
            Assertions.assertThatThrownBy(() -> janggiBoard.processTurn(leftPalacePoint, outOfPalacePoint))
                    .isInstanceOf(PathException.class)
                    .hasMessage(GENERAL_CANNOT_GO_OUT_PALACE.getMessage());
        }

    }

}
