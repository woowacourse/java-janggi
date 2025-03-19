package domain.piece;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChariotTest {
    @Test
    void 차는_가로로_움직일_수_있다() {
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.TEN, Column.ONE);
        boolean canMove = chariot.isValidDestination(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 차는_세로로_움직일_수_있다() {
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.THREE);
        boolean canMove = chariot.isValidDestination(source, destination);

        assertThat(canMove).isTrue();
    }

    @Test
    void 차는_가로_세로가_아닌_위치로_움직일_수_없다() {
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.TEN, Column.TWO);
        boolean canMove = chariot.isValidDestination(source, destination);

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
    void 차의_목적지까지의_이동경로에_포함되는_좌표를_반환() {
        Chariot chariot = new Chariot(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.ONE, Column.FIVE);

        List<Position> allRoute = chariot.findAllRoute(source, destination);

        assertAll(
                () -> assertThat(allRoute).hasSize(3),
                () -> assertThat(allRoute.get(0)).isEqualTo(new Position(Row.ONE, Column.TWO)),
                () -> assertThat(allRoute.get(1)).isEqualTo(new Position(Row.ONE, Column.THREE)),
                () -> assertThat(allRoute.get(2)).isEqualTo(new Position(Row.ONE, Column.FOUR))
        );
    }

    @Test
    void 차의_목적지에_같은팀이_있으면_이동불가() {
        Piece chariot = new Chariot(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);
        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = chariot.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 차의_이동경로에_기물이_있으면_이동불가() {
        Piece chariot = new Chariot(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);
        List<Piece> piecesOnRoute = List.of(chariot);

        boolean canMove = chariot.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 차의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece chariot = new Chariot(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);
        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = chariot.canMove(elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }
}