package janggi.domain.piece;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static janggi.domain.piece.PieceType.SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

class SoldierMoveStrategyTest {

    private Map<Position, Piece> board;
    private MoveStrategy moveStrategy;

    @BeforeEach
    void setUp() {
        board = new HashMap<>();
        moveStrategy = SoldierMoveStrategy.getInstance();
    }


    @ParameterizedTest
    @MethodSource("soldier_findMovablePositions_success1_테스트_케이스")
    @DisplayName("졸 기물 앞, 좌, 우로 움직일 수 있으며 뒤로는 움직일 수 없다")
    public void soldier_findMovablePositions_success1(Dynasty dynasty, List<Position> contains, Position doesNotContain) {
        // given
        Position from = Position.from(5, 5);
        board.put(from, new Piece(dynasty, SOLDIER));

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, dynasty);

        // then
        assertThat(positions)
                .containsExactlyInAnyOrder(contains.toArray(new Position[0]))
                .doesNotContain(doesNotContain);
    }

    private static Stream<Arguments> soldier_findMovablePositions_success1_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        CHO,
                        List.of(
                                Position.from(5, 4),
                                Position.from(5, 6),
                                Position.from(6, 5)
                        ),
                        Position.from(4, 5)
                ),
                Arguments.of(HAN,
                        List.of(
                                Position.from(4, 5),
                                Position.from(5, 4),
                                Position.from(5, 6)
                        ),
                        Position.from(6, 5)

                )
        );
    }

    @Test
    @DisplayName("졸 기물은 아군 기물이 있는 곳으로 움직일 수 없다.")
    public void soldier_findMovablePositions_success2() {
        Position from = Position.from(5, 5);
        board.put(from, new Piece(CHO, SOLDIER));
        Position allyPosition = Position.from(5, 6);
        board.put(allyPosition, new Piece(CHO, SOLDIER)); // 아군 기물 추가

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, CHO);

        // then
        assertThat(positions).doesNotContain(allyPosition);
    }

    @Test
    @DisplayName("졸 기물은 궁성 영역에 한해서 대각선으로 움직일 수 있다.")
    public void soldier_findMovablePositions_success3() {
        Position from = Position.from(8, 4);
        board.put(from, new Piece(CHO, SOLDIER));

        // when
        List<Position> positions = moveStrategy.findMovablePositions(board, from, CHO);

        // then
        assertThat(positions)
                .containsExactlyInAnyOrder(
                        Position.from(8, 3),
                        Position.from(8, 5),
                        Position.from(9, 4),
                        Position.from(9, 5) // 대각선
                );
    }

}
