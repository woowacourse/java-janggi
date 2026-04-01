package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(BoardInitializer.init(BoardSetting.LEFT_ELEPHANT_SET_UP));
    }

    @Test
    void 선택한_좌표에_위치한_기물을_반환한다() {
        assertThat(board.pieceAt(new Position(8, 0))).isEqualTo(new Piece(Camp.CHO, PieceType.CHARIOT));
    }

    @Test
    void 선택한_좌표에_기물이_없을_경우_예외를_반환한다() {
        assertThatThrownBy(() -> board.pieceAt(new Position(5, 5))).isInstanceOf(IllegalArgumentException.class);
    }

}
