import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SoldierTest {
    @Test
    void 졸병이_앞으로_이동_가능() {
        Soldier soldier = new Soldier(PieceColor.RED);

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FIVE, Column.ONE);
        boolean canMove = soldier.isValidDestination(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 졸병이_옆으로_이동_가능() {
        Soldier soldier = new Soldier(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.TWO);

        boolean canMove = soldier.isValidDestination(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 쫄병이_뒤로_이동_불가능() {
        Soldier soldier = new Soldier(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.THREE, Column.ONE);

        boolean canMove = soldier.isValidDestination(source, destination);

        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_목적지에_같은팀이_있으면_이동불가() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);

        boolean canMove = piece.canMove(0, elephant);
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_이동경로에_기물이_있으면_이동불가() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        int pieceCount = 1;
        boolean canMove = piece.canMove(pieceCount, elephant);
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        int pieceCount = 0;
        boolean canMove = piece.canMove(pieceCount, elephant);
        assertThat(canMove).isTrue();
    }

}