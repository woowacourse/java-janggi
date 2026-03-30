package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BoardInitializerTest {
    Map<Position, Piece> leftRightInitialBoard;
    Map<Position, Piece> innerOuterInitialBoard;

    @BeforeEach
    void setUp() {
        leftRightInitialBoard = BoardInitializer.init(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);
        innerOuterInitialBoard = BoardInitializer.init(SetUp.INNER_ELEPHANT, SetUp.OUTER_ELEPHANT);
    }

    @Test
    @DisplayName("초기화된 보드의 기물 개수는 32개이다.")
    void totalPieces_Is32() {
        assertThat(leftRightInitialBoard).hasSize(32);
        assertThat(innerOuterInitialBoard).hasSize(32);
    }

    @Nested
    class LeftRightInitialBoard {
        @ParameterizedTest(name = "({0}, {1})에는 {3}({2})가 위치한다.")
        @CsvSource(textBlock = """
                1,1,HAN,CHARIOT
                2,1,HAN,ELEPHANT
                3,1,HAN,HORSE
                4,1,HAN,GUARD
                5,2,HAN,GENERAL
                6,1,HAN,GUARD
                7,1,HAN,ELEPHANT
                8,1,HAN,HORSE
                9,1,HAN,CHARIOT
                2,3,HAN,CANNON
                8,3,HAN,CANNON
                1,4,HAN,SOLDIER
                3,4,HAN,SOLDIER
                5,4,HAN,SOLDIER
                7,4,HAN,SOLDIER
                9,4,HAN,SOLDIER
                1,7,CHO,SOLDIER
                3,7,CHO,SOLDIER
                5,7,CHO,SOLDIER
                7,7,CHO,SOLDIER
                9,7,CHO,SOLDIER
                2,8,CHO,CANNON
                8,8,CHO,CANNON
                1,10,CHO,CHARIOT
                2,10,CHO,ELEPHANT
                3,10,CHO,HORSE
                4,10,CHO,GUARD
                5,9,CHO,GENERAL
                6,10,CHO,GUARD
                7,10,CHO,ELEPHANT
                8,10,CHO,HORSE
                9,10,CHO,CHARIOT
                """)
        @DisplayName("왼상차림/오른상차림 보드의 모든 초기 기물 위치를 검증한다.")
        void containsAllExpectedPieces(int x, int y, Camp camp, PieceType pieceType) {
            assertPiece(leftRightInitialBoard, x, y, camp, pieceType);
        }
    }

    @Nested
    class InnerOuterInitialBoard {
        @ParameterizedTest(name = "({0}, {1})에는 {3}({2})가 위치한다.")
        @CsvSource(textBlock = """
                1,1,HAN,CHARIOT
                2,1,HAN,ELEPHANT
                3,1,HAN,HORSE
                4,1,HAN,GUARD
                5,2,HAN,GENERAL
                6,1,HAN,GUARD
                7,1,HAN,HORSE
                8,1,HAN,ELEPHANT
                9,1,HAN,CHARIOT
                2,3,HAN,CANNON
                8,3,HAN,CANNON
                1,4,HAN,SOLDIER
                3,4,HAN,SOLDIER
                5,4,HAN,SOLDIER
                7,4,HAN,SOLDIER
                9,4,HAN,SOLDIER
                1,7,CHO,SOLDIER
                3,7,CHO,SOLDIER
                5,7,CHO,SOLDIER
                7,7,CHO,SOLDIER
                9,7,CHO,SOLDIER
                2,8,CHO,CANNON
                8,8,CHO,CANNON
                1,10,CHO,CHARIOT
                2,10,CHO,HORSE
                3,10,CHO,ELEPHANT
                4,10,CHO,GUARD
                5,9,CHO,GENERAL
                6,10,CHO,GUARD
                7,10,CHO,ELEPHANT
                8,10,CHO,HORSE
                9,10,CHO,CHARIOT
                """)
        @DisplayName("안상차림/바깥상차림 보드의 모든 초기 기물 위치를 검증한다.")
        void containsAllExpectedPieces(int x, int y, Camp camp, PieceType pieceType) {
            assertPiece(innerOuterInitialBoard, x, y, camp, pieceType);
        }
    }

    private void assertPiece(Map<Position, Piece> board, int x, int y, Camp camp, PieceType pieceType) {
        Piece piece = board.get(new Position(x, y));

        assertThat(piece).isNotNull();
        assertThat(piece.camp()).isEqualTo(camp);
        assertThat(piece.type()).isEqualTo(pieceType);
    }
}
