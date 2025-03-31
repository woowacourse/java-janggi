package domain.piece;

import domain.move.Move;
import domain.move.Moves;
import domain.player.Player;
import java.util.List;

public class Elephant extends FixedMovePiece {

    private final List<Moves> moves = List.of(
            Moves.createMoves(Move.FRONT, Move.FRONT_LEFT, Move.FRONT_LEFT),
            Moves.createMoves(Move.FRONT, Move.FRONT_RIGHT, Move.FRONT_RIGHT),
            Moves.createMoves(Move.BACK, Move.BACK_LEFT, Move.BACK_LEFT),
            Moves.createMoves(Move.BACK, Move.BACK_RIGHT, Move.BACK_RIGHT),
            Moves.createMoves(Move.RIGHT, Move.FRONT_RIGHT, Move.FRONT_RIGHT),
            Moves.createMoves(Move.RIGHT, Move.BACK_RIGHT, Move.BACK_RIGHT),
            Moves.createMoves(Move.LEFT, Move.FRONT_LEFT, Move.FRONT_LEFT),
            Moves.createMoves(Move.LEFT, Move.BACK_LEFT, Move.BACK_LEFT)
    );

    public Elephant(Player player, int point) {
        super(player, point);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ELEPHANT;
    }

    @Override
    public List<Moves> getMoveList() {
        return moves;
    }
}
