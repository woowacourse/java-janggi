package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.rule.ElephantMoveRule;
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

class ElephantStrategyTest {

    private MoveStrategy elephantStrategy;
    private Piece elephant;
    private Piece otherTeamPiece;
    private Piece sameTeamPiece;

    @BeforeEach
    void setUp() {
        elephantStrategy = new DefaultMoveStrategy(List.of(new ElephantMoveRule()));
        elephant = PieceFactory.createElephant(Team.HAN);
        otherTeamPiece = PieceFactory.createCannon(Team.CHO);
        sameTeamPiece = PieceFactory.createCannon(Team.HAN);
    }

    @ParameterizedTest
    @DisplayName("상는 직선 한 칸 대각선으로 두 칸 이동 가능하다.")
    @CsvSource({
            "5, 4, 3, 1", "5, 4, 2, 2",
            "5, 4, 7, 1", "5, 4, 8, 2",
            "5, 4, 3, 7", "5, 4, 2, 6",
            "5, 4, 7, 7", "5, 4, 8, 6",
    })
    void testMovableElephant(int preX, int preY, int nextX, int nextY) {
        Piece elephantPiece = PieceFactory.createElephant(Team.HAN);
        assertThat(elephantPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isTrue();
    }


    @ParameterizedTest
    @DisplayName("상은 직선 한 칸 대각선으로 한 칸 이외에 이동이 불가능하다.")
    @CsvSource({
            "5, 4, 4, 3", "5, 4, 6, 1",
            "5, 4, 3, 4", "5, 4, 7, 2",
            "5, 4, 3, 6", "5, 4, 7, 4",
            "5, 4, 4, 7", "5, 4, 6, 5",
    })
    void testNotMovableElephant(int preX, int preY, int nextX, int nextY) {
        Piece elephantPiece = PieceFactory.createElephant(Team.HAN);
        assertThat(elephantPiece.canMove(new Position(preX, preY), new Position(nextX, nextY))).isFalse();
    }

    @ParameterizedTest
    @DisplayName("도착 좌표까지의 경로를 반환한다.")
    @CsvSource({
            "5, 4, 3, 1, 5, 3, 4, 2",
            "5, 4, 2, 2, 4, 4, 3, 3",
            "5, 4, 7, 1, 5, 3, 6, 2",
            "5, 4, 8, 2, 6, 4, 7, 3",
            "5, 4, 2, 6, 4, 4, 3, 5",
            "5, 4, 3, 7, 5, 5, 4, 6",
            "5, 4, 8, 6, 6, 4, 7, 5",
            "5, 4, 7, 7, 5, 5, 6, 6"
    })
    void testFindDestinationPath(int preX, int preY, int nextX, int nextY,
                                 int pathX1, int pathY1, int pathX2, int pathY2) {
        Piece elephantPiece = PieceFactory.createElephant(Team.HAN);
        List<Position> path = elephantPiece.findPath(new Position(preX, preY), new Position(nextX, nextY));
        assertThat(path).containsExactly(new Position(pathX1, pathY1), new Position(pathX2, pathY2));
    }

    @Test
    @DisplayName("이동 경로 상에 기물이 아무것도 없으면 통과할 수 있다.")
    void testCheckPathRuleWhenNoPieceInPath() {
        // given
        List<Piece> emptyPieces = List.of();
        // when & then
        assertThat(elephantStrategy.checkPathRule(emptyPieces)).isTrue();
    }

    @Test
    @DisplayName("이동 경로 상에 기물이 1개라도 있으면 막혀서 통과할 수 없다.")
    void testNotCheckPathRuleWhenAnyPieceInPath() {
        List<Piece> pathPieces = List.of(otherTeamPiece);
        List<Piece> pathPieces2 = List.of(otherTeamPiece, sameTeamPiece);
        // when & then
        assertThat(elephantStrategy.checkPathRule(pathPieces)).isFalse();
        assertThat(elephantStrategy.checkPathRule(pathPieces2)).isFalse();
    }

    @Test
    @DisplayName("도착 경로가 null이면 이동할 수 있다.")
    void testCanCaptureWhenDestinationIsEmpty() {
        // when & then
        assertThat(elephantStrategy.canCapture(elephant, null)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 상대 진영 기물이 있으면 이동할 수 있다.")
    void testCanCaptureWhenDestinationIsEnemy() {
        // when & then
        assertThat(elephantStrategy.canCapture(elephant, otherTeamPiece)).isTrue();
    }

    @Test
    @DisplayName("도착 경로에 상대 진영 기물이 있으면 이동할 수 없다.")
    void testNotCanCaptureWhenDestinationIsAlly() {
        // when & then
        assertThat(elephantStrategy.canCapture(elephant, sameTeamPiece)).isFalse();
    }
}
