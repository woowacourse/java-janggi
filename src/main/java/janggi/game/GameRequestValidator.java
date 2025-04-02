package janggi.game;

import janggi.board.BoardNavigator;
import janggi.board.Position;
import janggi.piece.Piece;
import janggi.team.Team;
import janggi.view.Input;
import java.util.List;
import java.util.Map;

public class GameRequestValidator {
    private final Input input;

    public GameRequestValidator(Input input) {
        this.input = input;
    }

    public Piece requestValidatedStartPoint(Team currentTeam) {
        while (true) {
            try {
                Map<String, Position> pieceStartingPoint = input.readPieceStartPoint(currentTeam);
                String pieceName = pieceStartingPoint.keySet().iterator().next();
                Position currentPosition = pieceStartingPoint.get(pieceName);

                currentTeam.validatePiece(pieceName, currentPosition);
                return currentTeam.findPieceByName(pieceName, currentPosition);
            } catch (IllegalArgumentException e) {
                input.displayError(e.getMessage());
            }
        }
    }

    public Position requestValidatedDestination(Team currentTeam, Piece currentPiece) {
        BoardNavigator boardNavigator = new BoardNavigator();
        String pieceName = currentPiece.getName();
        Position currentPosition = currentPiece.getPosition();
        while (true) {
            try {
                Position destination = input.readPieceDestination();
                currentTeam.validatePieceMovement(pieceName, currentPosition, destination);
                currentTeam.validateKingGuardDestinationIsInPalace(pieceName, destination);
                currentTeam.validateDestinationIsNotOccupiedBySameTeam(destination);

                List<Position> positionsOnPath = boardNavigator.findPositionsOnPath(currentPosition, destination);
                currentTeam.validateLegalMove(pieceName, positionsOnPath);

                return destination;
            } catch (IllegalArgumentException e) {
                input.displayError(e.getMessage());
            }
        }
    }
}
