package domain.piece;

import domain.Offset;
import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GuardTest {
    private Piece guard;

    @BeforeEach
    void setUp() {
        guard = new Guard(Team.CHO);
    }

    @Test
    void 사는_위로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, 1);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_아래로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, -1);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_좌로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 0);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_우로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 0);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_두칸을_이동할_수_없다() {
        Offset offset = new Offset(2, 0);

        Position from = new Position(3, 1);
        Position to = offset.applyTo(from);

        assertThrows(IllegalArgumentException.class, () -> guard.getPathOffset(from, to));
    }
}
