package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;

import java.util.Map;

public class Turn {

    private final Long id;
    private final TeamType currentTeam;
    private final Board board;

    private Turn(Long id, TeamType currentTeam, Board board) {
        this.id = id;
        this.currentTeam = currentTeam;
        this.board = board;
    }

    public static Turn createInitialTurn() {
        return new Turn(null, TeamType.HAN, Board.createInitialBoard());
    }

    public static Turn loadPreviousTurn(long turnId, TeamType teamType, Board board) {
        return new Turn(turnId, teamType, board);
    }

    public static Turn savedTurn(long turnId, Turn turn) {
        return new Turn(turnId, turn.currentTeam, turn.board);
    }

    public long getId() {
        return id;
    }

    public TeamType getCurrentTeam() {
        return currentTeam;
    }

    public boolean isRunning() {
        return board.isRunning();
    }

    public Map<Position, Piece> makeBoardSnapShot() {
        return board.makeSnapShot();
    }

    public String nextTurnTeamName() {
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
        return new Turn(null, currentTeamType, movedBoard);
    }

    public Map<Position, Piece> allPieces() {
        return board.allPieces();
    }

    public String winTeamName() {
        return board.winTeamName();
    }

    private TeamType opponentTeamType() {
        if (currentTeam == TeamType.CHU) {
            return TeamType.HAN;
        }
        return TeamType.CHU;
    }
}
