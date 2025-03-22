package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Column;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SoldierTest {
    @Test
    void 졸병이_앞으로_이동_가능() {
        // given
        Soldier soldier = new Soldier(PieceColor.RED);

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FIVE, Column.ONE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 졸병이_옆으로_이동_가능() {
        // given
        Soldier soldier = new Soldier(PieceColor.RED);

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.FOUR, Column.TWO);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isTrue();
    }

    @Test
    void 쫄병이_뒤로_이동_불가능() {
        // given
        Soldier soldier = new Soldier(PieceColor.RED);

        Position source = new Position(Row.FOUR, Column.ONE);
        Position destination = new Position(Row.THREE, Column.ONE);
        PiecePath path = new PiecePath(source, destination);

        // when
        boolean canMove = soldier.isValidMovement(path);

        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_목적지에_같은팀이_있으면_이동불가() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.RED);

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_이동경로에_기물이_있으면_이동불가() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        List<Piece> piecesOnRoute = List.of(elephant);

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 졸병의_이동경로에_기물이_없고_목적지가_같은팀이_아니면_이동가능() {
        Piece piece = new Soldier(PieceColor.RED);
        Piece elephant = new Elephant(PieceColor.BLUE);

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }

}
