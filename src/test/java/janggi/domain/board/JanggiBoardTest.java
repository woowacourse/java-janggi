package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import janggi.domain.piece.gererator.ChoPieceGenerator;
import janggi.domain.piece.gererator.DefaultChoPieceGenerator;
import janggi.domain.piece.gererator.DefaultHanPieceGenerator;
import janggi.domain.piece.gererator.HanPieceGenerator;
import janggi.domain.piece.gererator.KnightElephantSetting;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JanggiBoardTest {

    private static final KnightElephantSetting DEFAULT_CHO_KNIGHTELEPHANTSETTING =
            KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT;
    private static final KnightElephantSetting DEFAULT_HAN_KNIGHTELEPHANTSETTING =
            KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT;
    private static final ChoPieceGenerator CHO_PIECE_GENERATOR = new DefaultChoPieceGenerator();
    private static final HanPieceGenerator HAN_PIECE_GENERATOR = new DefaultHanPieceGenerator();

    @ParameterizedTest
    @CsvSource(value = {"4,4,5,5", "0,1,1,1", "2,1,1,1"})
    void 움직이려는_기물이_존재하지_않는다면_움직일_수_없다() {
        JanggiBoard janggiBoard = new JanggiBoard(
                HAN_PIECE_GENERATOR,
                CHO_PIECE_GENERATOR,
                DEFAULT_HAN_KNIGHTELEPHANTSETTING,
                DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        int x = 4;
        int y = 4;
        int destinationX = 5;
        int destinationY = 6;

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiBoard.move(x, y, destinationX, destinationY));
    }
}
