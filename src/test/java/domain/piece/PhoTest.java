package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.board.Board;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PhoTest {

    @DisplayName("포의 이동 가능한 경로를 검사한다. "
            + "이동 가능 경로: 상,하,좌,우 방향으로 기물 하나를 넘어서 이동한다.")
    @Test
    void phoTest() {
        Pho pho = new Pho(Country.HAN);
        Cha cha = new Cha(Country.CHO);

        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), pho);
        pieces.put(new Coordinate(6, 5), cha);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = pho.availableMovePositions(new Coordinate(5, 5), board);

        List<Coordinate> expected = List.of(
                new Coordinate(7, 5), new Coordinate(8, 5),
                new Coordinate(9, 5), new Coordinate(10, 5));

        assertThat(availableMovePositions).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("포는 포를 넘어갈 수 없다")
    @Test
    void phoTest2() {
        Pho pho = new Pho(Country.HAN);
        Pho pho2 = new Pho(Country.CHO);

        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), pho);
        pieces.put(new Coordinate(6, 5), pho2);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = pho.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(7, 5))).isFalse();
    }

    @DisplayName("포는 다른 팀의 기물이 있는 위치로 이동할 수 있다")
    @Test
    void phoTest3() {
        Pho pho = new Pho(Country.HAN);
        Cha cha = new Cha(Country.CHO);

        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), pho);
        pieces.put(new Coordinate(6, 5), cha);
        pieces.put(new Coordinate(7, 5), cha);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = pho.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(7, 5))).isTrue();
    }

    @DisplayName("포는 다른 팀의 포가 있는 위치로 이동할 수 없다")
    @Test
    void phoTest4() {
        Pho pho = new Pho(Country.HAN);
        Cha cha = new Cha(Country.CHO);
        Pho pho2 = new Pho(Country.CHO);

        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), pho);
        pieces.put(new Coordinate(6, 5), cha);
        pieces.put(new Coordinate(7, 5), pho2);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = pho.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(7, 5))).isFalse();
    }

    @DisplayName("포는 같은 팀의 기물이 있는 위치로 이동할 수 없다")
    @Test
    void phoTest5() {
        Pho pho = new Pho(Country.HAN);
        Cha cha = new Cha(Country.HAN);

        Map<Coordinate, Piece> pieces = new HashMap<>();
        pieces.put(new Coordinate(5, 5), pho);
        pieces.put(new Coordinate(6, 5), cha);
        pieces.put(new Coordinate(7, 5), cha);
        Board board = new Board(pieces);

        List<Coordinate> availableMovePositions = pho.availableMovePositions(new Coordinate(5, 5), board);

        assertThat(availableMovePositions.contains(new Coordinate(7, 5))).isFalse();
    }

}
