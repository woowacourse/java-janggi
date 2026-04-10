package domain.piece;

import domain.Offset;
import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;
import domain.board.Palace;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SoldierChoTest {

    private Piece soldier;
    private Optional<Palace> hanPalace;

    @BeforeEach
    void setUp() {
        soldier = new Soldier(Team.CHO);
        hanPalace = Optional.of(new Palace(new Position(4, 8)));
    }

    @Test
    void 졸은_위로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(0, 1);

        Position from = new Position(4, 4);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = soldier.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 졸은_좌로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 0);

        Position from = new Position(4, 4);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = soldier.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 졸은_우로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 0);

        Position from = new Position(4, 4);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = soldier.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 졸은_아래로_가지_못한다() {
        Offset offset = new Offset(0, -1);
        Position from = new Position(4, 4);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> soldier.getPathOffset(from, to, Optional.empty()));
    }

    @Test
    void 졸은_두칸을_가지_못한다() {
        Offset offset = new Offset(0, 2);
        Position from = new Position(4, 4);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> soldier.getPathOffset(from, to, Optional.empty()));
    }

    @Test
    void 졸은_궁성에_있을때_오른쪽_위_방향으로_갈_수_있다() {
        Offset offset = new Offset(1, 1);
        Position from = new Position(3, 7);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = soldier.getPathOffset(from, to, hanPalace);

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 졸은_궁성에_있을때_왼쪽_위_방향으로_갈_수_있다() {
        Offset offset = new Offset(-1, 1);
        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = soldier.getPathOffset(from, to, hanPalace);

        assertThat(pathPositions).isEqualTo(List.of());
    }

    @Test
    void 졸은_궁성에_있을때_왼쪽_아레_방향으로_갈_수_없다() {
        Offset offset = new Offset(-1, -1);
        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> soldier.getPathOffset(from, to, hanPalace));
    }

    @Test
    void 졸은_궁성에_있을때_오른쪽_아레_방향으로_갈_수_없다() {
        Offset offset = new Offset(1, -1);
        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> soldier.getPathOffset(from, to, hanPalace));
    }

    @Test
    void 졸은_궁성_밖에서_오른쪽_위_방향으로_갈_수_없다() {
        Offset offset = new Offset(1, 1);
        Position from = new Position(1, 8);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> soldier.getPathOffset(from, to, hanPalace));
    }

    @Test
    void 졸은_궁성_밖에서_왼쪽_위_방향으로_갈_수_없다() {
        Offset offset = new Offset(-1, 1);
        Position from = new Position(1, 8);
        Position to = offset.applyTo(from);

        assertThrows(IllegalStateException.class, () -> soldier.getPathOffset(from, to, hanPalace));
    }
}
