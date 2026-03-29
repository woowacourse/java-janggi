package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.CannonStrategy;
import janggi.domain.movestrategy.ChoSoldierStrategy;
import janggi.domain.movestrategy.ElephantStrategy;
import janggi.domain.movestrategy.GeneralStrategy;
import janggi.domain.movestrategy.HanSoldierStrategy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CannonPieceTest {

    @ParameterizedTest
    @DisplayName("포는 상하좌우 직전으로 칸 수 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 5, 3",
            "8, 3, 5, 3",
            "2, 3, 2, 8",
            "8, 3, 8, 1",
    })
    void testMoveCannon(int preX, int preY, int nextX, int nextY) {
        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());

        assertThat(cannonPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY)))
                .isTrue();
    }

    @ParameterizedTest
    @DisplayName("포는 상하좌우 직전이면 칸 수에 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 6, 4",
            "8, 3, 9, 1",
            "2, 3, 3, 6",
            "8, 3, 2, 4",
    })
    void testNotMovableCannon(int preX, int preY, int nextX, int nextY) {
        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());

        assertThat(cannonPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY)))
                .isFalse();
    }

    @Test
    @DisplayName("포는 상하좌우 직전이면 칸 수에 상관없이 이동 가능하다.")
    void testFindDestinationPath() {
        Position from = new Position(2, 3);
        Position to = new Position(5, 3);
        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());

        List<Position> result = cannonPiece.findPath(from, to);
        assertThat(result).containsExactly(new Position(3, 3), new Position(4, 3), new Position(5, 3));
    }

    @Test
    @DisplayName("포 이동 경로에 기물이 3개 있으면 이동 불가능하다.")
    void testNotMoveIfOtherPiecesInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN, new HanSoldierStrategy()));
        positionPieces.put(new Position(5, 6), new GeneralPiece(Team.CHO, new GeneralStrategy()));
        positionPieces.put(new Position(6, 6), new ElephantPiece(Team.CHO, new ElephantStrategy()));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 6))).isFalse();
    }

    @Test
    @DisplayName("포 이동 경로에 기물이 2개 있으면 이동 불가능하다.")
    void testNotMoveIfTwoPiecesInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN, new HanSoldierStrategy()));
        positionPieces.put(new Position(5, 6), new GeneralPiece(Team.CHO, new GeneralStrategy()));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 6))).isFalse();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 아닌 기물이 한 개 있고, 도착 경로에 포가 아닌 상대 기물이 한 개 있으면 이동 가능하다.")
    void testMoveIfNoCannonInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN, new HanSoldierStrategy()));
        positionPieces.put(new Position(7, 6), new GeneralPiece(Team.CHO, new GeneralStrategy()));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isTrue();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 아닌 기물이 한 개 있고, 도착 경로에 같은 진영 기물이 한 개 있으면 이동 불가능하다.")
    void testNotMoveIfNoCannonInPathAndSameTeamInDestination() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN, new HanSoldierStrategy()));
        positionPieces.put(new Position(7, 6), new GeneralPiece(Team.HAN, new GeneralStrategy()));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isFalse();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 아닌 기물이 1개만 존재하고, 도착 경로에 기물이 없다면 이동 가능하다.")
    void testMoveIfOnePieceInPathExceptCannon() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new SoldierPiece(Team.HAN, new HanSoldierStrategy()));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isTrue();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 있다면 이동 불가능하다.")
    void testNotMoveIfCannonInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(4, 6), new CannonPiece(Team.HAN, new CannonStrategy()));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isFalse();
    }

    @Test
    @DisplayName("포 이동 경로에 포가 아닌 기물이 있고, 도착 지점에 포가 존재한다면 이동 불가능하다.")
    void testNotMoveIfCannonInDestination() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(7, 6), new CannonPiece(Team.CHO, new CannonStrategy()));
        positionPieces.put(new Position(5, 6), new SoldierPiece(Team.CHO, new ChoSoldierStrategy()));

        CannonPiece cannonPiece = new CannonPiece(Team.HAN, new CannonStrategy());
        assertThat(cannonPiece.canMoveBySpecialMovingRule(positionPieces, new Position(7, 6))).isFalse();
    }
}
