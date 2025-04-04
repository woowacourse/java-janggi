package janggi.domain.setting;

import janggi.domain.piece.Byeong;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;
import java.util.ArrayList;
import java.util.List;

public enum AssignType {
    LEFT_SANG(List.of(1, 6), List.of(2, 7)),
    RIGHT_SANG(List.of(2, 7), List.of(1, 6)),
    IN_SANG(List.of(2, 6), List.of(1, 7)),
    OUT_SANG(List.of(1, 7), List.of(2, 6)),
    ;

    private final List<Integer> sangXPositions;
    private final List<Integer> maXPositions;

    AssignType(List<Integer> sangXPositions, List<Integer> maXPositions) {
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
        if (campType == CampType.CHO) {
            allPieces.addAll(Jol.generateInitialJols(campType));
            return allPieces;
        }
        allPieces.addAll(Byeong.generateInitialByeongs(campType));
        return allPieces;
    }
}
