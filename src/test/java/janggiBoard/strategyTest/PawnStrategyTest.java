package janggiBoard.strategyTest;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.strategy.PawnStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PawnStrategyTest {

    private PawnStrategy pawnStrategy;
    private TestPieceProvider testBoard;

    @BeforeEach
    void setUp() {
        pawnStrategy = new PawnStrategy();
        testBoard = new TestPieceProvider();
    }

    @Test
    void 초나라_졸인_경우_3가지_경우_반환() {
        Position currentPosition = new Position(5, 5);

        testBoard.setPiece(currentPosition, new Pawn(Team.CHO, pawnStrategy));

        List<Position> candidates = pawnStrategy.getMoveCandidates(currentPosition, testBoard);
        assertThat(candidates).hasSize(3)
                .containsExactlyInAnyOrder(
                        new Position(5, 4), new Position(5, 6), new Position(4, 5)
                );
    }

    @Test
    void 한나라_졸인_경우_3가지_경우_반환() {
        Position currentPosition = new Position(3, 5);

        testBoard.setPiece(currentPosition, new Pawn(Team.HAN, pawnStrategy));

        List<Position> candidates = pawnStrategy.getMoveCandidates(currentPosition, testBoard);
        assertThat(candidates).hasSize(3)
                .containsExactlyInAnyOrder(
                        new Position(4, 5), new Position(3, 6), new Position(3, 4)
                );
    }

    private static class TestPieceProvider implements PieceProvider {
        private Map<Position, Piece> pieces = new HashMap<>();

        void setPiece(Position position, Piece piece) {
            pieces.put(position, piece);
        }

        @Override
        public boolean isBlank(Position position) {
            return getPiece(position) instanceof Blank;
        }

        @Override
        public Piece getPiece(Position position) {
            return pieces.getOrDefault(position, new Blank());
        }
    }
}
