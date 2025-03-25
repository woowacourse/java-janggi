package janggi.domain.piece;

import static janggi.domain.TestFixture.BLUE_HORSE;
import static janggi.domain.TestFixture.RED_ELEPHANT;
import static janggi.domain.TestFixture.RED_HORSE;
import static janggi.domain.TestFixture.RED_SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Column;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElephantTest {

    @Test
    void 상은_선이동_힌칸_대각선이동_두칸_이동가능() {
        // given
        Piece elephant = RED_ELEPHANT;

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
        Piece elephant = RED_ELEPHANT;

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
        Piece elephant = RED_ELEPHANT;

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
        Piece elephant = RED_ELEPHANT;
        Piece destinationPiece = RED_HORSE;

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = elephant.canMove(elephant, destinationPiece, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 상의_이동경로에_기물이_있으면_이동불가() {
        Piece elephant = RED_ELEPHANT;
        Piece destinationPiece = BLUE_HORSE;

        List<Piece> piecesOnRoute = List.of(RED_SOLDIER);

        boolean canMove = elephant.canMove(elephant, destinationPiece, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 상의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece elephant = RED_ELEPHANT;
        Piece destinationPiece = BLUE_HORSE;
        List<Piece> piecesOnRoute = List.of();

        boolean canMove = elephant.canMove(elephant, destinationPiece, piecesOnRoute);
        assertThat(canMove).isTrue();
    }

    @DisplayName("상 이동경로의 좌표들을 찾을 수 있다")
    @Test
    void Elephant_findAllRoute_toDestination() {
        // given
        Piece elephant = RED_ELEPHANT;
        PiecePath path = new PiecePath(Position.of(1, 1), Position.of(4, 3));

        // when
        List<Position> allRoute = elephant.findAllRoute(path);

        // then
        assertThat(allRoute).hasSize(2);
        assertThat(allRoute.get(0)).isEqualTo(Position.of(2, 1));
        assertThat(allRoute.get(1)).isEqualTo(Position.of(3, 2));
    }
}
