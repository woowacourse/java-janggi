package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("초기화 시 한나라와 초나라의 기물이 정해진 위치에 모두 배치된다")
    @Test
    void initialize_PlacesAllPiecesCorrectly() {
        Board board = Board.initialize();

        assertThat(board.isPieceExist(new Position(1, 4))).isTrue();
        assertThat(board.isPieceExist(new Position(8, 4))).isTrue();
    }

    @DisplayName("제공된 목적지 목록에 타겟 좌표가 포함되어 있지 않으면 이동할 수 없다")
    @Test
    void movePiece_TargetNotInDestinations_ThrowsException() {
        Board board = Board.initialize();
        Position selected = new Position(0, 0);
        Position invalidTarget = new Position(5, 5);
        Destinations allowedDestinations = Destinations.of(List.of(new Position(0, 1), new Position(1, 0)));

        assertThatThrownBy(() -> board.movePiece(selected, invalidTarget, allowedDestinations))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 표시된 이동 가능 좌표를 선택해주세요.");
    }

    @DisplayName("정상적인 타겟 좌표가 주어지면 기물이 해당 위치로 이동한다")
    @Test
    void movePiece_ValidTarget_MovesPieceAndUpdatesState() {
        Board board = Board.initialize();
        Position selected = new Position(0, 0);
        Position target = new Position(1, 0);
        Destinations allowedDestinations = Destinations.of(List.of(target));

        board.movePiece(selected, target, allowedDestinations);

        assertThat(board.isPieceExist(selected)).isFalse();
        assertThat(board.isPieceExist(target)).isTrue();
    }

    @DisplayName("두 진영의 궁(PALACE)이 모두 보드 위에 존재하면 참을 반환한다")
    @Test
    void isBothPalaceExist_BothPalacesAlive_ReturnsTrue() {
        Board board = Board.initialize();

        assertThat(board.isBothPalaceExist()).isTrue();
    }

    @DisplayName("궁이 하나라도 잡혀 보드에 없으면 거짓을 반환한다")
    @Test
    void isBothPalaceExist_OnePalaceMissing_ReturnsFalse() {
        Map<Position, Piece> customState = new HashMap<>();
        customState.put(new Position(0, 4), new Piece(Side.HAN, PieceType.PALACE, "0"));
        Board board = new Board(customState);

        assertThat(board.isBothPalaceExist()).isFalse();
    }
}
