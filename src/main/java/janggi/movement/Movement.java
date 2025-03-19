package janggi.movement;

import janggi.position.Position;

import java.util.Arrays;
import java.util.List;

public enum Movement {
    KING("K", List.of(new Direction(1,0),new Direction(-1,0),new Direction(0,-1), new Direction(0,1))),
    GUARD("G", List.of(new Direction(1,0),new Direction(-1,0),new Direction(0,-1),new Direction(0,1))),
    HORSE("H", List.of(new Direction(2,-1),new Direction(2,1),new Direction(-2,-1),new Direction(-2,1),new Direction(-1,-2),new Direction(1,-2),new Direction(1,2),new Direction(-1,2))),
    ELEPHANT("E", List.of(new Direction(3,2),new Direction(3,-2),new Direction(-3,2),new Direction(-3,-2),new Direction(-2,-3),new Direction(2,-3),new Direction(2,3),new Direction(-2,3))),
    CHARIOT("C", List.of(new Direction(8,0),new Direction(-8,0),new Direction(0,-9),new Direction(0,9))),
    CANNON("P", List.of(new Direction(8,0),new Direction(-8,0),new Direction(0,-9),new Direction(0,9))),
    SOLDIER("S", List.of(new Direction(1,0),new Direction(-1,0),new Direction(0,1)));

    private final String pieceName;
    private final List<Direction> directions;

    Movement(String pieceName, List<Direction> directions) {
        this.pieceName = pieceName;
        this.directions = directions;
    }

    public static Movement findPiece(String name) {
        return Arrays.stream(Movement.values())
                .filter(movement -> movement.pieceName.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(""));
                //변화량들을 리스트를 반환
        //(리스트 변화량들만 반환 무브먼트 ->

        // /말. + 말의 현 위치) -
                // 말이 자신의 현 위치에 변화량들을 더한다 -> 이러한 가능성들 (리스트: 새로운 [임시] 이동 위치 가능성들) 반환
                // 임시 가능성들을 저장할 캐시 말이 >자기가 이동 할 수도 있는 곳들 임시 저장>
        //진영: 판에 있는 다른 말들에게 물어봄? 너 여기 이미 차지하고 있니? -> 아니야.
                // 가지치기: 실제로 이동가능한 위치 가능성들...
                // 출입력2번째: 자, 이러한 실제로 정말로 이동가능한 곳이 있어요~~어딜로 갈래?
                //입력 사용자: 옵션 몇 을 할게요 -> 할래?
                  // 말: 해당 변화량을 반영을, 실제로 반영해서 업데이트 (이전에는 그냥 시뮬레이션만)
    }

    public List<Direction> getDirections() {
        return directions;
    }
}
