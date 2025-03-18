import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    void 말은_선이동_한칸_대각선이동_한칸_이동가능() {
        Horse horse = new Horse(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.TWO);
        boolean canMove = horse.canMove(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 말은_대각선_이동_불가() {
        Horse horse = new Horse(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.TWO);
        boolean canMove = horse.canMove(source, destination);

        assertThat(canMove).isFalse();
    }

}