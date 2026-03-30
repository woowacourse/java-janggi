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

    public void move(Team turn, Position start, Position destination) {
        // 목적지에 있는 기물 아군 적군 판별
        Piece startPiece = selectNotEmptyPiece(start);

        startPiece.validateTurn(turn);
        Piece destinationPiece = pieces.get(destination);

        startPiece.validateNotAlly(destinationPiece);

        // 이동 여부 검사 (실패시 예외 발생)
        startPiece.check(BoardStatus.from(pieces), start, destination);

        // 이동
        pieces.remove(start);
        pieces.put(destination, startPiece);
    }

    private Piece selectNotEmptyPiece(Position position) {
        EmptyPiece emptyPiece = new EmptyPiece(new EmptyMoveStrategy(), Team.UNDEFINED);
        Piece piece = pieces.getOrDefault(position, emptyPiece);
        if (piece.isEmpty()) {
            throw new IllegalArgumentException(BoardErrorMessage.EMPTY_POSITION.getMessage());
        }
        return piece;
    }

    public BoardStatus getBoardStatus() {
        return BoardStatus.from(Map.copyOf(pieces));
    }
}
