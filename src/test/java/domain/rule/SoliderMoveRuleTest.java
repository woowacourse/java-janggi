package domain.rule;

import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.move.rule.SoliderMoveRule;
import domain.move.directions.Vector;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SoliderMoveRuleTest {

    @Test
    @DisplayName("도착지에 같은 팀이 있는 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenDestinationIsSameTeam() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team sameTeam = Team.HAN;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece sameTeamPiece = new Piece(sameTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, sameTeamPiece);

        SoliderMoveRule soliderMoveRule = new SoliderMoveRule();

        Assertions.assertThatThrownBy(() -> {
                    soliderMoveRule.checkMoveRule(from, List.of(to));
                }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("같은 팀의 위치로 이동할 수 없습니다.");
    }

    @Test
    @DisplayName("한 진영인 졸의 직진 방향은 내려감이다.")
    void straightOfHanSoliderIsDown() {
        Point start = new Point(0, 0);
        Point end = start.next(Vector.DOWN);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoliderMoveRule soliderMoveRule = new SoliderMoveRule();

        Assertions.assertThat(soliderMoveRule.checkMoveRule(from, List.of(to)))
                .isTrue();

    }

    @Test
    @DisplayName("한 진영인 졸의 직진 방향은 올라감이다.")
    void straightOfCHOSoliderIsUp() {
        Point start = new Point(1, 0);
        Point end = start.next(Vector.UP);

        Team sameTeam = Team.HAN;
        Team anotherTeam = Team.CHO;
        Piece soldier = new Piece(sameTeam, PieceType.SOLDIER);
        Piece anotherTeamPiece = new Piece(anotherTeam, PieceType.SOLDIER);

        Intersection from = new Intersection(start, soldier);
        Intersection to = new Intersection(end, anotherTeamPiece);

        SoliderMoveRule soliderMoveRule = new SoliderMoveRule();

        Assertions.assertThat(soliderMoveRule.checkMoveRule(from, List.of(to)))
                .isTrue();

    }

}
