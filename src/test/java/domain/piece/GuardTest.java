package domain.piece;

import domain.Offset;
import domain.board.Palace;
import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GuardTest {
    private Piece guard;
    private Optional<Palace> hanPalace;

    @BeforeEach
    void setUp() {
        guard = new Guard(Team.HAN);
        hanPalace = Optional.of(new Palace(new Position(4, 8)));
    }

    @Test
    void 사는_위로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, 1);
        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_아래로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, -1);
        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_좌로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 0);
        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_우로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 0);
        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_두칸을_이동할_수_없다() {
        Offset offset = new Offset(2, 0);

        Position from = new Position(3, 8);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> guard.getPathOffset(from, to, hanPalace));
    }

    @Test
    void 사는_궁성_중앙에서_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(1, 1);

        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_궁성_코너에서_오른쪽_위로_이동할_수_있다() {
        Offset offset = new Offset(1, 1);

        Position from = new Position(3, 7);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_궁성_코너에서_오른쪽_아래로_이동할_수_있다() {
        Offset offset = new Offset(1, -1);

        Position from = new Position(3, 9);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 사는_궁성_코너에서_왼쪽_위로_이동할_수_있다() {
        Offset offset = new Offset(-1, 1);

        Position from = new Position(5, 7);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 사는_궁성_코너에서_왼쪽_아래로_이동할_수_있다() {
        Offset offset = new Offset(-1, -1);

        Position from = new Position(5, 9);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = guard.getPathOffset(from, to, hanPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 사는_궁성_밖으로_이동할_수_없다() {
        Offset offset = new Offset(-1, 0);

        Position from = new Position(3, 8);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> guard.getPathOffset(from, to, hanPalace));
    }
}
