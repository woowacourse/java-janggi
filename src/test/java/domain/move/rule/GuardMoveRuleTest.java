package domain.move.rule;

import domain.intersection.Intersection;
import domain.move.path.Path;
import domain.move.path.exception.PathException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.move.path.exception.PathError.CANNOT_MOVE_DESTINATION_IS_SAME_TEAM;

public class GuardMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Piece guard = new Piece(sameTeam, PieceType.GUARD);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, guard);
        Intersection sameTeamIntersection = new Intersection(end, sameTeamPiece);

        GuardMoveRule guardMoveRule = new GuardMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    guardMoveRule.checkMoveRule(from, new Path(List.of(
                            sameTeamIntersection))
                    );
                }).isInstanceOf(PathException.class)
                .hasMessage(CANNOT_MOVE_DESTINATION_IS_SAME_TEAM.getMessage());
    }

    @Test
    @DisplayName("사는 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGuardWhenDestinationIsEmpty() {
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Piece guard = new Piece(sameTeam, PieceType.GUARD);

        Intersection from = new Intersection(start, guard);
        Intersection emptyIntersection = Intersection.empty(end);

        GuardMoveRule guardMoveRule = new GuardMoveRule();

        Assertions.assertThat(guardMoveRule.checkMoveRule(from, new Path(List.of(emptyIntersection))))
                .isTrue();
    }

    @Test
    @DisplayName("사는 도착지에 상대팀이 있으면 이동할 수 있다.")
    void canMoveGuardWhenDestinationIsOpponent() {
        Point start = new Point(1, 4);
        Point end = new Point(2, 4);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Piece guard = new Piece(sameTeam, PieceType.GUARD);
        Piece opponent = new Piece(anotherTeam, PieceType.GUARD);

        Intersection from = new Intersection(start, guard);
        Intersection opponentIntersection = new Intersection(end, opponent);

        GuardMoveRule guardMoveRule = new GuardMoveRule();

        Assertions.assertThat(guardMoveRule.checkMoveRule(from, new Path(List.of(opponentIntersection))))
                .isTrue();
    }

}
