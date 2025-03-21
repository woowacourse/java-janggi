package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.Team;
import domain.piece.Cannon;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Zzu;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Nested
    class ValidCases {

        @DisplayName("선택한 위치의 기물을 선택한 위치로 이동시킨다.")
        @Test
        void movePiece() {
            // given
            Piece piece = new Horse(Team.GREEN);
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), piece)
            );

            // when
            board.movePiece(new BoardPosition(0, 0), new BoardPosition(1, 2), Team.GREEN);
            Map<BoardPosition, Piece> pieces = board.getPieces();

            // then
            assertThat(pieces).containsEntry(new BoardPosition(1, 2), piece);
        }

        @DisplayName("포는 이동하려는 경로 사이에 장애물이 1개 있어야만 이동할 수 있다.")
        @Test
        void moveCannonPiece() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new Cannon(Team.RED),
                    new BoardPosition(0, 4), new Zzu(Team.RED)
            ));

            // when & then
            assertThatCode(
                    () -> board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 6), Team.RED))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("이동하려는 위치에 기물이 존재하지 않으면 예외가 발생한다.")
        @Test
        void validateSelectBoardPosition() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(1, 0), new Zzu(Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                    new BoardPosition(0, 0),
                    new BoardPosition(1, 1), Team.GREEN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이동하려는 기물이 없습니다.");
        }

        @DisplayName("이동하려는 기물의 팀이 자신의 팀이 아닌 경우 예외가 발생한다.")
        @Test
        void validateSelectPieceTeam() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new Zzu(Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                    new BoardPosition(0, 0),
                    new BoardPosition(0, 1), Team.RED))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("다른 팀의 기물을 움직일 수 없습니다.");
        }

        @DisplayName("이동하려는 위치에 아군 기물이 존재하면 예외가 발생한다.")
        @Test
        void validateDestinationPieceTeam() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new Zzu(Team.GREEN),
                    new BoardPosition(1, 0), new Zzu(Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                    new BoardPosition(0, 0),
                    new BoardPosition(1, 0), Team.GREEN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이동하려는 위치에 아군 기물이 존재합니다.");
        }

        @DisplayName("이동경로에 적합하지 않은 장애물이 존재하면 예외가 발생한다.")
        @Test
        void validateMovementRule() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new Horse(Team.GREEN),
                    new BoardPosition(0, 1), new Zzu(Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                    new BoardPosition(0, 0),
                    new BoardPosition(1, 2), Team.GREEN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이동경로에 넘을 수 없는 기물이 있습니다.");
        }

        @Nested
        class CanonCases {

            @DisplayName("포는 이동하려는 경로 사이에 장애물이 2개이상 있으면 예외가 발생한다.")
            @Test
            void movePieceCannonCase1() {
                // given
                Board board = new Board(Map.of(
                        new BoardPosition(0, 0),
                        new Cannon(Team.RED),
                        new BoardPosition(0, 4), new Zzu(Team.RED),
                        new BoardPosition(0, 5), new Zzu(Team.GREEN))
                );

                // when & then
                assertThatThrownBy(
                        () -> board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 6), Team.RED))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동경로에 넘을 수 없는 기물이 있습니다.");
            }

            @DisplayName("포가 포를 넘으면 예외가 발생한다.")
            @Test
            void movePieceCannonCase2() {
                // given
                Board board = new Board(Map.of(
                        new BoardPosition(0, 0),
                        new Cannon(Team.RED),
                        new BoardPosition(0, 4),
                        new Cannon(Team.RED))
                );

                // when & then
                assertThatThrownBy(
                        () -> board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 6), Team.RED))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("이동경로에 넘을 수 없는 기물이 있습니다.");
            }

            @DisplayName("포가 잡으려는 기물이 포라면 예외가 발생한다.")
            @Test
            void movePieceCannonCase3() {
                // given
                Board board = new Board(Map.of(
                        new BoardPosition(0, 0), new Cannon(Team.RED),
                        new BoardPosition(0, 4), new Zzu(Team.RED),
                        new BoardPosition(0, 6), new Cannon(Team.GREEN))
                );

                // when & then
                assertThatThrownBy(
                        () -> board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 6), Team.RED))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("도착 위치에 있는 기물은 잡을 수 없는 기물입니다.");
            }
        }
    }
}
