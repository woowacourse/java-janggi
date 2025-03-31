package domain.piece;

import java.util.function.Function;

public enum PieceType {
    CANNON("포", Cannon::new),
    CHARIOT("차", Chariot::new),
    ELEPHANT("상", Elephant::new),
    GENERAL("왕", General::new),
    GUARD("사", Guard::new),
    HORSE("마", Horse::new),
    JJU("쭈", Jju::new);  // Soldiers

    private final String title;
    private final Function<Team, Piece> pieceCreator;

    PieceType(
        final String title,
        final Function<Team, Piece> pieceCreator
    ) {
        this.title = title;
        this.pieceCreator = pieceCreator;
    }

    public String getTitle() {
        return title;
    }

    public Piece pieceCreator(final Team team) {
        return pieceCreator.apply(team);
    }
}
