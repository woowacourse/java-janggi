package domain.piece;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest {
    private Piece horse;

    @BeforeEach
    void setUp() {
        horse = new Piece(PieceType.HORSE, Team.CHO, new HorseStrategy());
    }

    @Test
    void 마는_위로_두칸_왼쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(4, 7);

        List<Position> pathPositions = horse.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 6)));
    }

    @Test
    void 마는_위로_두칸_오른쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(6, 7);

        List<Position> pathPositions = horse.getPathPositions(from, to);


        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 6)));
    }


    @Test
    void 마는_위로_한칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(3, 6);

        List<Position> pathPositions = horse.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 5)));
    }


    @Test
    void 마는_아래로_한칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(3, 4);

        List<Position> pathPositions = horse.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 5)));
    }


    @Test
    void 마는_아래로_두칸_왼쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(4, 3);

        List<Position> pathPositions = horse.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 4)));
    }


    @Test
    void 마는_아래로_두칸_오른쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(6, 3);

        List<Position> pathPositions = horse.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 4)));
    }


    @Test
    void 마는_위로_한칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(7, 6);

        List<Position> pathPositions = horse.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(6, 5)));
    }


    @Test
    void 마는_아래로_한칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(7, 6);

        List<Position> pathPositions = horse.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(6, 5)));
    }
}
