package testUtil;

import java.util.ArrayList;
import java.util.List;

import domain.PieceFinder;
import domain.Position;
import domain.enums.PieceType;
import domain.pieces.None;
import domain.pieces.Piece;

public final class BoardTestUtil {

    public static List<PieceType> createMasangSangMa(){
        return new ArrayList<>(
                List.of(PieceType.MA,PieceType.SANG,PieceType.SANG,PieceType.MA,
                        PieceType.MA,PieceType.SANG,PieceType.SANG,PieceType.MA)
        );
    }

    public static List<PieceType> createSangMaSangMa(){
        return new ArrayList<>(
                List.of(PieceType.SANG,PieceType.MA,PieceType.SANG,PieceType.MA,
                        PieceType.SANG,PieceType.MA,PieceType.SANG,PieceType.MA)
        );
    }

    public static PieceFinder createPieceFinder() {
        PieceFinder finder = new PieceFinder() {
            @Override
            public Piece find(Position position) {
                return None.INSTANCE;
            }
        };
        return finder;
    }
}

