package domain.move.rule;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
import domain.intersection.palace.NormalIntersection;
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

class ElephantMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        // given
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Team sameTeam = Team.CHO;
        Piece elephant = new Piece(sameTeam, PieceType.ELEPHANT);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.ELEPHANT);

        Intersection origin = new NormalIntersection(start, elephant);
        Intersection middleIntersection1 = NormalIntersection.empty(middlePoint1);
        Intersection middleIntersection2 = NormalIntersection.empty(middlePoint2);
        Intersection sameTeamDestination = new NormalIntersection(end, sameTeamPiece);

        // when
        ElephantMoveRule elephantRule = new ElephantMoveRule();
        Path sameTeamPath = new Path(List.of(
                origin,
                middleIntersection1,
                middleIntersection2,
                sameTeamDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> elephantRule.validateMoveRule(sameTeamPath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("상의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void shouldThrowExceptionWhenPathHasObstacle() {
        // given
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Team sameTeam = Team.CHO;
        Piece elephant = new Piece(sameTeam, PieceType.ELEPHANT);
        Piece obstacle = new Piece(sameTeam, PieceType.ELEPHANT);

        Intersection origin = new NormalIntersection(start, elephant);
        Intersection obstacleIntersection = new NormalIntersection(middlePoint1, obstacle);
        Intersection intersection = NormalIntersection.empty(middlePoint2);
        Intersection destination = NormalIntersection.empty(end);

        // when
        ElephantMoveRule elephantMoveRule = new ElephantMoveRule();
        Path obstaclePath = new Path(List.of(
                origin,
                obstacleIntersection,
                intersection,
                destination));

        // then
        Assertions.assertThatThrownBy(() -> elephantMoveRule.validateMoveRule(obstaclePath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_PATH_HAS_OBSTACLE.getMessage());
    }

    @Test
    @DisplayName("상은 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void horseCanMoveWhenNoObstacleAndDestinationIsEmpty() {
        //given
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Piece elephant = new Piece(Team.CHO, PieceType.ELEPHANT);

        Intersection origin = new NormalIntersection(start, elephant);
        Intersection intersection1 = NormalIntersection.empty(middlePoint1);
        Intersection intersection2 = NormalIntersection.empty(middlePoint2);
        Intersection emptyDestination = NormalIntersection.empty(end);
        Intersection expected = new NormalIntersection(end, elephant);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                intersection1,
                intersection2,
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
    @DisplayName("상은 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void elephantCanMoveWhenNoObstacleAndDestinationIsOpponent() {
        // given
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Team team = Team.CHO;
        Team opponentTeam = Team.HAN;
        Piece elephant = new Piece(team, PieceType.ELEPHANT);
        Piece opponent = new Piece(opponentTeam, PieceType.ELEPHANT);

        Intersection origin = new NormalIntersection(start, elephant);
        Intersection intersection1 = NormalIntersection.empty(middlePoint1);
        Intersection intersection2 = NormalIntersection.empty(middlePoint2);
        Intersection opponentDestination = new NormalIntersection(end, opponent);
        Intersection expected = new NormalIntersection(end, elephant);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                intersection1,
                intersection2,
                opponentDestination
        )));

        // when
        janggiBoard.processTurn(start, end);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
