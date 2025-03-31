package domain.piece;

import domain.Position;
import domain.Team;
import domain.move.Move;
import domain.move.Moves;
import domain.player.Player;
import java.util.ArrayList;
import java.util.List;

public class King extends PalaceFixedMovePiece {

    public King(Player player, int point) {
        super(player, point);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public List<Moves> getMoveList(Position startPosition) {
        List<Moves> possibleMoves = new ArrayList<>(List.of(
                Moves.createMoves(Move.FRONT),
                Moves.createMoves(Move.BACK),
                Moves.createMoves(Move.RIGHT),
                Moves.createMoves(Move.LEFT)
        ));

        if (isPalaceCenter(startPosition)) {
            for (Position corner : getPalaceCorners(this.player)) {
                Move move = getMove(startPosition, corner);
                if (move != null && !possibleMoves.contains(Moves.createMoves(move))) {
                    possibleMoves.add(Moves.createMoves(move));
                }
            }
        }

        if (isPalaceCorner(startPosition)) {
            Move move = getMove(startPosition, getPalaceCenter(this.player));
            if (move != null && !possibleMoves.contains(Moves.createMoves(move))) {
                possibleMoves.add(Moves.createMoves(move));
            }
        }

        return possibleMoves;
    }

    private boolean isPalaceCenter(Position position) {
        return position.equals(new Position(2, 5)) || position.equals(new Position(9, 5));
    }

    private boolean isPalaceCorner(Position position) {
        return getPalaceCorners(this.player).contains(position);
    }

    private Position getPalaceCenter(Player player) {
        return (player.getTeam() == Team.RED) ? new Position(2, 5) : new Position(9, 5);
    }

    private List<Position> getPalaceCorners(Player player) {
        if (player.getTeam() == Team.RED) {
            return List.of(new Position(1, 4), new Position(1, 6), new Position(3, 4), new Position(3, 6));
        }
        return List.of(new Position(8, 4), new Position(8, 6), new Position(10, 4), new Position(10, 6));
    }

    private Move getMove(Position start, Position end) {
        int rowDiff = start.compareRow(end);
        int colDiff = start.compareColumn(end);

        if (rowDiff == -1 && colDiff == 1) {
            return Move.FRONT_RIGHT;
        }
        if (rowDiff == 1 && colDiff == 1) {
            return Move.FRONT_LEFT;
        }
        if (rowDiff == -1 && colDiff == -1) {
            return Move.BACK_RIGHT;
        }
        if (rowDiff == 1 && colDiff == -1) {
            return Move.BACK_LEFT;
        }
        throw new IllegalArgumentException("움직일 수 없는 방향입니다.");
    }
}
