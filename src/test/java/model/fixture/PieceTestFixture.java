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

    // ============================
    // 궁성 내 이동 (將/士 공통)
    // ============================

    // 한나라 궁성: (0,3)~(2,5), 대각선 위치: (0,3),(0,5),(1,4),(2,3),(2,5)

    public static Stream<Arguments> 궁성_중심에서_상하좌우_이동() {
        return Stream.of(
                Arguments.of(new Position(1, 4), new Position(0, 4)),  // 상
                Arguments.of(new Position(1, 4), new Position(2, 4)),  // 하
                Arguments.of(new Position(1, 4), new Position(1, 3)),  // 좌
                Arguments.of(new Position(1, 4), new Position(1, 5))   // 우
        );
    }

    public static Stream<Arguments> 궁성_대각선_위치에서_대각선_이동() {
        return Stream.of(
                Arguments.of(new Position(1, 4), new Position(0, 3)),  // 중심 → 좌상 꼭짓점
                Arguments.of(new Position(1, 4), new Position(0, 5)),  // 중심 → 우상 꼭짓점
                Arguments.of(new Position(1, 4), new Position(2, 3)),  // 중심 → 좌하 꼭짓점
                Arguments.of(new Position(1, 4), new Position(2, 5)),  // 중심 → 우하 꼭짓점
                Arguments.of(new Position(0, 3), new Position(1, 4)),  // 꼭짓점 → 중심
                Arguments.of(new Position(2, 5), new Position(1, 4))   // 꼭짓점 → 중심
        );
    }

    public static Stream<Arguments> 궁성_비대각선_위치에서_대각선_이동_불가() {
        return Stream.of(
                Arguments.of(new Position(0, 4), new Position(1, 5)),  // 상변 중간 → 대각선
                Arguments.of(new Position(0, 4), new Position(1, 3)),  // 상변 중간 → 대각선
                Arguments.of(new Position(1, 3), new Position(0, 4)),  // 좌변 중간 → 대각선
                Arguments.of(new Position(1, 3), new Position(2, 4))   // 좌변 중간 → 대각선
        );
    }

    public static Stream<Arguments> 궁성_밖으로_이동_불가() {
        return Stream.of(
                Arguments.of(new Position(0, 3), new Position(0, 2)),  // 궁성 좌측 밖
                Arguments.of(new Position(0, 5), new Position(0, 6)),  // 궁성 우측 밖
                Arguments.of(new Position(2, 4), new Position(3, 4))   // 궁성 아래 밖
        );
    }

    // ============================
    // 궁성 내 兵/卒 대각선 이동
    // ============================

    // 초나라 졸: 전진이 row- 방향, 적 궁성은 한나라 궁성 (0,3)~(2,5)
    public static Stream<Arguments> 초나라_졸_궁성_대각선_전진_가능() {
        return Stream.of(
                Arguments.of(new Position(2, 3), new Position(1, 4)),  // 좌하 꼭짓점 → 중심 (전진 대각선)
                Arguments.of(new Position(2, 5), new Position(1, 4))   // 우하 꼭짓점 → 중심 (전진 대각선)
        );
    }

    public static Stream<Arguments> 초나라_졸_궁성_대각선_후진_불가() {
        return Stream.of(
                Arguments.of(new Position(1, 4), new Position(2, 3)),  // 중심 → 좌하 (후퇴 대각선)
                Arguments.of(new Position(1, 4), new Position(2, 5))   // 중심 → 우하 (후퇴 대각선)
        );
    }

    // 한나라 병: 전진이 row+ 방향, 적 궁성은 초나라 궁성 (7,3)~(9,5)
    public static Stream<Arguments> 한나라_병_궁성_대각선_전진_가능() {
        return Stream.of(
                Arguments.of(new Position(7, 3), new Position(8, 4)),  // 좌상 꼭짓점 → 중심 (전진 대각선)
                Arguments.of(new Position(7, 5), new Position(8, 4))   // 우상 꼭짓점 → 중심 (전진 대각선)
        );
    }

    public static Stream<Arguments> 한나라_병_궁성_대각선_후진_불가() {
        return Stream.of(
                Arguments.of(new Position(8, 4), new Position(7, 3)),  // 중심 → 좌상 (후퇴 대각선)
                Arguments.of(new Position(8, 4), new Position(7, 5))   // 중심 → 우상 (후퇴 대각선)
        );
    }

    // ============================
    // 궁성 내 車 대각선 이동
    // ============================

    public static Stream<Arguments> 차_궁성_대각선_이동_가능() {
        return Stream.of(
                Arguments.of(new Position(0, 3), new Position(2, 5)),  // 좌상 → 우하 (2칸 대각선)
                Arguments.of(new Position(2, 5), new Position(0, 3)),  // 우하 → 좌상
                Arguments.of(new Position(0, 5), new Position(2, 3)),  // 우상 → 좌하
                Arguments.of(new Position(0, 3), new Position(1, 4))   // 꼭짓점 → 중심 (1칸 대각선)
        );
    }

    // ============================
    // 궁성 내 包 대각선 이동
    // ============================

    public static Stream<Arguments> 포_궁성_대각선_이동_가능() {
        return Stream.of(
                Arguments.of(new Position(0, 3), new Position(2, 5)),  // 좌상 → 우하 (중심에 기물 필요)
                Arguments.of(new Position(2, 3), new Position(0, 5))   // 좌하 → 우상
        );
    }
}