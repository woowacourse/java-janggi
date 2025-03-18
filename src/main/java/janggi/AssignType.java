package janggi;

import java.util.ArrayList;
import java.util.List;

public enum AssignType {
    LEFT_TOP("왼상(상마상마)", List.of(1,6), List.of(2,7)),
    RIGHT_TOP("오른상(마상마상)", List.of(2,7), List.of(1,6)),
    IN_TOP("안상(마상상마)", List.of(2,6), List.of(1,7)),
    OUT_TOP("바깥상(상마마상)", List.of(1,7), List.of(2,6)),
    ;

    private final String name;
    private final List<Integer> sangXPositions;
    private final List<Integer> maXPositions;

    AssignType(String name, List<Integer> sangXPositions, List<Integer> maXPositions) {
        this.name = name;
        this.sangXPositions = sangXPositions;
        this.maXPositions = maXPositions;
    }

    public List<Piece> makeAssign(CampType campType) {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(makeSangPieces(campType));
        allPieces.addAll(makeMaPieces(campType));
        allPieces.addAll(makeRemainAllPieces(campType));
        return allPieces;
    }

    private List<Piece> makeRemainAllPieces(CampType campType) {
        List<Piece> allPieces = new ArrayList<>();
        List<PieceType> pieceTypes = List.of(PieceType.values());
        pieceTypes.stream()
                .filter(pieceType -> pieceType != PieceType.MA && pieceType != PieceType.SANG)
                .map(pieceType -> makeRemainPieces(campType, pieceType))
                .forEach(allPieces::addAll);
        return allPieces;
    }

    private List<Piece> makeRemainPieces(CampType campType, PieceType pieceType) {
        int yPosition = Math.abs(campType.getStartYPosition() - pieceType.getHeight());
        return pieceType.getxPositions().stream()
                .map(xPosition -> new Piece(pieceType, new Position(xPosition,yPosition)))
                .toList();
    }

    private List<Piece> makeMaPieces(CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.MA.getHeight());
        return this.maXPositions.stream()
                .map(xPosition -> new Piece(PieceType.MA, new Position(xPosition, yPosition)))
                .toList();
    }

    private List<Piece> makeSangPieces(CampType campType) {
        int yPosition = Math.abs(campType.getStartYPosition() - PieceType.SANG.getHeight());
        return this.sangXPositions.stream()
                .map(xPosition -> new Piece(PieceType.SANG, new Position(xPosition, yPosition)))
                .toList();
    }

    public String getName() {
        return name;
    }
}
