package domain;

import domain.piece.Piece;
import domain.piece.Team;
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

    public BoardStatus getBoardStatus() {
        return BoardStatus.from(Map.copyOf(pieces));
    }

    public void move(Team turn, Position start, Position destination) {
        // 목적지에 있는 기물 아군 적군 판별
        Piece startPiece = selectNotEmptyPiece(start);

        validateIsSameTeam(turn, startPiece);
        Piece destinationPiece = pieces.get(destination);

        validateTargetNotOccupiedByAlly(startPiece, destinationPiece);

        // 이동 여부 검사 (실패시 예외 발생)
        startPiece.check(BoardStatus.from(pieces), start, destination);

        // 이동
        pieces.remove(start);
        pieces.put(destination, startPiece);
    }

    private void validateTargetNotOccupiedByAlly(Piece startPiece, Piece destinationPiece) {
        if (destinationPiece != null) {
            if (destinationPiece.getTeam() == startPiece.getTeam()) {
                throw new IllegalArgumentException("이동할 수 없습니다. (목적지에 아군이 존재함)");
            }
        }
    }

    private void validateIsSameTeam(Team turn, Piece startPiece) {
        if (startPiece != null && startPiece.getTeam() != turn) {
            throw new IllegalArgumentException("아군만 이동할 수 있습니다.");
        }
    }

    private Piece selectNotEmptyPiece(Position position) {
        Piece piece = pieces.get(position);
        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에 존재하는 기물 없음");
        }
        return piece;
    }

}
