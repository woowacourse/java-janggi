package janggi.game;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.rule.CampType;
import janggi.rule.PieceAssignType;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PieceAssigner {

    public List<Piece> assignPieces(CampType campType, PieceAssignType assignType) {
        List<Piece> pieces = new ArrayList<>();
        pieces.addAll(makeGung(campType));
        pieces.addAll(makeSa(campType));
        pieces.addAll(makeCha(campType));
        pieces.addAll(makePo(campType));
        pieces.addAll(makeMa(campType, assignType));
        pieces.addAll(makeSang(campType, assignType));
        pieces.addAll(makeByungOrJol(campType));
        return Collections.unmodifiableList(pieces);
    }

    private List<Piece> makeGung(CampType campType) {
        int baseY = campType.getStartYPosition();
        return List.of(new Piece(PieceType.GUNG, new Position(4, Math.abs(baseY - 1))));
    }

    private List<Piece> makeSa(CampType campType) {
        int baseY = campType.getStartYPosition();
        return List.of(
                new Piece(PieceType.SA, new Position(3, Math.abs(baseY))),
                new Piece(PieceType.SA, new Position(5, Math.abs(baseY))));
    }

    private List<Piece> makeCha(CampType campType) {
        int baseY = campType.getStartYPosition();
        return List.of(
                new Piece(PieceType.CHA, new Position(0, Math.abs(baseY))),
                new Piece(PieceType.CHA, new Position(8, Math.abs(baseY))));
    }

    private List<Piece> makePo(CampType campType) {
        int baseY = campType.getStartYPosition();
        return List.of(
                new Piece(PieceType.PO, new Position(1, Math.abs(baseY - 2))),
                new Piece(PieceType.PO, new Position(7, Math.abs(baseY - 2))));
    }

    private List<Piece> makeMa(CampType campType, PieceAssignType assignType) {
        return assignType.getMaXPositions().stream()
                .map(x -> new Position(x, campType.getStartYPosition()))
                .map(position -> new Piece(PieceType.MA, position))
                .toList();
    }

    private List<Piece> makeSang(CampType campType, PieceAssignType assignType) {
        return assignType.getSangXPositions().stream()
                .map(x -> new Position(x, campType.getStartYPosition()))
                .map(position -> new Piece(PieceType.SANG, position))
                .toList();
    }

    private List<Piece> makeByungOrJol(CampType campType) {
        if (campType == CampType.HAN) {
            return makeByung(campType);
        }
        return makeJol(campType);
    }

    private List<Piece> makeJol(CampType campType) {
        int baseY = campType.getStartYPosition();
        return List.of(new Piece(PieceType.JOL, new Position(0, Math.abs(baseY - 3))),
                new Piece(PieceType.JOL, new Position(2, Math.abs(baseY - 3))),
                new Piece(PieceType.JOL, new Position(4, Math.abs(baseY - 3))),
                new Piece(PieceType.JOL, new Position(6, Math.abs(baseY - 3))),
                new Piece(PieceType.JOL, new Position(8, Math.abs(baseY - 3))));
    }

    private List<Piece> makeByung(CampType campType) {
        int baseY = campType.getStartYPosition();
        return List.of(new Piece(PieceType.BYUNG, new Position(0, Math.abs(baseY - 3))),
                new Piece(PieceType.BYUNG, new Position(2, Math.abs(baseY - 3))),
                new Piece(PieceType.BYUNG, new Position(4, Math.abs(baseY - 3))),
                new Piece(PieceType.BYUNG, new Position(6, Math.abs(baseY - 3))),
                new Piece(PieceType.BYUNG, new Position(8, Math.abs(baseY - 3))));
    }
}
