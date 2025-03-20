package domain.piece;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CannonTest {
    @Test
    void 포의_목적지까지의_이동경로에_포함되는_좌표를_반환() {
        Cannon cannon = new Cannon(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.ONE, Column.FIVE);

        List<Position> allRoute = cannon.findAllRoute(source, destination);

        assertAll(
                () -> assertThat(allRoute).hasSize(3),
                () -> assertThat(allRoute.get(0)).isEqualTo(new Position(Row.ONE, Column.TWO)),
                () -> assertThat(allRoute.get(1)).isEqualTo(new Position(Row.ONE, Column.THREE)),
                () -> assertThat(allRoute.get(2)).isEqualTo(new Position(Row.ONE, Column.FOUR))
        );
    }


    @Test
    void 포의_목적지에_같은팀이_있으면_이동불가() {
        Piece piece = new Cannon(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_없으면_이동불가() {
        Piece piece = new Cannon(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        List<Piece> piecesOnRoute = List.of();

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_두개면_이동불가() {
        Piece piece = new Cannon(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        List<Piece> piecesOnRoute = List.of(new Elephant(PieceColor.BLUE), new Elephant(PieceColor.RED));

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_하나고_목적지가_같은팀이_아니면_이동가능() {
        Piece piece = new Cannon(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);
        List<Piece> piecesOnRoute = List.of(new Elephant(PieceColor.BLUE));

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }

    @Test
    void 포의_이동경로에_포가_있으면_기물이_하나여도_이동불가() {
        Piece piece = new Cannon(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);
        List<Piece> piecesOnRoute = List.of(new Cannon(PieceColor.BLUE));

        boolean canMove = piece.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_목적지가_포라면_이동불가() {
        Piece piece = new Cannon(PieceColor.RED);
        Piece cannon = new Cannon(PieceColor.BLUE);
        List<Piece> piecesOnRoute = List.of(new Elephant(PieceColor.BLUE));

        boolean canMove = piece.canMove(cannon, piecesOnRoute);
        assertThat(canMove).isFalse();
    }
}