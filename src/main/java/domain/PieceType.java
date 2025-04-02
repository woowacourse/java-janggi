package domain;

import domain.piece.*;

import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {
    MA("마", Ma::new, 5),
    CHA("차", Cha::new, 13),
    SANG("상", Sang::new, 3),
    SA("사", Sa::new, 3),
    GUNG("궁", Gung::new, 0),
    PHO("포", Pho::new, 7),
    BYEONG("병", Byeong::new, 2),
    ;

    private final String name;
    private final Function<Country, Piece> creator;
    private final int score;

    PieceType(String name, Function<Country, Piece> creator, int score) {
        this.name = name;
        this.creator = creator;
        this.score = score;
    }

    public Piece createPiece(Country country) {
        return creator.apply(country);
    }

    public static PieceType convertToPieceType(String stringPieceType) {
        return Arrays.stream(PieceType.values()).
                filter(pieceType -> pieceType.getName().equals(stringPieceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 PieceType 입니다."));
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
