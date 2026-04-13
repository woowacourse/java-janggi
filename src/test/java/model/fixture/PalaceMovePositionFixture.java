package model.fixture;

import java.util.List;
import java.util.stream.Stream;
import model.Team;
import model.coordinate.Position;
import model.piece.PieceType;
import org.junit.jupiter.params.provider.Arguments;

public class PalaceMovePositionFixture {

    // ============================
    // 將 & 士 (PalacePiece) 성공 케이스
    // ============================

    public static Stream<Arguments> 장_사_이동_가능한_위치() {
        return Stream.of(
                // 1. 한나라 (HAN): 상단 궁성 (0,3~2,5)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(0, 4), PieceType.GENERAL), // 사방위 (중앙 -> 하)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(2, 4), PieceType.GUARD),   // 사방위 (중앙 -> 상)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(1, 3), PieceType.GENERAL), // 사방위 (중앙 -> 좌)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(1, 5), PieceType.GUARD),   // 사방위 (중앙 -> 우)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(2, 5), PieceType.GUARD),   // 대각선 (중앙->우하)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(0, 5), PieceType.GUARD),   // 대각선 (중앙->우상)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(2, 3), PieceType.GENERAL), // 대각선 (중앙->좌하)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(0, 3), PieceType.GENERAL), // 대각선 (중앙->좌상)

                // 2. 초나라 (CHO): 하단 궁성 (7,3~9,5)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(9, 4), PieceType.GENERAL), // 사방위 (중앙 -> 하)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(7, 4), PieceType.GUARD),   // 사방위 (중앙 -> 상)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(8, 3), PieceType.GENERAL), // 사방위 (중앙 -> 좌)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(8, 5), PieceType.GUARD),   // 사방위 (중앙 -> 우)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(9, 5), PieceType.GENERAL), // 대각선 (중앙->좌상)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(9, 3), PieceType.GUARD),   // 대각선 (중앙->좌하)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(7, 5), PieceType.GUARD),   // 대각선 (중앙->우상)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(7, 3), PieceType.GUARD)    // 대각선 (중앙->우하)
        );
    }

    // ============================
    // 將 & 士 (PalacePiece) 실패 케이스
    // ============================

    public static Stream<Arguments> 장_사_이동_불가능한_위치() {
        return Stream.of(
                // 1. 궁성 사방위 이탈 (한나라 기준) - 상단은 장기판 밖
                Arguments.of(Team.HAN, new Position(0, 3), new Position(0, 2), PieceType.GENERAL), // 좌측 이탈
                Arguments.of(Team.HAN, new Position(0, 5), new Position(0, 6), PieceType.GUARD),   // 우측 이탈
                Arguments.of(Team.HAN, new Position(2, 4), new Position(3, 4), PieceType.GUARD),   // 하단 이탈

                // 2. 궁성 사방위 이탈 (초나라 기준) - 하단은 장기판 밖
                Arguments.of(Team.CHO, new Position(9, 3), new Position(9, 2), PieceType.GENERAL), // 좌측 이탈
                Arguments.of(Team.CHO, new Position(9, 5), new Position(9, 6), PieceType.GUARD),   // 우측 이탈
                Arguments.of(Team.CHO, new Position(7, 4), new Position(6, 4), PieceType.GENERAL), // 상단 이탈

                // 3. 잘못된 대각선 시도 (대각선 선이 없는 지점)
                Arguments.of(Team.HAN, new Position(0, 4), new Position(1, 3), PieceType.GENERAL), // 상단 변 -> 좌측 중앙
                Arguments.of(Team.HAN, new Position(0, 4), new Position(1, 5), PieceType.GENERAL), // 상단 변 -> 우측 중앙
                Arguments.of(Team.HAN, new Position(1, 3), new Position(0, 4), PieceType.GUARD),   // 좌측 변 -> 상단 중앙
                Arguments.of(Team.HAN, new Position(1, 3), new Position(0, 2), PieceType.GUARD),   // 좌측 변 -> 하단 중앙
                Arguments.of(Team.CHO, new Position(9, 4), new Position(8, 5), PieceType.GENERAL), // 하단 변 -> 우측 중앙
                Arguments.of(Team.CHO, new Position(9, 4), new Position(8, 3), PieceType.GENERAL), // 하단 변 -> 좌측 중앙
                Arguments.of(Team.CHO, new Position(8, 5), new Position(9, 4), PieceType.GUARD),   // 우측 변 -> 하단 중앙
                Arguments.of(Team.CHO, new Position(8, 5), new Position(7, 4), PieceType.GUARD),   // 우측 변 -> 상단 중앙

                // 4. 거리 초과 및 특수 실패
                Arguments.of(Team.HAN, new Position(0, 4), new Position(2, 4), PieceType.GENERAL), // 직선 2칸
                Arguments.of(Team.CHO, new Position(7, 3), new Position(9, 5), PieceType.GUARD),   // 대각선 2칸
                Arguments.of(Team.HAN, new Position(1, 4), new Position(1, 4), PieceType.GENERAL), // 제자리 이동

                // 5. 출발지가 궁성 밖인 경우
                Arguments.of(Team.HAN, new Position(3, 4), new Position(2, 4), PieceType.GENERAL)
        );
    }

