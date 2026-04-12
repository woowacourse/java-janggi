package janggi.domain.piece;

import janggi.domain.Camp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PieceFactory {
    private static final Map<String, Function<Camp, Piece>> pieceCreators;


    static {
        Map<String, Function<Camp, Piece>> creators = new HashMap<>();


        creators.put("車", Chariot::new);

        creators.put("包", Cannon::new);
        creators.put("砲", Cannon::new);

        creators.put("馬", Horse::new);

        creators.put("士", Advisor::new);
        creators.put("仕", Advisor::new);

        creators.put("楚", General::new);
        creators.put("漢", General::new);

        creators.put("象", Elephant::new);
        creators.put("相", Elephant::new);

        creators.put("卒", Soldier::new);
        creators.put("兵", Soldier::new);

        pieceCreators = Collections.unmodifiableMap(creators);
    }

    public static Piece create(String pieceNameStr, String campStr) {
        Camp camp = parseCamp(campStr);
        String nameKey = pieceNameStr.toUpperCase();

        Function<Camp, Piece> creator = pieceCreators.get(nameKey);

        if (creator == null) {
            throw new IllegalArgumentException("존재하지 않는 기물입니다: " + pieceNameStr);
        }

        return creator.apply(camp);
    }

    private static Camp parseCamp(String campStr) {
        try {
            return Camp.valueOf(campStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 진영입니다: " + campStr);
        }
    }
}
