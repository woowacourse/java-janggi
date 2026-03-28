package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.move.rule.CannonMoveRule;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.path.exception.ErrorMessage.*;

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
        Team sameTeam = Team.CHO;

        Intersection from = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));

        Intersection onlyObstacleIntersection = new Intersection(middlePoint5, new Piece(sameTeam, PieceType.CANNON));
        Intersection to = new Intersection(end, new Piece(sameTeam, PieceType.CHARIOT));

        CannonMoveRule cannonMoveRule = new CannonMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    cannonMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            onlyObstacleIntersection,
                            intersection6,
                            intersection7,
                            intersection8,
                            to)));
                }).isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getErrorMessage());
    }

    @Test
    @DisplayName("포의 이동경로 장애물이 포이면 예외가 발생한다.")
    void shouldThrowExceptionWhenObstacleIsCannon() {
        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;

        Intersection from = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection cannonObstacle = new Intersection(middlePoint7, new Piece(anotherTeam, PieceType.CANNON));
        Intersection to = Intersection.empty(end);

        CannonMoveRule cannonMoveRule = new CannonMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    cannonMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            intersection5,
                            intersection6,
                            cannonObstacle,
                            intersection8,
                            to)));
                }).isInstanceOf(PathException.class)
                .hasMessage(CANNON_CANNOT_JUMP_CANNON.getErrorMessage());
    }

    @Test
    @DisplayName("포는 두개의 장애물을 넘어갈 때 예외가 발생한다.")
    void shouldThrowExceptionWhenCannonJumpTwoObstacle() {
        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;

        Intersection from = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection obstacle1 = new Intersection(middlePoint5, new Piece(anotherTeam, PieceType.CHARIOT));
        Intersection obstacle2 = new Intersection(middlePoint6, new Piece(anotherTeam, PieceType.CHARIOT));
        Intersection to = Intersection.empty(end);

        CannonMoveRule cannonMoveRule = new CannonMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    cannonMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            obstacle1,
                            obstacle2,
                            intersection7,
                            intersection8,
                            to)));
                }).isInstanceOf(PathException.class)
                .hasMessage(CANNON_MUST_JUMP_ONE_PIECE.getErrorMessage());
    }

    @Test
    @DisplayName("포의 경로에 장애물이 없을 때 예외가 발생한다.")
    void shouldThrowExceptionWhenCannonPathDoesntObstacle() {
        Team sameTeam = Team.CHO;

        Intersection from = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection to = Intersection.empty(end);

        CannonMoveRule cannonMoveRule = new CannonMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    cannonMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            intersection5,
                            intersection6,
                            intersection7,
                            intersection8,
                            to)));
                }).isInstanceOf(PathException.class)
                .hasMessage(CANNON_MUST_JUMP_ONE_PIECE.getErrorMessage());
    }

    @Test
    @DisplayName("포가 포를 공격할때 예외가 발생한다.")
    void shouldThrowExceptionWhenCannonAttackCannon() {
        Team sameTeam = Team.CHO;
        Team anotherTeam = Team.HAN;

        Intersection from = new Intersection(start, new Piece(sameTeam, PieceType.CANNON));
        Intersection obstacle = new Intersection(middlePoint6, new Piece(sameTeam, PieceType.CHARIOT));
        Intersection to = new Intersection(end, new Piece(anotherTeam, PieceType.CANNON));


        CannonMoveRule cannonMoveRule = new CannonMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    cannonMoveRule.checkMoveRule(from, new Path(List.of(intersection1,
                            intersection2,
                            intersection3,
                            intersection4,
                            intersection5,
                            obstacle,
                            intersection7,
                            intersection8,
                            to)));
                }).isInstanceOf(PathException.class)
                .hasMessage(CANNON_CANNOT_ATTACK_CANNON.getErrorMessage());
    }

}
