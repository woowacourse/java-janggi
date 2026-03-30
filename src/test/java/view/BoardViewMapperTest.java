package view;

import static org.assertj.core.api.Assertions.assertThat;

import board.Board;
import board.LeftSangSetup;
import org.junit.jupiter.api.Test;
import pieces.PieceType;
import pieces.Side;
import view.dto.PieceDto;

class BoardViewMapperTest {

    private final BoardViewMapper boardViewMapper = new BoardViewMapper();

    @Test
    void 보드를_출력용_이차원_배열로_변환한다() {
        // given
        Board board = new LeftSangSetup().initialize(Side.CHO)
                .merge(new LeftSangSetup().initialize(Side.HAN));

        // when
        PieceDto[][] result = boardViewMapper.map(board);

        // then
        assertThat(result[0][0].pieceType()).isEqualTo(PieceType.CHA);
        assertThat(result[0][0].side()).isEqualTo(Side.CHO);

        assertThat(result[9][0].pieceType()).isEqualTo(PieceType.CHA);
        assertThat(result[9][0].side()).isEqualTo(Side.HAN);
    }

    @Test
    void 빈칸은_EMPTY와_null_side로_변환한다() {
        // given
        Board board = new LeftSangSetup().initialize(Side.CHO)
                .merge(new LeftSangSetup().initialize(Side.HAN));

        // when
        PieceDto[][] result = boardViewMapper.map(board);

        // then
        assertThat(result[4][4].pieceType()).isEqualTo(PieceType.EMPTY);
        assertThat(result[4][4].side()).isNull();
        assertThat(result[4][4].isEmpty()).isTrue();
    }

    @Test
    void 기물의_진영을_함께_변환한다() {
        // given
        Board board = new LeftSangSetup().initialize(Side.CHO)
                .merge(new LeftSangSetup().initialize(Side.HAN));

        // when
        PieceDto[][] result = boardViewMapper.map(board);

        // then
        assertThat(result[3][0].side()).isEqualTo(Side.CHO);
        assertThat(result[6][0].side()).isEqualTo(Side.HAN);
    }
}
