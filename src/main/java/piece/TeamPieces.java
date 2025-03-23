package piece;

import java.util.Map;

public class TeamPieces {

    private final Map<Team, Pieces> teamBoard;

    public TeamPieces(InitiateJanggiTeamPieces initiateJanggiTeamPieces) {
        this.teamBoard = initiateJanggiTeamPieces.janggiInitiatePieces();
    }

    public Pieces allPieces() {
        Pieces bluePieces = teamBoard.get(Team.BLUE);
        Pieces redPieces = teamBoard.get(Team.RED);
        return new Pieces(bluePieces.add(redPieces));
    }

    public boolean isKingDead() {
        return false;
    }

    public void move(Team team, Position selectPiecePosition, Position selectPosition) {
        Pieces allPieces = allPieces();
        Pieces moveTeamPieces = teamBoard.get(team);
        Pieces otherPieces = otherTeamPieces(team);
        Piece piece = moveTeamPieces.move(selectPiecePosition, selectPosition, allPieces);
        moveTeamPieces.killPieceFrom(piece, otherPieces);
    }

    private Pieces otherTeamPieces(Team team) {
        if (team == Team.BLUE) {
            return teamBoard.get(Team.RED);
        }
        return teamBoard.get(Team.BLUE);
    }
}
