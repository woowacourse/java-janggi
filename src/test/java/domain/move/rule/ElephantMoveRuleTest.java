package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.move.rule.ElephantMoveRule;
import domain.point.Point;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static domain.move.path.exception.ErrorMessage.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;
import static domain.move.path.exception.ErrorMessage.CANNOT_MOVE_PATH_HAS_OBSTACLE;

public class ElephantMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Team sameTeam = Team.CHO;
        Piece elephant = new Piece(sameTeam, PieceType.ELEPHANT);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.ELEPHANT);

        Intersection from = new Intersection(start, elephant);
        Intersection middleIntersection1 = Intersection.empty(middlePoint1);
        Intersection middleIntersection2 = Intersection.empty(middlePoint2);
        Intersection sameTeamIntersection = new Intersection(end, sameTeamPiece);

        ElephantMoveRule elephantRule = new ElephantMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    elephantRule.checkMoveRule(from, new Path(List.of(
                            middleIntersection1,
                            middleIntersection2,
                            sameTeamIntersection)));
                }).isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getErrorMessage());
    }

    @Test
    @DisplayName("상의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void shouldThrowExceptionWhenPathHasObstacle() {
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Team sameTeam = Team.CHO;
        Piece elephant = new Piece(sameTeam, PieceType.ELEPHANT);
        Piece obstacle = new Piece(sameTeam, PieceType.ELEPHANT);

        Intersection from = new Intersection(start, elephant);
        Intersection obstacleIntersection = new Intersection(middlePoint1, obstacle);
        Intersection intersection = Intersection.empty(middlePoint2);
        Intersection to = Intersection.empty(end);

        ElephantMoveRule elephantMoveRule = new ElephantMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    elephantMoveRule.checkMoveRule(from, new Path(List.of(
                            obstacleIntersection,
                            intersection,
                            to)));
                }).isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_PATH_HAS_OBSTACLE.getErrorMessage());
    }

    @Test
    @DisplayName("상은 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void horseCanMove_WhenNoObstacle_AndDestinationIsEmpty() {
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Piece elephant = new Piece(Team.CHO, PieceType.ELEPHANT);

        Intersection from = new Intersection(start, elephant);
        Intersection intersection1 = Intersection.empty(middlePoint1);
        Intersection intersection2 = Intersection.empty(middlePoint2);
        Intersection to = Intersection.empty(end);

        ElephantMoveRule elephantMoveRule = new ElephantMoveRule();

        Assertions.assertThat(
                        elephantMoveRule.checkMoveRule(from, new Path(List.of(
                                intersection1,
                                intersection2,
                                to))))
                .isTrue();
    }

    @Test
    @DisplayName("상은 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void elephantCanMoveWhenNoObstacleAndDestinationIsOpponent() {
        Point start = new Point(0, 0);
        Point middlePoint1 = new Point(1, 0);
        Point middlePoint2 = new Point(2, 1);
        Point end = new Point(3, 2);

        Team team = Team.CHO;
        Team opponentTeam = Team.HAN;
        Piece elephant = new Piece(team, PieceType.ELEPHANT);
        Piece opponent = new Piece(opponentTeam, PieceType.ELEPHANT);

        Intersection from = new Intersection(start, elephant);
        Intersection intersection1 = Intersection.empty(middlePoint1);
        Intersection intersection2 = Intersection.empty(middlePoint2);
        Intersection to = new Intersection(end, opponent);

        ElephantMoveRule elephantMoveRule = new ElephantMoveRule();

        Assertions.assertThat(
                        elephantMoveRule.checkMoveRule(from, new Path((List.of(
                                intersection1,
                                intersection2,
                                to)))))
                .isTrue();
    }

}
