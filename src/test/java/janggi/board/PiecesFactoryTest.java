package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.direction.PieceType;
import janggi.piece.PiecesFactory;
import janggi.piece.board.Board;
import janggi.piece.board.BoardOrder;
import janggi.position.Position;
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
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)).getPieceType()).
                        isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)).getPieceType())
                        .isEqualTo(PieceType.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)).getPieceType())
                        .isEqualTo(PieceType.HORSE),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)).getPieceType()).
                        isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)).getPieceType())
                        .isEqualTo(PieceType.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)).getPieceType())
                        .isEqualTo(PieceType.HORSE)
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
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)).getPieceType()).
                        isEqualTo(PieceType.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)).getPieceType())
                        .isEqualTo(PieceType.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)).getPieceType()).
                        isEqualTo(PieceType.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)).getPieceType())
                        .isEqualTo(PieceType.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT)
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
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)).getPieceType()).
                        isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)).getPieceType())
                        .isEqualTo(PieceType.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)).getPieceType())
                        .isEqualTo(PieceType.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)).getPieceType()).
                        isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)).getPieceType())
                        .isEqualTo(PieceType.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)).getPieceType())
                        .isEqualTo(PieceType.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT)
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
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)).getPieceType()).
                        isEqualTo(PieceType.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)).getPieceType())
                        .isEqualTo(PieceType.HORSE),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)).getPieceType()).
                        isEqualTo(PieceType.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)).getPieceType())
                        .isEqualTo(PieceType.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)).getPieceType())
                        .isEqualTo(PieceType.HORSE)
        );
    }
}
