package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.ChariotStrategy;
import janggi.domain.movestrategy.ElephantStrategy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotPieceTest {
    @ParameterizedTest
    @DisplayName("차는 상하좌우 직선이면 칸 수에 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 6, 4",
            "8, 3, 9, 1",
            "2, 3, 3, 6",
            "8, 3, 2, 4",
    })
    void testNotMovableChariot(int preX, int preY, int nextX, int nextY) {
        ChariotPiece chariotPiece = new ChariotPiece(Team.HAN, new ChariotStrategy());

        Assertions.assertThat(chariotPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY)))
                .isFalse();
    }

    @ParameterizedTest
    @DisplayName("차는 상하좌우 직선으로 칸 수 상관없이 이동 가능하다.")
    @CsvSource({
            "2, 3, 5, 3",
            "8, 3, 5, 3",
            "2, 3, 2, 8",
            "8, 3, 8, 1",
    })
    void testMoveChariot(int preX, int preY, int nextX, int nextY) {
        ChariotPiece chariotPiece = new ChariotPiece(Team.HAN, new ChariotStrategy());

        Assertions.assertThat(chariotPiece.canMoveByBasicMovingRule(new Position(preX, preY), new Position(nextX, nextY)))
                .isTrue();
    }

    @Test
    @DisplayName("차는 상하좌우 직선이면 칸 수에 상관없이 이동 가능하다.")
    void testFindDestinationPath() {
        Position from = new Position(2, 3);
        Position to = new Position(5, 3);
        ChariotPiece chariotPiece = new ChariotPiece(Team.HAN, new ChariotStrategy());

        List<Position> result = chariotPiece.findPath(from, to);
        assertThat(result).containsExactly(new Position(3, 3), new Position(4, 3), new Position(5, 3));
    }

    @Test
    @DisplayName("차 이동 경로에 기물 2개 존재하면 이동할 수 없다.")
    void testMoveOtherPiecesInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 5), new ElephantPiece(Team.HAN, new ElephantStrategy()));
        positionPieces.put(new Position(5, 6), new ElephantPiece(Team.HAN, new ElephantStrategy()));

        ChariotPiece chariotPiece = new ChariotPiece(Team.HAN, new ChariotStrategy());
        assertThat(chariotPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 6))).isFalse();
    }

    @Test
    @DisplayName("차 이동 경로에 아무 기물이 없고 도착지에 같은 진영 기물이 존재한다면 이동할 수 없다.")
    void testNotMoveIfSameTeamPieceInDestination() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 6), new ElephantPiece(Team.HAN, new ElephantStrategy()));

        ChariotPiece chariotPiece = new ChariotPiece(Team.HAN, new ChariotStrategy());
        assertThat(chariotPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 6))).isFalse();
    }

    @Test
    @DisplayName("차 이동 경로에 아무 기물이 없고 도착지에 상대 진영 기물이 존재한다면 이동할 수 없다.")
    void testNotMoveIfOtherTeamPieceInDestination() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        positionPieces.put(new Position(5, 6), new ElephantPiece(Team.CHO, new ElephantStrategy()));

        ChariotPiece chariotPiece = new ChariotPiece(Team.HAN, new ChariotStrategy());
        assertThat(chariotPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 6))).isTrue();
    }

    @Test
    @DisplayName("차 이동 경로에 아무 기물이 없고 도착지에 아무 기물이 없다면 이동 가능하다.")
    void testMoveNoPieceInPath() {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        ChariotPiece chariotPiece = new ChariotPiece(Team.HAN, new ChariotStrategy());
        assertThat(chariotPiece.canMoveBySpecialMovingRule(positionPieces, new Position(5, 6))).isTrue();
    }


}
