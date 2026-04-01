package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTypeTest {

    @Test
    @DisplayName("HORSE의 calculatePaths는 상하좌우를 기준으로 파생된 8개의 기하학적 경로를 반환한다.")
    void calculatePaths_Horse() {
        Position current = new Position(4, 4);
        PieceType horse = PieceType.HORSE;

        Paths paths = horse.calculatePaths(current, Side.CHO);

        int pathCount = 0;
        for (Path path : paths) {
            pathCount++;
        }

        assertThat(pathCount).isEqualTo(8);
    }

    @Test
    @DisplayName("ELEPHANT의 calculatePaths는 상하좌우를 기준으로 파생된 8개의 기하학적 경로를 반환한다.")
    void calculatePaths_Elephant() {
        Position current = new Position(4, 4);
        PieceType elephant = PieceType.ELEPHANT;

        Paths paths = elephant.calculatePaths(current, Side.CHO);

        int pathCount = 0;
        for (Path path : paths) {
            pathCount++;
        }

        assertThat(pathCount).isEqualTo(8);
    }

    @Test
    @DisplayName("한 칸만 이동하는 기물의 calculatePaths는 규칙에 맞춰 한 칸 이동하는 경로들을 반환한다")
    void calculatePaths_Step() {
        Position current = new Position(4, 4);
        PieceType palace = PieceType.PALACE;

        Paths paths = palace.calculatePaths(current, Side.CHO);

        int pathCount = 0;
        for (Path path : paths) {
            pathCount++;
        }

        assertThat(pathCount).isEqualTo(4);
    }

    @Test
    @DisplayName("여러 칸 이동하는 기물의 calculatePaths는 규칙에 맞춰 위치의 경계값까지 이동하는 경로들을 반환한다")
    void calculatePaths_Slide() {
        Position current = new Position(4, 4);
        PieceType chariot = PieceType.CHARIOT;

        Paths paths = chariot.calculatePaths(current, Side.CHO);

        assertThat(paths).isNotEmpty();
    }

    @Test
    @DisplayName("determineDestinations는 경로 상의 멱(경유지)에 장애물이 있으면 해당 목적지를 제외한다.")
    void determineDestinations_Horse_WithObstacle() {
        Position current = new Position(4, 4);
        PieceType horse = PieceType.HORSE;
        Paths paths = horse.calculatePaths(current, Side.CHO);

        Map<Position, Piece> boardState = new HashMap<>();
        Position obstacleTransit = new Position(5, 4);
        boardState.put(obstacleTransit, new Piece(Side.CHO, PieceType.SOLDIER, "0"));

        Piece movingPiece = new Piece(Side.HAN, PieceType.HORSE, "0");

        List<Position> destinations = horse.determineDestinations(paths, boardState, movingPiece);

        Position blockedDest1 = new Position(6, 3);
        Position blockedDest2 = new Position(6, 5);

        assertThat(destinations).doesNotContain(blockedDest1, blockedDest2);
        assertThat(destinations).hasSize(6);
    }
}
