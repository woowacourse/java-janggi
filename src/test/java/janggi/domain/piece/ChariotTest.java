package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Column;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChariotTest {
    @Test
    void 차는_가로로_움직일_수_있다() {
        // given
        Chariot chariot = new Chariot(PieceColor.RED);

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.ZERO, Column.ONE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = chariot.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 차는_세로로_움직일_수_있다() {
        // given
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.THREE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = chariot.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 차는_가로_세로가_아닌_위치로_움직일_수_없다() {
        // given
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.ZERO, Column.TWO);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = chariot.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 차의_목적지까지의_이동경로에_포함되는_좌표를_반환() {
        // given
        Chariot chariot = new Chariot(PieceColor.RED);

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.ONE, Column.FIVE);
        PiecePath path = new PiecePath(source, destination);

        // when
        List<Position> allRoute = chariot.findAllRoute(path);

        // then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(allRoute).hasSize(3);
        softly.assertThat(allRoute.get(0)).isEqualTo(new Position(Row.ONE, Column.TWO));
        softly.assertThat(allRoute.get(1)).isEqualTo(new Position(Row.ONE, Column.THREE));
        softly.assertThat(allRoute.get(2)).isEqualTo(new Position(Row.ONE, Column.FOUR));

        softly.assertAll();
    }

    @Test
    void 차의_목적지에_같은팀이_있으면_이동불가() {
        Piece chariot = new Chariot(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);
        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = chariot.canMove(chariot, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 차의_이동경로에_기물이_있으면_이동불가() {
        Piece chariot = new Chariot(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);
        List<Piece> piecesOnRoute = List.of(chariot);

        boolean canMove = chariot.canMove(chariot, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 차의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece chariot = new Chariot(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);
        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = chariot.canMove(chariot, elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }
}
