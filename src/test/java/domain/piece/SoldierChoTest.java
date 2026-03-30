package domain.piece;

import domain.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierChoTest {

    private Piece soldier;

    @BeforeEach
    void setUp() {
        soldier = new ChoSoldier(Team.CHO);
    }

    @Test
    void 졸은_위로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, 1);
        List<Offset> pathPositions = soldier.getPathOffset(offset);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 졸은_좌로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 0);
        List<Offset> pathPositions = soldier.getPathOffset(offset);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 졸은_우로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 0);
        List<Offset> pathPositions = soldier.getPathOffset(offset);
        assertThat(pathPositions).isEqualTo(List.of());
    }

}
