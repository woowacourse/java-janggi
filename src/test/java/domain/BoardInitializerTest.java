package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardInitializerTest {
    Map<Position, Piece> leftRightInitialBoard;
    Map<Position, Piece> innerOuterInitialBoard;

    @BeforeEach
    void setUp() {
        leftRightInitialBoard = BoardInitializer.init(1, 2);
        innerOuterInitialBoard = BoardInitializer.init(3, 4);
    }

    @Test
    @DisplayName("초기화된 보드의 기물 개수는 32개이다.")
    void totalPieces_Is32() {
        int size = leftRightInitialBoard.size();

        assertThat(size).isEqualTo(32);
    }

    @Nested
    class LeftRightInitialBoard {
        @Test
        @DisplayName("초나라는 왼상차림일 경우 (2,10)에는 상이 위치한다.")
        void choLeftElephantSetUp_Then_Elephant_Located_AtTwoTen() {
            Piece piece = leftRightInitialBoard.get(new Position(2, 10));

            assertThat(piece.type()).isEqualTo(PieceType.ELEPHANT);
        }

        @Test
        @DisplayName("초나라는 왼상차림의 경우 (3,10)에는 말이 위치한다.")
        void choLeftElephantSetUp_Then_Horse_Located_AtTwoTen() {
            Piece piece = leftRightInitialBoard.get(new Position(3, 10));

            assertThat(piece.type()).isEqualTo(PieceType.HORSE);
        }

        @Test
        @DisplayName("한나라는 오른상차림일 경우 (2,1)에는 상이 위치한다.")
        void hanLeftElephantSetUp_Then_Elephant_Located_AtTwoTen() {
            Piece piece = leftRightInitialBoard.get(new Position(2, 1));

            assertThat(piece.type()).isEqualTo(PieceType.ELEPHANT);
        }

        @Test
        @DisplayName("한나라는 오른상차림의 경우 (3,1)에는 말이 위치한다.")
        void hanLeftElephantSetUp_Then_Horse_Located_AtTwoTen() {
            Piece piece = leftRightInitialBoard.get(new Position(3, 1));

            assertThat(piece.type()).isEqualTo(PieceType.HORSE);
        }
    }

    @Nested
    class InnerOuterInitialBoard {

        @Test
        @DisplayName("초나라는 안상차림의 경우 (2,10)에는 말이 위치한다.")
        void hanInnerElephantSetUp_Then_Elephant_Located_AtTwoTen() {
            Piece piece = innerOuterInitialBoard.get(new Position(2, 10));

            assertThat(piece.type()).isEqualTo(PieceType.HORSE);
        }

        @Test
        @DisplayName("초나라는 안상차림의 경우 (3,10)에는 상이 위치한다.")
        void hanInnerElephantSetUp_Then_Horse_Located_AtTwoTen() {
            Piece piece = innerOuterInitialBoard.get(new Position(3, 10));

            assertThat(piece.type()).isEqualTo(PieceType.ELEPHANT);
        }

        @Test
        @DisplayName("한나라는 바깥상차림의 경우 (2,1)에는 상이 위치한다.")
        void hanOuterElephantSetUp_Then_Elephant_Located_AtTwoTen() {
            Piece piece = innerOuterInitialBoard.get(new Position(2, 1));

            assertThat(piece.type()).isEqualTo(PieceType.ELEPHANT);
        }

        @Test
        @DisplayName("한나라는 바깥상차림의 경우 (3,1)에는 말이 위치한다.")
        void hanOuterElephantSetUp_Then_Horse_Located_AtTwoTen() {
            Piece piece = innerOuterInitialBoard.get(new Position(3, 1));

            assertThat(piece.type()).isEqualTo(PieceType.HORSE);
        }
    }


}

