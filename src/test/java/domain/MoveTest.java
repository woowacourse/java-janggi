package domain;

import domain.board.JanggiBoard;
import domain.fixture.TestIntersectionGenerator;
import domain.intersection.Intersection;
import domain.piece.Chariot;
import domain.piece.Soldier;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoveTest {

    @Test
    @DisplayName("이동이 끝난 뒤 출발지는 비어있고, 도착지는 기물이 존재한다.")
    void should_move_piece_to_destination_and_leave_source_empty() {
        Point start = new Point(0, 0);
        Point end = new Point(3, 0);

        Chariot chariot = new Chariot(Team.CHO);
        Soldier soldier = new Soldier(Team.HAN);

        Intersection from = new Intersection(start, chariot);
        Intersection to = new Intersection(end, soldier);

        JanggiBoard janggiBoard = new JanggiBoard(new TestIntersectionGenerator(List.of(from, to)));

        janggiBoard.tryToMove(start, end);

        Intersection expectedEmpty = Intersection.empty(start);
        Intersection expectedChariot = new Intersection(end, chariot);

        Assertions.assertThat(from).isEqualTo(expectedEmpty);
        Assertions.assertThat(to).isEqualTo(expectedChariot);
    }

}
