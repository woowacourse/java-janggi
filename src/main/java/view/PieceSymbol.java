package view;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamType;
import java.util.Arrays;

public enum PieceSymbol {
    CHO_CANNON(PieceType.CANNON, TeamType.CHO, "c"),
    CHO_CHARIOT(PieceType.CHARIOT, TeamType.CHO, "r"),
    CHO_ELEPHANT(PieceType.ELEPHANT, TeamType.CHO, "e"),
    CHO_GUARD(PieceType.GUARD, TeamType.CHO, "g"),
    CHO_HORSE(PieceType.HORSE, TeamType.CHO, "h"),
    CHO_KING(PieceType.KING, TeamType.CHO, "k"),
    CHO_SOLIDER(PieceType.SOLDIER, TeamType.CHO, "s"),
    HAN_CANNON(PieceType.CANNON, TeamType.HAN, "C"),
    HAN_CHARIOT(PieceType.CHARIOT, TeamType.HAN, "R"),
    HAN_ELEPHANT(PieceType.ELEPHANT, TeamType.HAN, "E"),
    HAN_GUARD(PieceType.GUARD, TeamType.HAN, "G"),
    HAN_HORSE(PieceType.HORSE, TeamType.HAN, "H"),
    HAN_KING(PieceType.KING, TeamType.HAN, "K"),
    HAN_SOLIDER(PieceType.SOLDIER, TeamType.HAN, "S");

    private final PieceType pieceType;
    private final TeamType teamType;
    private final String symbol;

    PieceSymbol(PieceType pieceType, TeamType teamType, String symbol) {
        this.pieceType = pieceType;
        this.teamType = teamType;
        this.symbol = symbol;
    }

    public static String findSymbol(Piece piece) {
        PieceSymbol findPieceSymbol = Arrays.stream(PieceSymbol.values())
                .filter(pieceSymbol -> piece.isSameType(pieceSymbol.pieceType))
                .filter(pieceSymbol -> piece.isSameTeam(pieceSymbol.teamType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("만족하는 말 기호가 없습니다."));
        return findPieceSymbol.symbol;
    }
}
