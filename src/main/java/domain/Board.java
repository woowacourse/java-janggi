package domain;

import domain.piece.Piece;
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

    public void move(Position start, Position destination) {
        // 목적지에 있는 기물 아군 적군 판별
        Piece startPiece = pieces.get(start);
        Piece destinationPiece = pieces.get(destination);

        if (startPiece == null) {
            throw new IllegalArgumentException("이동할 수 있는 기물이 없습니다.");
        }
        if (destinationPiece != null) {
            if (destinationPiece.getTeam() == startPiece.getTeam()) {
                throw new IllegalArgumentException("이동할 수 없습니다. (목적지에 아군이 존재함)");
            }
        }

        // 이동 여부 검사 (실패시 예외 발생)
        startPiece.check(BoardStatus.from(pieces), start, destination);

        // 이동
        pieces.remove(start);
        pieces.put(destination, startPiece);
    }

}
