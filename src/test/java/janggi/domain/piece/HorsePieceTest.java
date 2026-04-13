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

class HorsePieceTest {

    @ParameterizedTest
    @DisplayName("마는 직선 한 칸 대각선으로 한 칸 이동 가능하다.")
    @CsvSource({
            "5, 4, 4, 2", "5, 4, 6, 2",
            "5, 4, 3, 3", "5, 4, 7, 3",
            "5, 4, 3, 5", "5, 4, 7, 5",
            "5, 4, 4, 6", "5, 4, 6, 6",
    })
    void testMovableHorse(int preX, int preY, int nextX, int nextY) {
        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        assertThat(horsePiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }


    @ParameterizedTest
    @DisplayName("마는 직선 한 칸 대각선으로 한 칸 이외에 이동이 불가능하다.")
    @CsvSource({
            "5, 4, 4, 3", "5, 4, 6, 1",
            "5, 4, 3, 4", "5, 4, 7, 2",
            "5, 4, 3, 6", "5, 4, 7, 4",
            "5, 4, 4, 7", "5, 4, 6, 5",
    })
    void testNotMovableHorse(int preX, int preY, int nextX, int nextY) {
        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        assertThat(horsePiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("도착 좌표까지의 경로를 반환한다.")
    @CsvSource({
            "5, 4, 4, 2, 5, 3",
            "5, 4, 6, 2, 5, 3",
            "5, 4, 3, 3, 4, 4",
            "5, 4, 7, 3, 6, 4",
            "5, 4, 3, 5, 4, 4",
            "5, 4, 7, 5, 6, 4",
            "5, 4, 4, 6, 5, 5",
            "5, 4, 6, 6, 5, 5",
    })
    void testFindDestinationPath(int preX, int preY, int nextX, int nextY,
                                 int pathX1, int pathY1) {
        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        List<Position> path = horsePiece.findPath(new Position(preX, preY), new Position(nextX, nextY));
        assertThat(path).containsExactly(new Position(pathX1, pathY1), new Position(nextX, nextY));
    }

    @Test
    @DisplayName("마 이동 경로에 기물이 1개 있다면 이동할 수 없다.")
    void testMoveOtherPiecesInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 5), new ElephantPiece(Team.HAN));

        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        assertThat(horsePiece.canMoveBySpecialMovingRule(positionPieces, new Position(6, 6))).isFalse();
    }

    @Test
    @DisplayName("마 이동 경로에 기물이 없고 도착 경로에 같은 진영 기물이 있다면 이동할 수 없다.")
    void testNotMoveIfSameTeamPieceInDestination() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 6), new ElephantPiece(Team.HAN));

        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        assertThat(horsePiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 6))).isFalse();
    }

    @Test
    @DisplayName("마 이동 경로에 기물이 없고 도착 경로에 상대 진영 기물이 있다면 이동할 수 있다.")
    void testMoveIfOtherTeamPieceInDestination() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 6), new ElephantPiece(Team.CHO));

        HorsePiece horsePiece = new HorsePiece(Team.HAN);
        assertThat(horsePiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 6))).isTrue();
    }
}
