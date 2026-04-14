package janggi.domain;

import janggi.domain.movepath.MovePathStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.dto.BoardSpot;
import janggi.dto.BoardSpots;
import janggi.dto.MoveRoute;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
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

    public void validateMove(Position startPosition, Position endPosition, TeamType playingTeam) {
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
        validateMove(startPosition, endPosition, playingTeam);
        Team movedCurrentTeam = currentTeam(playingTeam).move(startPosition, endPosition);
        Team remainedOpponentTeam = removeOpponentPiece(playingTeam, endPosition);
        return createMovedBoard(playingTeam, movedCurrentTeam, remainedOpponentTeam);
    }

    public boolean hasGung(TeamType teamType) {
        return findSpecificTeam(teamType).hasPieceType(PieceType.GUNG);
    }

    public Optional<TeamType> findWinner() {
        boolean chuHasGung = hasGung(TeamType.CHU);
        boolean hanHasGung = hasGung(TeamType.HAN);
        if (chuHasGung == hanHasGung) {
            return Optional.empty();
        }
        if (chuHasGung) {
            return Optional.of(TeamType.CHU);
        }
        return Optional.of(TeamType.HAN);
    }

    public int calculateScore(TeamType teamType) {
        Team team = findSpecificTeam(teamType);
        return team.calculatePiecesScore();
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
        Optional<MovePathStrategy> movePath = piece.findMovePath(
            piecePosition.getX(),
            piecePosition.getY(),
            targetPosition.getX(),
            targetPosition.getY()
        );
        if (movePath.isEmpty()) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        MoveRoute moveRoute = createMoveRoute(piecePosition, targetPosition, movePath.get());
        if (!piece.canMove(moveRoute)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
    }

    private MoveRoute createMoveRoute(
        Position startPosition,
        Position targetPosition,
        MovePathStrategy movePath
    ) {
        return new MoveRoute(
            findIntermediatePieceTypes(startPosition, targetPosition, movePath),
            findTargetPieceType(targetPosition)
        );
    }

    private List<PieceType> findIntermediatePieceTypes(
        Position startPosition,
        Position targetPosition,
        MovePathStrategy movePath
    ) {
        return movePath.intermediatePositions(startPosition, targetPosition).stream()
            .map(this::findPieceType)
            .flatMap(Optional::stream)
            .toList();
    }

    private Optional<PieceType> findTargetPieceType(Position targetPosition) {
        return findPieceType(targetPosition);
    }

    private Optional<PieceType> findPieceType(Position position) {
        return findPiece(position)
            .map(Piece::getPieceType);
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
        throw new IllegalArgumentException("존재하지 않는 팀입니다.");
    }
}
