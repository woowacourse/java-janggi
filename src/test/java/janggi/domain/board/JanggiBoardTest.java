package janggi.domain.board;

import janggi.domain.piece.Side;
import janggi.domain.piece.Elephant;
import janggi.domain.position.Position;
import janggi.domain.piece.Rook;
import janggi.domain.piece.generator.ChoPieceGenerator;
import janggi.domain.piece.generator.DefaultChoPieceGenerator;
import janggi.domain.piece.generator.DefaultHanPieceGenerator;
import janggi.domain.piece.generator.HanPieceGenerator;
import janggi.domain.piece.generator.KnightElephantSetting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class JanggiBoardTest {

    private static final KnightElephantSetting DEFAULT_CHO_KNIGHTELEPHANTSETTING =
            KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT;
    private static final KnightElephantSetting DEFAULT_HAN_KNIGHTELEPHANTSETTING =
            KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT;
    private static final ChoPieceGenerator CHO_PIECE_GENERATOR = new DefaultChoPieceGenerator();
    private static final HanPieceGenerator HAN_PIECE_GENERATOR = new DefaultHanPieceGenerator();

    @ParameterizedTest
    @CsvSource(value = {"4,4,5,5", "0,1,1,1", "2,1,1,1"})
    void 움직이려는_기물이_존재하지_않는다면_움직일_수_없다(int x, int y, int destinationX, int destinationY) {
        JanggiBoard janggiBoard = new JanggiBoard(
                HAN_PIECE_GENERATOR,
                CHO_PIECE_GENERATOR,
                DEFAULT_HAN_KNIGHTELEPHANTSETTING,
                DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiBoard.move(new Position(x, y), new Position(destinationX, destinationY)));
    }

    @Test
    void 기물을_이동시킬_수_있다() {
        JanggiBoard janggiBoard = new JanggiBoard(
                HAN_PIECE_GENERATOR,
                CHO_PIECE_GENERATOR,
                DEFAULT_HAN_KNIGHTELEPHANTSETTING,
                DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        janggiBoard.move(new Position(1, 9), new Position(3, 6));
        assertThat(janggiBoard.findPieceByPosition(new Position(3,6)))
            .isEqualTo(new Elephant(Side.CHO, 3, 6));
    }

    @Test
    void 움직인_위치에_적_기물이_있으면_적_기물을_잡을_수_있다() {
        JanggiBoard janggiBoard = new JanggiBoard(
            HAN_PIECE_GENERATOR,
            (setting) -> List.of(new Rook(Side.CHO, 4, 7)),
            DEFAULT_HAN_KNIGHTELEPHANTSETTING,
            DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        janggiBoard.move(new Position(4, 7) , new Position(4, 8));

        assertThat(janggiBoard.findPieceByPosition(new Position(4, 8)))
            .isEqualTo(new Rook(Side.CHO, 4, 8));
    }
}
