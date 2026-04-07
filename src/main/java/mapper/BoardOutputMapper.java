package mapper;

import domain.board.BoardState;
import domain.board.IntersectionState;
import domain.piece.PieceType;
import domain.team.Team;
import dto.IntersectionDto;
import dto.IntersectionsDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardOutputMapper {
    private static final Map<PieceType, String> CHO_PIECE_CHINESE_CHARACTER_MAP = Map.of(
            PieceType.GENERAL, "楚",
            PieceType.CHARIOT, "車",
            PieceType.CANNON, "包",
            PieceType.HORSE, "馬",
            PieceType.ELEPHANT, "象",
            PieceType.GUARD, "士",
            PieceType.SOLDIER, "卒"
    );
    private static final Map<PieceType, String> HAN_PIECE_CHINESE_CHARACTER_MAP = Map.of(
            PieceType.GENERAL, "漢",
            PieceType.CHARIOT, "車",
            PieceType.CANNON, "包",
            PieceType.HORSE, "馬",
            PieceType.ELEPHANT, "象",
            PieceType.GUARD, "士",
            PieceType.SOLDIER, "兵"
    );
    private static final Map<Team, String> TEAM_STRING_MAP = Map.of(
            Team.CHO, "CHO",
            Team.HAN, "HAN"
    );
    private static final String NONE_PIECE_CHARACTER = "＋";
    private static final String NONE_PIECE_TEAM = "NONE";

    public IntersectionsDto toDto(BoardState boardState) {
        List<IntersectionDto> intersections = new ArrayList<>();

        for (IntersectionState state : boardState.getBoardState()) {
            int y = state.getPoint().y();
            int x = state.getPoint().x();
            String pieceLabel = getPieceLabel(state.getPieceType(), state.getTeam());
            String teamLabel = toTeamLabel(state.getTeam());

            intersections.add(new IntersectionDto(y, x, pieceLabel, teamLabel));
        }
        return new IntersectionsDto(List.copyOf(intersections));
    }

    private String getPieceLabel(PieceType pieceType, Team team) {
        if (pieceType == PieceType.NONE || team == Team.NONE) {
            return NONE_PIECE_CHARACTER;
        }
        if (team == Team.CHO) {
            return CHO_PIECE_CHINESE_CHARACTER_MAP.get(pieceType);
        }
        return HAN_PIECE_CHINESE_CHARACTER_MAP.get(pieceType);
    }

    private String toTeamLabel(Team team) {
        if (team == Team.NONE) {
            return NONE_PIECE_TEAM;
        }
        return TEAM_STRING_MAP.get(team);
    }
}
