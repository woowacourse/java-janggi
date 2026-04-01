package domain.enums;

import java.util.Collections;
import java.util.List;

public enum MaSang {
    MASANGSANGMA(1,List.of(PieceType.MA, PieceType.SANG, PieceType.SANG, PieceType.MA)),
    MASANGMASANG(2,List.of(PieceType.MA, PieceType.SANG, PieceType.MA, PieceType.SANG)),
    SANGMASANGMA(3,List.of(PieceType.SANG, PieceType.MA, PieceType.SANG, PieceType.MA)),
    SANGMAMASANG(4,List.of(PieceType.SANG, PieceType.MA, PieceType.MA, PieceType.SANG));

    private int num;
    private List<PieceType> pieces;

    MaSang(int num, List<PieceType> pieces) {
        this.num = num;
        this.pieces = pieces;
    }

    public static MaSang getByNum(int num) {
        for ( MaSang maSang : MaSang.values()){
            if (num==maSang.num){
                return maSang;
            }
        }
        throw new IllegalArgumentException("범위 내의 숫자가 아닙니다.");
    }

    public List<PieceType> getPieces() {
        return Collections.unmodifiableList(pieces);
    }
}
