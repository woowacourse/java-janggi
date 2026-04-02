package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.dto.BoardSpot;
import janggi.dto.BoardSpots;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Board {

    private static final int FIRST_INDEX = 1;
    private static final int LAST_X_INDEX = 9;
    private static final int LAST_Y_INDEX = 10;

    private final Map<TeamType, Team> teams;

    private Board(Map<TeamType, Team> teams) {
        this.teams = new EnumMap<>(teams);
    }

    public static Board createInitialBoard() {
        Map<TeamType, Team> teams = new EnumMap<>(TeamType.class);
        teams.put(TeamType.CHU, Team.createInitialTeam(TeamType.CHU));
        teams.put(TeamType.HAN, Team.createInitialTeam(TeamType.HAN));
        return new Board(teams);
    }

    public BoardSpots makeSnapShot() {
        HashMap<Position, BoardSpot> boardSpots = new HashMap<>();
        for (Team team : teams.values()) {
            boardSpots.putAll(team.makeSnapShot().value());
        }
        return new BoardSpots(boardSpots);
    }

    public boolean isPieceExist(Position position, TeamType beforeTeam) {
        validateRange(position);
        Team nowTeam = opponentTeam(beforeTeam);
        return nowTeam.findPiece(position).isPresent();
    }

    public Piece findNextTurnTeamPiece(Position position, TeamType beforeTeamType) {
        Team nowTeam = opponentTeam(beforeTeamType);
        return findTeamPiece(position, nowTeam);
    }

    public Optional<Piece> findPiece(Position position) {
        return teams.values().stream()
            .map(team -> team.findPiece(position))
            .flatMap(Optional::stream)
            .findFirst();
    }

    public boolean hasPiece(Position position) {
        return findPiece(position).isPresent();
    }

    public void canMove(Position startPosition, Position endPosition, TeamType playingTeam) {
        validateRange(startPosition);
        validateRange(endPosition);
        Piece piece = findTeamPiece(startPosition, currentTeam(playingTeam));
        validateTargetPosition(currentTeam(playingTeam), endPosition);
        validateCanMove(piece, startPosition, endPosition);
    }

    public Board move(
        Position startPosition,
        Position endPosition,
        TeamType playingTeam
    ) {
        canMove(startPosition, endPosition, playingTeam);
        Team movedCurrentTeam = currentTeam(playingTeam).move(startPosition, endPosition);
        Team remainedOpponentTeam = removeOpponentPiece(playingTeam, endPosition);
        return createMovedBoard(playingTeam, movedCurrentTeam, remainedOpponentTeam);
    }

    private Piece findTeamPiece(Position position, Team nowTeam) {
        return nowTeam.findPiece(position)
            .orElseThrow(() -> new IllegalArgumentException("입력한 위치에 기물이 없습니다."));
    }

    private Team currentTeam(TeamType nowTurnTeamType) {
        return findSpecificTeam(nowTurnTeamType);
    }

    private Team opponentTeam(TeamType nowTurnTeamType) {
        return findSpecificTeam(nowTurnTeamType.findOpponent());
    }

    private void validateCanMove(Piece piece, Position piecePosition, Position targetPosition) {
        if (!piece.isValidMovePattern(piecePosition.getX(), piecePosition.getY(), targetPosition.getX(),
            targetPosition.getY())) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        if (!piece.isObstaclesNotExist(piecePosition, targetPosition, this)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private void validateTargetPosition(Team team, Position targetPosition) {
        if (team.findPiece(targetPosition).isPresent()) {
            throw new IllegalArgumentException("같은 팀의 기물이 있는 위치로는 이동할 수 없습니다.");
        }
    }

    private Team removeOpponentPiece(TeamType nowTurn, Position targetPosition) {
        Team opponentTeam = opponentTeam(nowTurn);
        if (opponentTeam.findPiece(targetPosition).isEmpty()) {
            return opponentTeam;
        }
        return opponentTeam.remove(targetPosition);
    }

    private Board createMovedBoard(TeamType nowTurn, Team movedCurrentTeam, Team remainedOpponentTeam) {
        Map<TeamType, Team> movedTeams = new EnumMap<>(teams);
        movedTeams.put(nowTurn, movedCurrentTeam);
        movedTeams.put(nowTurn.findOpponent(), remainedOpponentTeam);
        return new Board(movedTeams);
    }

    private void validateRange(Position inputPosition) {
        int x = inputPosition.getX();
        int y = inputPosition.getY();
        if (isNotInRange(FIRST_INDEX, LAST_X_INDEX, x) || isNotInRange(FIRST_INDEX, LAST_Y_INDEX, y)) {
            throw new IllegalArgumentException("입력한 좌표가 장기판 범위 밖입니다.");
        }
    }

    private boolean isInRange(int start, int last, int index) {
        return index >= start && index <= last;
    }

    private boolean isNotInRange(int start, int last, int index) {
        return !isInRange(start, last, index);
    }

    private Team findSpecificTeam(TeamType teamType) {
        if (teams.containsKey(teamType)) {
            return teams.get(teamType);
        }
        throw new IllegalArgumentException(String.format("%s 팀이 존재하지 않습니다.", teamType.getName()));
    }
}
