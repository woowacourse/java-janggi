package team.janggi.domain.board;

import java.util.List;
import team.janggi.domain.Position;
import team.janggi.domain.Team;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;
import team.janggi.exception.GameNotFinishedException;
import team.janggi.exception.GameOverException;
import team.janggi.exception.PieceCanNotMoveException;

public class Board {
    private final BoardPieces boardPieces;

    private Team winner;

    public Board(BoardPiecesInitializer initializer) {
        this(new BoardPieces(), initializer);
    }

    public Board(BoardPieces boardPieces, BoardPiecesInitializer initializer) {
        this.boardPieces = boardPieces;
        this.winner = null;

        initializer.initBoardStatus(boardPieces);
    }

    public BoardStateReader getStateReader() {
        return boardPieces.getBoardStateReader();
    }

    public void move(Team team, Position from, Position to) {
        if (isGameFinished()) {
            throw new GameOverException();
        }

        validateMove(team, from, to);

        boardPieces.movePiece(from, to);

        updateWinner();
    }

    public double getScore(Team team) {
        final int score = boardPieces.getAllPiece().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(Piece::getScore)
                .sum();

        if (team == Team.HAN) {
            return score + 1.5;
        }

        return score;
    }

    private void updateWinner() {
        final List<Piece> kings = boardPieces.getAllPiece().stream()
                .filter(piece -> piece.isPieceType(PieceType.KING))
                .toList();
        if (kings.size() > 1) {
            return;
        }

        final Piece lastKing = kings.getLast();
        if (lastKing.isSameTeam(Team.CHO)) {
            winner = Team.CHO;
            return;
        }

        if (lastKing.isSameTeam(Team.HAN)) {
            winner = Team.HAN;
            return;
        }
    }

    public boolean isGameFinished() {
        return winner != null;
    }

    public Team getWinner() {
        if (!isGameFinished()) {
            throw new GameNotFinishedException();
        }
        return winner;
    }

    private void validateMove(Team team, Position from, Position to) {
        validatePosition(from, to);
        validateTeam(team, from);
        validatePieceCanMove(from, to);
    }

    private void validateTeam(Team team, Position from) {
        final Piece piece = boardPieces.getPiece(from);

        if (!piece.isSameTeam(team)) {
            throw new PieceCanNotMoveException("자신의 기물만 이동할 수 있습니다.");
        }
    }

    private void validatePosition(Position from, Position to) {
        if (isOutOfBounds(from) || isOutOfBounds(to)) {
            throw new PieceCanNotMoveException("보드의 범위를 벗어난 위치입니다.");
        }

        if (isEmptySpace(from)) {
            throw new PieceCanNotMoveException("선택하신 위치에 기물이 없습니다.");
        }
    }

    private void validatePieceCanMove(Position from, Position to) {
        final Piece piece = boardPieces.getPiece(from);
        if (!piece.canMove(from, to, boardPieces.getBoardStateReader())) {
            throw new PieceCanNotMoveException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private boolean isEmptySpace(Position from) {
        return boardPieces.getPiece(from).isPieceType(PieceType.EMPTY);
    }

    private boolean isOutOfBounds(Position position) {
        return boardPieces.isOutOfBounds(position);
    }
}
