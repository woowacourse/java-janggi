package model.fixture;

import java.util.List;
import java.util.stream.Stream;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.params.provider.Arguments;

public class PieceMovePathFixture {

    static Stream<Arguments> 사방위_이동_경로_테스트_데이터() {
        return Stream.of(
                // 1. 여러 칸 이동 (중간 경로 존재)
                Arguments.of(
                        new Position(0, 0),
                        new Position(0, 5),
                        List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4))
                ),
                // 2. 한 칸 이동 (중간 경로 없음)
                Arguments.of(
                        new Position(3, 4),
                        new Position(3, 5),
                        List.of()
                ),
                // 3. 수직 이동 (행 방향 이동)
                Arguments.of(
                        new Position(0, 0),
                        new Position(3, 0),
                        List.of(new Position(1, 0), new Position(2, 0))
                ),
                // 4. 궁성 교차점에서 수평 이동
                Arguments.of(
                        new Position(2, 5),
                        new Position(2, 8),
                        List.of(new Position(2, 6), new Position(2, 7))
                )
        );
    }

    // === 마 이동 데이터 ===
    static Stream<Arguments> 마_이동_경로_테스트_데이터() {
        return Stream.of(
                // 1. 세로(rowDiff)로 먼저 움직이는 경우 (rowDiff=2, colDiff=1)
                Arguments.of(new Position(5, 4), new Position(7, 3), List.of(new Position(6, 4))), // 북서
                Arguments.of(new Position(5, 4), new Position(7, 5), List.of(new Position(6, 4))), // 북동
                Arguments.of(new Position(5, 4), new Position(3, 3), List.of(new Position(4, 4))), // 남서
                Arguments.of(new Position(5, 4), new Position(3, 5), List.of(new Position(4, 4))), // 남동

                // 2. 가로(colDiff)로 먼저 움직이는 경우 (rowDiff=1, colDiff=2)
                Arguments.of(new Position(5, 4), new Position(6, 6), List.of(new Position(5, 5))), // 동북
                Arguments.of(new Position(5, 4), new Position(4, 6), List.of(new Position(5, 5))), // 동남
                Arguments.of(new Position(5, 4), new Position(6, 2), List.of(new Position(5, 3))), // 서북
                Arguments.of(new Position(5, 4), new Position(4, 2), List.of(new Position(5, 3)))  // 서남
        );
    }

    // === 상 이동 데이터 ===
    static Stream<Arguments> 상_이동_경로_테스트_데이터() {
        return Stream.of(
                // 1. 세로(rowDiff)로 먼저 움직이는 경우 (rowDiff=3, colDiff=2)
                // 북서, 북동, 남서, 남동 순서
                Arguments.of(new Position(5, 4), new Position(8, 2), List.of(new Position(6, 4), new Position(7, 3))),
                Arguments.of(new Position(5, 4), new Position(8, 6), List.of(new Position(6, 4), new Position(7, 5))),
                Arguments.of(new Position(5, 4), new Position(2, 2), List.of(new Position(4, 4), new Position(3, 3))),
                Arguments.of(new Position(5, 4), new Position(2, 6), List.of(new Position(4, 4), new Position(3, 5))),

                // 2. 가로(colDiff)로 먼저 움직이는 경우 (rowDiff=2, colDiff=3)
                // 동북, 동남, 서북, 서남 순서
                Arguments.of(new Position(5, 4), new Position(7, 7), List.of(new Position(5, 5), new Position(6, 6))),
                Arguments.of(new Position(5, 4), new Position(3, 7), List.of(new Position(5, 5), new Position(4, 6))),
                Arguments.of(new Position(5, 4), new Position(7, 1), List.of(new Position(5, 3), new Position(6, 2))),
                Arguments.of(new Position(5, 4), new Position(3, 1), List.of(new Position(5, 3), new Position(4, 2)))
        );
    }

    static Stream<Arguments> 졸_병_이동_경로_테스트_데이터() {
        return Stream.of(
                // 한나라 전진/좌/우
                Arguments.of(Team.HAN, new Position(3, 2), new Position(4, 2)),
                Arguments.of(Team.HAN, new Position(3, 2), new Position(3, 1)),
                Arguments.of(Team.HAN, new Position(3, 2), new Position(3, 3)),
                // 초나라 전진/좌/우
                Arguments.of(Team.CHO, new Position(6, 2), new Position(5, 2)),
                Arguments.of(Team.CHO, new Position(6, 2), new Position(6, 1)),
                Arguments.of(Team.CHO, new Position(6, 2), new Position(6, 3)),
                // 한나라 적진 궁성 이동
                Arguments.of(Team.HAN, new Position(7, 5), new Position(8, 4)), // 대각선
                // 초나라 적진 궁성 이동
                Arguments.of(Team.CHO, new Position(2, 4), new Position(1, 4)) // 대각선
        );
    }
}
