package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Soldier;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    private List<Piece> pieces;

    @BeforeEach
    void beforeEach() {
        pieces = List.of(
                new Horse(Position.of(1, 1), TeamType.CHO),
                new Soldier(Position.of(1, 2), TeamType.HAN),
                new Soldier(Position.of(0, 1), TeamType.CHO),
                new Horse(Position.of(0, 2), TeamType.HAN),
                new King(Position.of(4, 3), TeamType.CHO),
                new King(Position.of(3, 2), TeamType.HAN)
        );
    }

    @Test
    @DisplayName("사용자가 이동하려는 말의 좌표가 비어있으면 예외가 발생한다.")
    void movePieceException() {
        Board board = new Board(List.of());
        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(1, 2);
        assertThatThrownBy(() -> board.movePiece(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표에는 말이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("이동하지 못한다면 예외가 발생한다.")
    void movePieceException2() {
        Board board = new Board(pieces);

        Position startPosition = Position.of(0, 1);
        Position endPosition = Position.of(1, 1);

        assertThatThrownBy(() -> board.movePiece(startPosition, endPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("해당 자리에 아무도 없으면 이동한다.")
    void movePieceException3() {
        Board board = new Board(pieces);

        Position startPosition = Position.of(0, 1);
        Position endPosition = Position.of(0, 0);

        board.movePiece(startPosition, endPosition);

        List<Piece> alivePieces = board.getAlivePieces();
        Optional<Piece> optionalPiece = alivePieces.stream().filter(piece -> piece.hasSamePosition(endPosition))
                .findAny();
        assertThat(optionalPiece).isPresent();
        Piece findPiece = optionalPiece.get();
        PieceType type = findPiece.getType();
        assertThat(type).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("해당 자리에 적이 있으면 이동하고 죽인다.")
    void movePieceException4() {
        Board board = new Board(pieces);
        Position startPosition = Position.of(0, 1);
        Position endPosition = Position.of(0, 2);

        board.movePiece(startPosition, endPosition);

        List<Piece> alivePieces = board.getAlivePieces();
        assertThat(alivePieces).hasSize(5);
        Optional<Piece> optionalPiece = alivePieces.stream().filter(piece -> piece.hasSamePosition(endPosition))
                .findAny();
        assertThat(optionalPiece).isPresent();
        Piece findPiece = optionalPiece.get();
        PieceType type = findPiece.getType();
        assertThat(type).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("게임이 끝났으면 true를 반환한다")
    void isFinishedTest() {
        Board board = new Board(pieces);

        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(3, 2);

        board.movePiece(startPosition, endPosition);

        assertThat(board.isFinished()).isTrue();
    }

    @Test
    @DisplayName("게임이 끝나지 않았으면 false를 반환한다")
    void isFinishedTest2() {
        Board board = new Board(pieces);

        assertThat(board.isFinished()).isFalse();
    }

    @Test
    @DisplayName("이긴 팀을 반환한다")
    void findWinTeamTest() {
        Board board = new Board(pieces);

        Position startPosition = Position.of(1, 1);
        Position endPosition = Position.of(3, 2);

        board.movePiece(startPosition, endPosition);

        assertThat(board.findWinTeam()).isEqualTo(TeamType.CHO);
    }
}
