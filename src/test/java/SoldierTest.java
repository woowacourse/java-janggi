import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SoldierTest {
    @Test
    void 졸병이_앞으로_이동_가능() {
        Soldier soldier = new Soldier(PieceColor.RED);

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FIVE, Column.ONE);
        boolean canMove = soldier.canMove(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 졸병이_옆으로_이동_가능() {
        Soldier soldier = new Soldier(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.TWO);

        boolean canMove = soldier.canMove(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 쫄병이_뒤로_이동_불가능() {
        Soldier soldier = new Soldier(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.THREE, Column.ONE);

        boolean canMove = soldier.canMove(source, destination);

        assertThat(canMove).isFalse();
    }

}