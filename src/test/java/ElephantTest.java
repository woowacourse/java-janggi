import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {

    @Test
    void 상은_선이동_힌칸_대각선이동_두칸_이동가능() {
        Elephant elephant = new Elephant(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.FOUR);
        boolean canMove = elephant.isValidDestination(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 상은_대각선_이동_불가() {
        Elephant elephant = new Elephant(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.TWO);
        boolean canMove = elephant.isValidDestination(source, destination);

        assertThat(canMove).isFalse();
    }

    @Test
    void 상의_목적지까지의_이동경로에_포함되는_좌표를_반환() {
        Elephant elephant = new Elephant(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.THREE);

        List<Position> allRoute = elephant.findAllRoute(source, destination);

        assertThat(allRoute).hasSize(2);
        assertThat(allRoute.get(0)).isEqualTo(new Position(Row.TWO, Column.ONE));
        assertThat(allRoute.get(1)).isEqualTo(new Position(Row.THREE, Column.TWO));
    }


    @Test
    void 상의_목적지에_같은팀이_있으면_이동불가() {
        Piece piece = new Elephant(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 상의_이동경로에_기물이_있으면_이동불가() {
        Piece piece = new Elephant(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        List<Piece> piecesOnRoute = List.of(elephant);

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 상의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece piece = new Elephant(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);
        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }
}