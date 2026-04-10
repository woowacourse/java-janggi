package domain.game;

import domain.piece.Camp;
import domain.piece.PieceType;
import java.util.Optional;

public record TurnResult(
        Optional<PieceType> capturedPieceType,
        Optional<Camp> checkedCamp,
        Optional<Camp> winner
) {
    public static TurnResult empty() {
        return new TurnResult(Optional.empty(), Optional.empty(), Optional.empty());
    }
}
