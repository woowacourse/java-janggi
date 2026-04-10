package janggi.domain.board;

import janggi.domain.Arrangement;
import janggi.domain.MoveResult;
import janggi.domain.PalaceTopology;
import janggi.domain.Position;
import janggi.domain.ScoreStatus;
import janggi.domain.Side;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Ma;
import janggi.domain.piece.None;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;
import janggi.factory.BoardFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {
    private final PalaceTopology palaceTopology = PalaceTopology.from();

    @Test
    void 자기_진영의_기물을_움직이면_정상_작동한다() {
        Board board = new Board(
                BoardFactory.createInitialBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG),
                BoardFactory.createInitialScoresBySide()
        );

        MoveResult moveResult = board.move(new Position(1, 1), new Position(2, 1), Side.HAN);

        assertThat(moveResult.capturedPieceType()).isEqualTo(PieceType.NONE);
    }

    @Test
    void 다른_진영의_기물을_움직이면_예외_처리한다() {
        Board board = new Board(
                BoardFactory.createInitialBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG),
                BoardFactory.createInitialScoresBySide());

        assertThatThrownBy(() -> board.move(new Position(1, 1), new Position(2, 1), Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자기 진영의 기물만 움직일 수 있습니다.");
    }

    @Test
    void 기물을_잡지_못하면_점수는_변하지_않는다() {
        Board board = createBoardWithPieces(Map.of(
                new Position(1, 1), new Cha(Side.HAN, palaceTopology)
        ));

        ScoreStatus before = board.getScoreStatus();
        MoveResult moveResult = board.move(new Position(1, 1), new Position(1, 2), Side.HAN);
        ScoreStatus after = board.getScoreStatus();

        assertThat(moveResult.capturedPieceType()).isEqualTo(PieceType.NONE);
        assertThat(after).isEqualTo(before);
    }

    @ParameterizedTest
    @CsvSource({
            "PAWN, 70.0",
            "SA, 69.0",
            "SANG, 69.0",
            "MA, 67.0",
            "PO, 65.0",
            "CHA, 59.0"
    })
    void 한이_초의_기물을_잡았을_경우_올바르게_점수가_계산된다(PieceType capturedPieceType, double expectedChoScore) {
        Board board = createBoardWithPieces(Map.of(
                new Position(1, 1), new Cha(Side.HAN, palaceTopology),
                new Position(1, 3), createPieceByType(capturedPieceType, Side.CHO)
        ));

        MoveResult moveResult = board.move(new Position(1, 1), new Position(1, 3), Side.HAN);

        ScoreStatus scoreStatus = board.getScoreStatus();
        assertThat(moveResult.capturedPieceType()).isEqualTo(capturedPieceType);
        assertThat(scoreStatus.choScore()).isEqualTo(expectedChoScore);
    }

    @ParameterizedTest
    @CsvSource({
            "PAWN, 71.5",
            "SA, 70.5",
            "SANG, 70.5",
            "MA, 68.5",
            "PO, 66.5",
            "CHA, 60.5"
    })
    void 초가_한의_기물을_잡았을_경우_올바르게_점수가_계산된다(PieceType capturedPieceType, double expectedHanScore) {
        Board board = createBoardWithPieces(Map.of(
                new Position(10, 1), new Cha(Side.CHO, palaceTopology),
                new Position(8, 1), createPieceByType(capturedPieceType, Side.HAN)
        ));

        MoveResult moveResult = board.move(new Position(10, 1), new Position(8, 1), Side.CHO);

        ScoreStatus scoreStatus = board.getScoreStatus();
        assertThat(moveResult.capturedPieceType()).isEqualTo(capturedPieceType);
        assertThat(scoreStatus.hanScore()).isEqualTo(expectedHanScore);
    }

    @Test
    void 궁을_잡아도_점수는_변하지_않고_궁_포획_결과를_반환한다() {
        Board board = createBoardWithPieces(Map.of(
                new Position(10, 5), new Cha(Side.CHO, palaceTopology),
                new Position(2, 5), new Gung(Side.HAN, palaceTopology)
        ));

        MoveResult moveResult = board.move(new Position(10, 5), new Position(2, 5), Side.CHO);

        ScoreStatus scoreStatus = board.getScoreStatus();
        assertThat(scoreStatus.choScore()).isEqualTo(72.0);
        assertThat(scoreStatus.hanScore()).isEqualTo(73.5);
        assertThat(moveResult.isCapturedGung()).isTrue();
    }

    private Board createBoardWithPieces(Map<Position, Piece> pieces) {
        Map<Position, Piece> boardMap = new HashMap<>();
        for (int row = Board.BOARD_START_ROWS; row <= Board.BOARD_END_ROWS; row++) {
            for (int col = Board.BOARD_START_COLS; col <= Board.BOARD_END_COLS; col++) {
                boardMap.put(new Position(row, col), new None());
            }
        }
        boardMap.putAll(pieces);
        return new Board(boardMap, BoardFactory.createInitialScoresBySide());
    }

    private Piece createPieceByType(PieceType pieceType, Side side) {
        return switch (pieceType) {
            case CHA -> new Cha(side, palaceTopology);
            case GUNG -> new Gung(side, palaceTopology);
            case MA -> new Ma(side, palaceTopology);
            case PAWN -> new Pawn(side, palaceTopology);
            case PO -> new Po(side, palaceTopology);
            case SA -> new Sa(side, palaceTopology);
            case SANG -> new Sang(side, palaceTopology);
            case NONE -> new None();
        };
    }
}