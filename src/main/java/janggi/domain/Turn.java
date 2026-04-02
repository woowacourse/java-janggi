package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
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

    public boolean isMyTeamPieceExist(Position position) {
        return board.isPieceExist(position, movedTeam);
    }

    public Piece findPiece(Position position) {
        return board.findNextTurnTeamPiece(position, movedTeam);
    }

    public Turn move(Position startPosition, Position endPosition) {
        TeamType nowTurn = playingTeamType();
        Board movedBoard = board.move(startPosition, endPosition, nowTurn);
        return new Turn(nowTurn, movedBoard);
    }

    public void canMove(Position startPosition, Position endPosition) {
        board.canMove(startPosition, endPosition, playingTeamType());
    }

    public TeamType nextTurnTeam() {
        return playingTeamType();
    }

    public BoardSpots makeBoardSnapShot() {
        return board.makeSnapShot();
    }

    private TeamType playingTeamType() {
        if (movedTeam == TeamType.CHU) {
            return TeamType.HAN;
        }
        return TeamType.CHU;
    }
}
