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

public class ThreatAnalyzerTest {

    private final ThreatAnalyzer threatAnalyzer = new ThreatAnalyzer();

    @Test
    @DisplayName("상대 차가 장군의 위치로 이동할 수 있으면 장군 상태다.")
    void isInCheck_When_EnemyChariotCanAttackGeneral() {
        Board board = new Board(Map.of(
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT)
        ));

        boolean result = threatAnalyzer.isInCheck(board, Camp.HAN);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("장군까지 가는 경로가 막혀 있으면 장군 상태가 아니다.")
    void notInCheck_When_PathToGeneralIsBlocked() {
        Board board = new Board(Map.of(
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(5, 4), new Piece(Camp.HAN, PieceType.SOLDIER),
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT)
        ));

        boolean result = threatAnalyzer.isInCheck(board, Camp.HAN);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상대 포가 목을 두고 장군을 공격할 수 있으면 장군 상태다.")
    void isInCheck_When_EnemyCannonHasNeck() {
        Board board = new Board(Map.of(
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(5, 4), new Piece(Camp.HAN, PieceType.SOLDIER),
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CANNON)
        ));

        boolean result = threatAnalyzer.isInCheck(board, Camp.HAN);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("상대 기물이 장군의 위치로 이동할 수 없으면 장군 상태가 아니다.")
    void notInCheck_When_EnemyPieceCanNotAttackGeneral() {
        Board board = new Board(Map.of(
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(4, 5), new Piece(Camp.CHO, PieceType.SOLDIER)
        ));

        boolean result = threatAnalyzer.isInCheck(board, Camp.HAN);

        assertThat(result).isFalse();
    }
}
