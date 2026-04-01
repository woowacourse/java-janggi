package domain.move.rule;

import domain.board.JanggiBoard;
import domain.intersection.Intersection;
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

import static domain.move.path.exception.PathError.*;

class CannonMoveRuleTest {

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

        Intersection origin = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection onlyObstacleIntersection = new Intersection(middlePoint5, new Piece(sameTeam, PieceType.CANNON));
        Intersection sameTeamDestination = new Intersection(end, new Piece(sameTeam, PieceType.CHARIOT));

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

        Intersection origin = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection cannonObstacle = new Intersection(middlePoint7, new Piece(anotherTeam, PieceType.CANNON));
        Intersection destination = Intersection.empty(end);

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

        Intersection origin = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection obstacle1 = new Intersection(middlePoint5, new Piece(anotherTeam, PieceType.CHARIOT));
        Intersection obstacle2 = new Intersection(middlePoint6, new Piece(anotherTeam, PieceType.CHARIOT));
        Intersection destination = Intersection.empty(end);

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

        Intersection origin = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection destination = Intersection.empty(end);

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

        Intersection origin = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection obstacle = new Intersection(middlePoint6, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection cannonDestination = new Intersection(end, new Piece(anotherTeam, PieceType.CANNON));

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
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);
        Piece opponentChariot = new Piece(Team.HAN, PieceType.CHARIOT);

        Intersection origin = new Intersection(start, cannon);
        Intersection obstacleIntersection = new Intersection(middlePoint4, opponentChariot);
        Intersection opponentDestination = new Intersection(end, opponentChariot);
        Intersection expected = new Intersection(end, cannon);

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
        janggiBoard.tryToMove(start, end, Team.CHO);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("포는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void cannonCanMoveWhenNoObstacleAndDestinationIsEmpty() {
        // given
        Piece cannon = new Piece(Team.CHO, PieceType.CANNON);
        Piece opponentChariot = new Piece(Team.HAN, PieceType.CHARIOT);

        Intersection origin = new Intersection(start, cannon);
        Intersection obstacleIntersection = new Intersection(middlePoint4, opponentChariot);
        Intersection opponentDestination = Intersection.empty(end);
        Intersection expected = new Intersection(end, cannon);

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
        janggiBoard.tryToMove(start, end, Team.CHO);
        Intersection actual = janggiBoard.findIntersection(end);

        // then
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }

}
