package board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class SangSetupTest {

    private static final int BOARD_ROW_SIZE = 9;
    private static final int BOARD_COLUMN_SIZE = 8;

    @ParameterizedTest
    @EnumSource(Side.class)
    void 공통_기물이_올바른_위치에_초기화된다(Side side) {
        // given
        Map<Position, Piece> pieces = getDefaultSangSetup(side);
        // when
        Board board = SangSetup.initialize(SangSetupType.LEFT_SANG_SETUP, SangSetupType.LEFT_SANG_SETUP);
        // then
        pieces.forEach((position, piece) ->
            assertThat(board.pieces().get(position)).isEqualTo(piece)
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 왼상차림이_올바른_위치에_초기화된다(Side side) {
        // given
        SangSetupType type = SangSetupType.LEFT_SANG_SETUP;
        Map<Position, Piece> positions = Map.of(
            position(side, 0, 1), new Piece(side, PieceType.SANG),
            position(side, 0, 2), new Piece(side, PieceType.MA),
            position(side, 0, 6), new Piece(side, PieceType.SANG),
            position(side, 0, 7), new Piece(side, PieceType.MA)
        );
        // when
        Board board = SangSetup.initialize(type, type);
        // then
        positions.forEach((position, piece) ->
            assertThat(board.pieces().get(position)).isEqualTo(piece)
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 오른상차림이_올바른_위치에_초기화된다(Side side) {
        // given
        SangSetupType type = SangSetupType.RIGHT_SANG_SETUP;
        Map<Position, Piece> positions = Map.of(
            position(side, 0, 1), new Piece(side, PieceType.MA),
            position(side, 0, 2), new Piece(side, PieceType.SANG),
            position(side, 0, 6), new Piece(side, PieceType.MA),
            position(side, 0, 7), new Piece(side, PieceType.SANG)
        );
        // when
        Board board = SangSetup.initialize(type, type);
        // then
        positions.forEach((position, piece) ->
            assertThat(board.pieces().get(position)).isEqualTo(piece)
        );
    }

    @ParameterizedTest
    @EnumSource(Side.class)
    void 안상차림이_올바른_위치에_초기화된다(Side side) {
        // given
        SangSetupType type = SangSetupType.INNER_SANG_SETUP;
        Map<Position, Piece> positions = Map.of(
            position(side, 0, 1), new Piece(side, PieceType.MA),
            position(side, 0, 2), new Piece(side, PieceType.SANG),
            position(side, 0, 6), new Piece(side, PieceType.SANG),
            position(side, 0, 7), new Piece(side, PieceType.MA)
        );
        // when
        Board board = SangSetup.initialize(type, type);
        // then
        positions.forEach((position, piece) ->
            assertThat(board.pieces().get(position)).isEqualTo(piece)
        );
    }

    private Map<Position, Piece> getDefaultSangSetup(Side side) {
        Map<Position, Piece> positions = new HashMap<>();
        positions.put(position(side, 0, 0), new Piece(side, PieceType.CHA));
        positions.put(position(side, 0, 3), new Piece(side, PieceType.SA));
        positions.put(position(side, 1, 4), new Piece(side, PieceType.GUNG));
        positions.put(position(side, 0, 5), new Piece(side, PieceType.SA));
        positions.put(position(side, 0, 8), new Piece(side, PieceType.CHA));

        positions.put(position(side, 2, 1), new Piece(side, PieceType.PO));
        positions.put(position(side, 2, 7), new Piece(side, PieceType.PO));

        positions.put(position(side, 3, 0), new Piece(side, PieceType.JOL_BYEONG));
        positions.put(position(side, 3, 2), new Piece(side, PieceType.JOL_BYEONG));
        positions.put(position(side, 3, 4), new Piece(side, PieceType.JOL_BYEONG));
        positions.put(position(side, 3, 6), new Piece(side, PieceType.JOL_BYEONG));
        positions.put(position(side, 3, 8), new Piece(side, PieceType.JOL_BYEONG));
        return positions;
    }

    private Position position(Side side, int choRow, int choColumn) {
        Position position = new Position(choRow, choColumn);
        if (side.isCho()) {
            return position;
        }
        return position.reverse();
    }
}