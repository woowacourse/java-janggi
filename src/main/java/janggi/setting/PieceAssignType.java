package janggi.setting;

import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Jol;
import janggi.piece.Ma;
import janggi.piece.Piece;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.piece.Sang;
import java.util.ArrayList;
import java.util.List;

public enum PieceAssignType {
    LEFT_SANG("왼상(상마상마)", List.of(1, 6), List.of(2, 7)),
    RIGHT_SANG("오른상(마상마상)", List.of(2, 7), List.of(1, 6)),
    IN_SANG("안상(마상상마)", List.of(2, 6), List.of(1, 7)),
    OUT_SANG("바깥상(상마마상)", List.of(1, 7), List.of(2, 6));

    private final String name;
    private final List<Integer> sangXPositions;
    private final List<Integer> maXPositions;

    PieceAssignType(String name, List<Integer> sangXPositions, List<Integer> maXPositions) {
        this.name = name;
        this.sangXPositions = sangXPositions;
        this.maXPositions = maXPositions;
    }

    public List<Piece> makeAssign(CampType campType) {
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(Sang.generateInitialSangs(campType, sangXPositions));
        allPieces.addAll(Ma.generateInitialMas(campType, maXPositions));
        allPieces.addAll(Gung.generateInitialGung(campType));
        allPieces.addAll(Sa.generateInitialSas(campType));
        allPieces.addAll(Cha.generateInitialChas(campType));
        allPieces.addAll(Po.generateInitialPos(campType));
        allPieces.addAll(Jol.generateInitialJols(campType));
        return allPieces;
    }

    public String getName() {
        return name;
    }
}