    // ============================
    // 車 & 包 궁성 대각선 케이스
    // ============================
    public static Stream<Arguments> 차_포_궁성_대각선_이동_경로() {
        return Stream.of(
                // 한나라 - 교차점 → 중앙 한 칸 (경로 없음)
                Arguments.of(Team.HAN, new Position(0, 3), new Position(1, 4), List.of()),
                Arguments.of(Team.HAN, new Position(0, 5), new Position(1, 4), List.of()),
                Arguments.of(Team.HAN, new Position(2, 3), new Position(1, 4), List.of()),
                Arguments.of(Team.HAN, new Position(2, 5), new Position(1, 4), List.of()),
                // 한나라 - 중앙 → 교차점 한 칸 (경로 없음)
                Arguments.of(Team.HAN, new Position(1, 4), new Position(0, 3), List.of()),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(0, 5), List.of()),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(2, 3), List.of()),
                Arguments.of(Team.HAN, new Position(1, 4), new Position(2, 5), List.of()),
                // 한나라 - 교차점 → 교차점 두 칸 (중앙 경유)
                Arguments.of(Team.HAN, new Position(0, 3), new Position(2, 5), List.of(new Position(1, 4))),
                Arguments.of(Team.HAN, new Position(0, 5), new Position(2, 3), List.of(new Position(1, 4))),
                Arguments.of(Team.HAN, new Position(2, 3), new Position(0, 5), List.of(new Position(1, 4))),
                Arguments.of(Team.HAN, new Position(2, 5), new Position(0, 3), List.of(new Position(1, 4))),

                // 초나라 - 교차점 → 중앙 한 칸 (경로 없음)
                Arguments.of(Team.CHO, new Position(7, 3), new Position(8, 4), List.of()),
                Arguments.of(Team.CHO, new Position(7, 5), new Position(8, 4), List.of()),
                Arguments.of(Team.CHO, new Position(9, 3), new Position(8, 4), List.of()),
                Arguments.of(Team.CHO, new Position(9, 5), new Position(8, 4), List.of()),
                // 초나라 - 중앙 → 교차점 한 칸 (경로 없음)
                Arguments.of(Team.CHO, new Position(8, 4), new Position(7, 3), List.of()),
                Arguments.of(Team.CHO, new Position(8, 4), new Position(7, 5), List.of()),
                Arguments.of(Team.CHO, new Position(8, 4), new Position(9, 3), List.of()),
                Arguments.of(Team.CHO, new Position(8, 4), new Position(9, 5), List.of()),
                // 초나라 - 교차점 → 교차점 두 칸 (중앙 경유)
                Arguments.of(Team.CHO, new Position(7, 3), new Position(9, 5), List.of(new Position(8, 4))),
                Arguments.of(Team.CHO, new Position(7, 5), new Position(9, 3), List.of(new Position(8, 4))),
                Arguments.of(Team.CHO, new Position(9, 3), new Position(7, 5), List.of(new Position(8, 4))),
                Arguments.of(Team.CHO, new Position(9, 5), new Position(7, 3), List.of(new Position(8, 4)))
        );
    }

    public static Stream<Arguments> 차_포_궁성_대각선_이동_불가능한_위치() {
        return Stream.of(
                // 한나라 - 교차점이 아닌 곳에서 대각선 시도
                Arguments.of(Team.HAN, new Position(0, 4), new Position(1, 5)),
                Arguments.of(Team.HAN, new Position(0, 4), new Position(1, 3)),
                Arguments.of(Team.HAN, new Position(1, 3), new Position(2, 4)),
                Arguments.of(Team.HAN, new Position(1, 5), new Position(2, 4)),
                // 한나라 - 궁성 밖으로 대각선 이동
                Arguments.of(Team.HAN, new Position(0, 3), new Position(3, 6)),
                Arguments.of(Team.HAN, new Position(2, 5), new Position(4, 7)),

                // 초나라 - 교차점이 아닌 곳에서 대각선 시도
                Arguments.of(Team.CHO, new Position(7, 4), new Position(8, 5)),
                Arguments.of(Team.CHO, new Position(7, 4), new Position(8, 3)),
                Arguments.of(Team.CHO, new Position(8, 3), new Position(9, 4)),
                Arguments.of(Team.CHO, new Position(8, 5), new Position(9, 4)),
                // 초나라 - 궁성 밖으로 대각선 이동
                Arguments.of(Team.CHO, new Position(7, 3), new Position(4, 0)),
                Arguments.of(Team.CHO, new Position(9, 5), new Position(6, 2))
        );
    }

    public static Stream<Arguments> 졸_병_궁성_이동_불가능한_위치() {
        return Stream.of(
                // 적군 궁성 내 '후퇴' 대각선 시도
                Arguments.of(Team.CHO, new Position(2, 4), new Position(3, 5)),
                // 적군 궁성 내 대각선 2칸 이동 시도
                Arguments.of(Team.HAN, new Position(7, 3), new Position(9, 5))
        );
    }
}