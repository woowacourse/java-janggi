package domain.game;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LegalMoveAnalyzerTest {

    private final LegalMoveAnalyzer legalMoveAnalyzer = new LegalMoveAnalyzer();

    @Test
    @DisplayName("이동 후 장군이 공격받으면 합법 수 검증에 실패한다.")
    void throwException_When_MoveAttackOwnGeneral() {
        Board board = new Board(Map.of(
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(5, 4), new Piece(Camp.HAN, PieceType.SOLDIER),
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT)
        ));

        assertThatThrownBy(() -> legalMoveAnalyzer.validate(board, new Position(5, 4), new Position(4, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 자신의 장군이 공격받는 수는 둘 수 없습니다.");
    }
}
