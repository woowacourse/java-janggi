package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.position.Position;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardFactoryTest {

    @Test
    void 상마상마_보드_생성_테스트() {
        // Given
        BoardFactory boardFactory = new BoardFactory();

        // When
        BoardOrder hanBoardOrder = BoardOrder.ELEPHANT_HORSE_ELEPHANT_HORSE;
        BoardOrder choBoardOrder = BoardOrder.ELEPHANT_HORSE_ELEPHANT_HORSE;
        Board board = boardFactory.makeBoard(hanBoardOrder, choBoardOrder);

        // Then
        Map<Position, Piece> pieces = board.getPieces();
        Assertions.assertAll(
                () -> assertThat(pieces.get(new Position(1, 2))).isInstanceOf(Elephant.class),
                () -> assertThat(pieces.get(new Position(1, 3))).isInstanceOf(Horse.class),
                () -> assertThat(pieces.get(new Position(1, 7))).isInstanceOf(Elephant.class),
                () -> assertThat(pieces.get(new Position(1, 8))).isInstanceOf(Horse.class)
        );
    }

    @Test
    void 마상마상_보드_생성_테스트() {
        // Given
        BoardFactory boardFactory = new BoardFactory();

        // When
        BoardOrder hanBoardOrder = BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;
        BoardOrder choBoardOrder = BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;
        Board board = boardFactory.makeBoard(hanBoardOrder, choBoardOrder);

        // Then
        Map<Position, Piece> pieces = board.getPieces();
        Assertions.assertAll(
                () -> assertThat(pieces.get(new Position(1, 2))).isInstanceOf(Horse.class),
                () -> assertThat(pieces.get(new Position(1, 3))).isInstanceOf(Elephant.class),
                () -> assertThat(pieces.get(new Position(1, 7))).isInstanceOf(Horse.class),
                () -> assertThat(pieces.get(new Position(1, 8))).isInstanceOf(Elephant.class)
        );
    }

    @Test
    void 상마마상_보드_생성_테스트() {
        // Given
        BoardFactory boardFactory = new BoardFactory();

        // When
        BoardOrder hanBoardOrder = BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
        BoardOrder choBoardOrder = BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
        Board board = boardFactory.makeBoard(hanBoardOrder, choBoardOrder);

        // Then
        Map<Position, Piece> pieces = board.getPieces();
        Assertions.assertAll(
                () -> assertThat(pieces.get(new Position(1, 2))).isInstanceOf(Elephant.class),
                () -> assertThat(pieces.get(new Position(1, 3))).isInstanceOf(Horse.class),
                () -> assertThat(pieces.get(new Position(1, 7))).isInstanceOf(Horse.class),
                () -> assertThat(pieces.get(new Position(1, 8))).isInstanceOf(Elephant.class)
        );
    }

    @Test
    void 마상상마_보드_생성_테스트() {
        // Given
        BoardFactory boardFactory = new BoardFactory();

        // When
        BoardOrder hanBoardOrder = BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
        BoardOrder choBoardOrder = BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
        Board board = boardFactory.makeBoard(hanBoardOrder, choBoardOrder);

        // Then
        Map<Position, Piece> pieces = board.getPieces();
        Assertions.assertAll(
                () -> assertThat(pieces.get(new Position(1, 2))).isInstanceOf(Horse.class),
                () -> assertThat(pieces.get(new Position(1, 3))).isInstanceOf(Elephant.class),
                () -> assertThat(pieces.get(new Position(1, 7))).isInstanceOf(Elephant.class),
                () -> assertThat(pieces.get(new Position(1, 8))).isInstanceOf(Horse.class)
        );
    }
}
