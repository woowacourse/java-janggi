package janggi.domain.movestrategy;

import janggi.domain.board.BoardDirection;
import janggi.domain.board.Position;
import janggi.domain.movestrategy.rule.DirectionalOneStepMoveRule;
import janggi.domain.movestrategy.rule.PalaceDiagonalDirectionalOneStepMoveRule;
import janggi.domain.palace.Palace;
import janggi.domain.palace.PalaceFactory;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SoliderStrategyTest {

    private MoveStrategy hanSoliderStrategy;
    private MoveStrategy choSoliderStrategy;

    private Piece solider;
    private Piece otherTeamPiece;
    private Piece sameTeamPiece;

    @BeforeEach
    void setUp() {
        Palace hanPalace = PalaceFactory.createPalace(Team.HAN);
        Palace choPalace = PalaceFactory.createPalace(Team.CHO);

        hanSoliderStrategy = new DefaultMoveStrategy(List.of(
                new DirectionalOneStepMoveRule(BoardDirection.UP),
                new PalaceDiagonalDirectionalOneStepMoveRule(hanPalace, BoardDirection.UP)));
        choSoliderStrategy = new DefaultMoveStrategy(List.of(
                new DirectionalOneStepMoveRule(BoardDirection.DOWN),
                new PalaceDiagonalDirectionalOneStepMoveRule(choPalace, BoardDirection.DOWN)));


        solider = PieceFactory.createSolider(Team.HAN, BoardDirection.UP);

        otherTeamPiece = PieceFactory.createChariot(Team.CHO);
        sameTeamPiece = PieceFactory.createChariot(Team.HAN);
    }

    @ParameterizedTest
    @DisplayName("진영이 한이면 위로 한 칸 이동 가능하다.")
    @CsvSource({
            "3, 3, 3, 4",
            "3, 4, 3, 5",
            "3, 5, 3, 6"
    })
    void testMoveSoliderWhenTeamIsHAN(int preX, int preY, int nextX, int nextY) {
        Piece soliderPiece = PieceFactory.createSolider(Team.HAN, BoardDirection.UP);
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
        Piece soliderPiece = PieceFactory.createSolider(Team.HAN, BoardDirection.UP);
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
        Piece soliderPiece = PieceFactory.createSolider(Team.CHO, BoardDirection.DOWN);
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
        Piece soliderPiece = PieceFactory.createSolider(Team.CHO, BoardDirection.DOWN);
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
        Piece soliderPiece1 = PieceFactory.createSolider(Team.CHO, BoardDirection.DOWN);
        Piece soliderPiece2 = PieceFactory.createSolider(Team.HAN, BoardDirection.UP);
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
        Piece soliderPiece1 = PieceFactory.createSolider(Team.CHO, BoardDirection.DOWN);
        Piece soliderPiece2 = PieceFactory.createSolider(Team.HAN, BoardDirection.UP);
        assertThat(soliderPiece1.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
        assertThat(soliderPiece2.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("한나라 병은 앞으로 1칸 또는 좌우로 1칸 이동할 수 있다.")
    @CsvSource({
            "5, 5, 5, 6", // 앞으로 1칸 (Y 방향 1 증가)
            "5, 5, 4, 5", // 좌로 1칸
            "5, 5, 6, 5"  // 우로 1칸
    })
    void testHanSoliderCanMove(int fromX, int fromY, int toX, int toY) {
        Position from = new Position(fromX, fromY);
        Position to = new Position(toX, toY);

        assertThat(hanSoliderStrategy.canMove(from, to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("초나라 병은 앞으로 1칸 또는 좌우로 1칸 이동할 수 있다.")
    @CsvSource({
            "5, 5, 5, 4",
            "5, 5, 4, 5",
            "5, 5, 6, 5"
    })
    void testChoSoliderCanMove(int fromX, int fromY, int toX, int toY) {
        Position from = new Position(fromX, fromY);
        Position to = new Position(toX, toY);

        assertThat(choSoliderStrategy.canMove(from, to)).isTrue();
    }

    @ParameterizedTest
    @DisplayName("병은 절대 뒤로 가거나, 두 칸을 이동하거나, 대각선으로 갈 수 없다.")
    @CsvSource({
            "5, 5, 5, 4",
            "5, 5, 5, 7",
            "5, 5, 6, 6"
    })
    void testHanSoliderCannotMove(int fromX, int fromY, int toX, int toY) {
        Position from = new Position(fromX, fromY);
        Position to = new Position(toX, toY);

        assertThat(hanSoliderStrategy.canMove(from, to)).isFalse();
    }

    @Test
    @DisplayName("이동 경로에기물이 없어야 이동할 수 있다.")
    void testCheckPathRule_Empty() {
        assertThat(hanSoliderStrategy.checkPathRule(List.of())).isTrue();
    }

    @Test
    @DisplayName("이동 경로가 null이면 이동할 수 있다.")
    void testCanCaptureEmpty() {
        assertThat(hanSoliderStrategy.canCapture(solider, null)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 상대 진영 기물이 있으면 이동할 수 있다.")
    void testCanCaptureEnemy() {
        assertThat(hanSoliderStrategy.canCapture(solider, otherTeamPiece)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 아군 기물이 있으면 이동할 수 없다.")
    void testNotCanCaptureSameTeam() {
        assertThat(hanSoliderStrategy.canCapture(solider, sameTeamPiece)).isFalse();
    }
}
