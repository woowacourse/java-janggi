package janggi.board;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PieceAssigner {

    public List<Piece> assignPieces(CampType campType, PieceAssignType assignType) {
        List<Piece> pieces = new ArrayList<>();
        pieces.addAll(makePiecesExceptForMaAngSang(campType));
        pieces.addAll(makeMaPieces(assignType, campType.getStartYPosition()));
        pieces.addAll(makeSangPieces(assignType, campType.getStartYPosition()));
        return Collections.unmodifiableList(pieces);
    }

    private List<Piece> makeMaPieces(PieceAssignType assignType, int yPosition) {
        return assignType.getMaXPositions().stream()
                .map(x -> new Position(x, yPosition))
                .map(position -> new Piece(PieceType.MA, position))
                .toList();
    }

    private List<Piece> makeSangPieces(PieceAssignType assignType, int yPosition) {
        return assignType.getSangXPositions().stream()
                .map(x -> new Position(x, yPosition))
                .map(position -> new Piece(PieceType.SANG, position))
                .toList();
    }

    private List<Piece> makePiecesExceptForMaAngSang(CampType campType) {
        int baseY = campType.getStartYPosition();
        return List.of(
                new Piece(PieceType.GUNG, new Position(4, Math.abs(baseY - 1))),
                new Piece(PieceType.SA, new Position(3, Math.abs(baseY))),
                new Piece(PieceType.SA, new Position(5, Math.abs(baseY))),
                new Piece(PieceType.CHA, new Position(0, Math.abs(baseY))),
                new Piece(PieceType.CHA, new Position(8, Math.abs(baseY))),
                new Piece(PieceType.PO, new Position(1, Math.abs(baseY - 2))),
                new Piece(PieceType.PO, new Position(7, Math.abs(baseY - 2))),
                new Piece(PieceType.JOL, new Position(0, Math.abs(baseY - 3))),
                new Piece(PieceType.JOL, new Position(2, Math.abs(baseY - 3))),
                new Piece(PieceType.JOL, new Position(4, Math.abs(baseY - 3))),
                new Piece(PieceType.JOL, new Position(6, Math.abs(baseY - 3))),
                new Piece(PieceType.JOL, new Position(8, Math.abs(baseY - 3)))
        );
    }
}
