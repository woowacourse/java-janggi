package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.direction.PieceMovement;
import janggi.piece.Board;
import janggi.piece.PiecesFactory;
import janggi.position.Position;
import org.junit.jupiter.api.Test;

class MoveRulesFactoryTest {

    @Test
    void 상마상마_보드_생성_테스트() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();

        // When
        final BoardOrder hanBoardOrder = BoardOrder.ELEPHANT_HORSE_ELEPHANT_HORSE;
        final BoardOrder choBoardOrder = BoardOrder.ELEPHANT_HORSE_ELEPHANT_HORSE;
        final Board hanBoard = piecesFactory.makeHanPieces(hanBoardOrder);
        final Board choBoard = piecesFactory.makeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE)
        );
    }

    @Test
    void 마상마상_보드_생성_테스트() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();

        // When
        final BoardOrder hanBoardOrder = BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;
        final BoardOrder choBoardOrder = BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;
        final Board hanBoard = piecesFactory.makeHanPieces(hanBoardOrder);
        final Board choBoard = piecesFactory.makeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT)
        );
    }

    @Test
    void 상마마상_보드_생성_테스트() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();

        // When
        final BoardOrder hanBoardOrder = BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
        final BoardOrder choBoardOrder = BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
        final Board hanBoard = piecesFactory.makeHanPieces(hanBoardOrder);
        final Board choBoard = piecesFactory.makeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT)
        );
    }

    @Test
    void 마상상마_보드_생성_테스트() {
        // Given
        final PiecesFactory piecesFactory = new PiecesFactory();

        // When
        final BoardOrder hanBoardOrder = BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
        final BoardOrder choBoardOrder = BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
        final Board hanBoard = piecesFactory.makeHanPieces(hanBoardOrder);
        final Board choBoard = piecesFactory.makeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanBoard.findPieceByPosition(new Position(1, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),

                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choBoard.findPieceByPosition(new Position(10, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE)
        );
    }
}
