package janggi.game;

import janggi.board.BoardSetup;
import janggi.db.BoardStatus;
import janggi.palace.PalaceFactory;
import janggi.piece.Piece;
import janggi.team.Team;
import janggi.team.TeamFactory;
import janggi.team.TeamName;
import janggi.team.Teams;
import janggi.view.Input;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class GameProcessManager {
    private static final String ANSWER_POSITIVE = "Y";

    private final Input input;
    private final BoardStatus boardStatus;

    public GameProcessManager(Input input, BoardStatus boardStatus) {
        this.input = input;
        this.boardStatus = boardStatus;
    }

    public Teams initializeTeams() {
        if (boardStatus.isBoardStatusEmpty()) {
            return createNewTeams();
        }
        return loadOrResetTeams();
    }

    public void saveTeams(Team teamCho, Team teamHan) {
        boardStatus.clearBoardStatus();
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(teamCho.getBoard());
        allPieces.addAll(teamHan.getBoard());
        boardStatus.saveBoardStatus(allPieces);
    }

    public boolean isContinue(Team teamHan, Team teamCho) {
        return !isGameOver(teamHan, teamCho) && input.readGameContinue().equalsIgnoreCase(ANSWER_POSITIVE);
    }

    private boolean isGameOver(Team teamHan, Team teamCho) {
        return teamHan.isKingCaught() || teamCho.isKingCaught();
    }

    private Teams createNewTeams() {
        Team teamCho = repeatInput(() -> TeamFactory.createTeam(BoardSetup.of(input.readSetup(TeamName.CHO))));
        Team teamHan = repeatInput(() -> TeamFactory.createTeam(BoardSetup.of(input.readSetup(TeamName.HAN))));
        return new Teams(teamCho, teamHan);
    }

    private Teams loadOrResetTeams() {
        if (input.readLoadData().equalsIgnoreCase(ANSWER_POSITIVE)) {
            return loadTeamsFromBoardStatus();
        }
        boardStatus.clearBoardStatus();
        return createNewTeams();
    }

    private Teams loadTeamsFromBoardStatus() {
        List<Piece> allPieces = boardStatus.loadBoardStatus();
        List<Piece> choPieces = allPieces.stream().filter(piece -> piece.getTeamName().equals("초")).toList();
        List<Piece> hanPieces = allPieces.stream().filter(piece -> piece.getTeamName().equals("한")).toList();

        Team teamCho = new Team(choPieces, PalaceFactory.createPalace(TeamName.CHO), TeamName.CHO);
        Team teamHan = new Team(hanPieces, PalaceFactory.createPalace(TeamName.HAN), TeamName.HAN);
        return new Teams(teamCho, teamHan);
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
}
