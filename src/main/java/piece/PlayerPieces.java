package piece;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import piece.position.Position;

public class PlayerPieces {

    private static final String INVALID_PLAYER_SIZE = "장기는 2명이서 할 수 있습니다";
    private static final int PLAYER_SIZE = 2;

    private final Map<Team, Pieces> teamBoard;

    public PlayerPieces(Map<Team, Pieces> teamBoard) {
        this.teamBoard = new HashMap<>(teamBoard);
        if (teamBoard.size() != PLAYER_SIZE) {
            throw new IllegalArgumentException(INVALID_PLAYER_SIZE);
        }
    }

    public Pieces allPieces() {
        Pieces bluePieces = teamBoard.get(Team.BLUE);
        Pieces redPieces = teamBoard.get(Team.RED);
        return bluePieces.add(redPieces);
    }

    public Optional<Team> kingDeadTeam() {
        for (Entry<Team, Pieces> teamPiecesEntry : teamBoard.entrySet()) {
            Pieces pieces = teamPiecesEntry.getValue();
            if (!pieces.isPieceExist(PieceType.GUNG)) {
                return Optional.of(teamPiecesEntry.getKey());
            }
        }
        return Optional.empty();
    }

    public void move(Team team, Position selectPiecePosition, Position selectPosition) {
        Pieces allPieces = allPieces();
        Pieces moveTeamPieces = teamBoard.get(team);
        Pieces otherPieces = otherTeamPieces(team);
        Piece piece = moveTeamPieces.move(selectPiecePosition, selectPosition, allPieces);
        moveTeamPieces.killPieceFrom(piece, otherPieces);
    }

    private Pieces otherTeamPieces(Team team) {
        Team opposite = team.opposite();
        return teamBoard.get(opposite);
    }
}
