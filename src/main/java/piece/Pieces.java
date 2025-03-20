package piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pieces {

    private static final String INVALID_PIECE_POSITION = "규칙에 맞지않은 입력입니다";
    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public int size() {
        return pieces.size();
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    public Piece getFirstPiece() {
        return pieces.getFirst();
    }

    public Piece getLastPiece() {
        return pieces.getLast();
    }

    public void killPieceFrom(Piece killerPiece) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if (killerPiece.isSamePosition(piece) && !killerPiece.isSameTeam(piece)) {
                pieces.remove(i);
                return;
            }
        }
    }

    public List<Piece> add(Pieces otherPieces) {
        List<Piece> resultPieces = new ArrayList<>(otherPieces.getPieces());
        resultPieces.addAll(pieces);
        return Collections.unmodifiableList(resultPieces);
    }

    public void move(Position selectPiecePosition, Position movePosition) {
        Piece piece = findPiece(selectPiecePosition);
        Route route = piece.getRoute(selectPiecePosition, movePosition);
        Pieces piecesOnRoute = findPiecesOnRoute(route);
        piece.move(piecesOnRoute, movePosition);
    }

    private Pieces findPiecesOnRoute(Route route) {
        Map<Position, Piece> positionPieces = new HashMap<>();
        for (Piece piece : pieces) {
            Position position = piece.getPosition();
            positionPieces.put(position, piece);
        }

        return findPiecesOnRoute(positionPieces, route.positions());
    }

    private Pieces findPiecesOnRoute(Map<Position, Piece> positionPieces, List<Position> onRoutePositions) {
        List<Piece> onRoutePieces = new ArrayList<>();
        for (Position onRoutePosition : onRoutePositions) {
            if (!positionPieces.containsKey(onRoutePosition)) {
                continue;
            }
            onRoutePieces.add(positionPieces.get(onRoutePosition));
        }
        return new Pieces(onRoutePieces);
    }

    private Piece findPiece(Position selectPiecePosition) {
        for (Piece piece : pieces) {
            if (piece.isSamePosition(selectPiecePosition)) {
                return piece;
            }
        }
        throw new IllegalArgumentException(INVALID_PIECE_POSITION);
    }
}
