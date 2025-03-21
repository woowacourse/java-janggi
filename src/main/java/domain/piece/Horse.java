package domain.piece;

import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {

    private final List<List<Move>> moves =
            List.of(List.of(Move.FRONT, Move.FRONT_LEFT),
                    List.of(Move.FRONT, Move.FRONT_RIGHT),
                    List.of(Move.BACK, Move.BACK_LEFT),
                    List.of(Move.BACK, Move.BACK_RIGHT),
                    List.of(Move.RIGHT, Move.FRONT_RIGHT),
                    List.of(Move.RIGHT, Move.BACK_RIGHT),
                    List.of(Move.LEFT, Move.FRONT_LEFT),
                    List.of(Move.LEFT, Move.BACK_LEFT));

    public Horse(Team team) {
        super(team);
    }

    @Override
    public boolean isCanon() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public List<Position> calculatePath(Position startPosition, Position targetPosition) {
        for (List<Move> moveList : moves) {
            boolean compareResult = comparePath(startPosition, targetPosition, moveList);
            if (compareResult) {
                return convertToPath(moveList, startPosition);
            }
        }
        throw new IllegalArgumentException("이 위치로 이동할 수 없습니다.");
    }

    private List<Position> convertToPath(List<Move> moves, Position startPosition) {
        List<Position> path = new ArrayList<>();
        for (int i = 0; i < moves.size() - 1; i++) {
            startPosition = startPosition.movePosition(moves.get(i));
            path.add(startPosition);
        }
        return path;
    }

    private boolean comparePath(Position startPosition, Position targetPosition, List<Move> moveList) {
        Position movedPosition = startPosition;
        for (Move move : moveList) {
            movedPosition = movedPosition.movePosition(move);
        }
        return movedPosition.equals(targetPosition);
    }
}
