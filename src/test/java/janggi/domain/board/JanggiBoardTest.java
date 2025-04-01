package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMaker;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.generator.ChoPieceGenerator;
import janggi.domain.piece.generator.HanPieceGenerator;
import janggi.domain.piece.generator.KnightElephantSetting;
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
    private static final ChoPieceGenerator CHO_PIECE_GENERATOR = new ChoPieceGenerator();
    private static final HanPieceGenerator HAN_PIECE_GENERATOR = new HanPieceGenerator();

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

        janggiBoard.move(new Position(1, 0), new Position(3, 3));
        Map<Position, Piece> pieceMap = janggiBoard.getPlacedPieces().getValues();
        assertThat(pieceMap.get(new Position(3, 3))).isEqualTo(
            PieceMaker.createPiece(PieceType.ELEPHANT, Side.HAN, new Position(3, 3)));
        assertThat(pieceMap.get(new Position(1, 0))).isNull();
    }

    @Test
    void 움직인_위치에_적_기물이_있으면_적_기물을_잡을_수_있다() {
        JanggiBoard janggiBoard = new JanggiBoard(
            HAN_PIECE_GENERATOR,
            new FakeChoPieceGenerator(List.of(PieceMaker.createPiece(PieceType.ROOK, Side.HAN, new Position(4, 4)))),
            DEFAULT_HAN_KNIGHTELEPHANTSETTING,
            DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        janggiBoard.move(new Position(4, 4), new Position(4, 3));

        Map<Position, Piece> pieceMap = janggiBoard.getPlacedPieces().getValues();
        assertThat(pieceMap.get(new Position(4, 3))).isEqualTo(
            PieceMaker.createPiece(PieceType.ROOK, Side.HAN, new Position(4, 3)));
        assertThat(pieceMap.get(new Position(4, 4))).isNull();
    }

    @Test
    void 왕이_없다면_게임이_끝난_것이다() {
        JanggiBoard janggiBoard = new JanggiBoard(
            new FakeHanPieceGenerator(List.of()),
            new FakeChoPieceGenerator(List.of(PieceMaker.createPiece(PieceType.KING, Side.CHO, new Position(4, 4)))),
            DEFAULT_HAN_KNIGHTELEPHANTSETTING,
            DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        assertThat(janggiBoard.isEnd()).isTrue();
    }

    @Test
    void 왕이_하나만_남은_경우_해당_왕의_진영이_승리한다() {
        JanggiBoard janggiBoard = new JanggiBoard(
            new FakeHanPieceGenerator(List.of()),
            new FakeChoPieceGenerator(List.of(PieceMaker.createPiece(PieceType.KING, Side.CHO, new Position(4, 4)))),
            DEFAULT_HAN_KNIGHTELEPHANTSETTING,
            DEFAULT_CHO_KNIGHTELEPHANTSETTING
        );

        assertThat(janggiBoard.getWinner()).isEqualTo(Side.CHO);
    }

    private static class FakeHanPieceGenerator extends HanPieceGenerator {

        private final List<Piece> piecesToGenerate;

        public FakeHanPieceGenerator(List<Piece> piecesToGenerate) {
            this.piecesToGenerate = piecesToGenerate;
        }

        @Override
        public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
            return piecesToGenerate;
        }
    }

    private static class FakeChoPieceGenerator extends ChoPieceGenerator {

        private final List<Piece> piecesToGenerate;

        public FakeChoPieceGenerator(List<Piece> piecesToGenerate) {
            this.piecesToGenerate = piecesToGenerate;
        }

        @Override
        public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
            return piecesToGenerate;
        }
    }
}
