package domain;

import static fixtures.PositionFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Soldier;
import domain.piece.TeamType;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Map<Position,Piece> pieces;
    @BeforeEach
    void beforeEach() {
        pieces = Map.of(
                B1, new Horse(TeamType.CHO),
                C1,new Soldier(TeamType.HAN),
                B0,new Soldier(TeamType.CHO),
                C0,new Horse(TeamType.HAN),
                D4,new King(TeamType.CHO),
                C3,new King(TeamType.HAN)
        );
    }

    @Test
    @DisplayName("사용자가 이동하려는 말의 좌표가 비어있으면 예외가 발생한다.")
    void movePieceException() {
        Board board = new Board(Map.of());
        Position startPosition = B1;
        Position endPosition = C1;
        assertThatThrownBy(() -> board.movePiece(startPosition, endPosition, TeamType.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표에는 말이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("이동하지 못한다면 예외가 발생한다.")
    void movePieceException2() {
        Board board = new Board(pieces);

        Position startPosition = B0;
        Position endPosition = B1;

        assertThatThrownBy(() -> board.movePiece(startPosition, endPosition, TeamType.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 좌표로 이동시킬 수 없습니다.");
    }

    @Test
    @DisplayName("해당 자리에 아무도 없으면 이동한다.")
    void movePieceException3() {
        Board board = new Board(pieces);

        Position startPosition = B0;
        Position endPosition = A0;

        board.movePiece(startPosition, endPosition, TeamType.CHO);

        Map<Position, Piece> alivePieces = board.getAlivePieces();
        Piece findPiece = alivePieces.getOrDefault(endPosition, null);
        assertThat(findPiece).isNotNull();
        PieceType type = findPiece.getType();
        assertThat(type).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("해당 자리에 적이 있으면 이동하고 죽인다.")
    void movePieceException4() {
        Board board = new Board(pieces);
        Position startPosition = B0;
        Position endPosition = C0;

        board.movePiece(startPosition, endPosition, TeamType.CHO);

        Map<Position, Piece> alivePieces = board.getAlivePieces();
        assertThat(alivePieces).hasSize(5);
        Piece findPiece = alivePieces.getOrDefault(endPosition, null);
        assertThat(findPiece).isNotNull();
        PieceType type = findPiece.getType();
        assertThat(type).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("게임이 끝났으면 true를 반환한다")
    void isFinishedTest() {
        Board board = new Board(pieces);

        Position startPosition = B1;
        Position endPosition = C3;

        board.movePiece(startPosition, endPosition, TeamType.CHO);

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

        Position startPosition = B1;
        Position endPosition = C3;

        board.movePiece(startPosition, endPosition, TeamType.CHO);

        assertThat(board.findWinTeam()).isEqualTo(TeamType.CHO);
    }
}
