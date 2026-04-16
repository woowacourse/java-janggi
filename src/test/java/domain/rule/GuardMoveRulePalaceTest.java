package domain.rule;

import domain.intersection.Intersection;
import domain.move.GuardMoveRule;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.point.Point;
import domain.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GuardMoveRulePalaceTest {
    @Test
    @DisplayName("사(Guard)는 궁성 밖으로 이동하려고 하면 예외가 발생한다.")
    void should_throw_exception_when_guard_moves_out_of_palace() {
        Point fromPoint = new Point(8, 4);
        Point toPoint = new Point(6, 4);

        Intersection from = new Intersection(fromPoint, new Piece(Team.CHO, PieceType.GUARD));
        Intersection to = Intersection.empty(toPoint);

        GuardMoveRule rule = new GuardMoveRule();

        Assertions.assertThatThrownBy(() -> rule.findPossiblePoints(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치/방향입니다.");
    }

    @Test
    @DisplayName("사(Guard)는 궁성 안에서 상하좌우로 1칸 이동할 수 있다.")
    void should_move_guard_one_step_straight_in_palace() {
        Point fromPoint = new Point(8, 4);
        Point toPoint = new Point(9, 4);

        Intersection from = new Intersection(fromPoint, new Piece(Team.CHO, PieceType.GUARD));
        Intersection to = Intersection.empty(toPoint);

        GuardMoveRule rule = new GuardMoveRule();

        Assertions.assertThat(rule.findPossiblePoints(from, to))
                .containsExactly(toPoint);
    }

    @Test
    @DisplayName("사(Guard)는 궁성 대각 위치에서만 1칸 대각 이동할 수 있다.")
    void should_move_guard_diagonal_only_between_palace_diagonal_points() {
        Point diagonalFrom = new Point(8, 4);
        Point diagonalTo = new Point(9, 5);

        Intersection from = new Intersection(diagonalFrom, new Piece(Team.CHO, PieceType.GUARD));
        Intersection to = Intersection.empty(diagonalTo);

        GuardMoveRule rule = new GuardMoveRule();

        Assertions.assertThat(rule.findPossiblePoints(from, to))
                .containsExactly(diagonalTo);

        Point nonDiagonalFrom = new Point(7, 4);
        Point nonDiagonalTo = new Point(8, 5);

        Intersection nonDiagonalFromIntersection =
                new Intersection(nonDiagonalFrom, new Piece(Team.CHO, PieceType.GUARD));
        Intersection nonDiagonalToIntersection = Intersection.empty(nonDiagonalTo);

        Assertions.assertThatThrownBy(
                        () -> rule.findPossiblePoints(nonDiagonalFromIntersection, nonDiagonalToIntersection))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치/방향입니다.");
    }
}

