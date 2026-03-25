package janggi.domain;

import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;
import janggi.domain.side.TeamType;
import janggi.dto.BoardSpot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Pieces {

    private final Map<Position, Piece> value;

    private Pieces(Map<Position, Piece> value) {
        this.value = value;
    }

    public Pieces move(int startX, int startY, int endX, int endY) {
        Piece piece = value.get(new Position(startX, startY));
        if (piece.canMove(startX, startY, endX, endY)) {
            // TODO: move하기
            // 기존 좌표 삭제 + 새 좌표 삽입
        }
        return new Pieces(new HashMap<>(value));
    }

    public static Pieces createHan() {
        Map<Position, Piece> pieces = new HashMap<>();
        createChas(pieces, 10);
        createMas(pieces, 10);
        createSangs(pieces, 10);
        createSas(pieces, 10);
        createGung(pieces, 9);
        createPos(pieces, 8);
        createJols(pieces, 7, TeamType.HAN);
        return new Pieces(pieces);
    }

    public static Pieces createChu() {
        Map<Position, Piece> pieces = new HashMap<>();
        createChas(pieces, 1);
        createMas(pieces, 1);
        createSangs(pieces, 1);
        createSas(pieces, 1);
        createGung(pieces, 2);
        createPos(pieces, 3);
        createJols(pieces, 4, TeamType.CHU);
        return new Pieces(pieces);
    }


    private static void createChas(Map<Position, Piece> pieces, int indexY) {
        pieces.put(new Position(1, indexY), new Cha());
        pieces.put(new Position(9, indexY), new Cha());
    }

    private static void createMas(Map<Position, Piece> pieces, int indexY) {
        pieces.put(new Position(2, indexY), new Ma());
        pieces.put(new Position(8, indexY), new Ma());
    }

    private static void createSangs(Map<Position, Piece> pieces, int indexY) {
        pieces.put(new Position(7, indexY), new Sang());
        pieces.put(new Position(10 - 7, indexY), new Sang());
    }

    private static void createSas(Map<Position, Piece> pieces, int indexY) {
        pieces.put(new Position(4, indexY), new Sa());
        pieces.put(new Position(10 - 4, indexY), new Sa());
    }

    private static void createGung(Map<Position, Piece> pieces, int indexY) {
        pieces.put(new Position(5, indexY), new Gung());
    }

    private static void createPos(Map<Position, Piece> pieces, int indexY) {
        pieces.put(new Position(2, indexY), new Po());
        pieces.put(new Position(10 - 2, indexY), new Po());
    }

    private static void createJols(Map<Position, Piece> pieces, int indexY, TeamType teamType) {
        for (int i = 1; i < 10; i += 2) {
            pieces.put(new Position(i, indexY), new Jol(teamType));
        }
    }

    public boolean isPieceExists(int x, int y) {
        Position position = new Position(x, y);
        return value.containsKey(position);
    }

    public List<BoardSpot> makeSpots() {
        List<BoardSpot> boardSpots = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : value.entrySet()) {
            boardSpots.add(new BoardSpot(
                entry.getKey().makePositionKey(),
                entry.getValue().nickname()
            ));
        }
        return boardSpots;
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(value.get(position));
    }
}
