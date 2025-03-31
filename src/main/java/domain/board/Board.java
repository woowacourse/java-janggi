package domain.board;

import domain.Team;
import domain.pieces.Piece;
import domain.pieces.PieceNames;
import execptions.JanggiArgumentException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Board {

    private static final int VALID_ROW_SIZE = 10;
    private static final int VALID_COLUMN_SIZE = 9;

    private final Map<BoardPoint, Piece> locations;

    private static final List<Step> UNABLE_BOARD_STEPS = List.of(
            new Step(new BoardPoint(0, 4), new BoardPoint(1, 3)),
            new Step(new BoardPoint(0, 4), new BoardPoint(1, 5)),
            new Step(new BoardPoint(1, 3), new BoardPoint(2, 4)),
            new Step(new BoardPoint(1, 5), new BoardPoint(2, 4))
    );

    public Board(final Map<BoardPoint, Piece> locations) {
        validateRange(locations);
        this.locations = locations;
    }

    private void validateRange(final Map<BoardPoint, Piece> locations) {
        for (BoardPoint boardPoint : locations.keySet()) {
            validateRangeOfPoint(boardPoint);
        }
    }

    private void validateRangeOfPoint(BoardPoint boardPoint) {
        if (boardPoint.row() >= VALID_ROW_SIZE || boardPoint.column() >= VALID_COLUMN_SIZE) {
            throw new JanggiArgumentException("보드의 크기 범위에 맞지 않습니다.");
        }
    }

    public Map<BoardPoint, Piece> getLocations() {
        return new HashMap<>(locations);
    }

    public void movePiece(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint,
                          final Team team) {
        validateBoardRoutes(startBoardPoint, arrivalBoardPoint);
        processMovement(startBoardPoint, arrivalBoardPoint, team);
    }

    public boolean isGeneralDied() {
        for (BoardPoint boardPoint : locations.keySet()) {
            String name = locations.get(boardPoint).getName();
            if (name.equals(PieceNames.GENERAL.name())) {
                return true;
            }
        }
        return false;
    }

    public int calculateScoreOf(Team team) {
        int result = 0;
        for (BoardPoint boardPoint : locations.keySet()) {
            result = addScore(team, boardPoint, result);
        }
        return result;
    }

    private int addScore(Team team, BoardPoint boardPoint, int result) {
        Piece piece = locations.get(boardPoint);
        if (piece.hasEqualTeam(team)) {
            result += piece.getScore();
        }
        return result;
    }

    private void validateBoardRoutes(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint) {
        for (final Step step : UNABLE_BOARD_STEPS) {
            if (step.equals(new Step(startBoardPoint, arrivalBoardPoint))) {
                throw new JanggiArgumentException("이동 불가능한 경로입니다.");
            }
        }
    }

    private void processMovement(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint,
                                 final Team team) {
        checkStartPoint(startBoardPoint, team);
        final Piece pieceAtStartPoint = locations.get(startBoardPoint);
        checkPieceIsAbleToMove(startBoardPoint, arrivalBoardPoint, pieceAtStartPoint);
        movePiece(startBoardPoint, arrivalBoardPoint, pieceAtStartPoint);
    }

    private void movePiece(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint,
                           final Piece pieceAtStartPoint) {
        locations.put(arrivalBoardPoint, pieceAtStartPoint);
        locations.remove(startBoardPoint);
    }

    private void checkPieceIsAbleToMove(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint,
                                        final Piece pieceAtStartPoint) {
        checkOutOfRoute(startBoardPoint, arrivalBoardPoint, pieceAtStartPoint);

        final List<BoardPoint> routeBoardPoints = pieceAtStartPoint.getRoutePoints(startBoardPoint,
                arrivalBoardPoint);
        final PieceOnRoute pieceOnRoute = getAllPieceOnRoute(routeBoardPoints);

        checkPieceOnRoute(pieceAtStartPoint, pieceOnRoute);
    }

    private void checkStartPoint(final BoardPoint startBoardPoint, final Team team) {
        if (!locations.containsKey(startBoardPoint)) {
            throw new JanggiArgumentException("출발점에 이동할 기물이 없습니다.");
        }

        Piece pieceAtStartPoint = locations.get(startBoardPoint);

        if (!pieceAtStartPoint.hasEqualTeam(team)) {
            throw new JanggiArgumentException("아군 기물만 움직일 수 있습니다.");
        }
    }

    private void checkPieceOnRoute(final Piece pieceAtStartPoint, final PieceOnRoute pieceOnRoute) {
        if (!pieceAtStartPoint.isMovable(pieceOnRoute)) {
            throw new JanggiArgumentException("해당 경로로 이동할 수 없습니다.");
        }
    }

    private void checkOutOfRoute(final BoardPoint startBoardPoint, final BoardPoint arrivalBoardPoint,
                                 final Piece pieceAtStartPoint) {
        if (!pieceAtStartPoint.isAbleToArrive(startBoardPoint, arrivalBoardPoint)) {
            throw new JanggiArgumentException("해당 기물이 도착할 수 없는 위치입니다.");
        }
    }

    private PieceOnRoute getAllPieceOnRoute(final List<BoardPoint> routeBoardPoints) {
        Piece pieceAtArrivalPoint = locations.getOrDefault(routeBoardPoints.getLast(), null);

        List<Piece> piecesOnRoute = routeBoardPoints.subList(0, routeBoardPoints.size() - 1).stream()
                .filter(locations::containsKey)
                .map(locations::get)
                .toList();

        return new PieceOnRoute(piecesOnRoute, pieceAtArrivalPoint);
    }
}
