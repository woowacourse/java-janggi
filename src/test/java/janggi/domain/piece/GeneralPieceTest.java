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

class GeneralPieceTest {

    @ParameterizedTest
    @DisplayName("궁은 상하좌우 한 칸씩 이동 가능하다.")
    @CsvSource({
            "5,2,6,2",
            "5,2,4,2",
            "5,2,5,3",
            "5,2,5,1"
    })
    void testMoveGeneral(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(
                generalPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("진영이 한인 궁은 한 궁성 안 대각선으로 한 칸 이동할 수 있다.")
    @CsvSource({
            "5,2,4,1",
            "5,2,6,1",
            "5,2,4,3",
            "5,2,6,3"
    })
    void testMoveGeneralDiagonallyInHanPalace(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(
                generalPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("진영이 초인 궁은 초 궁성 안 대각선으로 한 칸 이동할 수 있다.")
    @CsvSource({
            "5,9,4,8",
            "5,9,6,8",
            "5,9,4,10",
            "5,9,6,10"
    })
    void testMoveGeneralDiagonallyInChoPalace(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.CHO);
        assertThat(
                generalPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("진영이 한인 궁은 한 궁성 밖으로 이동할 수 없다.")
    @CsvSource({
            "4,1,3,1",
            "6,1,7,1",
            "5,3,5,4"
    })
    void testNotMoveGeneralOutsideHanPalace(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(
                generalPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영이 초인 궁은 초 궁성 밖으로 이동할 수 없다.")
    @CsvSource({
            "4,8,3,8",
            "6,8,7,8",
            "5,8,5,7"
    })
    void testNotMoveGeneralOutsideChoPalace(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.CHO);
        assertThat(
                generalPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영이 한인 궁은 한 궁성 안 대각선 선이 아닌 방향으로는 이동할 수 없다.")
    @CsvSource({
            "4,2,5,3",
            "6,2,5,3"
    })
    void testNotMoveGeneralDiagonallyOutsideHanPalaceLine(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(
                generalPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영이 초인 궁은 초 궁성 안 대각선 선이 아닌 방향으로는 이동할 수 없다.")
    @CsvSource({
            "4,9,5,10",
            "6,9,5,10"
    })
    void testNotMoveGeneralDiagonallyOutsideChoPalaceLine(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.CHO);
        assertThat(
                generalPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }


    @ParameterizedTest
    @DisplayName("궁은 상하좌우 두 칸 이상 이동하지 못한다.")
    @CsvSource({
            "5,2,7,2",
            "5,2,3,4",
            "5,2,5,4",
            "5,2,3,2"
    })
    void testNotMovableGeneral(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(
                generalPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("도착 좌표까지의 경로를 반환한다.")
    @CsvSource({
            "5,2,6,2",
            "5,2,4,2",
            "5,2,5,3",
            "5,2,5,1"
    })
    void testFindDestinationPath(int preX, int preY, int nextX, int nextY) {
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        List<Position> path = generalPiece.findPath(new Position(preX, preY), new Position(nextX, nextY));
        assertThat(path).containsExactly(new Position(nextX, nextY));
    }

    @Test
    @DisplayName("궁은 이동 경로에 같은 진영의 기물이 존재하면 움직일 수 없다.")
    void testNotMoveIfSameTeamPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 5), new ElephantPiece(Team.HAN));
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(generalPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 5))).isFalse();
    }

    @Test
    @DisplayName("궁은 이동 경로에 기물이 존재하지 않으면 움직일 수 있다.")
    void testMoveNoPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(generalPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 6))).isTrue();
    }

    @Test
    @DisplayName("궁은 이동 경로에 다른 진영의 기물이 존재하면 움직일 수 있다.")
    void testMoveOtherTeamPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 5), new ElephantPiece(Team.CHO));
        GeneralPiece generalPiece = new GeneralPiece(Team.HAN);
        assertThat(generalPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 5))).isTrue();
    }
}
