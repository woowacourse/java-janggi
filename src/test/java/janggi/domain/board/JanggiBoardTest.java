package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import janggi.domain.piece.gererator.ChoPieceGenerator;
import janggi.domain.piece.gererator.DefaultChoPieceGenerator;
import janggi.domain.piece.gererator.DefaultHanPieceGenerator;
import janggi.domain.piece.gererator.HanPieceGenerator;
import janggi.domain.piece.gererator.KnightElephantSetting;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
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
    void 움직이려는_기물이_존재하지_않는다면_움직일_수_없다(int x, int y, int destinationX, int destinationY) {
        JanggiBoard janggiBoard = new JanggiBoard(
                HAN_PIECE_GENERATOR,
                CHO_PIECE_GENERATOR,
                DEFAULT_HAN_KNIGHTELEPHANTSETTING,
                DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        assertThatIllegalArgumentException()
                .isThrownBy(() -> janggiBoard.move(x, y, destinationX, destinationY));
    }

    @Test
    void 기물을_이동시킬_수_있다() {
        JanggiBoard janggiBoard = new JanggiBoard(
                HAN_PIECE_GENERATOR,
                CHO_PIECE_GENERATOR,
                DEFAULT_HAN_KNIGHTELEPHANTSETTING,
                DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        janggiBoard.move(1, 0, 3, 3);
        Map<Position, Piece> pieceMap = janggiBoard.getPieceMap();
        assertThat(pieceMap.get(new Position(3, 3))).isEqualTo(new Elephant(Side.HAN, 3, 3));
        assertThat(pieceMap.get(new Position(1, 0))).isNull();
    }

    @Test
    void 움직인_위치에_적_기물이_있으면_적_기물을_잡을_수_있다() {
        JanggiBoard janggiBoard = new JanggiBoard(
                HAN_PIECE_GENERATOR,
                (setting) -> List.of(new Rook(Side.CHO, 4, 4)),
                DEFAULT_HAN_KNIGHTELEPHANTSETTING,
                DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        janggiBoard.move(4, 4, 4, 3);

        Map<Position, Piece> pieceMap = janggiBoard.getPieceMap();
        assertThat(pieceMap.get(new Position(4, 3))).isEqualTo(new Rook(Side.CHO, 4, 3));
        assertThat(pieceMap.get(new Position(4, 4))).isNull();
    }

    @Test
    void 왕이_없다면_게임이_끝난_것이다() {
        JanggiBoard janggiBoard = new JanggiBoard(
                (setting) -> List.of(),
                (setting) -> List.of(new King(Side.CHO, 4, 4)),
                DEFAULT_HAN_KNIGHTELEPHANTSETTING,
                DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        assertThat(janggiBoard.isEnd()).isTrue();
    }

    @Test
    void 왕이_하나만_남은_경우_해당_왕의_진영이_승리한다() {
        JanggiBoard janggiBoard = new JanggiBoard(
                (setting) -> List.of(),
                (setting) -> List.of(new King(Side.CHO, 4, 4)),
                DEFAULT_HAN_KNIGHTELEPHANTSETTING,
                DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        assertThat(janggiBoard.getWinner()).isEqualTo(Side.CHO);
    }
}
