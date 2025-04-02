package janggi.view;

import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
import java.util.Arrays;
import java.util.List;

public enum PieceTitle {

    GUNG(PieceType.GUNG, List.of("漢", "楚")),
    SA(PieceType.SA, "士"),
    MA(PieceType.MA, "馬"),
    SANG(PieceType.SANG, "象"),
    CHA(PieceType.CHA, "車"),
    PO(PieceType.PO, "包"),
    JOL(PieceType.JOL, "卒"),
    BYEONG(PieceType.BYEONG, "兵"),
    ;

    private final PieceType pieceType;
    private final List<String> titles;

    PieceTitle(PieceType pieceType, List<String> titles) {
        this.pieceType = pieceType;
        this.titles = titles;
    }

    PieceTitle(PieceType pieceType, String title) {
        this.pieceType = pieceType;
        this.titles = List.of(title);
    }

    public static String getTitleFromTypeAndTeam(PieceType pieceType, TeamType teamType) {
        return Arrays.stream(values())
                .filter(pieceTitle -> pieceTitle.pieceType == pieceType)
                .map(pieceTitle -> pieceTitle.getTitle(teamType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 기물을 찾을 수 없습니다."));
    }

    public String getTitle(TeamType teamType) {
        if (teamType == TeamType.HAN) {
            return titles.getFirst();
        }
        return titles.getLast();
    }
}
