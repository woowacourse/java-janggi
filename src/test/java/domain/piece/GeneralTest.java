package domain.piece;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralTest {
    private General general;

    @BeforeEach
    void setUp() {
        general = new General(PieceType.GENERAL, Team.CHO);
    }

    @Test
    void 궁은_위로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(4, 1);
        Position to = new Position(4,2);

        List<Position> pathPositions = general.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 2)));
    }

    @Test
    void 궁은_좌로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(4, 1);
        Position to = new Position(3, 1);

        List<Position> pathPositions = general.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(3, 1)));
    }

    @Test
    void 궁은_우로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(4, 1);
        Position to = new Position(5, 1);

        List<Position> pathPositions = general.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 1)));
    }


    @Test
    void 궁은_아래로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(4, 1);
        Position to = new Position(4, 0);

        List<Position> pathPositions = general.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 0)));
    }
}
