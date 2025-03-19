package domain.board;

import domain.piece.*;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(afterPositionPiece).isInstanceOf(EmptyPiece.class);
        assertThat(movedPiece).isEqualTo(horse);
    }
}
