package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.ScoreBoard;
import janggi.domain.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import janggi.dto.MoveResultDto;
import janggi.exception.ExceptionMessage;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardChecker {

    private final Map<Position, Piece> board;
    private final ScoreBoard scoreBoard;

    public static Board restore(Map<Position, Piece> board) {
        return new Board(board, ScoreBoard.restore(board));
    }

    private Board(Map<Position, Piece> board, ScoreBoard scoreBoard) {
        this.board = board;
        this.scoreBoard = scoreBoard;
    }

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
        this.scoreBoard = ScoreBoard.create();
    }

    @Override
    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    @Override
    public Piece pieceAt(Position position) {
        if (hasPieceAt(position)) {
            return board.get(position);
        }
        throw new IllegalArgumentException(ExceptionMessage.SOURCE_NOT_EXISTS.getMessage());
    }

    @Override
    public boolean isSamePieceRule(Position source, Position target) {
        if (hasPieceAt(target)) {
            Piece sourcePiece = board.get(source);
            Piece targetPiece = board.get(target);
            return sourcePiece.isSamePieceRule(targetPiece.pieceRule());
        }
        return false;
    }

    public MoveResultDto movePiece(Position source, Position destination, CampType campType) {

        validateSource(source, campType);
        validateDestination(destination, source, campType);

        Piece piece = board.get(source);
        piece.validateMove(source, destination, this);

        boolean captured = board.containsKey(destination);
        if (captured) {
            Piece existsPiece = board.get(destination);
            scoreBoard.minusScore(campType.next(), existsPiece);
        }
        board.put(destination, piece);
        board.remove(source);
        return new MoveResultDto(source, destination, captured);
    }

    public void validateSource(Position source, CampType campType) {
        if (!hasPieceAt(source)) {
            throw new IllegalArgumentException(ExceptionMessage.SOURCE_NOT_EXISTS.getMessage());
        }
        if (!hasSameCampPieceAt(source, campType)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_CAMP_PIECE.getMessage());
        }
    }

    public void validateDestination(Position destination, Position source, CampType campType) {
        if (destination.equals(source)) {
            throw new IllegalArgumentException(ExceptionMessage.PIECE_MUST_MOVE.getMessage());
        }
        if (hasSameCampPieceAt(destination, campType)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_CAMP_PIECE_AT_DESTINATION.getMessage());
        }
    }

    private boolean hasSameCampPieceAt(Position position, CampType campType) {
        if (hasPieceAt(position)) {
            Piece piece = board.get(position);
            return piece.isSameCampType(campType);
        }
        return false;
    }

    public boolean isRivalGeneralKilled(Turn turn) {
        CampType campType = turn.peekNextTurn();
        return board.values().stream()
                .noneMatch(piece -> piece.isSameCampType(campType) && piece.isSamePieceRule(PieceRule.GENERAL));
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    public Map<CampType, Double> getScoreBoard() {
        return Map.copyOf(scoreBoard.getScoreBoard());
    }
}
