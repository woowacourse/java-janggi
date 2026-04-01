package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static domain.BoardSetting.*;
import static org.assertj.core.api.Assertions.assertThat;

class BoardInitializerTest {
    private Map<Position, Piece> positions;

    @BeforeEach
    void setUp() {
        positions = createFixPosition();
    }


    @Nested
    class 상차림_테스트 {
        @Test
        void 왼상차림은_상마상마_순서대로_배치한다() {
            Map<Position, Piece> pieces = BoardInitializer.init(LEFT_ELEPHANT_SET_UP);
            positions.put(new Position(1, 0), new Piece(Camp.CHO, PieceType.ELEPHANT));
            positions.put(new Position(2, 0), new Piece(Camp.CHO, PieceType.HORSE));
            positions.put(new Position(6, 0), new Piece(Camp.CHO, PieceType.ELEPHANT));
            positions.put(new Position(7, 0), new Piece(Camp.CHO, PieceType.HORSE));
            positions.put(new Position(1, 9), new Piece(Camp.HAN, PieceType.ELEPHANT));
            positions.put(new Position(2, 9), new Piece(Camp.HAN, PieceType.HORSE));
            positions.put(new Position(6, 9), new Piece(Camp.HAN, PieceType.ELEPHANT));
            positions.put(new Position(7, 9), new Piece(Camp.HAN, PieceType.HORSE));

            assertThat(pieces).isEqualTo(positions);
        }

        @Test
        void 오른상차림은_마상마상_순서대로_배치한다() {
            Map<Position, Piece> pieces = BoardInitializer.init(RIGHT_ELEPHANT_SET_UP);
            positions.put(new Position(1, 0), new Piece(Camp.CHO, PieceType.HORSE));
            positions.put(new Position(2, 0), new Piece(Camp.CHO, PieceType.ELEPHANT));
            positions.put(new Position(6, 0), new Piece(Camp.CHO, PieceType.HORSE));
            positions.put(new Position(7, 0), new Piece(Camp.CHO, PieceType.ELEPHANT));
            positions.put(new Position(1, 9), new Piece(Camp.HAN, PieceType.HORSE));
            positions.put(new Position(2, 9), new Piece(Camp.HAN, PieceType.ELEPHANT));
            positions.put(new Position(6, 9), new Piece(Camp.HAN, PieceType.HORSE));
            positions.put(new Position(7, 9), new Piece(Camp.HAN, PieceType.ELEPHANT));


            assertThat(pieces).isEqualTo(positions);
        }

        @Test
        void 바깥상차림은_마상상마_순서대로_배치한다() {
            Map<Position, Piece> pieces = BoardInitializer.init(OUTER_ELEPHANT_SET_UP);
            positions.put(new Position(1, 0), new Piece(Camp.CHO, PieceType.HORSE));
            positions.put(new Position(2, 0), new Piece(Camp.CHO, PieceType.ELEPHANT));
            positions.put(new Position(6, 0), new Piece(Camp.CHO, PieceType.ELEPHANT));
            positions.put(new Position(7, 0), new Piece(Camp.CHO, PieceType.HORSE));
            positions.put(new Position(1, 9), new Piece(Camp.HAN, PieceType.HORSE));
            positions.put(new Position(2, 9), new Piece(Camp.HAN, PieceType.ELEPHANT));
            positions.put(new Position(6, 9), new Piece(Camp.HAN, PieceType.ELEPHANT));
            positions.put(new Position(7, 9), new Piece(Camp.HAN, PieceType.HORSE));


            assertThat(pieces).isEqualTo(positions);
        }

        @Test
        void 안상차림은_상마마상_순서대로_배치한다() {
            Map<Position, Piece> pieces = BoardInitializer.init(INNER_ELEPHANT_SET_UP);
            positions.put(new Position(1, 0), new Piece(Camp.CHO, PieceType.ELEPHANT));
            positions.put(new Position(2, 0), new Piece(Camp.CHO, PieceType.HORSE));
            positions.put(new Position(6, 0), new Piece(Camp.CHO, PieceType.HORSE));
            positions.put(new Position(7, 0), new Piece(Camp.CHO, PieceType.ELEPHANT));
            positions.put(new Position(1, 9), new Piece(Camp.HAN, PieceType.ELEPHANT));
            positions.put(new Position(2, 9), new Piece(Camp.HAN, PieceType.HORSE));
            positions.put(new Position(6, 9), new Piece(Camp.HAN, PieceType.HORSE));
            positions.put(new Position(7, 9), new Piece(Camp.HAN, PieceType.ELEPHANT));


            assertThat(pieces).isEqualTo(positions);
        }
    }

    private static Map<Position, Piece> createFixPosition() {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(new Position(8, 0), new Piece(Camp.CHO, PieceType.CHARIOT));
        pieces.put(new Position(0, 0), new Piece(Camp.CHO, PieceType.CHARIOT));
        pieces.put(new Position(0, 9), new Piece(Camp.HAN, PieceType.CHARIOT));
        pieces.put(new Position(8, 9), new Piece(Camp.HAN, PieceType.CHARIOT));

        pieces.put(new Position(3, 0), new Piece(Camp.CHO, PieceType.GUARD));
        pieces.put(new Position(5, 0), new Piece(Camp.CHO, PieceType.GUARD));
        pieces.put(new Position(3, 9), new Piece(Camp.HAN, PieceType.GUARD));
        pieces.put(new Position(5, 9), new Piece(Camp.HAN, PieceType.GUARD));

        pieces.put(new Position(1, 2), new Piece(Camp.CHO, PieceType.CANNON));
        pieces.put(new Position(7, 2), new Piece(Camp.CHO, PieceType.CANNON));
        pieces.put(new Position(1, 7), new Piece(Camp.HAN, PieceType.CANNON));
        pieces.put(new Position(7, 7), new Piece(Camp.HAN, PieceType.CANNON));

        pieces.put(new Position(0, 3), new Piece(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(2, 3), new Piece(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(4, 3), new Piece(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(6, 3), new Piece(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(8, 3), new Piece(Camp.CHO, PieceType.SOLDIER));
        pieces.put(new Position(0, 6), new Piece(Camp.HAN, PieceType.SOLDIER));
        pieces.put(new Position(2, 6), new Piece(Camp.HAN, PieceType.SOLDIER));
        pieces.put(new Position(4, 6), new Piece(Camp.HAN, PieceType.SOLDIER));
        pieces.put(new Position(6, 6), new Piece(Camp.HAN, PieceType.SOLDIER));
        pieces.put(new Position(8, 6), new Piece(Camp.HAN, PieceType.SOLDIER));

        pieces.put(new Position(4, 1), new Piece(Camp.CHO, PieceType.GENERAL));
        pieces.put(new Position(4, 8), new Piece(Camp.HAN, PieceType.GENERAL));

        return pieces;
    }
}
