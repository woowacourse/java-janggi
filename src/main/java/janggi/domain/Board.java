package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.side.Chu;
import janggi.domain.side.Han;
import janggi.domain.side.Team;
import janggi.domain.side.TeamType;
import janggi.dto.BoardSpot;
import janggi.dto.BoardSpots;
import java.util.HashMap;
import java.util.Optional;

public class Board {

    private static final int FIRST_INDEX = 1;
    private static final int LAST_X_INDEX = 9;
    private static final int LAST_Y_INDEX = 10;

    private final Team chu;
    private final Team han;

    private Board(Team chu, Team han) {
        this.chu = chu;
        this.han = han;
    }

    public static Board createInitialBoard() {
        return new Board(Chu.createInitialChu(), Han.createInitialHan());
    }

    public BoardSpots makeSnapShot() {
        HashMap<Position, BoardSpot> boardSpots = new HashMap<>(chu.makeSnapShot());
        boardSpots.putAll(han.makeSnapShot());
        return new BoardSpots(boardSpots);
    }

    public boolean isPieceExist(Position position, TeamType currentTeamType) {
        validateRange(position);
        Team currentTeam = currentTeam(currentTeamType);
        return currentTeam.isPieceExist(position);
    }

    public Piece findNextTurnTeamPiece(Position position, TeamType currentTeamType) {
        Team currentTeam = currentTeam(currentTeamType);
        return findTeamPiece(position, currentTeam);
    }

    public Optional<Piece> findPiece(Position position) {
        Optional<Piece> chuPiece = chu.findPiece(position);
        if (chuPiece.isPresent()) {
            return chuPiece;
        }
        return han.findPiece(position);
    }

    public boolean hasPiece(Position position) {
        return findPiece(position).isPresent();
    }

    public void canMove(Position startPosition, Position endPosition, TeamType currentTeamType) {
        validateRange(endPosition);
        Piece piece = findTeamPiece(startPosition, currentTeam(currentTeamType));
        validateEndPosition(currentTeam(currentTeamType), endPosition);
        validateCanMove(piece, startPosition, endPosition);
    }

    public Board move(
            Position startPosition,
            Position endPosition,
            TeamType nowTurn
    ) {
        Team movedCurrentTeam = currentTeam(nowTurn).move(startPosition, endPosition);
        Team remainedOpponentTeam = removeOpponentPiece(nowTurn, endPosition);
        return createMovedBoard(nowTurn, movedCurrentTeam, remainedOpponentTeam);
    }

    public String getPieceName(Position position, TeamType currentTeamType) {
        Piece piece = findTeamPiece(position, currentTeam(currentTeamType));
        return piece.name();
    }

    private Piece findTeamPiece(Position position, Team currentTeam) {
        return currentTeam.findPiece(position)
                .orElseThrow(() -> new IllegalArgumentException("입력한 위치에 기물이 없습니다."));
    }

    private Team currentTeam(TeamType nowTurn) {
        if (nowTurn == TeamType.CHU) {
            return chu;
        }
        return han;
    }

    private Team opponentTeam(TeamType nowTurn) {
        if (nowTurn == TeamType.CHU) {
            return han;
        }
        return chu;
    }

    private void validateEndPosition(Team team, Position endPosition) {
        if (team.findPiece(endPosition).isPresent()) {
            throw new IllegalArgumentException("같은 팀의 기물이 있는 위치로는 이동할 수 없습니다.");
        }
    }

    private void validateCanMove(Piece piece, Position startPosition, Position endPosition) {
        piece.validateCanMove(startPosition, endPosition, this);
    }

    private Team removeOpponentPiece(TeamType nowTurn, Position targetPosition) {
        Team opponentTeam = opponentTeam(nowTurn);
        if (opponentTeam.findPiece(targetPosition).isEmpty()) {
            return opponentTeam;
        }
        return opponentTeam.remove(targetPosition);
    }

    private Board createMovedBoard(TeamType nowTurn, Team movedCurrentTeam, Team remainedOpponentTeam) {
        if (nowTurn == TeamType.CHU) {
            return new Board(movedCurrentTeam, remainedOpponentTeam);
        }
        return new Board(remainedOpponentTeam, movedCurrentTeam);
    }

    private void validateRange(Position inputPosition) {
        int x = inputPosition.getX();
        int y = inputPosition.getY();
        if (isNotInRange(FIRST_INDEX, LAST_X_INDEX, x) || isNotInRange(FIRST_INDEX, LAST_Y_INDEX, y)) {
            throw new IllegalArgumentException("입력한 좌표가 장기판 범위 밖입니다.");
        }
    }

    private boolean isNotInRange(int start, int last, int index) {
        return !isInRange(start, last, index);
    }

    private boolean isInRange(int start, int last, int index) {
        return index >= start && index <= last;
    }
}
