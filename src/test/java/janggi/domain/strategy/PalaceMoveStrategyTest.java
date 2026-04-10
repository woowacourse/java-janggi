package janggi.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceMoveStrategyTest {

    @Test
    @DisplayName("궁성을 벗어나는 방향으로는 이동 가능한 목적지가 반환되지 않아야 한다")
    void cannotMoveOutsidePalace() {
        PalaceMoveStrategy strategy = new PalaceMoveStrategy();
        Position edgeOfPalace = new Position(0, 3);
        Piece palace = new Piece(Side.CHO, PieceType.PALACE, "0");

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(edgeOfPalace, palace);
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(edgeOfPalace, EnumSet.of(Direction.W), board);

        assertThat(destinations.isEmpty()).isTrue();
    }
}
