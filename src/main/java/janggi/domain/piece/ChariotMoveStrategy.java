package janggi.domain.piece;

import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> canMovePositions(Map<Position, Piece> board, Position from) {
        List<Position> canMovePositions = new ArrayList<>();

        Piece piece = board.get(from);
        for (Direction dir : Direction.values()) {
            List<Position> positions = from.findPositionsByDirection(dir);
            for (Position to : positions) {
                if(board.containsKey(to)) {
                    // 다른 팀을 만났을 때
                    if (!board.get(to).isSameDynasty(piece.dynasty())) {
                        canMovePositions.add(to);
                    }
                    continue;
                }
                canMovePositions.add(to);
            }

        }

        return List.of();
    }

    @Override
    public boolean canMove(Map<Position, Piece> board, Position from, Position to) {
        return false;
    }
}
