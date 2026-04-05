package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.rule.PalaceDiagonalForwardMoveRule;
import janggi.domain.movestrategy.rule.StraightForwardMoveRule;
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

class CannonStrategyTest {

    private MoveStrategy cannonMoveStrategy;
    private Piece cannon;
    private Piece otherSideCannon;
    private Piece notCannon;
    private Piece otherSideNotCannon;

    @BeforeEach
    void setUp() {
        cannonMoveStrategy = new CannonStrategy(List.of(
                new StraightForwardMoveRule(),
                new PalaceDiagonalForwardMoveRule(List.of(
                        PalaceFactory.createPalace(Team.HAN),
                        PalaceFactory.createPalace(Team.CHO)))));
        cannon = PieceFactory.createCannon(Team.HAN);
        otherSideCannon = PieceFactory.createCannon(Team.CHO);
        notCannon = PieceFactory.createChariot(Team.HAN);
        otherSideNotCannon = PieceFactory.createChariot(Team.CHO);
    }

    @ParameterizedTest
    @DisplayName("포는 상하좌우 직전으로 칸 수 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 5, 3",
            "8, 3, 5, 3",
            "2, 3, 2, 8",
            "8, 3, 8, 1",
    })
    void testMoveCannon(int preX, int preY, int nextX, int nextY) {
        // when & then
        assertThat(cannonMoveStrategy.canMove(new Position(preX, preY), new Position(nextX, nextY)))
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
        // when & then
        assertThat(cannonMoveStrategy.canMove(new Position(preX, preY), new Position(nextX, nextY)))
                .isFalse();
    }

    @Test
    @DisplayName("포는 상하좌우 직전이면 칸 수에 상관없이 이동 가능하다.")
    void testFindDestinationPath() {
        // given
        Position from = new Position(2, 3);
        Position to = new Position(5, 3);

        // when
        List<Position> result = cannonMoveStrategy.findPath(from, to);

        // then
        assertThat(result).containsExactly(new Position(3, 3), new Position(4, 3));
    }

    @Test
    @DisplayName("경로에 포가 존재하면 통과할 수 없다.")
    void testNotCheckPathRuleWhenOnePieceInPath() {
        // given
        List<Piece> pathPieces1 = List.of(otherSideCannon);
        List<Piece> pathPieces2 = List.of(cannon);

        // when & then
        assertThat(cannonMoveStrategy.checkPathRule(pathPieces1)).isFalse();
        assertThat(cannonMoveStrategy.checkPathRule(pathPieces2)).isFalse();
    }

    @Test
    @DisplayName("경로에 아무 기물이 없으면 통과할 수 없다.")
    void testCheckPathRuleWhenNoPieceInPath() {
        // given
        List<Piece> emptyPieces = List.of();

        // when & then
        assertThat(cannonMoveStrategy.checkPathRule(emptyPieces)).isFalse();
    }

    @Test
    @DisplayName("경로에 기물이 2개 이상 있으면 통과할 수 없다.")
    void testCheckPathRuleWhenTwoMorePiecesInPath() {
        // given
        List<Piece> pieces = List.of(notCannon, notCannon, notCannon);
        List<Piece> pieces2 = List.of(notCannon, notCannon, notCannon, notCannon);

        // when & then
        assertThat(cannonMoveStrategy.checkPathRule(pieces)).isFalse();
        assertThat(cannonMoveStrategy.checkPathRule(pieces2)).isFalse();
    }

    @Test
    @DisplayName("경로에 포가 아닌 기물이 한 개 있다면 통과할 수 있다.")
    void testCheckPathRuleWhenOnePieceInPath() {
        // given
        List<Piece> pieces = List.of(notCannon);

        // when & then
        assertThat(cannonMoveStrategy.checkPathRule(pieces)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 포가 있으면 이동할 수 없다.")
    void testNotCaptureWhenCannonOnDestination() {
        // when & then
        assertThat(cannonMoveStrategy.canCapture(cannon, otherSideCannon)).isFalse();
        assertThat(cannonMoveStrategy.canCapture(otherSideCannon, cannon)).isFalse();
    }

    @Test
    @DisplayName("도착 경로에 포가 아닌 상대 기물이 있다면 이동할 수 있다.")
    void testCanCaptureWhenOtherSidePieceOnDestination() {
        // when & then
        assertThat(cannonMoveStrategy.canCapture(cannon, otherSideNotCannon)).isTrue();
    }

    @Test
    @DisplayName("도착 경로가 null이면 이동할 수 있다.")
    void testCanCaptureWhenDestinationIsEmpty() {
        // when & then
        assertThat(cannonMoveStrategy.canCapture(cannon, null)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 같은 진영 기물이 있다면 이동할 수 없다.")
    void testNotCaptureWhenAllyPieceOnDestination() {
        // when & then
        assertThat(cannonMoveStrategy.canCapture(cannon, notCannon)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("포는 궁성 대각선을 따라 이동 가능하다.")
    @CsvSource({
            "4, 1, 5, 2",
            "4, 1, 6, 3",
            "6, 3, 4, 1",
            "4, 8, 6, 10",
            "6, 10, 4, 8"
    })
    void testMoveCannonDiagonalInPalace(int preX, int preY, int nextX, int nextY) {
        assertThat(cannonMoveStrategy.canMove(new Position(preX, preY), new Position(nextX, nextY)))
                .isTrue();
    }

    @Test
    @DisplayName("포는 궁성 대각선 2칸 이동 시 포가 아닌 기물 1개를 넘어야 이동 가능하다.")
    void testCheckPathRuleWhenOnePieceInPalaceDiagonalPath() {
        // given
        List<Piece> pathPieces = List.of(notCannon);

        // when & then
        assertThat(cannonMoveStrategy.checkPathRule(pathPieces)).isTrue();
    }

    @Test
    @DisplayName("포는 궁성 대각선 2칸 이동 시 포를 넘을 수 없다.")
    void testNotCheckPathRuleWhenCannonInPalaceDiagonalPath() {
        // given
        List<Piece> pathPieces = List.of(otherSideCannon);

        // when & then
        assertThat(cannonMoveStrategy.checkPathRule(pathPieces)).isFalse();
    }
}
