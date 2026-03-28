package domain;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import java.util.Map;

public class Board {
    private static final String DESTINATION_HAS_ALLY = "목적지에 아군이 존재하여 이동할 수 없습니다.";
    private static final String SHOULD_CHOOSE_CORRECT_TEAM_PIECE = "자신의 아군 기물만 이동할 수 있습니다.";
    private static final String EMPTY_POSITION = "해당 위치에 기물이 존재하지 않습니다.";
    private final Map<Position, Piece> pieces;

    private Board(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board of(SettingType choSettingType, SettingType hanSettingType) {
        Map<Position, Piece> setup = new BoardInitializer().setup(choSettingType, hanSettingType);
        return new Board(setup);
    }

    public void move(Team turn, Position start, Position destination) {
        Piece startPiece = getPiece(start);

        validateIsSameTeam(turn, startPiece);
        Piece destinationPiece = pieces.get(destination);

        validateIsNotAlly(startPiece, destinationPiece);

        startPiece.check(BoardStatus.from(pieces), start, destination);

        pieces.remove(start);
        pieces.put(destination, startPiece);
    }

    private Piece getPiece(Position position) {
        Piece piece = pieces.get(position);
        if (piece == null) {
            throw new IllegalArgumentException(EMPTY_POSITION);
        }
        return piece;
    }

    private void validateIsSameTeam(Team turn, Piece startPiece) {
        if (startPiece != null && startPiece.getTeam() != turn) {
            throw new IllegalArgumentException(SHOULD_CHOOSE_CORRECT_TEAM_PIECE);
        }
    }

    private void validateIsNotAlly(Piece startPiece, Piece destinationPiece) {
        if (destinationPiece != null) {
            if (destinationPiece.getTeam() == startPiece.getTeam()) {
                throw new IllegalArgumentException(DESTINATION_HAS_ALLY);
            }
        }
    }

    public BoardStatus getBoardStatus() {
        return BoardStatus.from(Map.copyOf(pieces));
    }
}
