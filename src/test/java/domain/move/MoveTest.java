package domain.move;

import domain.board.JanggiBoard;
import domain.intersection.exception.IntersectionException;
import fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.intersection.exception.IntersectionError.ORIGIN_INTERSECTION_IS_EMPTY;
import static domain.intersection.exception.IntersectionError.ORIGIN_INTERSECTION_IS_NOT_OPPONENT;

public class MoveTest {

    @Test
    @DisplayName("이동이 끝난 뒤 출발지는 비어있고, 도착지는 기물이 존재한다.")
    void shouldMovePieceToDestinationAndLeaveSourceEmpty() {
        Team currentTurn = Team.CHO;
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Piece chariot = new Piece(currentTurn, PieceType.CHARIOT);
        Piece soldier = new Piece(Team.HAN, PieceType.SOLDIER);

        Intersection origin = new Intersection(start, chariot);
        Intersection destination = new Intersection(end, soldier);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(origin, destination)));
        janggiBoard.tryToMove(start, end, currentTurn);

        Intersection expectedEmpty = Intersection.empty(start);
        Intersection expectedChariot = new Intersection(end, chariot);

        Assertions.assertThat(origin).isEqualTo(expectedEmpty);
        Assertions.assertThat(destination).isEqualTo(expectedChariot);
    }

    @Test
    @DisplayName("상대 칸을 출발 좌표로 지정하면, 예외가 발생한다.")
    void shouldThrowExceptionWhenOriginIsOpponent() {
        Point start = new Point(0, 0);
        Point end = new Point(1, 0);

        Team team = Team.CHO;
        Team opponentTeam = Team.HAN;
        Piece choPiece = new Piece(team, PieceType.SOLDIER);

        Intersection opponentIntersection = new Intersection(start, choPiece);
        Intersection destination = Intersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                opponentIntersection,
                destination)
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
        Intersection destination = Intersection.empty(end);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(
                emptyIntersection,
                destination)
        ));

        Assertions.assertThatThrownBy(() -> {
                    janggiBoard.tryToMove(start, end, Team.CHO);
                }).isInstanceOf(IntersectionException.class)
                .hasMessage(ORIGIN_INTERSECTION_IS_EMPTY.getMessage());
    }

}
