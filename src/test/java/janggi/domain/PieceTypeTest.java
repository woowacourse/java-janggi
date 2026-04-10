package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.strategy.ElephantMoveStrategy;
import janggi.domain.strategy.HorseMoveStrategy;
import janggi.domain.strategy.SlideMoveStrategy;
import janggi.domain.strategy.StepMoveStrategy;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeTest {

    @Test
    @DisplayName("HORSE는 상하좌우를 기준으로 파생된 8개의 기하학적 최종 목적지를 반환한다.")
    void calculatePaths_Horse() {
        HorseMoveStrategy strategy = new HorseMoveStrategy();
        Position current = new Position(4, 4);

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, new Piece(Side.CHO, PieceType.HORSE, "0"));
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(
                current, EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), board
        );

        assertThat(destinations.getDestinations()).hasSize(8);
    }

    @Test
    @DisplayName("ELEPHANT는 상하좌우를 기준으로 파생된 8개의 기하학적 최종 목적지를 반환한다.")
    void calculatePaths_Elephant() {
        ElephantMoveStrategy strategy = new ElephantMoveStrategy();
        Position current = new Position(4, 4);

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, new Piece(Side.CHO, PieceType.ELEPHANT, "0"));
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(
                current, EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), board
        );

        assertThat(destinations.getDestinations()).hasSize(8);
    }

    @Test
    @DisplayName("한 칸만 이동하는 기물의 전략은 규칙에 맞춰 한 칸 이동하는 목적지들을 반환한다")
    void calculatePaths_Step() {
        StepMoveStrategy strategy = new StepMoveStrategy();
        Position current = new Position(4, 4);

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, new Piece(Side.CHO, PieceType.PALACE, "0"));
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(
                current, EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), board
        );

        assertThat(destinations.getDestinations()).hasSize(4);
    }

    @Test
    @DisplayName("여러 칸 이동하는 기물의 전략은 규칙에 맞춰 위치의 경계값까지 이동하는 목적지들을 반환한다")
    void calculatePaths_Slide() {
        SlideMoveStrategy strategy = new SlideMoveStrategy();
        Position current = new Position(4, 4);

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, new Piece(Side.CHO, PieceType.CHARIOT, "0"));
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(
                current, EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), board
        );

        assertThat(destinations.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("이동 경로 상의 멱(경유지)에 장애물이 있으면 해당 방향의 목적지들을 제외한다.")
    void determineDestinations_Horse_WithObstacle() {
        HorseMoveStrategy strategy = new HorseMoveStrategy();
        Position current = new Position(4, 4);

        Map<Position, Piece> boardState = new HashMap<>();
        boardState.put(current, new Piece(Side.HAN, PieceType.HORSE, "0"));
        Position obstacleTransit = new Position(5, 4);
        boardState.put(obstacleTransit, new Piece(Side.CHO, PieceType.SOLDIER, "0"));
        Board board = new Board(boardState);

        Destinations destinations = strategy.findDestinations(
                current, EnumSet.of(Direction.N, Direction.S, Direction.E, Direction.W), board
        );

        Position blockedDest1 = new Position(6, 3);
        Position blockedDest2 = new Position(6, 5);

        assertThat(destinations.getDestinations()).doesNotContain(blockedDest1, blockedDest2);
        assertThat(destinations.getDestinations()).hasSize(6);
    }
}
