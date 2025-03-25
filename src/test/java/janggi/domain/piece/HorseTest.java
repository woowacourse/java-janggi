package janggi.domain.piece;

import static janggi.domain.TestFixture.BLUE_ELEPHANT;
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
import org.junit.jupiter.api.Test;

class HorseTest {

    @Test
    void 말은_선이동_한칸_대각선이동_한칸_이동가능() {
        // given
        Piece horse = RED_HORSE;

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.TWO);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = horse.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 말은_대각선_이동_불가() {
        // given
        Piece horse = RED_HORSE;

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.TWO);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = horse.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 목적지까지의_이동경로에_포함되는_좌표를_반환() {
        // given
        Piece horse = RED_HORSE;

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.THREE, Column.TWO);
        PiecePath path = new PiecePath(source, destination);

        // when
        List<Position> positions = horse.findAllRoute(path);

        // then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(positions).hasSize(1);
        softly.assertThat(positions.getFirst()).isEqualTo(new Position(Row.TWO, Column.ONE));

        softly.assertAll();
    }

    @Test
    void 말의_목적지에_같은팀이_있으면_이동불가() {
        Piece horse = RED_HORSE;
        Piece destinationPiece = RED_ELEPHANT;

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = horse.canMove(horse, destinationPiece, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 말의_이동경로에_기물이_있으면_이동불가() {
        Piece horse = RED_HORSE;
        Piece destinationPiece = BLUE_ELEPHANT;

        List<Piece> piecesOnRoute = List.of(RED_SOLDIER);

        boolean canMove = horse.canMove(horse, destinationPiece, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 말의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece horse = RED_HORSE;
        Piece destinationPiece = BLUE_ELEPHANT;

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = horse.canMove(horse, destinationPiece, piecesOnRoute);
        assertThat(canMove).isTrue();
    }
}
