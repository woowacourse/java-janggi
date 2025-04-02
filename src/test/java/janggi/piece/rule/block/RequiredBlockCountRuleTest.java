package janggi.piece.rule.block;

import fixture.PieceFixture;
import janggi.board.Board;
import janggi.coordinate.Position;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Pieces;
import janggi.player.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RequiredBlockCountRuleTest {

    @Test
    @DisplayName("허용하는 기물의 숫자로 음수를 설정할 수 없다")
    void cannotSetNegativeValue() {
        // given
        // when
        // then
        assertThatThrownBy(() -> RequiredBlockCountRule.withBlock(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("요구되는 블록 수는 음수가 될 수 없습니다");
    }

    @Test
    @DisplayName("withNonBlock()은 0개의 기물이 있어야 하는 블로킹 전략을 반환한다")
    void withNonBlockReturnsZeroBlockStrategy() {
        // given
        final RequiredBlockCountRule strategy = RequiredBlockCountRule.withNonBlock();
        final Board board = Board.from(Pieces.empty());

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
        final Position departure = Position.of(1, 1);
        final Position destination = Position.of(1, 5);

        final List<Piece> pieces = List.of(
                PieceFixture.createPiece(1, 2, PieceType.SOLDIER, Team.HAN),
                PieceFixture.createPiece(1, 3, PieceType.SOLDIER, Team.HAN)
        );
        final Board board = Board.from(Pieces.empty().addAll(pieces));
        final RequiredBlockCountRule strategy = RequiredBlockCountRule.withBlock(2);

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
        final Position departure = Position.of(1, 1);
        final Position destination = Position.of(1, 5);

        final List<Piece> pieces = List.of(
                PieceFixture.createPiece(1, 2, PieceType.SOLDIER, Team.HAN)
        );
        final Board board = Board.from(Pieces.empty().addAll(pieces));
        final RequiredBlockCountRule strategy = RequiredBlockCountRule.withBlock(2);

        // when
        // then
        assertThatThrownBy(() -> strategy.validate(board, departure, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이동 경로에 기물 갯수가 조건에 맞지 않습니다.");
    }
}
