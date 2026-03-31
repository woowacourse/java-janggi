package model.fixture;

import model.coordinate.Position;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class PieceTestFixture {

    // ============================
    // 직선 이동 (車, 包 공통)
    // ============================

    public static Stream<Arguments> 직선_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(0, 5)),  // 우 이동
                Arguments.of(new Position(0, 0), new Position(5, 0)),  // 하 이동
                Arguments.of(new Position(5, 5), new Position(0, 5)),  // 상 이동
                Arguments.of(new Position(5, 5), new Position(5, 0))   // 좌 이동
        );
    }

    public static Stream<Arguments> 직선_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(0, 0), new Position(1, 1)),  // 정대각선 (1:1)
                Arguments.of(new Position(0, 0), new Position(2, 1)),  // rowDiff > colDiff
                Arguments.of(new Position(0, 0), new Position(1, 2)),  // rowDiff < colDiff
                Arguments.of(new Position(5, 5), new Position(7, 3)),  // 음수 방향 대각선
                Arguments.of(new Position(5, 5), new Position(9, 8))   // 둘 다 다름
        );
    }

    // ============================
    // 馬
    // ============================

    public static Stream<Arguments> 마_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(3, 4)),  // 상+좌 (row-2, col-1)
                Arguments.of(new Position(5, 5), new Position(3, 6)),  // 상+우 (row-2, col+1)
                Arguments.of(new Position(5, 5), new Position(7, 4)),  // 하+좌 (row+2, col-1)
                Arguments.of(new Position(5, 5), new Position(7, 6)),  // 하+우 (row+2, col+1)
                Arguments.of(new Position(5, 5), new Position(4, 3)),  // 좌+상 (row-1, col-2)
                Arguments.of(new Position(5, 5), new Position(6, 3)),  // 좌+하 (row+1, col-2)
                Arguments.of(new Position(5, 5), new Position(4, 7)),  // 우+상 (row-1, col+2)
                Arguments.of(new Position(5, 5), new Position(6, 7))   // 우+하 (row+1, col+2)
        );
    }

    public static Stream<Arguments> 마_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(5, 7)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(7, 5)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(7, 7)),  // 정대각선
                Arguments.of(new Position(5, 5), new Position(8, 7)),  // 상 이동 (row+3, col+2)
                Arguments.of(new Position(5, 5), new Position(5, 5))   // 제자리
        );
    }

    // ============================
    // 象
    // ============================

    public static Stream<Arguments> 상_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(2, 3)),  // 상+좌 (row-3, col-2)
                Arguments.of(new Position(5, 5), new Position(2, 7)),  // 상+우 (row-3, col+2)
                Arguments.of(new Position(5, 5), new Position(8, 3)),  // 하+좌 (row+3, col-2)
                Arguments.of(new Position(5, 5), new Position(8, 7)),  // 하+우 (row+3, col+2)
                Arguments.of(new Position(5, 5), new Position(3, 2)),  // 좌+상 (row-2, col-3)
                Arguments.of(new Position(5, 5), new Position(7, 2)),  // 좌+하 (row+2, col-3)
                Arguments.of(new Position(5, 5), new Position(3, 8)),  // 우+상 (row-2, col+3)
                Arguments.of(new Position(5, 5), new Position(7, 8))   // 우+하 (row+2, col+3)
        );
    }

    public static Stream<Arguments> 상_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(5, 5), new Position(3, 4)),  // 마 이동 (row-2, col-1)
                Arguments.of(new Position(5, 5), new Position(5, 8)),  // 직선 이동
                Arguments.of(new Position(5, 5), new Position(8, 8)),  // 정대각선
                Arguments.of(new Position(5, 5), new Position(4, 4)),  // 1칸 대각선
                Arguments.of(new Position(5, 5), new Position(5, 5))   // 제자리
        );
    }

    // ============================
    // 兵 (HAN)
    // ============================

    public static Stream<Arguments> 한나라_병_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(3, 2), new Position(4, 2)),  // 전진 (하)
                Arguments.of(new Position(3, 2), new Position(3, 1)),  // 좌
                Arguments.of(new Position(3, 2), new Position(3, 3))   // 우
        );
    }

    public static Stream<Arguments> 한나라_병_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(3, 2), new Position(2, 2)),  // 후퇴 (상)
                Arguments.of(new Position(3, 2), new Position(5, 2)),  // 두 칸 전진
                Arguments.of(new Position(3, 2), new Position(3, 4)),  // 두 칸 옆
                Arguments.of(new Position(3, 2), new Position(4, 3))   // 대각선
        );
    }

    // ============================
    // 卒 (CHO)
    // ============================

    public static Stream<Arguments> 초나라_졸_이동_가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(6, 2), new Position(5, 2)),  // 전진 (상)
                Arguments.of(new Position(6, 2), new Position(6, 1)),  // 좌
                Arguments.of(new Position(6, 2), new Position(6, 3))   // 우
        );
    }

    public static Stream<Arguments> 초나라_졸_이동_불가능한_위치() {
        return Stream.of(
                Arguments.of(new Position(6, 2), new Position(7, 2)),  // 후퇴 (하)
                Arguments.of(new Position(6, 2), new Position(4, 2)),  // 두 칸 전진
                Arguments.of(new Position(6, 2), new Position(6, 4)),  // 두 칸 옆
                Arguments.of(new Position(6, 2), new Position(5, 3))   // 대각선
        );
    }
}