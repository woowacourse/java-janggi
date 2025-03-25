package janggi.game;

import janggi.board.Board;
import janggi.board.BoardSetup;
import janggi.board.Position;
import janggi.team.Team;
import janggi.team.TeamCho;
import janggi.team.TeamHan;
import janggi.team.TeamName;
import janggi.view.Input;
import janggi.view.Output;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Manager {
    private static final String REGEX_PATTERN = "[\\[\\]]";
    private static final String DELIMITER_COMMA = ",";
    private static final String ANSWER_POSITIVE = "Y";

    private final Input input;
    private final Output output;

    public Manager() {
        this.input = new Input();
        this.output = new Output();
    }

    public void run() {
        TeamCho teamCho = repeatInput(
                () -> new TeamCho(BoardSetup.of(TeamName.CHO, input.readPositionOption(TeamName.CHO))));
        TeamHan teamHan = repeatInput(
                () -> new TeamHan(BoardSetup.of(TeamName.HAN, input.readPositionOption(TeamName.HAN))));

        Board board = new Board();
        Team oldTeam = teamHan;
        do {
            output.printBoard(teamHan, teamCho);

            Team currentTeam = validateTurn(board, teamCho, teamHan, oldTeam);
            oldTeam = currentTeam;

            Map<String, Position> startingPieceInfo = validateStartingPoint(currentTeam);
            String pieceName = startingPieceInfo.keySet().iterator().next();
            Position currentPosition = startingPieceInfo.get(pieceName);

            Position destination = validateDestination(board, currentTeam, pieceName, currentPosition);

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

    private Team validateTurn(Board board, TeamCho teamCho, TeamHan teamHan, Team oldTeam) {
        while (true) {
            try {
                TeamName teamName = repeatInput(() -> TeamName.from(input.readPieceTeamName()));
                Team currentTeam = teamName.equals(TeamName.CHO) ? teamCho : teamHan;

                board.validateTeamTurn(oldTeam, currentTeam);
                return currentTeam;
            } catch (IllegalArgumentException e) {
                input.displayError(e.getMessage());
            }
        }
    }

    private Map<String, Position> validateStartingPoint(Team currentTeam) {
        while (true) {
            try {
                List<String> startingPieceInfos = input.readPieceStartPoint();
                String pieceName = startingPieceInfos.getFirst();
                Position currentPosition = parsePosition(startingPieceInfos.getLast());

                currentTeam.validatePiece(pieceName, currentPosition);
                return Map.of(pieceName, currentPosition);
            } catch (IllegalArgumentException e) {
                input.displayError(e.getMessage());
            }
        }
    }

    private Position validateDestination(Board board, Team currentTeam, String pieceName, Position currentPosition) {
        while (true) {
            try {
                String pieceMovedInfo = input.readPieceDestination();
                Position destination = parsePosition(pieceMovedInfo);

                board.validatePieceRange(destination);
                currentTeam.validatePieceMovement(pieceName, currentPosition, destination);
                currentTeam.validateKingGuardDestinationIsInPalace(pieceName, destination);
                currentTeam.validateDestinationIsNotOccupiedBySameTeam(destination);

                List<Position> positionsOnPath = board.findPositionsOnPath(currentPosition, destination);
                currentTeam.validateLegalMove(pieceName, positionsOnPath);

                return destination;
            } catch (IllegalArgumentException e) {
                input.displayError(e.getMessage());
            }
        }
    }

    private Position parsePosition(String coordinates) {
        List<Integer> pieceCoordinates = Arrays.stream(coordinates.replaceAll(REGEX_PATTERN, "").split(DELIMITER_COMMA))
                .map(Integer::parseInt)
                .toList();
        return new Position(pieceCoordinates.getFirst(), pieceCoordinates.getLast());
    }

    private boolean checkContinue(TeamHan teamHan, TeamCho teamCho) {
        if (teamHan.isKingCaught() || teamCho.isKingCaught()) {
            return false;
        }
        return input.readGameContinue().equalsIgnoreCase(ANSWER_POSITIVE);
    }
}
