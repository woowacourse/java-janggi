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
        PieceDto choCha = result.get(0).get(0);
        PieceDto hanCha = result.get(9).get(0);
        // then
        assertThat(choCha.pieceType()).isEqualTo(PieceType.CHA);
        assertThat(choCha.side()).isEqualTo(Side.CHO);

        assertThat(hanCha.pieceType()).isEqualTo(PieceType.CHA);
        assertThat(hanCha.side()).isEqualTo(Side.HAN);
    }

    @Test
    void 빈칸은_EMPTY와_null_side로_변환한다() {
        PieceDto emptyPiece = result.get(4).get(4);
        // then
        assertThat(emptyPiece.pieceType()).isEqualTo(PieceType.EMPTY);
        assertThat(emptyPiece.side()).isNull();
        assertThat(emptyPiece.isEmpty()).isTrue();
    }

    @Test
    void 기물의_진영을_함께_변환한다() {
        PieceDto choPiece = result.get(3).get(0);
        PieceDto hanPiece = result.get(6).get(0);

        // then
        assertThat(choPiece.side()).isEqualTo(Side.CHO);
        assertThat(hanPiece.side()).isEqualTo(Side.HAN);
    }
}
