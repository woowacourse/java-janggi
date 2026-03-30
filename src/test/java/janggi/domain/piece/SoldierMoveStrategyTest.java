package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;

class SoldierMoveStrategyTest {

    private Map<Position, Piece> board;
    private MoveStrategy moveStrategy;

    @BeforeEach
    void setUp() {
        board = new HashMap<>();
        moveStrategy = new SoldierMoveStrategy();
    }

    @ParameterizedTest
    @MethodSource("졸_기물의_이동가능한_위치_목록_반환_테스트_케이스")
    @DisplayName("졸 기물의 이동가능한 위치 목록을 반환한다")
    public void soldier_findMovablePositions_success(Dynasty ally, Dynasty enemy, List<Position> results) {
        // given
        Position from = Position.from(5, 5);
        board.put(from, new Piece(ally, moveStrategy));

        // 1. 오른쪽에 상대편
        board.put(Position.from(5 ,6), new Piece(enemy, moveStrategy));
        // 2. 왼쪽에 우리편
        board.put(Position.from(5 ,4), new Piece(ally, moveStrategy));

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, ally);

        // then
        Assertions.assertThat(positions)
                .containsExactlyInAnyOrder(results.toArray(new Position[0]));
    }

    private static Stream<Arguments> 졸_기물의_이동가능한_위치_목록_반환_테스트_케이스() {
        return Stream.of(
                Arguments.of(CHO, HAN, List.of(Position.from(5, 6), Position.from(6, 5))),
                Arguments.of(HAN, CHO, List.of(Position.from(5, 6), Position.from(4, 5)))
        );
    }
}
