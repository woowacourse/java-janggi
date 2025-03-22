package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.Empty;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceColor;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PlayingBoardTest {

    @Test
    void 출발지에_있는_피스가_목적지까지_이동() {
        InitialBoard initialBoard = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());

        Position source = new Position(Row.ONE, Column.TWO);
        Position destination = new Position(Row.THREE, Column.THREE);
        Piece horse = new Horse(PieceColor.RED);

        playingBoard.move(PieceType.HORSE, source, destination);

        Piece movedPiece = playingBoard.getPieceBy(destination);
        Piece afterPositionPiece = playingBoard.getPieceBy(source);

        assertThat(afterPositionPiece).isInstanceOf(Empty.class);
        assertThat(movedPiece.isSamePieceType(horse)).isTrue();
    }

    @Test
    void 목적지가_잘못된_이동시_예외발생() {
        InitialBoard initialBoard = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());

        Position source = new Position(Row.ONE, Column.TWO);
        Position destination = new Position(Row.THREE, Column.TWO);

        assertThatThrownBy(() -> playingBoard.move(PieceType.HORSE, source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 말은_이동경로에_기물이있으면_예외발생() {
        InitialBoard initialBoard = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());

        Position source = new Position(Row.ONE, Column.TWO);
        Position destination = new Position(Row.TWO, Column.FOUR);

        System.out.println(playingBoard.getPieceBy(new Position(Row.ONE, Column.THREE)));

        assertThatThrownBy(() -> playingBoard.move(PieceType.HORSE, source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 움직이려는_기물과_해당_위치의_기물이_일치하지_않으면_예외발생() {
        InitialBoard initialBoard = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.ONE);

        assertThatThrownBy(() -> playingBoard.move(PieceType.SOLDIER, source, destination))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 움직이려는_기물과_해당_위치의_기물이_일치하면_정상_이동() {
        InitialBoard initialBoard = InitialBoard.createBoard(BoardSetup.INNER_ELEPHANT, BoardSetup.INNER_ELEPHANT);
        PlayingBoard playingBoard = new PlayingBoard(initialBoard.getInitialBoard());

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.TWO, Column.ONE);

        assertThatCode(() -> playingBoard.move(PieceType.CHARIOT, source, destination))
                .doesNotThrowAnyException();
    }
}
