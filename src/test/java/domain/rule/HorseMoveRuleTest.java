package domain.rule;

import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.move.rule.HorseMoveRule;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HorseMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Team sameTeam = Team.CHO;
        Piece horse = new Piece(sameTeam, PieceType.HORSE);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.HORSE);

        Intersection from = new Intersection(start, horse);
        Intersection middleIntersection = Intersection.empty(middlePoint);
        Intersection to = new Intersection(end, sameTeamPiece);

        HorseMoveRule horseRule = new HorseMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    horseRule.checkMoveRule(from, List.of(middleIntersection, to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("마의 이동 경로에 장애물이 있으면 예외가 발생한다.")
    void shouldThrowExceptionWhenPathHasObstacle() {
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Team sameTeam = Team.CHO;
        Piece horse = new Piece(sameTeam, PieceType.HORSE);
        Piece obstacle = new Piece(sameTeam, PieceType.HORSE);

        Intersection from = new Intersection(start, horse);
        Intersection middleIntersection = new Intersection(middlePoint, obstacle);
        Intersection to = Intersection.empty(end);

        HorseMoveRule horseRule = new HorseMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    horseRule.checkMoveRule(from, List.of(middleIntersection, to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 다른 기물이 있어 통과할 수 없습니다.");
    }

    @Test
    @DisplayName("마는 경로에 장애물이 없고 도착지가 비어 있으면 이동한다.")
    void horseCanMove_WhenNoObstacle_AndDestinationIsEmpty() {
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Piece horse = new Piece(Team.CHO, PieceType.HORSE);

        Intersection from = new Intersection(start, horse);
        Intersection middleIntersection = Intersection.empty(middlePoint);
        Intersection to = Intersection.empty(end);

        HorseMoveRule horseRule = new HorseMoveRule();

        Assertions.assertThat(horseRule.checkMoveRule(from, List.of(middleIntersection, to)))
                .isTrue();
    }

    @Test
    @DisplayName("마는 경로에 장애물이 없고 도착지에 상대팀이 있으면 이동한다.")
    void horseCanMoveWhenNoObstacleAndDestinationIsOpponent() {
        Point start = new Point(0, 0);
        Point middlePoint = new Point(1, 0);
        Point end = new Point(2, 1);

        Team team = Team.CHO;
        Team opponentTeam = Team.HAN;
        Piece horse = new Piece(team, PieceType.HORSE);
        Piece opponent = new Piece(opponentTeam, PieceType.HORSE);

        Intersection from = new Intersection(start, horse);
        Intersection middleIntersection = Intersection.empty(middlePoint);
        Intersection to = new Intersection(start, opponent);

        HorseMoveRule horseRule = new HorseMoveRule();

        Assertions.assertThat(horseRule.checkMoveRule(from, List.of(middleIntersection, to)))
                .isTrue();
    }


}
