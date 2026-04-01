package domain.movement;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.EnumMap;
import java.util.Map;

public final class MovementValidator {
    private static final Map<PieceType, MovementRule> RULES = new EnumMap<>(PieceType.class);

    static {
        RULES.put(PieceType.CHARIOT, new ChariotRule());
        RULES.put(PieceType.CANNON, new CannonRule());
        RULES.put(PieceType.HORSE, new LeapPieceRule());
        RULES.put(PieceType.ELEPHANT, new LeapPieceRule());
        RULES.put(PieceType.GENERAL, new PalacePieceRule());
        RULES.put(PieceType.GUARD, new PalacePieceRule());
        RULES.put(PieceType.SOLDIER, new PalacePieceRule());
    }

    private final Board board;

    public MovementValidator(Board board) {
        this.board = board;
    }

    public boolean isValid(Piece piece, Paths candidatePaths, Position to) {
        if (board.hasFriendOf(to, piece)) {
            return false;
        }
        return RULES.get(piece.getPieceType()).isValid(piece, candidatePaths, to, board);
    }
}