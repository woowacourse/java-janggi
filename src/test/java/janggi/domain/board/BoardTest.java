package janggi.domain.board;


import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.coordination.BoardCoordination;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.fixed.Soldier;
import janggi.domain.point.Point;
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
        BoardSetUp testSetUp = side -> Map.of(
                new Point(0, 0),
                new Soldier(Side.CHO),
                new Point(1, 0),
                new Soldier(Side.CHO),
                new Point(0, 1),
                new Soldier(Side.HAN));

        BoardSetUp emptyBoardSetUp = side -> Collections.emptyMap();
        Board board = Board.setUp(testSetUp, emptyBoardSetUp);

        Point from = new Point(0, 0);
        Set<Point> destinations = board.destinations(board.getPieceMovements(from), from, BoardCoordination::isInRange);

        assertThat(destinations).hasSize(1)
                .contains(new Point(0, 1));
    }

    @Test
    @DisplayName("moveTo(): Point from에서 Point to로 기물을 이동시킨다.")
    void moveTo() {
        BoardSetUp choBoardSetUp = side -> Map.of(
                new Point(0, 0),
                new Soldier(side),
                new Point(1, 0),
                new Soldier(side));

        BoardSetUp hanBoardSetUp = side -> Map.of(new Point(0, 1),
                new Soldier(side));

        Board board = Board.setUp(choBoardSetUp, hanBoardSetUp);

        board.moveTo(new Point(0, 0), new Point(0, 1));

        assertThat(board.getPieces()).hasSize(2);
        assertThat(board.getPieces().containsKey(new Point(0, 0))).isFalse();
    }
}
