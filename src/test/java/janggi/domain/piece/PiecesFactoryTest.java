package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.board.Board;
import janggi.domain.board.BoardOrder;
import janggi.domain.piece.position.Position;
import org.junit.jupiter.api.Test;

class PiecesFactoryTest {

    @Test
    void 상마상마_보드_생성_테스트() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();

        // When
        final BoardOrder hanBoardOrder = BoardOrder.ELEPHANT_HORSE_ELEPHANT_HORSE;
        final BoardOrder choBoardOrder = BoardOrder.ELEPHANT_HORSE_ELEPHANT_HORSE;
        final Board hanBoard = piecesFactory.initializeHanPieces(hanBoardOrder);
        final Board choBoard = piecesFactory.initializeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2))).
                        isEqualTo(Piece.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)))
                        .isEqualTo(Piece.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)))
                        .isEqualTo(Piece.HORSE),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2))).
                        isEqualTo(Piece.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)))
                        .isEqualTo(Piece.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)))
                        .isEqualTo(Piece.HORSE)
        );
    }

    @Test
    void 마상마상_보드_생성_테스트() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();

        // When
        final BoardOrder hanBoardOrder = BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;
        final BoardOrder choBoardOrder = BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;
        final Board hanBoard = piecesFactory.initializeHanPieces(hanBoardOrder);
        final Board choBoard = piecesFactory.initializeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)))
                        .isEqualTo(Piece.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)))
                        .isEqualTo(Piece.ELEPHANT),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)))
                        .isEqualTo(Piece.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)))
                        .isEqualTo(Piece.ELEPHANT)
        );
    }

    @Test
    void 상마마상_보드_생성_테스트() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();

        // When
        final BoardOrder hanBoardOrder = BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
        final BoardOrder choBoardOrder = BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
        final Board hanBoard = piecesFactory.initializeHanPieces(hanBoardOrder);
        final Board choBoard = piecesFactory.initializeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2))).
                        isEqualTo(Piece.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)))
                        .isEqualTo(Piece.ELEPHANT),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2))).
                        isEqualTo(Piece.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)))
                        .isEqualTo(Piece.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)))
                        .isEqualTo(Piece.ELEPHANT)
        );
    }

    @Test
    void 마상상마_보드_생성_테스트() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();

        // When
        final BoardOrder hanBoardOrder = BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
        final BoardOrder choBoardOrder = BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
        final Board hanBoard = piecesFactory.initializeHanPieces(hanBoardOrder);
        final Board choBoard = piecesFactory.initializeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2))).
                        isEqualTo(Piece.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)))
                        .isEqualTo(Piece.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)))
                        .isEqualTo(Piece.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)))
                        .isEqualTo(Piece.HORSE),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2))).
                        isEqualTo(Piece.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)))
                        .isEqualTo(Piece.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)))
                        .isEqualTo(Piece.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)))
                        .isEqualTo(Piece.HORSE)
        );
    }
}
