package janggi.piece.strategy.block;

import fixture.PieceFixture;
import janggi.Board;
import janggi.Team;
import janggi.coordinate.Position;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Pieces;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequiredBlockCountStrategyTest {

    @Test
    @DisplayName("common()은 0개의 기물이 있어야 하는 블로킹 전략을 반환한다")
    void commonReturnsZeroBlockStrategy() {
        // given
        RequiredBlockCountStrategy strategy = RequiredBlockCountStrategy.common();
        Board board = Board.from(Pieces.empty());

        // when
        // then
        assertDoesNotThrow(() ->
                strategy.validate(board, Position.of(1, 1), Position.of(1, 5))
        );
    }

    @Test
    @DisplayName("경로상 기물이 요구된 개수와 같으면 검증이 통과된다")
    void validate_successWhenBlockCountMatches() {
        // given
        Position departure = Position.of(1, 1);
        Position destination = Position.of(1, 5);

        List<Piece> pieces = List.of(
                PieceFixture.createPiece(1, 2, PieceType.SOLDIER, Team.HAN),
                PieceFixture.createPiece(1, 3, PieceType.SOLDIER, Team.HAN)
        );
        Board board = Board.from(Pieces.empty().addAll(pieces));
        RequiredBlockCountStrategy strategy = new RequiredBlockCountStrategy(2);

        // when
        // then
        assertDoesNotThrow(() ->
                strategy.validate(board, departure, destination)
        );
    }

    @Test
    @DisplayName("경로상 기물 수가 요구와 다르면 예외가 발생한다")
    void validate_throwsWhenBlockCountNotMatch() {
        // given
        Position departure = Position.of(1, 1);
        Position destination = Position.of(1, 5);

        List<Piece> pieces = List.of(
                PieceFixture.createPiece(1, 2, PieceType.SOLDIER, Team.HAN)
        );
        Board board = Board.from(Pieces.empty().addAll(pieces));
        RequiredBlockCountStrategy strategy = new RequiredBlockCountStrategy(2);

        // when
        // then
        assertThatThrownBy(() -> strategy.validate(board, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
    }
}
