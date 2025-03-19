package domain.piece;

import domain.Direction;
import domain.Position;
import domain.TeamType;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {

    private static final List<Direction> directions;

    static {
        directions = List.of(Direction.DOWN,Direction.UP,Direction.LEFT,Direction.RIGHT);
    }

    public Chariot(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Chariot(Chariot chariot){
        super(chariot);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        Direction direction =null;
        for (Direction nextDirection : directions) {
            Position current = this.position;
            while(current.canMovePosition(nextDirection.getDeltaRow(),nextDirection.getDeltaColumn())){
                current = current.movePosition(nextDirection.getDeltaRow(), nextDirection.getDeltaColumn());
                if(current.equals(expectedPosition)){
                    direction = nextDirection;
                }
            }
        }
        if(direction==null){
            return false;
        }
        List<Position> intermediatePositions = findIntermediatePositions(direction, this.position, expectedPosition);
        for (Position intermediatePosition : intermediatePositions) {
            for (Piece piece : pieces) {
                if(piece.hasSamePosition(intermediatePosition)){
                    return false;
                }
            }
        }
        boolean check = pieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(expectedPosition) && piece.isSameTeam(this));

        if(check){
            return false;
        }

        return true;
    }

    private List<Position> findIntermediatePositions(Direction direction,Position start, Position end){
        Position cur = start;
        List<Position> positions = new ArrayList<>();
        while(!cur.equals(end)){
            cur = cur.movePosition(direction.getDeltaRow(),direction.getDeltaColumn());
            positions.add(cur);
        }
        positions.removeLast();
        return positions;
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }

    @Override
    public Piece newInstance() {
        return new Chariot(this);
    }

}
