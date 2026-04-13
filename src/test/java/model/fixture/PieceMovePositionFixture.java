package model.fixture;

import java.util.stream.Stream;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.params.provider.Arguments;

public class PieceMovePositionFixture {

    // ============================
    // 직선 이동 (車, 包 공통)
    // ============================

    public static Stream<Arguments> 사간방_대각선_이동_방향_케이스() {
        return Stream.of(
                Arguments.of(new Position(2, 2), new Position(4, 4)),   // 1. 북동 (NE): rowDiff 증가, colDiff 증가
                Arguments.of(new Position(5, 5), new Position(3, 7)),   // 2. 남동 (SE): rowDiff 감소, colDiff 증가
                Arguments.of(new Position(5, 5), new Position(2, 2)),   // 3. 남서 (SW): rowDiff 감소, colDiff 감소
                Arguments.of(new Position(2, 5), new Position(4, 3))    // 4. 북서 (NW): rowDiff 증가, colDiff 감소
        );
    }

    // ============================
    // 馬
    // ============================

    public static Stream<Arguments> 마_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(5, 7)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(7, 5)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(7, 7)),  // 정대각선
                Arguments.of(new Position(5, 5), new Position(8, 7)),  // 상 이동 (rowDiff+3, colDiff+2)
                Arguments.of(new Position(5, 5), new Position(5, 5))   // 제자리
        );
    }

    // ============================
    // 象
    // ============================

    public static Stream<Arguments> 상_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(3, 4)),  // 마 이동 (rowDiff-2, colDiff-1)
                Arguments.of(new Position(5, 5), new Position(5, 8)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(8, 8)),  // 정대각선
                Arguments.of(new Position(5, 5), new Position(4, 4)),  // 1칸 대각선
                Arguments.of(new Position(5, 5), new Position(5, 5))   // 제자리
        );
    }

    // ============================
    // 兵 & 卒 (Soldier) 통합 데이터
    // ============================
    public static Stream<Arguments> 졸_병_이동_불가능한_위치() {
        return Stream.concat(
                // 한나라 (HAN) 불가능
                Stream.of(
                        Arguments.of(Team.HAN, new Position(3, 2), new Position(2, 2)), // 후퇴
                        Arguments.of(Team.HAN, new Position(3, 2), new Position(5, 2)), // 2칸 전진
                        Arguments.of(Team.HAN, new Position(3, 2), new Position(4, 3))  // 대각선
                ),
                // 초나라 (CHO) 불가능
                Stream.of(
                        Arguments.of(Team.CHO, new Position(6, 2), new Position(7, 2)), // 후퇴
                        Arguments.of(Team.CHO, new Position(6, 2), new Position(4, 2)), // 2칸 전진
                        Arguments.of(Team.CHO, new Position(6, 2), new Position(5, 3))  // 대각선
                )
        );
    }
}