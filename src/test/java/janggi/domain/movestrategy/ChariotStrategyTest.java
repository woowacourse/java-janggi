package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.rule.PalaceDiagonalForwardMoveRule;
import janggi.domain.movestrategy.rule.StraightForwardMoveRule;
import janggi.domain.palace.PalaceFactory;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChariotStrategyTest {

    private MoveStrategy chariotMoveStrategy;
    private Piece chariot;
    private Piece otherTeamPiece;
    private Piece sameTeamPiece;

    @BeforeEach
    void setUp() {
        chariotMoveStrategy = new DefaultMoveStrategy(List.of(
                new StraightForwardMoveRule(),
                new PalaceDiagonalForwardMoveRule(List.of(
                        PalaceFactory.createPalace(Team.HAN),
                        PalaceFactory.createPalace(Team.CHO)))));
        chariot = PieceFactory.createChariot(Team.HAN);
        otherTeamPiece = PieceFactory.createCannon(Team.CHO);
        sameTeamPiece = PieceFactory.createCannon(Team.HAN);
    }

    @ParameterizedTest
    @DisplayName("차는 상하좌우 직전이면 칸 수에 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 6, 4",
            "8, 3, 9, 1",
            "2, 3, 3, 6",
            "8, 3, 2, 4",
    })
    void testNotMovableChariot(int preX, int preY, int nextX, int nextY) {
        Piece chariotPiece = PieceFactory.createChariot(Team.HAN);

        Assertions.assertThat(chariotPiece.canMove(new Position(preX, preY), new Position(nextX, nextY)))
                .isFalse();
    }

    @ParameterizedTest
    @DisplayName("차는 상하좌우 직전으로 칸 수 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 5, 3",
            "8, 3, 5, 3",
            "2, 3, 2, 8",
            "8, 3, 8, 1",
    })
    void testMoveChariot(int preX, int preY, int nextX, int nextY) {
        Piece chariotPiece = PieceFactory.createChariot(Team.HAN);

        Assertions.assertThat(chariotPiece.canMove(new Position(preX, preY), new Position(nextX, nextY)))
                .isTrue();
    }

    @Test
    @DisplayName("차는 상하좌우 직전이면 칸 수에 상관없이 이동 가능하다.")
    void testFindDestinationPath() {
        Position from = new Position(2, 3);
        Position to = new Position(5, 3);
        Piece chariotPiece = PieceFactory.createChariot(Team.HAN);

        List<Position> result = chariotPiece.findPath(from, to);
        assertThat(result).containsExactly(new Position(3, 3), new Position(4, 3));
    }

    @Test
    @DisplayName("이동 경로 상에 기물이 아무것도 없으면 통과할 수 있다.")
    void testCheckPathRuleWhenNoPieceInPath() {
        // given
        List<Piece> emptyPieces = List.of();
        // when & then
        assertThat(chariotMoveStrategy.checkPathRule(emptyPieces)).isTrue();
    }

    @Test
    @DisplayName("이동 경로 상에 기물이 1개라도 있으면 막혀서 통과할 수 없다.")
    void testNotCheckPathRuleWhenAnyPieceInPath() {
        List<Piece> pathPieces = List.of(otherTeamPiece);
        List<Piece> pathPieces2 = List.of(otherTeamPiece, sameTeamPiece);
        // when & then
        assertThat(chariotMoveStrategy.checkPathRule(pathPieces)).isFalse();
        assertThat(chariotMoveStrategy.checkPathRule(pathPieces2)).isFalse();
    }

    @Test
    @DisplayName("도착 경로가 null이면 이동할 수 있다.")
    void testCanCaptureWhenDestinationIsEmpty() {
        // when & then
        assertThat(chariotMoveStrategy.canCapture(chariot, null)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 상대 진영 기물이 있으면 이동할 수 있다.")
    void testCanCaptureWhenDestinationIsEnemy() {
        // when & then
        assertThat(chariotMoveStrategy.canCapture(chariot, otherTeamPiece)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 상대 진영 기물이 있으면 이동할 수 없다.")
    void testNotCanCaptureWhenDestinationIsAlly() {
        // when & then
        assertThat(chariotMoveStrategy.canCapture(chariot, sameTeamPiece)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("차는 궁성 대각선을 따라 이동 가능하다.")
    @CsvSource({
            "4, 1, 5, 2",
            "4, 1, 6, 3",
            "6, 3, 4, 1",
            "4, 8, 6, 10",
            "6, 10, 4, 8"
    })
    void testMoveChariotDiagonalInPalace(int preX, int preY, int nextX, int nextY) {
        assertThat(chariotMoveStrategy.canMove(new Position(preX, preY), new Position(nextX, nextY)))
                .isTrue();
    }

    @Test
    @DisplayName("차는 궁성 대각선 2칸 이동 시 경로에 기물이 있으면 이동할 수 없다.")
    void testNotCheckPathRuleWhenPieceInPalaceDiagonalPath() {
        // given
        List<Piece> pathPieces = List.of(otherTeamPiece);

        // when & then
        assertThat(chariotMoveStrategy.checkPathRule(pathPieces)).isFalse();
    }
}
