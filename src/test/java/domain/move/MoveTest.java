package domain.move;

import domain.board.JanggiBoard;
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

}
