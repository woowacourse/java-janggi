package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.janggi.Score;
import domain.janggi.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.Zzu;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Nested
    class ValidCases {

        @DisplayName("초기 장기판을 생성한다.")
        @Test
        void initialize() {
            assertThat(Board.initialize().getPieces()).isEqualTo(InitialBoardFixture.rawInitialBoard);
        }

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

        @DisplayName("현재 팀 별 점수를 계산한다.")
        @Test
        void calculateScore() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new General(Team.RED),
                    new BoardPosition(0, 1), new Chariot(Team.RED),
                    new BoardPosition(0, 2), new Horse(Team.RED),
                    new BoardPosition(0, 3), new Zzu(Team.RED),
                    new BoardPosition(1, 0), new General(Team.GREEN),
                    new BoardPosition(1, 1), new Elephant(Team.GREEN),
                    new BoardPosition(1, 2), new Guard(Team.GREEN),
                    new BoardPosition(1, 3), new Cannon(Team.GREEN)
            ));

            // when
            EnumMap<Team, Score> result = board.calculateScore();

            // then
            assertThat(result)
                    .containsExactlyInAnyOrderEntriesOf(
                            Map.of(Team.RED, new Score(21.5f), Team.GREEN, new Score(13))
                    );
        }

        @DisplayName("살아있는 왕 기물들을 찾는다.")
        @Test
        void findAliveGenerals() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new General(Team.RED),
                    new BoardPosition(0, 5), new General(Team.GREEN)
            ));

            // when & then
            assertThat(board.findAliveGenerals())
                    .containsExactlyInAnyOrder(new General(Team.RED), new General(Team.GREEN));
        }

        @DisplayName("이동시키려는 위치에 상대 기물이 있다면 상대 기물을 삭제한다.")
        @Test
        void removeDestinationEnemyPiece() {
            // given
            Piece selectPiece = new Horse(Team.GREEN);
            Piece destinationPiece = new Zzu(Team.RED);
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), selectPiece,
                    new BoardPosition(1, 2), destinationPiece
            ));

            // when
            board.movePiece(new BoardPosition(0, 0), new BoardPosition(1, 2), Team.GREEN);
            Map<BoardPosition, Piece> pieces = board.getPieces();

            // then
            assertThat(pieces).containsExactly(
                    Map.entry(new BoardPosition(1, 2), selectPiece));
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("이동하는 기물의 위치와 이동시키려는 위치가 동일하면 예외가 발생한다.")
        @Test
        void validateDifferentPositions() {
            // given
            Board board = new Board(Map.of(
                    new BoardPosition(0, 0), new Zzu(Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                    new BoardPosition(0, 0),
                    new BoardPosition(0, 0), Team.GREEN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("기물을 같은 위치로 이동시킬 수 없습니다.");
        }

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
