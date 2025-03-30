package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Board;
import domain.piece.Cannon;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Soldier;
import domain.position.Position;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardTest {

    private Map<Position, Piece> pieces;

    static Stream<Arguments> validateOwnPieceTest() {
        return Stream.of(
                Arguments.of(Position.of(1, 1), Position.of(2, 3), TeamType.HAN),
                Arguments.of(Position.of(1, 2), Position.of(1, 3), TeamType.CHO),
                Arguments.of(Position.of(0, 1), Position.of(1, 1), TeamType.HAN),
                Arguments.of(Position.of(3, 2), Position.of(3, 1), TeamType.CHO)
        );
    }

    @Test
    @DisplayName("사용자가 이동하려는 말의 좌표가 비어있으면 예외가 발생한다.")
    void movePieceException() {
        // given
        Board board = new Board(Map.of());
        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(1, 2);

        // when & then
        assertThatThrownBy(() -> board.movePiece(startPosition, endPosition, TeamType.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표에는 말이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("이동하지 못한다면 예외가 발생한다.")
    void movePieceException2() {
        // given
        Board board = new Board(pieces);
        Position startPosition = Position.of(0, 1);
        Position endPosition = Position.of(1, 1);

        // when & then
        assertThatThrownBy(() -> board.movePiece(startPosition, endPosition, TeamType.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동하려는 위치에 같은 팀의 기물이 존재합니다.");
    }

    @Test
    @DisplayName("해당 자리에 아무도 없으면 이동한다.")
    void movePieceException3() {
        // given
        Board board = new Board(pieces);

        Position startPosition = Position.of(0, 1);
        Position endPosition = Position.of(0, 0);

        // when
        board.movePiece(startPosition, endPosition, TeamType.CHO);

        // then
        Map<Position, Piece> alivePieces = board.getAlivePieces();
        assertThat(alivePieces).containsKey(endPosition);
        Piece findPiece = alivePieces.get(endPosition);
        PieceType type = findPiece.getType();
        assertThat(type).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("해당 자리에 적이 있으면 이동하고 죽인다.")
    void movePieceException4() {
        Board board = new Board(pieces);
        Position startPosition = Position.of(0, 1);
        Position endPosition = Position.of(0, 2);

        board.movePiece(startPosition, endPosition, TeamType.CHO);

        Map<Position, Piece> alivePieces = board.getAlivePieces();
        assertThat(alivePieces).hasSize(5);
        assertThat(alivePieces).containsKey(endPosition);
        Piece findPiece = alivePieces.get(endPosition);
        PieceType type = findPiece.getType();
        assertThat(type).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("게임이 진행중이면 true를 반환한다")
    void isFinishedTest2() {
        Board board = new Board(pieces);

        assertThat(board.isInProgress()).isTrue();
    }

    @Test
    @DisplayName("게임이 끝났으면 false를 반환한다")
    void isFinishedTest() {
        Board board = new Board(pieces);

        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(3, 2);

        board.movePiece(startPosition, endPosition, TeamType.CHO);

        assertThat(board.isInProgress()).isFalse();
    }

    @Test
    @DisplayName("이긴 팀을 반환한다")
    void findWinTeamTest() {
        Board board = new Board(pieces);

        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(3, 2);

        board.movePiece(startPosition, endPosition, TeamType.CHO);

        assertThat(board.findWinTeam()).isEqualTo(TeamType.CHO);
    }

    @BeforeEach
    void beforeEach() {
        pieces = Map.of(
                Position.of(1, 1), new Horse(TeamType.CHO),
                Position.of(1, 2), new Cannon(TeamType.HAN),
                Position.of(0, 1), new Soldier(TeamType.CHO),
                Position.of(0, 2), new Horse(TeamType.HAN),
                Position.of(4, 3), new King(TeamType.CHO),
                Position.of(3, 2), new King(TeamType.HAN)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("움직이려는 기물이 진행중인 팀의 기물이 아니면 예외가 발생한다")
    void validateOwnPieceTest(Position from, Position to, TeamType moveTeam) {
        // given
        Board board = new Board(pieces);

        // when & then
        assertThatThrownBy(() -> board.movePiece(from, to, moveTeam))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("본인 말만 움직일 수 있습니다.");
    }

    @Test
    @DisplayName("현재 각 팀의 점수를 반환한다")
    void calculateTeamScoreTest() {
        // given
        Board board = new Board(pieces);

        // when
        Map<TeamType, Double> scores = board.calculateTeamScore();

        // then
        Map<TeamType, Double> expected = Map.of(TeamType.HAN, 13.5, TeamType.CHO, 7.0);
        assertThat(scores).containsExactlyInAnyOrderEntriesOf(expected);
    }
}
