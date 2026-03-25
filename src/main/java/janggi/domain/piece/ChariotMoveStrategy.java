package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotMoveStrategy implements MoveStrategy{

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from) {
        List<Position> canMovePositions = new ArrayList<>();

        for (int[] dir: offset) {
//            new Position(from.row() + dir[0], from.column())
//            board.get()
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
