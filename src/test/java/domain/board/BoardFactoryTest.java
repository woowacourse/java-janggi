package domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardFactoryTest {

    @Nested
    class ValidCases {

        @DisplayName("초기 보드를 생성하면 모든 기물이 올바르게 배치된다.")
        @Test
        void createInitialBoard() {
            // when
            Board board = BoardFactory.createInitialBoard();

            // then
            Set<PieceType> pieceTypes = board.getPieces()
                .values()
                .stream()
                .map(Piece::getPieceType)
                .collect(Collectors.toSet());

            assertThat(pieceTypes).containsExactlyInAnyOrder(PieceType.values());
        }
    }
}
