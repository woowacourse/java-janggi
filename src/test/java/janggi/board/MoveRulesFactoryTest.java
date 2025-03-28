package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.direction.PieceMovement;
import janggi.piece.Pieces;
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
        final Pieces hanPieces = piecesFactory.makeHanPieces(hanBoardOrder);
        final Pieces choPieces = piecesFactory.makeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),

                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 8)).getPieceMovement())
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
        final Pieces hanPieces = piecesFactory.makeHanPieces(hanBoardOrder);
        final Pieces choPieces = piecesFactory.makeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),

                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 8)).getPieceMovement())
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
        final Pieces hanPieces = piecesFactory.makeHanPieces(hanBoardOrder);
        final Pieces choPieces = piecesFactory.makeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),

                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 8)).getPieceMovement())
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
        final Pieces hanPieces = piecesFactory.makeHanPieces(hanBoardOrder);
        final Pieces choPieces = piecesFactory.makeChoPieces(choBoardOrder);

        // Then
        assertAll(
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.HORSE),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(hanPieces.findPieceByPosition(new Position(1, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE),

                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 2)).getPieceMovement()).
                        isEqualTo(PieceMovement.HORSE),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 3)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 7)).getPieceMovement())
                        .isEqualTo(PieceMovement.ELEPHANT),
                () -> assertThat(choPieces.findPieceByPosition(new Position(10, 8)).getPieceMovement())
                        .isEqualTo(PieceMovement.HORSE)
        );
    }
}
