package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierPieceTest {

    @ParameterizedTest
    @DisplayName("진영이 한이면 위로 한 칸 이동 가능하다.")
    @CsvSource({
            "3, 3, 3, 4",
            "3, 4, 3, 5",
            "3, 5, 3, 6"
    })
    void testMoveSoldierWhenTeamIsHAN(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece = new SoldierPiece(Team.HAN);
        assertThat(
                soldierPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("진영이 한이면 아래로 이동이 불가능하다.")
    @CsvSource({
            "3, 3, 3, 2",
            "3, 4, 3, 3",
            "3, 5, 3, 4"
    })
    void testNotMoveSoldierWhenTeamIsHAN(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece = new SoldierPiece(Team.HAN);
        assertThat(
                soldierPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영이 초이면 아래로 한 칸 이동 가능하다.")
    @CsvSource({
            "3, 3, 3, 2",
            "3, 4, 3, 3",
            "3, 5, 3, 4"
    })
    void testMoveSoldierWhenTeamIsCHO(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece = new SoldierPiece(Team.CHO);
        assertThat(
                soldierPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }


    @ParameterizedTest
    @DisplayName("진영이 초이면 위로 이동이 불가능하다.")
    @CsvSource({
            "3, 3, 3, 4",
            "3, 4, 3, 5",
            "3, 5, 3, 6"
    })
    void testNotMoveSoldierWhenTeamIsCHO(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece = new SoldierPiece(Team.CHO);
        assertThat(
                soldierPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영에 관계없이 좌우로 한 칸 이동 가능하다.")
    @CsvSource({
            "3, 3, 4, 3",
            "3, 4, 2, 4",
            "3, 5, 4, 5",
            "3, 5, 2, 5"
    })
    void testMoveSoldierHorizontally(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece1 = new SoldierPiece(Team.CHO);
        SoldierPiece soldierPiece2 = new SoldierPiece(Team.HAN);
        assertThat(
                soldierPiece1.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
        assertThat(
                soldierPiece2.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("진영이 한인 병은 초 궁성 안에서 대각선 전진이 가능하다.")
    @CsvSource({
            "4,8,5,9",
            "6,8,5,9",
            "5,9,4,10",
            "5,9,6,10"
    })
    void testMoveHanSoldierDiagonallyInChoPalace(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece = new SoldierPiece(Team.HAN);
        assertThat(
                soldierPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("진영이 초인 병은 한 궁성 안에서 대각선 전진이 가능하다.")
    @CsvSource({
            "4,3,5,2",
            "6,3,5,2",
            "5,2,4,1",
            "5,2,6,1"
    })
    void testMoveChoSoldierDiagonallyInHanPalace(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece = new SoldierPiece(Team.CHO);
        assertThat(
                soldierPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("진영이 한인 병은 일반 위치나 자기 궁성 안에서는 대각선 이동할 수 없다.")
    @CsvSource({
            "3,3,4,4",
            "4,1,5,2",
            "6,1,5,2",
            "5,2,4,3",
            "5,2,6,3"
    })
    void testNotMoveHanSoldierDiagonallyOutsideChoPalace(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece = new SoldierPiece(Team.HAN);
        assertThat(
                soldierPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영이 초인 병은 일반 위치나 자기 궁성 안에서는 대각선 이동할 수 없다.")
    @CsvSource({
            "3,3,4,2",
            "4,8,5,9",
            "6,8,5,9",
            "5,9,4,10",
            "5,9,6,10"
    })
    void testNotMoveChoSoldierDiagonallyOutsideHanPalace(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece = new SoldierPiece(Team.CHO);
        assertThat(
                soldierPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }


    @ParameterizedTest
    @DisplayName("진영에 관계없이 두 칸 이상 이동이 불가능하다.")
    @CsvSource({
            "3, 3, 5, 3",
            "3, 4, 1, 4",
            "3, 5, 3, 7",
            "3, 5, 3, 3"
    })
    void testNotMoveSoldierTwoStepMore(int preX, int preY, int nextX, int nextY) {
        SoldierPiece soldierPiece1 = new SoldierPiece(Team.CHO);
        SoldierPiece soldierPiece2 = new SoldierPiece(Team.HAN);
        assertThat(
                soldierPiece1.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
        assertThat(
                soldierPiece2.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @Test
    @DisplayName("병은 이동 경로에 같은 진영의 기물이 존재하면 움직일 수 없다.")
    void testNotMoveIfSameTeamPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 5), new ElephantPiece(Team.HAN));
        SoldierPiece soldierPiece = new SoldierPiece(Team.HAN);
        assertThat(soldierPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 5))).isFalse();
    }

    @Test
    @DisplayName("병은 이동 경로에 기물이 존재하지 않으면 움직일 수 있다.")
    void testMoveNoPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        SoldierPiece soldierPiece = new SoldierPiece(Team.HAN);
        assertThat(soldierPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 5))).isTrue();
    }

    @Test
    @DisplayName("병은 이동 경로에 다른 진영의 기물이 존재하면 움직일 수 있다.")
    void testMoveOtherTeamPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 5), new ElephantPiece(Team.CHO));
        SoldierPiece soldierPiece = new SoldierPiece(Team.HAN);
        assertThat(soldierPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 5))).isTrue();
    }
}
