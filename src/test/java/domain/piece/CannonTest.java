package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.board.Column;
import domain.board.MovePath;
import domain.board.Position;
import domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CannonTest {
    @Test
    void 포의_목적지까지의_이동경로에_포함되는_좌표를_반환() {
        Cannon cannon = new Cannon(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.ONE, Column.FIVE);
        MovePath movePath = new MovePath(source, destination);

        List<Position> allRoute = cannon.findAllRoute(movePath);

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

    @Test
    void 포는_궁성_내부에서_대각으로_이동가능() {
        Piece piece = new Cannon(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.FOUR);
        Position destination = new Position(Row.THREE, Column.SIX);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = piece.isValidMovement(movePath);

        assertThat(canMove).isTrue();
    }

    @Test
    void 포는_궁성_외부에서_대각으로_이동불가능() {
        Piece piece = new Cannon(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.THREE);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = piece.isValidMovement(movePath);

        assertThat(canMove).isFalse();
    }
}
