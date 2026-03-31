package domain;

import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.piece.Team;
import domain.piece.strategy.EmptyMoveStrategy;
import domain.position.Position;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    private Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board of(SettingType choSettingType, SettingType hanSettingType) {
        Map<Position, Piece> setup = new BoardInitializer().setup(choSettingType, hanSettingType);
        return new Board(setup);
    }

    public void move(Turn turn, Position start, Position destination) {
        Piece startPiece = selectNotEmptyPiece(start);
        Piece destinationPiece = findTargetPiece(destination);

        startPiece.validateTurn(turn);
        startPiece.validateNotAlly(destinationPiece);

        startPiece.check(BoardStatus.from(pieces), start, destination);

        pieces.remove(start);
        pieces.put(destination, startPiece);
    }

    private Piece selectNotEmptyPiece(Position position) {
        Piece piece = findTargetPiece(position);
        if (piece.isEmpty()) {
            throw new IllegalArgumentException(BoardErrorMessage.EMPTY_POSITION.getMessage());
        }
        return piece;
    }

    private Piece findTargetPiece(Position position) {
        EmptyPiece emptyPiece = new EmptyPiece(new EmptyMoveStrategy(), Team.UNDEFINED);
        return pieces.getOrDefault(position, emptyPiece);
    }

    public BoardStatus getBoardStatus() {
        return BoardStatus.from(Map.copyOf(pieces));
    }
}
