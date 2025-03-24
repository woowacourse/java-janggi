package piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import piece.position.Position;

public class Pieces {

    private static final String PIECE_DOESNT_EXIST = "규칙에 맞지않은 입력입니다";

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void killPieceFrom(Piece killerPiece, Pieces otherPieces) {
        List<Piece> otherTeamPieces = otherPieces.pieces;
        for (int i = 0; i < otherPieces.size(); i++) {
            Piece otherPiece = otherTeamPieces.get(i);
            if (killerPiece.isSamePosition(otherPiece) && !killerPiece.isSameTeam(otherPiece)) {
                otherTeamPieces.remove(otherPiece);
                return;
            }
        }
    }

    public Pieces add(Pieces otherPieces) {
        List<Piece> resultPieces = new ArrayList<>(otherPieces.getPieces());
        resultPieces.addAll(pieces);
        return new Pieces(resultPieces);
    }

    public Piece move(Position selectPiecePosition, Position movePosition, Pieces boardAllPieces) {
        Piece piece = findPiece(selectPiecePosition);
        List<Position> route = piece.getRoute(selectPiecePosition, movePosition);
        Pieces piecesOnRoute = findPiecesOnRouteIncludeOtherTeam(route, piece, boardAllPieces);
        piece.move(piecesOnRoute, movePosition);
        return piece;
    }

    private Pieces findPiecesOnRouteIncludeOtherTeam(List<Position> route, Piece movePiece, Pieces boardAllPieces) {
        List<Piece> allPieces = boardAllPieces.getPieces();
        Map<Position, Piece> positionPieces = new HashMap<>();
        for (Piece piece : allPieces) {
            if (piece.equals(movePiece)) {
                continue;
            }
            Position position = piece.getPosition();
            positionPieces.put(position, piece);
        }

        return findPiecesOnRouteIncludeOtherTeam(positionPieces, route);
    }

    private Pieces findPiecesOnRouteIncludeOtherTeam(Map<Position, Piece> positionPieces,
                                                     List<Position> onRoutePositions) {
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
        throw new IllegalArgumentException(PIECE_DOESNT_EXIST);
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

    public boolean isPieceExist(PieceType pieceType) {
        for (Piece piece : pieces) {
            if (piece.isSameType(pieceType)) {
                return true;
            }
        }
        return false;
    }
}
