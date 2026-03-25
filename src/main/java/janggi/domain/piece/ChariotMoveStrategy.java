package janggi.domain.piece;

import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from) {
        List<Position> canMovePositions = new ArrayList<>();

        Piece piece = board.get(from);

        List<Position> from.north();
        List<Position> board.south();
        List<Position> board.east();
        List<Position> board.west();

        for (int[] dir : offset) {
            Position to = from.add(dir[0], dir[1]);
            // 말을 만났을때
            if (board.containsKey(to)) {
                // 다른 팀을 만났을 때
                if (!board.get(to).isSameDynasty(piece)) {
                    canMovePositions.add(to);
                }
                continue;
            }
            canMovePositions.add(to);
        }

        // 현재 위치에서 부터 상로 부터 가장 먼저 만나는 상대팀 말의 위치 찾기
        // 현재 위치에서 부터 하로 부터 가장 먼저 만나는 상대팀 말의 위치 찾기
        // 현재 위치에서 부터 좌로 부터 가장 먼저 만나는 상대팀 말의 위치 찾기
        // 현재 위치에서 부터 우로 부터 가장 먼저 만나는 상대팀 말의 위치 찾기

        return List.of();
    }

    @Override
    public boolean canMove(Map<Position, Piece> board, Position from, Position to) {
        return false;
    }
}
