package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Nested
    class ValidCases {

        @DisplayName("포는 이동하려는 경로 사이에 장애물이 1개 있어야만 이동할 수 있다.")
        @Test
        void moveCannonPiece() {
            Board board = new Board(Map.of(
                new Position(0, 0), new Piece(PieceType.CANNON, Team.RED),
                new Position(0, 4), new Piece(PieceType.쭈, Team.RED))
            );
            assertThatCode(
                () -> board.movePiece(new Position(0, 0), new Position(0, 6)))
                .doesNotThrowAnyException();
        }
    }

    @Nested
    class InvalidCases {

        @Nested
        class CanonCases {

            @DisplayName("포는 이동하려는 경로 사이에 장애물이 2개이상 있으면 이동할 수 없다.")
            @Test
            void movePieceCannonCase1() {
                Board board = new Board(Map.of(
                    new Position(0, 0), new Piece(PieceType.CANNON, Team.RED),
                    new Position(0, 4), new Piece(PieceType.쭈, Team.RED),
                    new Position(0, 5), new Piece(PieceType.쭈, Team.GREEN))
                );
                assertThatThrownBy(
                    () -> board.movePiece(new Position(0, 0),
                        new Position(0, 6)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이동경로에 적합하지 않은 장애물이 있습니다.");
            }

            @DisplayName("포는 포를 넘을 수 없다.")
            @Test
            void movePieceCannonCase2() {
                Board board = new Board(Map.of(
                    new Position(0, 0), new Piece(PieceType.CANNON, Team.RED),
                    new Position(0, 4), new Piece(PieceType.CANNON, Team.RED))
                );
                assertThatThrownBy(
                    () -> board.movePiece(new Position(0, 0),
                        new Position(0, 6)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("포는 포를 넘거나 잡을 수 없습니다.");
            }

            @DisplayName("포는 적 팀의 포를 잡을 수 없다.")
            @Test
            void movePieceCannonCase3() {
                Board board = new Board(Map.of(
                    new Position(0, 0), new Piece(PieceType.CANNON, Team.RED),
                    new Position(0, 4), new Piece(PieceType.쭈, Team.RED),
                    new Position(0, 6), new Piece(PieceType.CANNON, Team.GREEN))
                );
                assertThatThrownBy(
                    () -> board.movePiece(new Position(0, 0),
                        new Position(0, 6)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("포는 포를 넘거나 잡을 수 없습니다.");
            }
        }
    }
}
