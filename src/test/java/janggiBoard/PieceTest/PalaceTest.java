package janggiBoard.PieceTest;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.Guard;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.strategy.PalaceStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PalaceTest {

    private TestPieceProvider testBoard;
    private King king;
    private Guard guard;

    @BeforeEach
    void setUp() {
        PalaceStrategy palaceStrategy = new PalaceStrategy();
        king = new King(Team.CHO, palaceStrategy);
        guard = new Guard(Team.CHO, palaceStrategy);
        testBoard = new TestPieceProvider();
    }

    @Test
    void 궁이_목적지에_갈_수_있다() {
        Position currentPosition = new Position(8, 4);
        Position targetPosition = new Position(7, 4);

        testBoard.setPiece(currentPosition, king);
        boolean isKingMove = king.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isKingMove).isTrue();
    }

    @Test
    void 궁이_목적지에_갈_수_없다() {
        Position currentPosition = new Position(8, 3);
        Position targetPosition = new Position(7, 4);

        testBoard.setPiece(currentPosition, king);
        boolean isKingMove = king.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isKingMove).isFalse();
    }

    @Test
    void 사가_목적지에_갈_수_있다() {
        Position currentPosition = new Position(9, 5);
        Position targetPosition = new Position(8, 5);

        testBoard.setPiece(currentPosition, guard);
        boolean isGuardMove = guard.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isGuardMove).isTrue();
    }

    @Test
    void 사가_목적지에_갈_수_없다() {
        Position currentPosition = new Position(9, 3);
        Position targetPosition = new Position(7, 5);

        testBoard.setPiece(currentPosition, guard);
        boolean isGuardMove = guard.canMove(currentPosition, targetPosition, testBoard);
        assertThat(isGuardMove).isFalse();
    }


    private static class TestPieceProvider implements PieceProvider {
        private final Map<Position, Piece> boardState = new HashMap<>();

        void setPiece(Position position, Piece piece) {
            boardState.put(position, piece);
        }

        @Override
        public boolean isBlank(Position position) {
            return !boardState.containsKey(position) || boardState.get(position).isBlank();
        }

        @Override
        public Piece getPiece(Position position) {
            return boardState.getOrDefault(position, new Blank());
        }
    }
}
