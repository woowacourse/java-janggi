package domain.piece;

import domain.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralTest {
    private Piece general;

    @BeforeEach
    void setUp() {
        general = new Piece(PieceType.GENERAL, Team.CHO, new GeneralStrategy());
    }

    @Test
    void 궁은_위로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, 1);
        List<Offset> pathPositions = general.getPathPositions(offset);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_아래로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, -1);
        List<Offset> pathPositions = general.getPathPositions(offset);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_좌로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 0);
        List<Offset> pathPositions = general.getPathPositions(offset);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_우로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 0);
        List<Offset> pathPositions = general.getPathPositions(offset);
        assertThat(pathPositions).isEqualTo(List.of());
    }
}
