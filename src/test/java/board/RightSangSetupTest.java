package board;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pieces.Ma;
import pieces.Piece;
import pieces.Sang;
import pieces.Side;
import position.Position;

class RightSangSetupTest {

    private static final int BOARD_ROW_SIZE = 9;
    private static final int BOARD_COLUMN_SIZE = 8;

    @ParameterizedTest
    @EnumSource(Side.class)
    void 오른상차림이_올바른_위치에_초기화된다(Side side) {
        SangSetup setup = new RightSangSetup();
        Board board = setup.initialize(side);

        Map<Position, Piece> positions = new HashMap<>();
        positions.put(position(side, 0, 1), new Ma(side));
        positions.put(position(side, 0, 2), new Sang(side));
        positions.put(position(side, 0, 6), new Ma(side));
        positions.put(position(side, 0, 7), new Sang(side));

        positions.forEach((position, piece) ->
            assertThat(board.pieces().get(position)).isEqualTo(piece)
        );
    }

    private Position position(Side side, int choRow, int choColumn) {
        Position position = new Position(choRow, choColumn);
        if (side.isCho()) {
            return position;
        }
        return reverse(position);
    }

    private Position reverse(Position position) {
        return new Position(
            BOARD_ROW_SIZE - position.row().index(),
            BOARD_COLUMN_SIZE - position.column().index()
        );
    }
}
