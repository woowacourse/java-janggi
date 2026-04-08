package view.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Board;
import domain.board.LeftSangSetup;
import domain.pieces.PieceType;
import domain.pieces.Side;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.dto.PieceDto;

class BoardViewMapperTest {

    private final BoardViewMapper boardViewMapper = new BoardViewMapper();
    private List<List<PieceDto>> result;

    @BeforeEach
    void setUp() {
        // given & when
        Board board = new LeftSangSetup().initialize(Side.CHO)
                .merge(new LeftSangSetup().initialize(Side.HAN));
        result = boardViewMapper.map(board);
    }

    @Test
    void 보드를_출력용_이차원_컬렉션으로_변환한다() {
        // then
        assertThat(result.get(0).get(0).pieceType()).isEqualTo(PieceType.CHA);
        assertThat(result.get(0).get(0).side()).isEqualTo(Side.CHO);

        assertThat(result.get(9).get(0).pieceType()).isEqualTo(PieceType.CHA);
        assertThat(result.get(9).get(0).side()).isEqualTo(Side.HAN);
    }

    @Test
    void 빈칸은_EMPTY와_null_side로_변환한다() {
        // then
        assertThat(result.get(4).get(4).pieceType()).isEqualTo(PieceType.EMPTY);
        assertThat(result.get(4).get(4).side()).isNull();
        assertThat(result.get(4).get(4).isEmpty()).isTrue();
    }

    @Test
    void 기물의_진영을_함께_변환한다() {
        // then
        assertThat(result.get(3).get(0).side()).isEqualTo(Side.CHO);
        assertThat(result.get(6).get(0).side()).isEqualTo(Side.HAN);
    }
}
