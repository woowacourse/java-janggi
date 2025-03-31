package domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import domain.piece.Cannon;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.Jju;
import domain.piece.Piece;
import domain.piece.Score;
import domain.piece.Team;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardTest {

    @Nested
    class ValidCases {

        @DisplayName("선택한 위치에 같은 팀의 기물이 있으면 반환한다.")
        @ParameterizedTest
        @MethodSource("provideFindSelectedPieceCases")
        void findSelectedPiece(
            Map<BoardPosition, Piece> pieces,
            BoardPosition selectBoardPosition,
            Team currentTeam,
            boolean expected
        ) {
            // given
            Board board = new Board(pieces);

            // when
            Optional<Piece> result = board.findSelectedPiece(selectBoardPosition, currentTeam);

            // then
            assertThat(result.isPresent()).isEqualTo(expected);
        }

        static Stream<Arguments> provideFindSelectedPieceCases() {
            BoardPosition boardPosition = new BoardPosition(0, 0);

            return Stream.of(
                Arguments.of(
                    Map.of(boardPosition, new Jju(Team.GREEN)),
                    boardPosition,
                    Team.GREEN,
                    true
                ),
                Arguments.of(
                    Map.of(boardPosition, new Jju(Team.RED)),
                    boardPosition,
                    Team.GREEN,
                    false
                ),
                Arguments.of(
                    Map.of(),
                    boardPosition,
                    Team.GREEN,
                    false
                )
            );
        }

        @DisplayName("기물을 이동시키면 목적지로 이동하고 원래 자리는 비워진다.")
        @Test
        void movePiece() {
            // given
            Board board = new Board(Map.of(new BoardPosition(0, 0), new Jju(Team.GREEN)));

            // when
            board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 1), Team.GREEN);

            // then
            assertSoftly(softly -> {
                softly.assertThat(board.getPieces()
                        .containsKey(new BoardPosition(0, 0)))
                    .isFalse();
                softly.assertThat(board.getPieces()
                        .get(new BoardPosition(0, 1))
                        .getPieceType())
                    .isEqualTo(new Jju(Team.GREEN).getPieceType());
            });
        }

        @DisplayName("장애물 하나를 넘는 기물이먄 이동 시킬 수 있다.")
        @Test
        void movePiece_withObstacle() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Cannon(Team.RED),
                new BoardPosition(0, 2), new Jju(Team.GREEN),
                new BoardPosition(0, 4), new Guard(Team.GREEN)
            ));

            // when
            board.movePiece(new BoardPosition(0, 0), new BoardPosition(0, 4), Team.RED);

            // then
            assertSoftly(softly -> {
                softly.assertThat(board.getPieces()
                        .containsKey(new BoardPosition(0, 0)))
                    .isFalse();
                softly.assertThat(board.getPieces()
                        .get(new BoardPosition(0, 4))
                        .getPieceType())
                    .isEqualTo(new Cannon(Team.RED).getPieceType());
            });
        }

        @DisplayName("팀의 점수를 계산한다.")
        @Test
        void calculateScore() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.GREEN),
                new BoardPosition(1, 0), new Guard(Team.GREEN),
                new BoardPosition(2, 0), new Horse(Team.GREEN),
                new BoardPosition(3, 0), new Cannon(Team.GREEN)
            ));

            // when
            Score result = board.calculateScore(Team.GREEN);

            // then
            assertThat(result.value()).isEqualTo(17.0f);
        }

        @DisplayName("왕이 하나만 남은 경우를 확인한다.")
        @Test
        void isOnlyOneKingLeft() {
            // given
            Board boardWithOneKing = new Board(Map.of(
                new BoardPosition(0, 0), new General(Team.GREEN)
            ));
            Board boardWithTwoKings = new Board(Map.of(
                new BoardPosition(0, 0), new General(Team.GREEN),
                new BoardPosition(1, 0), new General(Team.RED)
            ));

            // when & then
            assertSoftly(softly -> {
                softly.assertThat(boardWithOneKing.isOnlyOneKingLeft())
                    .isTrue();
                softly.assertThat(boardWithTwoKings.isOnlyOneKingLeft())
                    .isFalse();
            });
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("보드 생성 시 기물들의 위치를 가져야한다.")
        @Test
        void validateNotNull() {
            // when & then
            assertThatThrownBy(() -> new Board(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드는 기물들을 가져야합니다.");
        }

        @DisplayName("이동하려는 위치에 기물이 없으면 예외가 발생한다.")
        @Test
        void movePiece_noPiece() {
            // given
            Board board = new Board(Map.of());

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                new BoardPosition(0, 0),
                new BoardPosition(0, 1), Team.RED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 기물이 없습니다.");
        }

        @DisplayName("이동하려는 기물의 팀이 자신의 팀이 아닌 경우 이동시킬 수 없다.")
        @Test
        void movePiece_otherTeam() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                new BoardPosition(0, 0),
                new BoardPosition(0, 1), Team.RED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다른 팀의 기물을 움직일 수 없습니다.");
        }

        @DisplayName("목적지에 아군 기물이 있으면 이동할 수 없다.")
        @Test
        void movePiece_sameTeam() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.GREEN),
                new BoardPosition(0, 1), new Guard(Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                new BoardPosition(0, 0),
                new BoardPosition(0, 1), Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 아군 기물이 존재합니다.");
        }

        @DisplayName("정의되지 않은 이동이면 예외가 발생한다.")
        @Test
        void movePiece_invalidDirection() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Jju(Team.GREEN)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                new BoardPosition(0, 0),
                new BoardPosition(1, 1), Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 이동할 수 없습니다.");
        }

        @DisplayName("기물이 장애물을 넘을 수 없으면 예외가 발생한다.")
        @Test
        void movePiece_withObstacle() {
            // given
            Board board = new Board(Map.of(
                new BoardPosition(0, 0), new Horse(Team.GREEN),
                new BoardPosition(0, 1), new Guard(Team.RED)
            ));

            // when & then
            assertThatThrownBy(() -> board.movePiece(
                new BoardPosition(0, 0),
                new BoardPosition(1, 2), Team.GREEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 말은 장애물을 넘을 수 없습니다.");
        }
    }
}
