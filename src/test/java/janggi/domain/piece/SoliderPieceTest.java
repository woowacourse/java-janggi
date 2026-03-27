package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.ElephantStrategy;
import janggi.domain.movestrategy.SoliderStrategy;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoliderPieceTest {

    @ParameterizedTest
    @DisplayName("진영이 한이면 위로 한 칸 이동 가능하다.")
    @CsvSource({
            "3, 3, 3, 4",
            "3, 4, 3, 5",
            "3, 5, 3, 6"
    })
    void testMoveSoliderWhenTeamIsHAN(int preX, int preY, int nextX, int nextY) {
        SoliderPiece soliderPiece = new SoliderPiece(Team.HAN, new SoliderStrategy());
        assertThat(soliderPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }

    @ParameterizedTest
    @DisplayName("진영이 한이면 아래로 이동이 불가능하다.")
    @CsvSource({
            "3, 3, 3, 2",
            "3, 4, 3, 3",
            "3, 5, 3, 4"
    })
    void testNotMoveSoliderWhenTeamIsHAN(int preX, int preY, int nextX, int nextY) {
        SoliderPiece soliderPiece = new SoliderPiece(Team.HAN, new SoliderStrategy());
        assertThat(soliderPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영이 초이면 아래로로 한 칸 이동 가능하다.")
    @CsvSource({
            "3, 3, 3, 2",
            "3, 4, 3, 3",
            "3, 5, 3, 4"
    })
    void testMoveSoliderWhenTeamIsCHO(int preX, int preY, int nextX, int nextY) {
        SoliderPiece soliderPiece = new SoliderPiece(Team.CHO, new SoliderStrategy());
        assertThat(soliderPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }


    @ParameterizedTest
    @DisplayName("진영이 초이면 위로 이동이 불가능하다.")
    @CsvSource({
            "3, 3, 3, 4",
            "3, 4, 3, 5",
            "3, 5, 3, 6"
    })
    void testNotMoveSoliderWhenTeamIsCHO(int preX, int preY, int nextX, int nextY) {
        SoliderPiece soliderPiece = new SoliderPiece(Team.CHO, new SoliderStrategy());
        assertThat(soliderPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("진영에 관계없이 좌우로 한 칸 이동 가능하다.")
    @CsvSource({
            "3, 3, 4, 3",
            "3, 4, 2, 4",
            "3, 5, 4, 5",
            "3, 5, 2, 5"
    })
    void testMoveSoliderHorizontally(int preX, int preY, int nextX, int nextY) {
        SoliderPiece soliderPiece1 = new SoliderPiece(Team.CHO, new SoliderStrategy());
        SoliderPiece soliderPiece2 = new SoliderPiece(Team.HAN, new SoliderStrategy());
        assertThat(soliderPiece1.canMove(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
        assertThat(soliderPiece2.canMove(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }


    @ParameterizedTest
    @DisplayName("진영에 관계없이 두 칸 이상 이동이 불가능하다.")
    @CsvSource({
            "3, 3, 5, 3",
            "3, 4, 1, 4",
            "3, 5, 3, 7",
            "3, 5, 3, 3"
    })
    void testNotMoveSoliderTwoStepMore(int preX, int preY, int nextX, int nextY) {
        SoliderPiece soliderPiece1 = new SoliderPiece(Team.CHO, new SoliderStrategy());
        SoliderPiece soliderPiece2 = new SoliderPiece(Team.HAN, new SoliderStrategy());
        assertThat(soliderPiece1.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
        assertThat(soliderPiece2.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @Test
    @DisplayName("병은 이동 경로에 같은 진영의 기물이 존재하면 움직일 수 없다.")
    void testNotMoveIfSameTeamPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 5), new ElephantPiece(Team.HAN, new ElephantStrategy()));
        SoliderPiece soliderPiece = new SoliderPiece(Team.HAN, new SoliderStrategy());
        assertThat(soliderPiece.determineMovingRule(positionPieces, new Position(5, 5))).isFalse();
    }

    @Test
    @DisplayName("병은 이동 경로에 기물이 존재하지 않으면 움직일 수 있다.")
    void testMoveNoPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        SoliderPiece soliderPiece = new SoliderPiece(Team.HAN, new SoliderStrategy());
        assertThat(soliderPiece.determineMovingRule(positionPieces, new Position(5, 5))).isTrue();
    }

    @Test
    @DisplayName("병은 이동 경로에 다른 진영의 기물이 존재하면 움직일 수 있다.")
    void testMoveOtherTeamPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 5), new ElephantPiece(Team.HAN, new ElephantStrategy()));
        SoliderPiece soliderPiece = new SoliderPiece(Team.HAN, new SoliderStrategy());
        assertThat(soliderPiece.determineMovingRule(positionPieces, new Position(5, 5))).isFalse();
    }
}
