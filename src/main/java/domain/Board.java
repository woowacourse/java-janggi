package domain;

import controller.dto.CurrentBoardStatus;
import domain.piece.Piece;
import exception.GameErrorMessage;
import exception.custom.InvalidGameInputException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import strategy.InitializeStrategy;

public class Board {
    private static final int MAX_ROW = 10;
    private static final int MIN_ROW = 1;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_COLUMN = 1;

    private static final int CHO_PALACE_MAX_ROW = 10;
    private static final int CHO_PALACE_MIN_ROW = 8;
    private static final int HAN_PALACE_MAX_ROW = 3;
    private static final int HAN_PALACE_MIN_ROW = 1;
    private static final int PALACE_MAX_COLUMN = 6;
    private static final int PALACE_MIN_COLUMN = 4;

    private static final Position CHO_PALACE_CENTER = Position.from(9, 5);
    private static final Position HAN_PALACE_CENTER = Position.from(2, 5);

    protected final Map<Position, Piece> pieces = new HashMap<>();

    /**
     * 초기화 전용 생성자
     */
    public Board(Map<Team, HorseElephantFormation> horseElephantFormations) {
        horseElephantFormations.forEach(((team, horseElephantFormation) ->
                initTeamBoard(horseElephantFormation.getStrategy(), team)));
    }

    public void move(Position from, Position to, Team team) {
        Piece piece = validateMovablePiece(from, to, team);
        validateCanMove(from, to, piece);
        movePiece(from, to, piece);
    }

    public boolean isExistSameType(Position position, Piece piece) {
        if (hasPieceInPosition(position)) {
            return pieces.get(position).getType()
                    .equals(piece.getType());
        }

        return false;
    }

    public boolean isEmpty(Position position) {
        return !pieces.containsKey(position);
    }

    public boolean hasSameTeamOn(Position position, Piece piece) {
        if (hasPieceInPosition(position)) {
            return pieces.get(position).isSameTeam(piece);
        }

        return false;
    }

    public List<Piece> findPiecesInLinePath(Position from, Position to) {
        if (from.isSameRow(to)) {
            return findPiecesInRow(from, to);
        }

        if (from.isSameColumn(to)) {
            return findPiecesInColumn(from, to);
        }
        return new ArrayList<>();
    }

    public List<CurrentBoardStatus> getCurrentStatus() {
        List<CurrentBoardStatus> currentBoardStatuses = new ArrayList<>();

        pieces.forEach(((position, piece) ->
                currentBoardStatuses.add(
                        CurrentBoardStatus.of(position, piece.getType(), piece.getTeam())
                )));

        return currentBoardStatuses;
    }

    public boolean isInPalace(Team team, Position target) {
        if (Team.CHO == team) {
            return target.isPossiblePosition(CHO_PALACE_MAX_ROW, CHO_PALACE_MIN_ROW, PALACE_MAX_COLUMN,
                    PALACE_MIN_COLUMN);
        }
        return target.isPossiblePosition(HAN_PALACE_MAX_ROW, HAN_PALACE_MIN_ROW, PALACE_MAX_COLUMN, PALACE_MIN_COLUMN);
    }

    public boolean isCenterPositionInPalace(Team team, Position target) {
        if (Team.CHO == team) {
            return CHO_PALACE_CENTER.isSamePosition(target);
        }
        return HAN_PALACE_CENTER.isSamePosition(target);
    }

    public boolean isExistPiece(PieceType target, Team team) {
        return pieces.values().stream()
                .anyMatch(piece -> piece.getType() == target && piece.getTeam() == team);
    }

    public int getCurrentScoreOfTeam(Team team) {
        return pieces.values().stream()
                .filter(piece -> piece.getTeam() == team)
                .mapToInt(Piece::getScore)
                .sum();
    }

    public String getPieceTypeOnPosition(Position target) {
        return pieces.get(target).getPieceTypeName();
    }

    public String getPieceTeamOnPosition(Position target) {
        return pieces.get(target).getTeamName();
    }

    /**
     * 헬퍼 메서드
     */
    private Piece validateMovablePiece(Position from, Position to, Team team) {
        Piece piece = pieces.get(from);

        if (piece == null) {
            throw new InvalidGameInputException(GameErrorMessage.PIECE_NOT_FOUND.getMessage());
        }

        if (piece.getTeam() != team) {
            throw new InvalidGameInputException(
                    String.format(GameErrorMessage.INVALID_TEAM_TURN.getMessage(), team.getKoreanName())
            );
        }

        if (!to.isPossiblePosition(MAX_ROW, MIN_ROW, MAX_COLUMN, MIN_COLUMN)) {
            throw new InvalidGameInputException(GameErrorMessage.INVALID_POSITION_RANGE.getMessage());
        }

        return piece;
    }

    private void validateCanMove(Position from, Position to, Piece piece) {
        if (!piece.canMove(from, to, this)) {
            throw new InvalidGameInputException(GameErrorMessage.INVALID_MOVE.getMessage());
        }
    }

    private void movePiece(Position from, Position to, Piece piece) {
        pieces.remove(from);
        pieces.put(to, piece);
    }

    private boolean hasPieceInPosition(Position position) {
        return pieces.containsKey(position);
    }

    private void initTeamBoard(InitializeStrategy strategy, Team team) {
        pieces.putAll(strategy.initialize(team));
    }

    private List<Piece> findPiecesInRow(Position from, Position to) {
        List<Piece> result = new ArrayList<>();

        int row = from.getRow();
        int start = Math.min(from.getColumn(), to.getColumn());
        int end = Math.max(from.getColumn(), to.getColumn());

        for (int column = start + 1; column < end; column++) {
            Position searchPosition = Position.from(row, column);
            if (pieces.containsKey(searchPosition)) {
                result.add(pieces.get(searchPosition));
            }
        }
        return result;
    }

    private List<Piece> findPiecesInColumn(Position from, Position to) {
        List<Piece> result = new ArrayList<>();

        int column = from.getColumn();
        int start = Math.min(from.getRow(), to.getRow());
        int end = Math.max(from.getRow(), to.getRow());

        for (int row = start + 1; row < end; row++) {
            Position searchPosition = Position.from(row, column);
            if (pieces.containsKey(searchPosition)) {
                result.add(pieces.get(searchPosition));
            }
        }
        return result;
    }
}
