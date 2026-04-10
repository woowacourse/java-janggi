package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.rule.PalaceDiagonalOneStepMoveRule;
import janggi.domain.movestrategy.rule.StraightOneStepMoveRule;
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

class GeneralStrategyTest {

    private MoveStrategy generalStrategy;
    private Piece general;
    private Piece otherTeamPiece;
    private Piece sameTeamPiece;

    @BeforeEach
    void setUp() {
        generalStrategy = new PalaceRestrictMoveStrategy(PalaceFactory.createPalace(Team.HAN),
                List.of(new StraightOneStepMoveRule(),
                        new PalaceDiagonalOneStepMoveRule(PalaceFactory.createPalace(Team.HAN))));
        general = PieceFactory.createGeneral(Team.HAN);
        otherTeamPiece = PieceFactory.createCannon(Team.CHO);
        sameTeamPiece = PieceFactory.createCannon(Team.HAN);
    }

    @ParameterizedTest
    @DisplayName("궁은 상하좌우 한 칸씩 이동 가능하다.")
    @CsvSource({
            "5,2,6,2",
            "5,2,4,2",
            "5,2,5,3",
            "5,2,5,1"
    })
    void testMoveGeneral(int preX, int preY, int nextX, int nextY) {
        Piece generalPiece = PieceFactory.createGeneral(Team.HAN);
        assertThat(generalPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }


    @ParameterizedTest
    @DisplayName("궁은 상하좌우 두 칸 이상 이동하지 못한다.")
    @CsvSource({
            "5,2,7,2",
            "5,2,3,2",
            "5,2,5,4",
            "5,2,3,2"
    })
    void testNotMovableGeneral(int preX, int preY, int nextX, int nextY) {
        Piece generalPiece = PieceFactory.createGeneral(Team.HAN);
        assertThat(generalPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
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
        Piece generalPiece = PieceFactory.createGeneral(Team.HAN);
        List<Position> path = generalPiece.findPath(new Position(preX, preY), new Position(nextX, nextY));

        assertThat(path).isEmpty();
    }

    @Test
    @DisplayName("이동 경로 상에 기물이 아무것도 없으면 통과할 수 있다.")
    void testCheckPathRuleWhenNoPieceInPath() {
        // given
        List<Piece> emptyPieces = List.of();
        // when & then
        assertThat(generalStrategy.checkPathRule(emptyPieces)).isTrue();
    }

    @Test
    @DisplayName("이동 경로 상에 기물이 1개라도 있으면 막혀서 통과할 수 없다.")
    void testNotCheckPathRuleWhenAnyPieceInPath() {
        List<Piece> pathPieces = List.of(otherTeamPiece);
        List<Piece> pathPieces2 = List.of(otherTeamPiece, sameTeamPiece);
        // when & then
        assertThat(generalStrategy.checkPathRule(pathPieces)).isFalse();
        assertThat(generalStrategy.checkPathRule(pathPieces2)).isFalse();
    }

    @Test
    @DisplayName("도착 경로가 null이면 이동할 수 있다.")
    void testCanCaptureWhenDestinationIsEmpty() {
        // when & then
        assertThat(generalStrategy.canCapture(general, null)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 상대 진영 기물이 있으면 이동할 수 있다.")
    void testCanCaptureWhenDestinationIsEnemy() {
        // when & then
        assertThat(generalStrategy.canCapture(general, otherTeamPiece)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 상대 진영 기물이 있으면 이동할 수 없다.")
    void testNotCanCaptureWhenDestinationIsAlly() {
        // when & then
        assertThat(generalStrategy.canCapture(general, sameTeamPiece)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("궁성 영역 밖으로는 이동할 수 없다.")
    @CsvSource({
            "4, 1, 3, 1",
            "6, 3, 7, 3",
            "5, 3, 5, 4",
    })
    void testCanNotMoveOutsidePalace(int preX, int preY, int nextX, int nextY) {
        // given
        Position from = new Position(preX, preY);
        Position to = new Position(nextX, nextY);

        // when & then
        assertThat(generalStrategy.canMove(from, to)).isFalse();
    }
}
