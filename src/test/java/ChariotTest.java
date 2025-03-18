import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChariotTest {
    @Test
    void 차는_가로로_움직일_수_있다() {
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.TEN, Column.ONE);
        boolean canMove = chariot.canMove(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 차는_세로로_움직일_수_있다() {
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.THREE);
        boolean canMove = chariot.canMove(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 차는_가로_세로가_아닌_위치로_움직일_수_없다() {
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.TEN, Column.TWO);
        boolean canMove = chariot.canMove(source, destination);

        assertThat(canMove).isFalse();
    }
}