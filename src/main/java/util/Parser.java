package util;

import domain.PieceType;
import domain.Position;
import dto.MoveCommand;

public class Parser {
    /**
     * 플레이어의 기물 이동 입력값을 처리한다
     *
     * 예) 졸 7,1 -> 6,1
     * 과 같이 [이동을 원하는 기물 + 해당 기물의 현위치 + -> + 목적지 위치] 를 검증한다
     *
     * @param command 플레이어의 기물 이동 입력값
     * @return PieceType, Position, Positin으로 파싱된 정보
     */
    public static MoveCommand parse(String command) {
        String[] split = command.split(" ");
        validateCommand(split);
        PieceType type = PieceType.fromKoreanName(split[0]);

        String[] positionFrom = split[1].split(",");
        Position from = Position.from(Integer.parseInt(positionFrom[0]), Integer.parseInt(positionFrom[1]));

        String[] positionTo = split[3].split(",");
        Position to = Position.from(Integer.parseInt(positionTo[0]), Integer.parseInt(positionTo[1]));

        return MoveCommand.of(type, from, to);
    }

    private static void validateCommand(String[] split) {
        if (split.length != 4) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }
}
