package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CannonPieceTest {

    @ParameterizedTest
    @DisplayName("포는 상하좌우 직선으로 칸 수 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 5, 3",
            "8, 3, 5, 3",
            "2, 3, 2, 8",
            "8, 3, 8, 1",
    })
    void testMoveCannon(int preX, int preY, int nextX, int nextY) {
        CannonPiece cannonPiece = new CannonPiece(Team.HAN);

        assertThat(cannonPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY)))
                .isTrue();
    }

    @ParameterizedTest
    @DisplayName("포는 궁성 안 대각선 반대편 코너로 이동할 수 있다.")
    @CsvSource({
            "4, 1, 6, 3",
            "6, 1, 4, 3",
            "4, 8, 6, 10",
            "6, 8, 4, 10",
    })
    void testMoveCannonDiagonallyInPalace(int preX, int preY, int nextX, int nextY) {
        CannonPiece cannonPiece = new CannonPiece(Team.HAN);

        assertThat(cannonPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY)))
                .isTrue();
    }

    @ParameterizedTest
    @DisplayName("포는 상하좌우 직선이 아니면 이동할 수 없다.")
    @CsvSource({
            "2, 3, 6, 4",
            "8, 3, 9, 1",
            "2, 3, 3, 6",
            "8, 3, 2, 4",
    })
    void testNotMovableCannon(int preX, int preY, int nextX, int nextY) {
        CannonPiece cannonPiece = new CannonPiece(Team.HAN);

        assertThat(cannonPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY)))
                .isFalse();
    }

    @ParameterizedTest
    @DisplayName("포는 이동 방향에 맞는 순서로 도착 좌표까지의 경로를 반환한다.")
    @CsvSource({
            "2, 3, 5, 3, 3, 3, 4, 3, 5, 3",
            "5, 3, 2, 3, 4, 3, 3, 3, 2, 3",
            "2, 3, 2, 6, 2, 4, 2, 5, 2, 6",
            "2, 6, 2, 3, 2, 5, 2, 4, 2, 3"
    })
    void testFindDestinationPath(int preX, int preY, int nextX, int nextY,
                                 int pathX1, int pathY1, int pathX2, int pathY2,
                                 int pathX3, int pathY3) {
        Position from = new Position(preX, preY);
        Position to = new Position(nextX, nextY);
        CannonPiece cannonPiece = new CannonPiece(Team.HAN);

        List<Position> result = cannonPiece.findPath(from, to);
        assertThat(result).containsExactly(
                new Position(pathX1, pathY1),
                new Position(pathX2, pathY2),
                new Position(pathX3, pathY3)
        );
    }

    @ParameterizedTest
    @DisplayName("포는 궁성 대각선 이동 시 도착 좌표까지의 경로를 반환한다.")
    @CsvSource({
            "4, 1, 6, 3, 5, 2",
            "6, 1, 4, 3, 5, 2",
            "4, 8, 6, 10, 5, 9",
            "6, 8, 4, 10, 5, 9"
    })
    void testFindDestinationDiagonalPath(int preX, int preY, int nextX, int nextY,
                                         int pathX1, int pathY1) {
        Position from = new Position(preX, preY);
        Position to = new Position(nextX, nextY);
        CannonPiece cannonPiece = new CannonPiece(Team.HAN);

        List<Position> result = cannonPiece.findPath(from, to);
        assertThat(result).containsExactly(new Position(pathX1, pathY1), new Position(nextX, nextY));
    }

    @Test
    @DisplayName("포 이동 경로에 기물이 3개 있으면 이동 불가능하다.")
    void testNotMoveIfOtherPiecesInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN));
        positionPieces.put(new Position(5, 6), new GeneralPiece(Team.CHO));
        positionPieces.put(new Position(6, 6), new ElephantPiece(Team.CHO));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 6))).isFalse();
    }

    @Test
    @DisplayName("포 이동 경로에 기물이 2개 있으면 이동 불가능하다.")
    void testNotMoveIfTwoPiecesInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN));
        positionPieces.put(new Position(5, 6), new GeneralPiece(Team.CHO));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 6))).isFalse();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 아닌 기물이 한 개 있고, 도착 경로에 포가 아닌 상대 기물이 한 개 있으면 이동 가능하다.")
    void testMoveIfNoCannonInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN));
        positionPieces.put(new Position(7, 6), new GeneralPiece(Team.CHO));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isTrue();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 아닌 기물이 한 개 있고, 도착 경로에 같은 진영 기물이 한 개 있으면 이동 불가능하다.")
    void testNotMoveIfNoCannonInPathAndSameTeamInDestination() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN));
        positionPieces.put(new Position(7, 6), new GeneralPiece(Team.HAN));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isFalse();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 아닌 기물이 1개만 존재하고, 도착 경로에 기물이 없다면 이동 가능하다.")
    void testMoveIfOnePieceInPathExceptCannon() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isTrue();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 있다면 이동 불가능하다.")
    void testNotMoveIfCannonInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new CannonPiece(Team.HAN));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isFalse();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 아닌 기물이 있고, 도착 지점에 포가 존재한다면 이동 불가능하다.")
    void testNotMoveIfCannonInDestination() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(7, 6), new CannonPiece(Team.CHO));
        positionPieces.put(new Position(5, 6), new SoldierPiece(Team.CHO));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isFalse();
    }

    @Test
    @DisplayName("포는 궁성 대각선 중심에 포가 아닌 기물이 있으면 이동할 수 있다.")
    void testMoveCannonIfBridgeExistsInPalaceDiagonalPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 2), new SoldierPiece(Team.HAN));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 3))).isTrue();
    }

    @Test
    @DisplayName("포는 궁성 대각선 중심에 포가 있으면 이동할 수 없다.")
    void testNotMoveCannonIfCannonExistsInPalaceDiagonalPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 2), new CannonPiece(Team.HAN));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 3))).isFalse();
    }

    @Test
    @DisplayName("포는 궁성 대각선 중심에 기물이 없으면 이동할 수 없다.")
    void testNotMoveCannonIfNoBridgeExistsInPalaceDiagonalPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 3))).isFalse();
    }

    @Test
    @DisplayName("포는 궁성 대각선 이동 시 도착 지점에 포가 있으면 이동할 수 없다.")
    void testNotMoveCannonIfDestinationIsCannonInPalaceDiagonalPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 2), new SoldierPiece(Team.HAN));
        positionPieces.put(new Position(6, 3), new CannonPiece(Team.CHO));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN);
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 3))).isFalse();
    }
}
