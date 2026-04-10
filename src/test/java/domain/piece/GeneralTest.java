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

class GeneralTest {
    private Piece general;
    private Optional<Palace> choPalace;

    @BeforeEach
    void setUp() {
        general = new General(Team.CHO);
        choPalace = Optional.of(new Palace(new Position(4, 1)));
    }

    @Test
    void 궁은_위로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, 1);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_아래로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, -1);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_좌로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 0);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_우로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 0);
        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_두칸을_이동할_수_없다() {
        Offset offset = new Offset(2, 0);

        Position from = new Position(3, 1);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> general.getPathOffset(from, to, choPalace));
    }

    @Test
    void 궁은_궁성_중앙에서_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(1, 1);

        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_궁성_코너에서_오른쪽_위로_이동할_수_있다() {
        Offset offset = new Offset(1, 1);

        Position from = new Position(3, 0);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_궁성_코너에서_오른쪽_아래로_이동할_수_있다() {
        Offset offset = new Offset(1, -1);

        Position from = new Position(3, 2);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 궁은_궁성_코너에서_왼쪽_위로_이동할_수_있다() {
        Offset offset = new Offset(-1, 1);

        Position from = new Position(5, 0);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 궁은_궁성_코너에서_왼쪽_아래로_이동할_수_있다() {
        Offset offset = new Offset(-1, -1);

        Position from = new Position(5, 2);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = general.getPathOffset(from, to, choPalace);
        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 궁은_궁성_밖으로_이동할_수_없다() {
        Offset offset = new Offset(-1, 0);

        Position from = new Position(3, 0);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> general.getPathOffset(from, to, choPalace));
    }
}
