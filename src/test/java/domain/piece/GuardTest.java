package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Position;
import domain.Team;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class GuardTest {

    /**
     * 사 규칙 : 앞, 뒤, 양옆 한 칸씩 이동 가능 1. 도착 지점이 한칸 앞뒤, 혹은 양옆인지 검증
     */
    @ParameterizedTest
    @MethodSource("validDirectionsProvider")
    void 도착_지점이_궁성_내_한칸거리인_경우_정상_테스트(Position to) {
        // given
        Board board = new Board();
        Piece guard = new Guard(Team.CHO);

        // when
        Position from = Position.from(10, 4);

        // then
        assertThat(guard.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Position> validDirectionsProvider() {
        return Stream.of(
                Position.from(10, 5), // 오른쪽 한칸
                Position.from(9, 4),  // 위로 한칸
                Position.from(9, 5)  // 오른쪽 대각선으로 한칸
        );
    }

    /**
     * 목적지에 같은 팀이 있는 경우 1. 목적지가 한칸 앞인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다. 2. 목적지가 한칸 뒤인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다. 3.
     * 목적지가 한칸 오른쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다. 4. 목적지가 한칸 왼쪽인 경우이면서 목적지에 같은 팀이 있는 경우 이동할 수 없다.
     */
    @ParameterizedTest
    @MethodSource("blockedBySameTeamProvider")
    void 목적지에_같은_팀이_있으면_상하좌우_모두_이동할_수_없다(Position to) {
        // given
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(10, 6);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, guard);
        testPiece.put(to, new Pawn(Team.CHO));

        Board board = new Board(testPiece);

        // when & then
        assertThat(guard.canMove(from, to, board)).isFalse();
    }

    static Stream<Position> blockedBySameTeamProvider() {
        return Stream.of(
                Position.from(10, 5),
                Position.from(9, 6),
                Position.from(9, 5)
        );
    }

    @Test
    void 궁성_외부_이동은_불가능하다() {
        Board board = new Board();
        Piece guard = new Guard(Team.CHO);

        Position from = Position.from(10, 4);
        Position to = Position.from(10, 3);

        assertThat(guard.canMove(from, to, board)).isFalse();
    }
}
