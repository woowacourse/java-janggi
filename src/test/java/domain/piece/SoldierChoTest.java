package domain.piece;

import domain.Offset;
import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SoldierChoTest {

    private Piece soldier;

    @BeforeEach
    void setUp() {
        soldier = new Soldier(Team.CHO);
    }

    @Test
    void 졸은_위로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, 1);

        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = soldier.getPathOffset(from, to);

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 졸은_좌로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 0);

        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = soldier.getPathOffset(from, to);

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 졸은_우로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 0);

        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = soldier.getPathOffset(from, to);

        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 졸은_아래로_가지_못한다() {
        Offset offset = new Offset(0, -1);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        assertThrows(IllegalArgumentException.class, () -> soldier.getPathOffset(from, to));
    }

    @Test
    void 졸은_두칸을_가지_못한다() {
        Offset offset = new Offset(0, 2);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        assertThrows(IllegalArgumentException.class, () -> soldier.getPathOffset(from, to));
    }

}
