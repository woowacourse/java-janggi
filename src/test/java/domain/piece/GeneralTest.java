package domain.piece;

import domain.Offset;
import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GeneralTest {
    private Piece general;

    @BeforeEach
    void setUp() {
        general = new General(Team.CHO);
    }

    @Test
    void 궁은_위로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, 1);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to);

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_아래로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, -1);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_좌로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 0);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_우로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 0);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_두칸을_이동할_수_없다() {
        Offset offset = new Offset(2, 0);

        Position from = new Position(3, 1);
        Position to = offset.applyTo(from);

        assertThrows(IllegalArgumentException.class, () -> general.getPathOffset(from, to));
    }
}
