import domain.InitialGameState;
import domain.PieceType;
import domain.Position;

import domain.TeamColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;



import static org.assertj.core.api.Assertions.assertThat;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InnerFormationStrategy;

class InitializerTest {

    InitialFormationStrategy choStrategy;
    InitialFormationStrategy hanStrategy;
    InitialGameState initialGameState;

    @BeforeEach
    public void setUp(){
        choStrategy = new InnerFormationStrategy();
        hanStrategy = new InnerFormationStrategy();
        Initializer initializer = new Initializer(choStrategy, hanStrategy);
        initialGameState = initializer.initialize();
    }


    @Nested
    class 초기화 {
        @Test
        void 초나라_한나라_전략을_주입받아_총_32개의_기물이_세팅된_초기_장기판을_반환한다() {
            assertThat(initialGameState.boardState()).hasSize(32);
            assertThat(initialGameState.boardState().get(Position.of(8, 4)).getPieceType()).isEqualTo(PieceType.KING);
            assertThat(initialGameState.boardState().get(Position.of(0, 2)).getPieceType()).isEqualTo(PieceType.ELEPHANT);
        }
    }

    @Nested
    class 기물목록 {
        @Test
        void 초나라_기물_리스트를_생성한다() {
            assertThat(initialGameState.choPieces()).hasSize(16);
            assertThat(
                    initialGameState.choPieces().stream()
                            .filter(p -> p.getTeamColor() == TeamColor.CHO)
                            .filter(p -> p.getPieceType() == PieceType.ELEPHANT)
            ).hasSize(2);

            assertThat(
                    initialGameState.choPieces().stream()
                            .filter(p -> p.getTeamColor() == TeamColor.CHO)
                            .filter(p -> p.getPieceType() == PieceType.HORSE)
            ).hasSize(2);

            assertThat(
                    initialGameState.choPieces().stream()
                            .filter(p -> p.getTeamColor() == TeamColor.CHO)
                            .filter(p -> p.getPieceType() == PieceType.CANNON)
            ).hasSize(2);

            assertThat(
                    initialGameState.choPieces().stream()
                            .filter(p -> p.getTeamColor() == TeamColor.CHO)
                            .filter(p -> p.getPieceType() == PieceType.ROOK)
            ).hasSize(2);

            assertThat(
                    initialGameState.choPieces().stream()
                            .filter(p -> p.getTeamColor() == TeamColor.CHO)
                            .filter(p -> p.getPieceType() == PieceType.GUARD)
            ).hasSize(2);

            assertThat(
                    initialGameState.choPieces().stream()
                            .filter(p -> p.getTeamColor() == TeamColor.CHO)
                            .filter(p -> p.getPieceType() == PieceType.PAWN)
            ).hasSize(5);

            assertThat(
                    initialGameState.choPieces().stream()
                            .filter(p -> p.getTeamColor() == TeamColor.CHO)
                            .filter(p -> p.getPieceType() == PieceType.KING)
            ).hasSize(1);
        }
    }
}
