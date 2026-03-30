package domain.intersection;

import domain.board.JanggiBoard;
import domain.intersection.exception.IntersectionException;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import fixture.TestIntersectionGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.intersection.exception.IntersectionError.ORIGIN_INTERSECTION_IS_EMPTY;
import static domain.intersection.exception.IntersectionError.ORIGIN_INTERSECTION_IS_NOT_OPPONENT;

public class IntersectionTest {

    @Test
    @DisplayName("상대 칸을 출발 좌표로 지정하면, 예외가 발생한다.")
    void shouldThrowExceptionWhenOriginIsOpponent() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team team = Team.CHO;
        Team opponentTeam = Team.HAN;
        Piece choPiece = new Piece(team, PieceType.SOLDIER);

        Intersection opponentIntersection = new Intersection(start, choPiece);
        Intersection to = Intersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                opponentIntersection,
                to)
        ));

        Assertions.assertThatThrownBy(() -> {
                    janggiBoard.tryToMove(start, end, opponentTeam);
                }).isInstanceOf(IntersectionException.class)
                .hasMessage(ORIGIN_INTERSECTION_IS_NOT_OPPONENT.getMessage());
    }

    @Test
    @DisplayName("빈 칸을 출발 좌표로 지정하면, 예외가 발생한다.")
    void shouldThrowExceptionWhenOriginIsEmpty() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Intersection emptyIntersection = Intersection.empty(start);
        Intersection to = Intersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                emptyIntersection,
                to)
        ));

        Assertions.assertThatThrownBy(() -> {
                    janggiBoard.tryToMove(start, end, Team.CHO);
                }).isInstanceOf(IntersectionException.class)
                .hasMessage(ORIGIN_INTERSECTION_IS_EMPTY.getMessage());
    }

}
