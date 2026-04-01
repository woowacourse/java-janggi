package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceDTO;
import janggi.domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = Board.initialize();
    }

    @Test
    @DisplayName("보드를 초기화시, 32개의 기물이 배치된다.")
    void 보드_초기화_개수_테스트() {
        Map<Position, Piece> piecePosition = board.getPiecePosition();
        assertThat(piecePosition).hasSize(32);
    }

    @Test
    @DisplayName("보드 초기화 시, 특정 위치에 올바른 기물이 배치된다.")
    void 보드_초기화_위치_테스트() {
        Map<Position, Piece> piecePosition = board.getPiecePosition();

        // (0, 0) 위치에 한나라 차(Chariot)가 있는지 확인
        Piece piece = piecePosition.get(new Position(0, 0));

        PieceDTO pieceDTO = PieceDTO.from(piece);

        assertAll(
                () -> assertThat(pieceDTO.side()).isEqualTo(Side.HAN),
                () -> assertThat(pieceDTO.pieceType()).isEqualTo(PieceType.CHARIOT)
        );
    }

    @Test
    @DisplayName("상대방의 기물을 선택하면 예외가 발생한다.")
    void 상대_기물_선택_예외_테스트() {
        // given
        Position hanPiecePosition = new Position(0, 0); // 한나라 기물 위치

        // when & then
        assertThatThrownBy(() -> board.calculateDestinations(hanPiecePosition, Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 상대방의 기물은 선택할 수 없습니다.");
    }

    @Test
    @DisplayName("기물을 이동시키는 경우, 이전 위치는 비고, 새로운 위치에 기물이 존재한다.")
    void 기물_이동_테스트() {
        // given
        Position selected = new Position(6, 0); // 초나라 졸 위치
        Position target = new Position(5, 0);

        // when
        board.movePiece(selected, target);

        // then
        Map<Position, Piece> piecePosition = board.getPiecePosition();
        assertAll(
                () -> assertThat(piecePosition.get(selected)).isNull(),
                () -> assertThat(piecePosition.get(target)).isNotNull()
        );
    }
}
