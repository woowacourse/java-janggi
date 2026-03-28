import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.TeamColor;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InnerFormationStrategy;

class InitializerTest {

    InitialFormationStrategy choStrategy;
    InitialFormationStrategy hanStrategy;
    Board board;

    @BeforeEach
    public void setUp() {
        choStrategy = new InnerFormationStrategy();
        hanStrategy = new InnerFormationStrategy();
        Initializer initializer = new Initializer(choStrategy, hanStrategy);
        board = initializer.initialize();
    }

    @Nested
    class 초기화 {
        @Test
        void 초나라_한나라_전략을_주입받아_총_32개의_기물이_세팅된_초기_장기판을_반환한다() {
            int totalPieces = board.findPiecesByTeam(TeamColor.CHO).size()
                    + board.findPiecesByTeam(TeamColor.HAN).size();
            assertThat(totalPieces).isEqualTo(32);
            assertThat(board.findPiece(Position.of(8, 4)).orElseThrow().getPieceType()).isEqualTo(PieceType.KING);
            assertThat(board.findPiece(Position.of(0, 2)).orElseThrow().getPieceType()).isEqualTo(PieceType.ELEPHANT);
        }
    }

    @Nested
    class 기물목록 {
        @Test
        void 초나라_기물_리스트를_생성한다() {
            var choPieces = board.findPiecesByTeam(TeamColor.CHO).stream()
                    .map(Map.Entry::getValue)
                    .toList();

            assertThat(choPieces).hasSize(16);
            assertThat(
                    choPieces.stream()
                            .filter(p -> p.getPieceType() == PieceType.ELEPHANT)
            ).hasSize(2);

            assertThat(
                    choPieces.stream()
                            .filter(p -> p.getPieceType() == PieceType.HORSE)
            ).hasSize(2);

            assertThat(
                    choPieces.stream()
                            .filter(p -> p.getPieceType() == PieceType.CANNON)
            ).hasSize(2);

            assertThat(
                    choPieces.stream()
                            .filter(p -> p.getPieceType() == PieceType.ROOK)
            ).hasSize(2);

            assertThat(
                    choPieces.stream()
                            .filter(p -> p.getPieceType() == PieceType.GUARD)
            ).hasSize(2);

            assertThat(
                    choPieces.stream()
                            .filter(p -> p.getPieceType() == PieceType.PAWN)
            ).hasSize(5);

            assertThat(
                    choPieces.stream()
                            .filter(p -> p.getPieceType() == PieceType.KING)
            ).hasSize(1);
        }
    }
}
