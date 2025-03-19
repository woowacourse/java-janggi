package domain;

import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Team, List<Piece>> pieces;

    // set을 사용한 일급 컬렉션으로 변경하기
    // set or map?
    // Map<Position, Piece>형태는?
    //
    public Board(Map<Team, List<Piece>> pieces) {
        this.pieces = pieces;
    }

    public void putPiece(Piece piece) {
        pieces.get(piece.getTeam()).add(piece);
    }

    public boolean isExists(Position position) {
        return pieces.entrySet().stream()
                .anyMatch(entry -> entry.getValue().stream()
                            .anyMatch(piece -> piece.getPosition().equals(position))
                );
    }

    public boolean isSameTeam(Position position, Team team) {
        return pieces.get(team).stream().anyMatch(piece -> piece.getPosition().equals(position));
    }

    public boolean isExistOtherTeamPiece(Position position, Team team) {
        if (!isExists(position)) {
            return false;
        }
        return !isSameTeam(position, team);
    }
}
