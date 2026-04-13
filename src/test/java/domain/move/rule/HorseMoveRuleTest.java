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
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;
import static domain.move.path.exception.PathError.CANNOT_MOVE_PATH_HAS_OBSTACLE;

class HorseMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        // given
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Team sameTeam = Team.CHO;
        Piece horse = new Piece(sameTeam, PieceType.HORSE);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.HORSE);

        Intersection origin = new NormalIntersection(start, horse);
        Intersection intersection = NormalIntersection.empty(middlePoint);
        Intersection sameTeamDestination = new NormalIntersection(end, sameTeamPiece);

        // when
        HorseMoveRule horseRule = new HorseMoveRule();
        Path sameTeamPath = new Path(List.of(
                origin,
                intersection,
                sameTeamDestination)
        );

        // then
        Assertions.assertThatThrownBy(() -> horseRule.validateMoveRule(sameTeamPath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("마의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void shouldThrowExceptionWhenPathHasObstacle() {
        // given
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Team sameTeam = Team.CHO;
        Piece horse = new Piece(sameTeam, PieceType.HORSE);
        Piece obstacle = new Piece(sameTeam, PieceType.HORSE);

        Intersection origin = new NormalIntersection(start, horse);
        Intersection obstacleIntersection = new NormalIntersection(middlePoint, obstacle);
        Intersection destination = NormalIntersection.empty(end);

        // when
        HorseMoveRule horseRule = new HorseMoveRule();
        Path obstaclePath = new Path(List.of(
                origin,
                obstacleIntersection,
                destination)
        );

        // then
        Assertions.assertThatThrownBy(() -> horseRule.validateMoveRule(obstaclePath))
                .isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_PATH_HAS_OBSTACLE.getMessage());
    }

    @Test
    @DisplayName("마는 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void horseCanMove_WhenNoObstacle_AndDestinationIsEmpty() {
        // given
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Team team = Team.CHO;
        Piece horse = new Piece(team, PieceType.HORSE);

        Intersection origin = new NormalIntersection(start, horse);
        Intersection intersection = NormalIntersection.empty(middlePoint);
        Intersection emptyDestination = NormalIntersection.empty(end);
        Intersection expected = new NormalIntersection(end, horse);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                intersection,
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
    @DisplayName("마는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void horseCanMoveWhenNoObstacleAndDestinationIsOpponent() {
        // given
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Team team = Team.CHO;
        Team opponentTeam = Team.HAN;
        Piece horse = new Piece(team, PieceType.HORSE);
        Piece opponent = new Piece(opponentTeam, PieceType.HORSE);

        Intersection origin = new NormalIntersection(start, horse);
        Intersection intersection = NormalIntersection.empty(middlePoint);
        Intersection opponentDestination = new NormalIntersection(end, opponent);
        Intersection expected = new NormalIntersection(end, horse);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                origin,
                intersection,
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
