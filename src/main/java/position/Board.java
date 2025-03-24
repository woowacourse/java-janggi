package position;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Set;
import piece.Blank;
import piece.Piece;
import piece.PieceType;
import piece.Team;

public final class Board {
    private final Team currentTeam;
    private final Map<Position, Piece> pieceOfPosition;

    public Board(Team team, Set<Piece> pieces) {
        currentTeam = team;
        pieceOfPosition = pieces.stream().collect(toMap(Piece::position, identity()));
    }

    public Board(Set<Piece> pieces) {
        currentTeam = Team.CHO;
        pieceOfPosition = pieces.stream().collect(toMap(Piece::position, identity()));
    }

    public Piece get(final Position position) {
        return pieceOfPosition.getOrDefault(position, new Blank(position));
    }

    public boolean isBlank(Position position) {
        return get(position).type() == PieceType.BLANK;
    }

    public boolean canMoveLast(Position position) {
        Piece piece = get(position);
        return (piece.type() == PieceType.BLANK) || piece.isDifferentTeam(currentTeam);
    }

    public void validateTeam(Team team) {
        if(team != currentTeam){
            throw new IllegalArgumentException("[ERROR] 같은 팀 기물만 움직일 수 있습니다.");
        }
    }
}
