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
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new Piece(PieceType.CANNON, Team.RED),
                    new BoardPosition(0, 4), new Piece(PieceType.쭈, Team.RED))
            );

            // when & then
            assertThatCode(
                    () -> board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 6), Team.RED))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("이동하려는 기물의 팀이 자신의 팀이 아닌 경우 예외가 발생한다.")
        @Test
        void movePiece() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new Piece(PieceType.쭈, Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                    new BoardPosition(0, 0),
                    new BoardPosition(0, 1), Team.RED))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("다른 팀의 기물을 움직일 수 없습니다.");
        }

        @Nested
        class CanonCases {

            @DisplayName("포는 이동하려는 경로 사이에 장애물이 2개이상 있으면 예외가 발생한다.")
            @Test
            void movePieceCannonCase1() {
                // given
                Board board = new Board(Map.of(
                        new BoardPosition(0, 0),
                        new Piece(PieceType.CANNON, Team.RED),
                        new BoardPosition(0, 4), new Piece(PieceType.쭈, Team.RED),
                        new BoardPosition(0, 5), new Piece(PieceType.쭈, Team.GREEN))
                );

                // when & then
                assertThatThrownBy(
                        () -> board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 6), Team.RED))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동경로에 적합하지 않은 장애물이 있습니다.");
            }

            @DisplayName("포가 포를 넘으면 예외가 발생한다.")
            @Test
            void movePieceCannonCase2() {
                // given
                Board board = new Board(Map.of(
                        new BoardPosition(0, 0),
                        new Piece(PieceType.CANNON, Team.RED),
                        new BoardPosition(0, 4),
                        new Piece(PieceType.CANNON, Team.RED))
                );

                // when & then
                assertThatThrownBy(
                        () -> board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 6), Team.RED))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("포는 포를 넘거나 잡을 수 없습니다.");
            }

            @DisplayName("포가 잡으려는 기물이 포라면 예외가 발생한다.")
            @Test
            void movePieceCannonCase3() {
                // given
                Board board = new Board(Map.of(
                        new BoardPosition(0, 0),
                        new Piece(PieceType.CANNON, Team.RED),
                        new BoardPosition(0, 4), new Piece(PieceType.쭈, Team.RED),
                        new BoardPosition(0, 6),
                        new Piece(PieceType.CANNON, Team.GREEN))
                );

                // when & then
                assertThatThrownBy(
                        () -> board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 6), Team.RED))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("포는 포를 넘거나 잡을 수 없습니다.");
            }
        }
    }
}
