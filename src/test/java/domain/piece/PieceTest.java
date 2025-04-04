package domain.piece;

import static org.assertj.core.api.Assertions.assertThatCode;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.board.PieceExtractor;
import domain.board.PieceFinder;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PieceTest {

    class TestPiece extends Piece {

        public TestPiece(Team team, Score score) {
            super(team, score);
        }

        @Override
        protected void validateArrival(BoardLocation current, BoardLocation target) {

        }

        @Override
        protected List<BoardLocation> extractIntermediatePath(BoardLocation current, BoardLocation target) {
            return List.of();
        }

        @Override
        protected void validateMovePath(List<Piece> pathPiece) {

        }

        @Override
        public PieceType getType() {
            return null;
        }
    }

    @Test
    void validateMovable() {
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(1, 2);
        TestPiece testPiece = new TestPiece(Team.HAN, new Score(0));
        Map<BoardLocation, Piece> pieces = Map.of(
                new BoardLocation(1,1), testPiece
        );
        Board board = new Board(pieces);

        PieceExtractor pieceExtractor = board::extractPathPiece;
        PieceFinder pieceFinder = board::findByLocation;
        // when & then
        assertThatCode(()->
                testPiece.validateMovable(current, destination, pieceExtractor, pieceFinder)
        ).doesNotThrowAnyException();
    }
}
