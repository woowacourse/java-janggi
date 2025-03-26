package janggi.game;

import janggi.board.BoardNavigator;
import janggi.board.BoardSetup;
import janggi.board.Position;
import janggi.team.Team;
import janggi.team.TeamFactory;
import janggi.team.TeamName;
import janggi.view.Input;
import janggi.view.Output;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Manager {

    private static final String ANSWER_POSITIVE = "Y";

    private final Input input;
    private final Output output;

    public Manager() {
        this.input = new Input();
        this.output = new Output();
    }

    public void run() {
        Team teamCho = repeatInput(
                () -> TeamFactory.createTeam(BoardSetup.of(input.readPositionOption(TeamName.CHO))));
        Team teamHan = repeatInput(
                () -> TeamFactory.createTeam(BoardSetup.of(input.readPositionOption(TeamName.HAN))));

        BoardNavigator boardNavigator = new BoardNavigator();
        Team oldTeam = teamHan;
        do {
            output.printBoard(teamHan, teamCho);

            Team currentTeam = oldTeam.equals(teamCho) ? teamHan : teamCho;
            oldTeam = currentTeam;

            Map<String, Position> startingPieceInfo = validateStartingPoint(currentTeam);
            String pieceName = startingPieceInfo.keySet().iterator().next();
            Position currentPosition = startingPieceInfo.get(pieceName);

            Position destination = validateDestination(boardNavigator, currentTeam, pieceName, currentPosition);

            currentTeam.move(pieceName, currentPosition, destination);
            Team opponentTeam = currentTeam.equals(teamCho) ? teamHan : teamCho;
            opponentTeam.updateStatusIfCaught(destination);
        } while (checkContinue(teamHan, teamCho));

        teamCho.trackTeamScore(TeamName.CHO);
        teamHan.trackTeamScore(TeamName.HAN);
        output.printTeamScore(teamHan, teamCho);
    }

    private <T> T repeatInput(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                input.displayError(e.getMessage());
            }
        }
    }

    private Map<String, Position> validateStartingPoint(Team currentTeam) {
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

    private Position validateDestination(BoardNavigator boardNavigator, Team currentTeam, String pieceName,
                                         Position currentPosition) {
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

    private boolean checkContinue(Team teamHan, Team teamCho) {
        if (teamHan.isKingCaught() || teamCho.isKingCaught()) {
            return false;
        }
        return input.readGameContinue().equalsIgnoreCase(ANSWER_POSITIVE);
    }
}
