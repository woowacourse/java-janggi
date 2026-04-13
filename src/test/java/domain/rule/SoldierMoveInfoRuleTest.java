package domain.rule;

import domain.intersection.Intersection;
import domain.move.SoldierMoveRule;
import domain.move.Vector;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierMoveInfoRuleTest {
    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void should_throw_exception_when_destination_is_same_team() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, sameTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    soldierMoveRule.validateMoveRule(from, List.of(to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("졸은 뒤로 이동할 수 없다. (한 진영 졸은 위쪽 이동 불가)")
    void should_reject_upward_move_for_han_soldier() {
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Piece soldier = new Piece(Team.HAN, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(Team.CHO, PieceType.SOLDIER);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        Assertions.assertThatThrownBy(() -> soldierMoveRule.findPossiblePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("졸은 뒤로 이동할 수 없다. (초 진영 졸은 아래쪽 이동 불가)")
    void should_reject_downward_move_for_cho_soldier() {
        Point start = new Point(0, 0);
        Point end = start.next(Vector.DOWN);

        Piece soldier = new Piece(Team.CHO, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(Team.HAN, PieceType.SOLDIER);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoldierMoveRule soldierMoveRule = new SoldierMoveRule();

        Assertions.assertThatThrownBy(() -> soldierMoveRule.findPossiblePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
