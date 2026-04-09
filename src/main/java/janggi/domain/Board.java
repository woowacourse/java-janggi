package janggi.domain;

import janggi.domain.piece.EmptyPosition;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements BoardView {
    private final List<List<Piece>> board;

    public Board() {
        board = BoardInitializer.createBoard();
    }

    private Board(List<List<Piece>> board) {
        this.board = board;
    }

    public static Board empty() {
        return new Board(BoardInitializer.createEmptyBoard());
    }

    public static Board of(Map<Position, Piece> pieces) {
        Board board = Board.empty();
        for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
            board.place(entry.getKey(), entry.getValue());
        }
        return board;
    }


    @Override
    public Piece findByPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return board.get(row).get(col);
    }

    @Override
    public boolean isEmptyPosition(Position position) {
        return findByPosition(position).isEmpty();
    }

    @Override
    public boolean isInsidePalace(Position position) {
        return PalaceRule.isInsidePalace(position);
    }

    @Override
    public boolean canMoveDiagonallyInPalace(Position from, Position to) {
        return PalaceRule.canMoveDiagonally(from, to);
    }

    @Override
    public boolean isDiagonalInPalace(Position from, Position to) {
        return PalaceRule.isDiagonalInPalace(from, to);
    }

    @Override
    public Position getDiagonalMidpointInPalace(Position from, Position to) {
        return PalaceRule.getDiagonalMidpoint(from, to);
    }

    public Piece move(Position from, Position to, Team currentTeam) {
        Piece fromPiece = findByPosition(from);
        Piece toPiece = findByPosition(to);

        validateCommonMove(currentTeam, fromPiece, toPiece);

        if (!fromPiece.canMove(from, to, this)) {
            throw new IllegalArgumentException("해당 기물의 이동 규칙에 맞지 않습니다.");
        }

        place(from, new EmptyPosition(Team.OTHER));
        place(to, fromPiece);

        return toPiece;
    }

    private void place(Position position, Piece piece) {
        List<Piece> row = board.get(position.getRow());
        row.set(position.getCol(), piece);
    }

    private void validateCommonMove(Team currentTeam, Piece fromPiece, Piece toPiece) {
        if (!fromPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신 진영의 기물을 선택해야합니다.");
        }

        if (fromPiece.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택하신 칸에 기물이 없습니다.");
        }

        if (toPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("이미 도착지점에 플레이어님의 진영 기물이 있습니다.");
        }
    }

    public int calculateScore(Team team) {
        int totalScore = 0;
        for (List<Piece> row : board) {
            for (Piece piece : row) {
                if (piece.isSameTeam(team)) {
                    totalScore += piece.score();
                }
            }
        }
        return totalScore;
    }


    // 빈곳은 db에 따로 저장안하려는데....
    // board 자료구조 자체가 map이었으면.....
    public Map<Position, Piece> findAllPieces() {
        Map<Position, Piece> pieces = new HashMap<>();
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                Position position = new Position(row, col);
                Piece piece = findByPosition(position);
                if (!piece.isEmpty()) {
                    pieces.put(position, piece);
                }
            }
        }
        return pieces;
    }


}
