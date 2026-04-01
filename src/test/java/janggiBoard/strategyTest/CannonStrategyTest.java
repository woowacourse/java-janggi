package janggiBoard.strategyTest;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.Cannon;
import domain.piece.Guard;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.strategy.CannonStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CannonStrategyTest {

    private CannonStrategy cannonStrategy;
    private TestPieceProvider testBoard;

    @BeforeEach
    public void setUp() {
        cannonStrategy = new CannonStrategy();
        testBoard = new TestPieceProvider();
    }

    @Test
    void 포가_기물_한개를_넘어_빈칸으로_이동하는지_확인한다() {
        Position currentPosition = new Position(0, 0);
        testBoard.setPiece(new Position(2, 0), new Guard(Team.CHO));

        List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, testBoard);
        assertThat(candidates).contains(new Position(3, 0), new Position(9, 0));
        assertThat(candidates).doesNotContain(new Position(1, 0), new Position(2, 0));
    }

    @Test
    void 포는_다른포를_건너뛸_수_없다() {
        Position currentPosition = new Position(0, 0);
        testBoard.setPiece(new Position(2, 0), new Cannon(Team.CHO));

        List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, testBoard);
        assertThat(candidates.size()).isEqualTo(0);
    }

    @Test
    void 포는_기물이_없으면_이동할_수_없다() {
        Position currentPosition = new Position(0, 0);
        testBoard.setAllBlank();

        List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, testBoard);
        assertThat(candidates.size()).isEqualTo(0);
    }

    @Test
    void 포는_다른포를_잡을_수_없다() {
        Position currentPosition = new Position(0, 0);
        testBoard.setPiece(new Position(2, 0), new Guard(Team.CHO));
        testBoard.setPiece(new Position(4, 0), new Cannon(Team.HAN));

        List<Position> candidates = cannonStrategy.getMoveCandidates(currentPosition, testBoard);
        assertThat(candidates).contains(new Position(3, 0));
        assertThat(candidates).doesNotContain(new Position(4, 0));
    }

    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Piece> pieces = new HashMap<>();

        void setPiece(Position position, Piece piece) {
            pieces.put(position, piece);
        }

        void setAllBlank() {
            pieces.clear();
        }

        @Override
        public boolean isBlank(Position position) {
            return !pieces.containsKey(position);
        }

        @Override
        public Piece getPiece(Position position) {
            return pieces.getOrDefault(position, new Blank());
        }
    }
}
