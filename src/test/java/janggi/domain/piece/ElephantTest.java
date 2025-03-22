package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Column;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    void 상은_선이동_힌칸_대각선이동_두칸_이동가능() {
        // given
        Elephant elephant = new Elephant(PieceColor.RED);

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.FOUR);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = elephant.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 상은_대각선_이동_불가() {
        // given
        Elephant elephant = new Elephant(PieceColor.RED);

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.TWO);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = elephant.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 상의_목적지까지의_이동경로에_포함되는_좌표를_반환() {
        Elephant elephant = new Elephant(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.THREE);
        PiecePath path = new PiecePath(source, destination);

        // when
        List<Position> allRoute = elephant.findAllRoute(path);

        // then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(allRoute).hasSize(2);
        softly.assertThat(allRoute.get(0)).isEqualTo(new Position(Row.TWO, Column.ONE));
        softly.assertThat(allRoute.get(1)).isEqualTo(new Position(Row.THREE, Column.TWO));

        softly.assertAll();
    }


    @Test
    void 상의_목적지에_같은팀이_있으면_이동불가() {
        Piece piece = new Elephant(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 상의_이동경로에_기물이_있으면_이동불가() {
        Piece piece = new Elephant(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        List<Piece> piecesOnRoute = List.of(elephant);

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 상의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece piece = new Elephant(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);
        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }
}
