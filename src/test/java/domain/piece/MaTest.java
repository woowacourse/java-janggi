package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.board.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MaTest {

    @DisplayName("마의 이동 가능한 경로를 검사한다. "
            + "이동 가능 경로: (1, 2), (2, 1), (2, -1), (1, -2), (-1, -2), (-2, -1), (-2, 1), (-1, 2)")
    @Test
    void maTest() {
        Ma ma = new Ma(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), ma);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = ma.availableMovePositions(new Coordinate(5, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(3, 4), new Coordinate(3, 6),
                new Coordinate(4, 3), new Coordinate(4, 7),
                new Coordinate(6, 3), new Coordinate(6, 7),
                new Coordinate(7, 4), new Coordinate(7, 6)
        );
        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("마는 같은 팀의 말을 잡을 수 없다")
    @Test
    void maTest2() {
        Ma ma = new Ma(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), ma);
        pieces.put(new Coordinate(6, 3), new Ma(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = ma.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(6, 3))).isFalse();
    }

    @DisplayName("마는 이동 가능한 경로에 다른 말이 있으면 이동할 수 없다")
    @Test
    void maTest4() {
        Ma ma = new Ma(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), ma);
        pieces.put(new Coordinate(4, 5), new Ma(Country.HAN));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = ma.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(3, 4))).isFalse();
        assertThat(availableMovePositions.contains(new Coordinate(3, 6))).isFalse();
    }

    @DisplayName("마는 다른 팀의 기물이 있는 위치로 이동할 수 있다")
    @Test
    void maTest3() {
        Ma ma = new Ma(Country.HAN);
        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), ma);
        pieces.put(new Coordinate(6, 3), new Ma(Country.CHO));
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = ma.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(6, 3))).isTrue();
    }
}
