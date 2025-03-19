package domain.piece;

import domain.Direction;
import domain.Position;
import domain.TeamType;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {
    private static final List<Direction> directions;

    static {
        directions = List.of(Direction.DOWN,Direction.UP,Direction.LEFT,Direction.RIGHT);
    }


    public Cannon(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Cannon(Cannon cannon){
        super(cannon);
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
                    break;
                }
            }
        }
        if(direction==null){
            return false;
        }
        List<Position> intermediatePositions = findIntermediatePositions(direction, this.position, expectedPosition);
        int count = 0;
        for (Position intermediatePosition : intermediatePositions) {
            for (Piece piece : pieces) {
                if(piece.hasSamePosition(intermediatePosition)){
                    if(piece.getType().equals(PieceType.CANNON)){
                        return false;
                    }
                    count ++;
                }
            }
        }

        if(count != 1) {
            return false;
        }

        boolean check = pieces.stream()
                .anyMatch(piece -> (piece.hasSamePosition(expectedPosition) && piece.isSameTeam(this)) || piece.getType() == PieceType.CANNON && piece != this);

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
        return PieceType.CANNON;
    }

    @Override
    public Piece newInstance() {
        return new Cannon(this);
    }

}
