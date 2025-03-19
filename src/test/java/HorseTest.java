import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest {

    @Test
    void 말은_선이동_한칸_대각선이동_한칸_이동가능() {
        Horse horse = new Horse(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.TWO);
        boolean canMove = horse.isValidDestination(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 말은_대각선_이동_불가() {
        Horse horse = new Horse(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.TWO);
        boolean canMove = horse.isValidDestination(source, destination);

        assertThat(canMove).isFalse();
    }

    @Test
    void 목적지까지의_이동경로에_포함되는_좌표를_반환() {
        Piece horse = new Horse(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.TWO);
        List<Position> positions = horse.findAllRoute(source, destination);

        assertThat(positions).hasSize(1);
        assertThat(positions.getFirst()).isEqualTo(new Position(Row.TWO, Column.ONE));
    }

    @Test
    void 말의_목적지에_같은팀이_있으면_이동불가() {
        Piece horse = new Horse(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);

        boolean canMove = horse.canMove(0, elephant);
        assertThat(canMove).isFalse();
    }

    @Test
    void 말의_이동경로에_기물이_있으면_이동불가() {
        Piece horse = new Horse(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        int pieceCount = 1;
        boolean canMove = horse.canMove(pieceCount, elephant);
        assertThat(canMove).isFalse();
    }

    @Test
    void 말의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece horse = new Horse(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        int pieceCount = 0;
        boolean canMove = horse.canMove(pieceCount, elephant);
        assertThat(canMove).isTrue();
    }
}