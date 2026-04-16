package janggi.domain.moveRules.palacemoverules;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.MoveRule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ZolPalaceMoveRuleTest {

    private final MoveRule zolPalaceMoveRule = new ZolPalaceMoveRule();

    @Test
    @DisplayName("초나라 졸은 궁성 밖에서 북, 동, 서 방향으로만 이동할 수 있다")
    void 초나라_졸_일반_이동() {
        Map<Position, Piece> state = new HashMap<>();
        Position start = new Position(5, 7);
        state.put(start, new Piece(Team.CHO, PieceType.ZOL));

        List<Position> positions = zolPalaceMoveRule.calculateAvailablePositions(start, Team.CHO, state);

        assertThat(positions).containsExactlyInAnyOrder(
                new Position(5, 6),
                new Position(6, 7),
                new Position(4, 7)
        );
    }

    @Test
    @DisplayName("초나라 졸이 한나라 궁성의 왼쪽 하단 모서리에 있을 때, (북,동,서) + 우상단전진이 가능하다")
    void 초나라_졸_한나라_궁성_모서리_이동() {
        Map<Position, Piece> state = new HashMap<>();
        Position start = new Position(4, 3);
        state.put(start, new Piece(Team.CHO, PieceType.ZOL));

        List<Position> positions = zolPalaceMoveRule.calculateAvailablePositions(start, Team.CHO, state);

        assertThat(positions).containsExactlyInAnyOrder(
                new Position(4, 2),
                new Position(5, 3),
                new Position(3, 3),
                new Position(5, 2)
        );
    }

    @Test
    @DisplayName("초나라 졸이 한나라 궁성 중앙에 있을 때, (북,동,서) + 좌상단/우상단 대각선 전진이 모두 가능하다")
    void 초나라_졸_한나라_궁성_중앙_이동() {
        Map<Position, Piece> state = new HashMap<>();
        Position start = new Position(5, 2);
        state.put(start, new Piece(Team.CHO, PieceType.ZOL));

        List<Position> positions = zolPalaceMoveRule.calculateAvailablePositions(start, Team.CHO, state);

        assertThat(positions).containsExactlyInAnyOrder(
                new Position(5, 1),
                new Position(6, 2),
                new Position(4, 2),
                new Position(4, 1),
                new Position(6, 1)
        );
    }

    @Test
    @DisplayName("한나라 졸이 초나라 궁성 중앙에 있을 때, (남,동,서) + 좌하단/우하단 대각선 전진이 모두 가능하다")
    void 한나라_졸_초나라_궁성_중앙_이동() {
        Map<Position, Piece> state = new HashMap<>();
        Position start = new Position(5, 9);
        state.put(start, new Piece(Team.HAN, PieceType.ZOL));

        List<Position> positions = zolPalaceMoveRule.calculateAvailablePositions(start, Team.HAN, state);

        assertThat(positions).containsExactlyInAnyOrder(
                new Position(5, 10),
                new Position(6, 9),
                new Position(4, 9),
                new Position(4, 10),
                new Position(6, 10)
        );
    }
}
