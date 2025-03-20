package piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Team, Pieces> teamBoard;

    public Board(String fileSrc) {
        String initiateFileName = getClass().getResource(fileSrc).getFile();
        this.teamBoard = PiecesCreateFactory.generate(initiateFileName);
    }

    public Map<Position, Piece> playerBoard() {
        List<Piece> allPieces = allPieces();
        Map<Position, Piece> playerBoard = new HashMap<>();
        for (Piece piece : allPieces) {
            if (piece.isSameTeam(Team.BLUE) && piece.isSamePosition(new Position(0, 0))) {
                System.out.println();
            }
            if (piece.isSameTeam(Team.BLUE) && piece.isSamePosition(new Position(1, 0))) {
                System.out.println();
            }

            playerBoard.put(piece.getPosition(), piece);
        }
        return playerBoard;
    }

    private List<Piece> allPieces() {
        Pieces bluePieces = teamBoard.get(Team.BLUE);
        Pieces redPieces = teamBoard.get(Team.RED);
        return bluePieces.add(redPieces);
    }

    public boolean isKingDead() {
        return false;
    }

    public void move(Team team, Position selectPiecePosition, Position selectPosition) {
        Pieces pieces = teamBoard.get(team);
        otherTeamPieces(team);
        Piece piece = pieces.move(selectPiecePosition, selectPosition, allPieces());
        Pieces otherPieces = otherTeamPieces(team);
        pieces.killPieceFrom(piece, otherPieces);

    }

    private Pieces otherTeamPieces(Team team) {
        if (team == Team.BLUE) {
            return teamBoard.get(Team.RED);
        }
        return teamBoard.get(Team.BLUE);
    }
}
