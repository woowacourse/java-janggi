package janggi.game;

import janggi.board.BoardNavigator;
import janggi.board.Position;
import janggi.team.Team;
import janggi.view.Input;
import java.util.List;
import java.util.Map;

public class GameValidator {
    private final Input input;

    public GameValidator(Input input) {
        this.input = input;
    }

    public Map<String, Position> validateStartPoint(Team currentTeam) {
        while (true) {
            try {
                Map<String, Position> pieceStartingPoint = input.readPieceStartPoint(currentTeam);
                String pieceName = pieceStartingPoint.keySet().iterator().next();
                Position currentPosition = pieceStartingPoint.get(pieceName);

                currentTeam.validatePiece(pieceName, currentPosition);
                return pieceStartingPoint;
            } catch (IllegalArgumentException e) {
                input.displayError(e.getMessage());
            }
        }
    }

    public Position validateDestination(
            Team currentTeam,
            String pieceName,
            Position currentPosition
    ) {
        BoardNavigator boardNavigator = new BoardNavigator();
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
