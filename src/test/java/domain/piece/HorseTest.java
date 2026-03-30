package domain.piece;

import domain.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest {
    private Piece horse;

    @BeforeEach
    void setUp() {
        horse = new Horse(Team.CHO);
    }

    @Test
    void 마는_위로_두칸_왼쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 2);

        List<Offset> pathPositions = horse.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, 1)));
    }

    @Test
    void 마는_위로_두칸_오른쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 2);

        List<Offset> pathPositions = horse.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, 1)));
    }


    @Test
    void 마는_위로_한칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-2, 1);

        List<Offset> pathPositions = horse.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0)));

    }


    @Test
    void 마는_아래로_한칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-2, -1);

        List<Offset> pathPositions = horse.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0)));
    }


    @Test
    void 마는_아래로_두칸_왼쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, -2);

        List<Offset> pathPositions = horse.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, -1)));

    }


    @Test
    void 마는_아래로_두칸_오른쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, -2);

        List<Offset> pathPositions = horse.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, -1)));
    }


    @Test
    void 마는_위로_한칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(2, 1);

        List<Offset> pathPositions = horse.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0)));
    }


    @Test
    void 마는_아래로_한칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(2, -1);

        List<Offset> pathPositions = horse.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0)));
    }
}
