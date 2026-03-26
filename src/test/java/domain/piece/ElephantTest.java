package domain.piece;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {
    private Elephant elephant;

    @BeforeEach
    void setUp() {
        elephant = new Elephant(PieceType.ELEPHANT);
    }

    @Test
    void 상은_위로_세칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(3, 8);

        List<Position> pathPositions = elephant.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 6), new Position(4, 7), new Position(3, 8)));
    }

    @Test
    void 상은_위로_세칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(6, 7);

        List<Position> pathPositions = elephant.getPathPositions(from, to);


        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 6), new Position(6, 7), new Position(7, 8)));
    }


    @Test
    void 마는_위로_두칸_왼쪽으로_세칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(2, 7);

        List<Position> pathPositions = elephant.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 5), new Position(3, 6), new Position(2, 7)));
    }


    @Test
    void 마는_아래로_두칸_왼쪽으로_세칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(2, 3);

        List<Position> pathPositions = elephant.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 5), new Position(3, 4), new Position(2, 3)));
    }


    @Test
    void 마는_아래로_세칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(3, 2);

        List<Position> pathPositions = elephant.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 4), new Position(4, 3), new Position(3, 2)));
    }


    @Test
    void 마는_아래로_세칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(7, 2);

        List<Position> pathPositions = elephant.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 4), new Position(6, 3), new Position(7, 2)));
    }


    @Test
    void 상은_위로_두칸_오른쪽으로_세칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(8, 7);

        List<Position> pathPositions = elephant.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(6, 5), new Position(7, 6), new Position(8, 7)));
    }


    @Test
    void 상은_아래로_두칸_오른쪽으로_세칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(8, 7);

        List<Position> pathPositions = elephant.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(6, 5), new Position(7, 6), new Position(8, 7)));
    }
}
