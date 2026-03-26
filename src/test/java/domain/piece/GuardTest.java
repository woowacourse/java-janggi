package domain.piece;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GuardTest {
    private Guard guard;

    @BeforeEach
    void setUp() {
        guard = new Guard(PieceType.GUARD);
    }

    @Test
    void 사는_위로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(3, 0);
        Position to = new Position(3, 1);

        List<Position> pathPositions = guard.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(3, 1)));
    }

    @Test
    void 사는_좌로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(5, 0);
        Position to = new Position(4, 0);

        List<Position> pathPositions = guard.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 0)));
    }

    @Test
    void 졸은_우로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(3, 0);
        Position to = new Position(4, 0);

        List<Position> pathPositions = guard.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 0)));
    }


    @Test
    void 사는_아래로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(3, 1);
        Position to = new Position(3, 0);

        List<Position> pathPositions = guard.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(3, 0)));
    }
}
