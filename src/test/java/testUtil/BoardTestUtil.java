package testUtil;

import java.util.ArrayList;
import java.util.List;

import domain.constant.PieceType;

public final class BoardTestUtil {

    public static List<PieceType> createMaSangSangMa(){
        return new ArrayList<>(
                List.of(PieceType.MA,PieceType.SANG,PieceType.SANG,PieceType.MA)
        );
    }

    public static List<PieceType> createSangMaSangMa(){
        return new ArrayList<>(
                List.of(PieceType.SANG,PieceType.MA,PieceType.SANG,PieceType.MA)
        );
    }
}

