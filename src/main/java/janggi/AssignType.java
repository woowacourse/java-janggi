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

    public String getName() {
        return name;
    }

    public List<Piece> makeAssign(CampType campType) {
        List<Piece> allPieces = new ArrayList<>();

        List<Piece> sangPieces = this.sangXPositions.stream()
                .map(x -> new Piece(PieceType.SANG,
                        new Position(x, Math.abs(campType.getStartYPosition() - PieceType.SANG.getHeight()))))
                .toList();
        allPieces.addAll(sangPieces);

        List<Piece> maList = this.maXPositions.stream()
                .map(x -> new Piece(PieceType.MA,
                        new Position(x, Math.abs(campType.getStartYPosition() - PieceType.MA.getHeight()))))
                .toList();
        allPieces.addAll(maList);

        // 나머지 만들어서 삽입
        List<PieceType> pieceTypes = List.of(PieceType.values());
        for (PieceType pieceType : pieceTypes) {
            if(pieceType == PieceType.MA || pieceType == PieceType.SANG){
                continue;
            }

            // 피스타입마다 피스 생성
            List<Piece> pieces = pieceType.getxPositions().stream()
                    .map(x -> new Piece(pieceType,
                            new Position(x, Math.abs(campType.getStartYPosition() - pieceType.getHeight()))))
                    .toList();
            allPieces.addAll(pieces);
        }

        return allPieces;
    }
}
