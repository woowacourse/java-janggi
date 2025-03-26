package piece.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;
import piece.position.JanggiPosition;

public class PlayerPieces {

    private static final String INVALID_PLAYER_SIZE = "장기는 2명이서 할 수 있습니다";

    private final Pieces blueTeamPieces;
    private final Pieces redTeamPieces;

    private final PlayerScores playerScores;

    public PlayerPieces(Map<Team, Pieces> teamBoard) {
        if (teamBoard.get(Team.BLUE) == null || teamBoard.get(Team.RED) == null) {
            throw new IllegalArgumentException(INVALID_PLAYER_SIZE);
        }
        blueTeamPieces = teamBoard.get(Team.BLUE);
        redTeamPieces = teamBoard.get(Team.RED);
        playerScores = new PlayerScores();
    }

    public Pieces allPieces() {
        List<Piece> resultTeamPiecesPieces = new ArrayList<>(blueTeamPieces.getPieces());
        resultTeamPiecesPieces.addAll(redTeamPieces.getPieces());
        return new Pieces(resultTeamPiecesPieces);
    }

    public Team kingDeadTeam() {
        if (!blueTeamPieces.isPieceExist(PieceType.GUNG)) {
            return Team.BLUE;
        }
        if (!redTeamPieces.isPieceExist(PieceType.GUNG)) {
            return Team.RED;
        }
        return Team.EMPTY;
    }

    public void placePhase(Team team, JanggiPosition selectPiecePosition, JanggiPosition wantedMovePosition) {
        Pieces moveTeamPieces = moveTeamPieces(team);
        Piece movePiece = move(moveTeamPieces, selectPiecePosition, wantedMovePosition);

        Pieces otherPieces = otherTeamPieces(team);
        Optional<Piece> deadPiece = moveTeamPieces.killPieceFrom(movePiece, otherPieces);
        deadPiece.ifPresent(playerScores::addScore);
    }

    public Piece move(Pieces movePieces, JanggiPosition selectPiecePosition, JanggiPosition wantedMovePosition) {
        Pieces allPieces = allPieces();
        return movePieces.move(selectPiecePosition, wantedMovePosition, allPieces);
    }

    private Pieces moveTeamPieces(Team team) {
        if (team == Team.BLUE) {
            return blueTeamPieces;
        }
        return redTeamPieces;
    }

    private Pieces otherTeamPieces(Team team) {
        Team opposite = team.opposite();
        return moveTeamPieces(opposite);
    }

    public Map<Team, Integer> getPlayerScores() {
        return playerScores.getCurrentPlayersScores();
    }
}
