package store;

import game.Team;
import location.Position;
import java.util.List;
import piece.Piece;

public class Player {

    private final Pieces myPieces;
    private final Pieces catchPieces;
    private final Team team;

    public Player(Pieces myPieces, Pieces catchPieces, Team team) {
        this.myPieces = myPieces;
        this.catchPieces = catchPieces;
        this.team = team;
    }

    public void replace(Piece piece, Piece movedPiece) {
        myPieces.delete(piece);
        myPieces.add(movedPiece);
    }

    public void catchPiece(Piece piece) {
        catchPieces.add(piece);
    }

    public Piece getPieceByPoint(Position position) {
        return myPieces.getByPosition(position);
    }

    public List<Piece> getMyPieces() {
        return myPieces.getPieces();
    }

    public double calculateTotalScore() {
        return team.getInitialScore() - catchPieces.calculateTotalScore();
    }

    public void delete(Piece piece) {
        myPieces.delete(piece);
    }

    public boolean isContainedPiece(Position position) {
        return myPieces.isContainedPieceAtPosition(position);
    }

    public boolean isSameTeam(Team targetTeam) {
        return team == targetTeam;
    }

    private boolean isAlreadyPlayerPieceInPosition(Position position) {
        return myPieces.isAlreadyPieceInPosition(position);
    }

    public void checkPlayerPieceAlreadyInDestination(Position end) {
        if (isAlreadyPlayerPieceInPosition(end)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 본인의 기물이 존재합니다.");
        }
    }
}
