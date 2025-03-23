package domain;

import static domain.Team.*;
import static domain.piece.PieceType.CANNON;
import static domain.piece.PieceType.CHARIOT;
import static domain.piece.PieceType.CHO_PAWN;
import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.HAN_PAWN;
import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.KING;
import static domain.piece.PieceType.SCHOLAR;

import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TeamBoard {

    protected final Map<BoardLocation, Piece> pieces;

    protected TeamBoard(Map<BoardLocation, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece findByLocation(BoardLocation current) {
        if (pieces.containsKey(current)) {
            return pieces.get(current);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다.");
    }

    public void validateAllyMove(List<BoardLocation> allPath, BoardLocation destination) {
        Set<BoardLocation> located = pieces.keySet();
        if (located.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 현재 목적지에 아군이 위치해있습니다");
        }

        for (BoardLocation boardLocation : allPath) {
            if (located.contains(boardLocation)) {
                throw new IllegalArgumentException("[ERROR] 현재 이동 경로에 아군이 위치해있습니다");
            }
        }
    }

    public void validatePaths(List<BoardLocation> allPath) {
        Set<BoardLocation> located = pieces.keySet();
        for (BoardLocation boardLocation : allPath) {
            if (located.contains(boardLocation)) {
                throw new IllegalArgumentException("[ERROR] 현재 이동 경로에 기물이 위치해있습니다");
            }
        }
    }

    public void validateDestinationAlly(Piece piece, BoardLocation destination) {
        Piece destinationPiece = pieces.get(destination);
        if (piece.isEqualTeam(destinationPiece)) {
            throw new IllegalArgumentException("[ERROR] 목표 위치에 아군 기물이 위치해있습니다");
        }
    }

    public void removeIfHas(BoardLocation destination) {
        pieces.remove(destination);
    }

    public void move(BoardLocation current, BoardLocation destination) {
        Piece piece = pieces.remove(current);
        pieces.put(destination, piece);
    }

    public void validateCannon(List<BoardLocation> allPath, BoardLocation destination) {
        int count = 0;
        for (BoardLocation boardLocation : allPath) {
            for (BoardLocation pieceLocation : pieces.keySet()) {
                if (boardLocation.equals(pieceLocation) && !pieces.get(pieceLocation).isCannon()) {
                    count++;
                }
            }
        }
        if (count != 1 || pieces.get(destination).isCannon()) {
            throw new IllegalArgumentException("[ERROR] 움직일 수 없는 좌표입니다");
        }
    }

    public Map<BoardLocation, Piece> getPieces() {
        return pieces;
    }

    public static TeamBoard createWithPieces(
            List<BoardLocation> hanHorseBoardLocations,
            List<BoardLocation> hanElephantBoardLocations,
            List<BoardLocation> choHorseBoardLocations,
            List<BoardLocation> choElephantBoardLocations) {
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        hanHorseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Piece(HORSE, HAN)));
        hanElephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Piece(ELEPHANT, HAN)));
        pieces.put(new BoardLocation(1, 1), new Piece(CHARIOT, HAN));
        pieces.put(new BoardLocation(4, 1), new Piece(SCHOLAR, HAN));
        pieces.put(new BoardLocation(6, 1), new Piece(SCHOLAR, HAN));
        pieces.put(new BoardLocation(9, 1), new Piece(CHARIOT, HAN));
        pieces.put(new BoardLocation(5, 2), new Piece(KING, HAN));
        pieces.put(new BoardLocation(2, 3), new Piece(CANNON, HAN));
        pieces.put(new BoardLocation(8, 3), new Piece(CANNON, HAN));
        pieces.put(new BoardLocation(1, 4), new Piece(HAN_PAWN, HAN));
        pieces.put(new BoardLocation(3, 4), new Piece(HAN_PAWN, HAN));
        pieces.put(new BoardLocation(5, 4), new Piece(HAN_PAWN, HAN));
        pieces.put(new BoardLocation(7, 4), new Piece(HAN_PAWN, HAN));
        pieces.put(new BoardLocation(9, 4), new Piece(HAN_PAWN, HAN));

        choHorseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Piece(HORSE, CHO)));
        choElephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Piece(ELEPHANT, CHO)));
        pieces.put(new BoardLocation(1, 10), new Piece(CHARIOT, CHO));
        pieces.put(new BoardLocation(4, 10), new Piece(SCHOLAR, CHO));
        pieces.put(new BoardLocation(6, 10), new Piece(SCHOLAR, CHO));
        pieces.put(new BoardLocation(9, 10), new Piece(CHARIOT, CHO));
        pieces.put(new BoardLocation(5, 9), new Piece(KING, CHO));
        pieces.put(new BoardLocation(2, 8), new Piece(CANNON, CHO));
        pieces.put(new BoardLocation(8, 8), new Piece(CANNON, CHO));
        pieces.put(new BoardLocation(1, 7), new Piece(CHO_PAWN, CHO));
        pieces.put(new BoardLocation(3, 7), new Piece(CHO_PAWN, CHO));
        pieces.put(new BoardLocation(5, 7), new Piece(CHO_PAWN, CHO));
        pieces.put(new BoardLocation(7, 7), new Piece(CHO_PAWN, CHO));
        pieces.put(new BoardLocation(9, 7), new Piece(CHO_PAWN, CHO));
        return new TeamBoard(pieces);
    }
}
