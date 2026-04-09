package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;

import java.util.Map;

public class Turn {

    private final TeamType movedTeam;
    private final Board board;

    private Turn(TeamType movedTeam, Board board) {
        this.movedTeam = movedTeam;
        this.board = board;
    }

    public static Turn createInitialTurn() {
        return new Turn(TeamType.HAN, Board.createInitialBoard());
    }

    public static Turn loadPreviousTurn(TeamType teamType, Board board) {
        return new Turn(teamType, board);
    }

    public boolean isRunning() {
        return board.isRunning();
    }

    public Map<Position, Piece> makeBoardSnapShot() {
        return board.makeSnapShot();
    }

    public String nextTurnTeam() {
        TeamType teamType = opponentTeamType();
        return teamType.getName();
    }

    public boolean isNextTurnTeamPieceExists(Position position) {
        return board.isPieceExists(position, opponentTeamType());
    }

    public String getPieceName(Position position) {
        return board.getPieceName(position, opponentTeamType());
    }

    public void validateCanMove(Position start, Position end) {
        board.validateCanMove(start, end, opponentTeamType());
    }

    public Turn move(Position start, Position end) {
        TeamType currentTeamType = opponentTeamType();
        Board movedBoard = board.move(start, end, currentTeamType);
        return new Turn(currentTeamType, movedBoard);
    }

    public String winTeamName() {
        return board.winTeamName();
    }

    private TeamType opponentTeamType() {
        if (movedTeam == TeamType.CHU) {
            return TeamType.HAN;
        }
        return TeamType.CHU;
    }
}
