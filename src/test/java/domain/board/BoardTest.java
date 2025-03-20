package domain.board;

import domain.piece.*;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardTest {

    @Test
    void 출발지에_있는_피스가_목적지까지_이동() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();

        Position source = new Position(Row.ONE, Column.TWO);
        Position destination = new Position(Row.THREE, Column.THREE);
        Piece horse = new Horse(PieceColor.RED);

        board.move(horse, source, destination);

        Piece movedPiece = board.getPieceBy(destination);
        Piece afterPositionPiece = board.getPieceBy(source);

        assertThat(afterPositionPiece).isInstanceOf(Empty.class);
        assertThat(movedPiece.isSamePiece(horse)).isTrue();
    }

    @Test
    void 목적지가_잘못된_이동시_예외발생() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();

        Position source = new Position(Row.ONE, Column.TWO);
        Position destination = new Position(Row.THREE, Column.TWO);
        Piece horse = new Horse(PieceColor.RED);

        assertThatThrownBy(() -> board.move(horse, source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 말은_이동경로에_기물이있으면_예외발생() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();

        Position source = new Position(Row.ONE, Column.TWO);
        Position destination = new Position(Row.TWO, Column.FOUR);
        Piece horse = new Horse(PieceColor.RED);

        System.out.println(board.getPieceBy(new Position(Row.ONE, Column.THREE)));

        assertThatThrownBy(() -> board.move(horse, source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 움직이려는_기물과_해당_위치의_기물이_일치하지_않으면_예외발생() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.ONE);
        Piece soldier = new Soldier(PieceColor.RED);

        assertThatThrownBy(() -> board.move(soldier, source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 움직이려는_기물과_해당_위치의_기물이_일치하면_정상_이동() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.ONE);
        Piece Chariot = new Chariot(PieceColor.RED);

        assertThatCode(() -> board.move(Chariot, source, destination))
                .doesNotThrowAnyException();
    }
}
