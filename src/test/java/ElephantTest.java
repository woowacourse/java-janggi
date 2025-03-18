import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    @Test
    void 상은_선이동_힌칸_대각선이동_두칸_이동가능() {
        Elephant elephant = new Elephant(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.FOUR);
        boolean canMove = elephant.canMove(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 상은_대각선_이동_불가() {
        Elephant elephant = new Elephant(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.TWO);
        boolean canMove = elephant.canMove(source, destination);

        assertThat(canMove).isFalse();
    }

}