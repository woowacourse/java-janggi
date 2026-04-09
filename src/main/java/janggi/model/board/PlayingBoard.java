package janggi.model.board;

import janggi.model.Team;
import janggi.model.piece.Piece;
import janggi.model.piece.PieceType;
import janggi.model.position.absolute.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayingBoard implements Board {

    private final Map<Position, Piece> boardInfo;

    private PlayingBoard(Map<Position, Piece> boardInfo) {
        this.boardInfo = boardInfo;
    }

    public static PlayingBoard of(Map<Position, Piece> board) {
        return new PlayingBoard(Map.copyOf(board));
    }

    @Override
    public Board move(
            Team team,
            Position from,
            Position to
    ) {
        Piece pieceAtFrom = getPieceAt(from);

        if (!pieceAtFrom.isSameTeam(team)) {
            throw new IllegalArgumentException("상대편 기물을 움직일 수 없습니다.");
        }

        List<Piece> piecesOnPath = pieceAtFrom
                .getLegalPath(from, to)
                .findPiecesOn(boardInfo);

        Optional<Piece> target = findPieceAt(to);
        validateMovable(
                pieceAtFrom,
                target,
                piecesOnPath
        );

        if (target.filter(this::isJang).isPresent()) {
            return new CompletedBoard(team);
        }

        Map<Position, Piece> movedBoard = createMovedBoard(
                from,
                to,
                pieceAtFrom
        );

        if (isTeamEliminated(movedBoard, Team.CHO)) {
            return new CompletedBoard(Team.HAN);
        }

        if (isTeamEliminated(movedBoard, Team.HAN)) {
            return new CompletedBoard(Team.CHO);
        }

        return new PlayingBoard(movedBoard);
    }

    private Piece getPieceAt(Position position) {
        return findPieceAt(position)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));
    }

    private Optional<Piece> findPieceAt(Position position) {
        return Optional.ofNullable(boardInfo.get(position));
    }

    private void validateMovable(
            Piece pieceAtFrom,
            Optional<Piece> target,
            List<Piece> piecesOnPath
    ) {
        boolean canMove = target
                .map(piece -> pieceAtFrom.canPassThrough(piecesOnPath, piece))
                .orElseGet(() -> pieceAtFrom.canPassThrough(piecesOnPath));

        if (!canMove) {
            throw new IllegalArgumentException("해당 경로로 기물을 움직일 수 없습니다.");
        }
    }

    private boolean isJang(Piece piece) {
        return piece.getPieceType() == PieceType.JANG;
    }

    private Map<Position, Piece> createMovedBoard(
            Position from,
            Position to,
            Piece pieceAtFrom
    ) {
        Map<Position, Piece> movedBoard = new HashMap<>(boardInfo);
        movedBoard.put(to, pieceAtFrom);
        movedBoard.remove(from);
        return movedBoard;
    }

    private boolean isTeamEliminated(
            Map<Position, Piece> board,
            Team team
    ) {
        return board.values().stream()
                .noneMatch(piece -> piece.isSameTeam(team));
    }

    @Override
    public boolean isWinnerDetermined() {
        return false;
    }

    @Override
    public Map<Position, Piece> getBoardInfo() {
        return Map.copyOf(boardInfo);
    }

    @Override
    public Team winner() {
        throw new IllegalArgumentException("아직 게임이 종료되지 않았습니다.");
    }

    @Override
    public int getMaterialScoreOf(Team team) {
        return boardInfo.values().stream()
                .filter(value -> value.isSameTeam(team))
                .mapToInt(value -> value.getPieceType().getScore())
                .sum();
    }
}
