package domain.piece;

import domain.Offset;
import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {
    private Piece elephant;

    @BeforeEach
    void setUp() {
        elephant = new Piece(PieceType.ELEPHANT, Team.CHO, new ElephantStrategy());
    }

    @Test
    void 상은_위로_세칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-2, 3);

        List<Offset> pathPositions = elephant.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, 1), new Offset(-1, 2)));
    }

    @Test
    void 상은_위로_세칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(2, 3);

        List<Offset> pathPositions = elephant.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, 1), new Offset(1, 2)));
    }


    @Test
    void 상은_위로_두칸_왼쪽으로_세칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-3, 2);

        List<Offset> pathPositions = elephant.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0), new Offset(-2, 1)));
    }


    @Test
    void 상은_아래로_두칸_왼쪽으로_세칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-3, -2);

        List<Offset> pathPositions = elephant.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0), new Offset(-2, -1)));
    }


    @Test
    void 상은_아래로_세칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-2, -3);

        List<Offset> pathPositions = elephant.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, -1), new Offset(-1, -2)));
    }


    @Test
    void 상은_아래로_세칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(2, -3);

        List<Offset> pathPositions = elephant.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, -1), new Offset(1, -2)));
    }


    @Test
    void 상은_위로_두칸_오른쪽으로_세칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(3, 2);

        List<Offset> pathPositions = elephant.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, 1)));
    }


    @Test
    void 상은_아래로_두칸_오른쪽으로_세칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(3, -2);

        List<Offset> pathPositions = elephant.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, -1)));
    }
}
