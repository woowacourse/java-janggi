package janggi.domain.board;


import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("destinations(): Point에 있는 Piece의 모든 목적지를 리턴한다")
    void destinations() {
        BoardSetUp testSetUp = (side) -> Map.of(
                new Point(0, 0),
                new Soldier(Side.CHO),
                new Point(1, 0),
                new Soldier(Side.CHO),
                new Point(0, 1),
                new Soldier(Side.HAN));

        BoardSetUp emptyBoardSetUp = (side) -> Collections.emptyMap();
        Board board = Board.setUp(testSetUp, emptyBoardSetUp);

        Point from = new Point(0, 0);
        Set<Point> destinations = board.destinations(from);

        assertThat(destinations.size()).isEqualTo(1);
        assertThat(destinations).contains(new Point(0, 1));
    }

    @Test
    @DisplayName("moveTo(): Point from에서 Point to로 기물을 이동시킨다.")
    void moveTo() {
        BoardSetUp testSetUp = (side) -> Map.of(
                new Point(0, 0),
                new Soldier(Side.CHO),
                new Point(1, 0),
                new Soldier(Side.CHO),
                new Point(0, 1),
                new Soldier(Side.HAN));

        BoardSetUp emptyBoardSetUp = (side) -> Collections.emptyMap();

        Board board = Board.setUp(testSetUp, emptyBoardSetUp);

        board.moveTo(new Point(0,0), new Point(0,1));

        assertThat(board.getBoard().size()).isEqualTo(2);
        assertThat(board.getBoard().containsKey(new Point(0,0))).isFalse();
    }


}
