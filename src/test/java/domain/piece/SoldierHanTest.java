package domain.piece;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierHanTest {

    private Piece soldier;

    @BeforeEach
    void setUp() {
        soldier = new Piece(PieceType.SOLDIER, Team.HAN, new HanSoldierStrategy());
    }

    @Test
    void 병은_위로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(0, 4);
        Position to = new Position(0, 3);

        List<Position> pathPositions = soldier.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 병은_좌로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(2, 3);
        Position to = new Position(1, 3);

        List<Position> pathPositions = soldier.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 병은_우로_한칸_움직일_수_있는_경로가_있다() {
        Position from = new Position(0, 3);
        Position to = new Position(1, 3);

        List<Position> pathPositions = soldier.getPathPositions(from, to);

        assertThat(pathPositions).isEqualTo(List.of());
    }

}
