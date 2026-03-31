package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.side.TeamType;
import janggi.dto.BoardSpots;

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

    public boolean isNextTurnTeamPieceExists(Position position) {
        return board.isPieceExist(position, opponentTeamType());
    }

    public Piece findPiece(Position position) {
        return board.findNextTurnTeamPiece(position, opponentTeamType());
    }

    public String getPieceName(Position position) {
        return board.getPieceName(position, opponentTeamType());
    }

    public Turn move(Position start, Position end) {
        TeamType currentTeamType = opponentTeamType();
        Board movedBoard = board.move(start, end, currentTeamType);
        return new Turn(currentTeamType, movedBoard);
    }

    public void validateCanMove(Position start, Position end) {
        board.validateCanMove(start, end, opponentTeamType());
    }

    public String nextTurnTeam() {
        TeamType teamType = opponentTeamType();
        return teamType.getName();
    }

    public BoardSpots makeBoardSnapShot() {
        return board.makeSnapShot();
    }

    private TeamType opponentTeamType() {
        if (movedTeam == TeamType.CHU) {
            return TeamType.HAN;
        }
        return TeamType.CHU;
    }
}
