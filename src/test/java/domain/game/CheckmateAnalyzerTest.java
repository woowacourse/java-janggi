package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckmateAnalyzerTest {
    @Test
    @DisplayName("장군 상태가 아니면 외통수가 아니다.")
    void returnFalse_When_NotInCheck() {
        Board board = new Board(Map.of(
                new Position(5, 9), new Piece(Camp.CHO, PieceType.GENERAL),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(1, 5), new Piece(Camp.CHO, PieceType.CHARIOT)
        ));

        assertThat(CheckmateAnalyzer.isCheckmate(board, Camp.HAN)).isFalse();
    }

    @Test
    @DisplayName("장군 상태라도 장군이 피할 수 있으면 외통수가 아니다.")
    void returnFalse_When_GeneralCanEscape() {
        Board board = new Board(Map.of(
                new Position(5, 9), new Piece(Camp.CHO, PieceType.GENERAL),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT)
        ));

        assertThat(CheckmateAnalyzer.isCheckmate(board, Camp.HAN)).isFalse();
    }

    @Test
    @DisplayName("장군 상태라도 다른 기물이 공격 기물을 잡을 수 있으면 외통수가 아니다.")
    void returnFalse_When_AllyCanCaptureAttacker() {
        Board board = new Board(Map.of(
                new Position(5, 9), new Piece(Camp.CHO, PieceType.GENERAL),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(4, 1), new Piece(Camp.HAN, PieceType.GUARD),
                new Position(4, 2), new Piece(Camp.CHO, PieceType.SOLDIER)
        ));

        assertThat(CheckmateAnalyzer.isCheckmate(board, Camp.HAN)).isFalse();
    }

    @Test
    @DisplayName("장군 상태라도 다른 기물이 공격 경로를 막을 수 있으면 외통수가 아니다.")
    void returnFalse_When_BlockOtherPieces() {
        Board board = new Board(Map.of(
                new Position(5, 9), new Piece(Camp.CHO, PieceType.GENERAL),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(4, 3), new Piece(Camp.HAN, PieceType.GUARD),
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT)
        ));

        assertThat(CheckmateAnalyzer.isCheckmate(board, Camp.HAN)).isFalse();
    }

    @Test
    @DisplayName("장군을 해소할 합법 수가 없으면 외통수다.")
    void returnTrue_When_NoLegalMove() {
        Board board = new Board(Map.of(
                new Position(5, 9), new Piece(Camp.CHO, PieceType.GENERAL),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(4, 4), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(6, 4), new Piece(Camp.CHO, PieceType.CHARIOT)
        ));

        assertThat(CheckmateAnalyzer.isCheckmate(board, Camp.HAN)).isTrue();
    }
}
