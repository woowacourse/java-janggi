package domain;

import domain.piece.King;
import domain.piece.Piece;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import strategy.InitializeStrategy;

public class Board {
    private static final int MAX_ROW = 10;
    private static final int MIN_ROW = 1;
    private static final int MAX_COLUMN = 9;
    private static final int MIN_COLUMN = 1;
    private static final double SECOND_PLAYER_HANDICAP = 1.5;

    private final Map<Position, Piece> pieces = new HashMap<>();
    private boolean isGameOver = false;

    public Board(InitializeStrategy choInitializeStrategy, InitializeStrategy hanInitializeStrategy) {
        initTeamBoard(choInitializeStrategy, Team.CHO);
        initTeamBoard(hanInitializeStrategy, Team.HAN);
    }

    /**
     * 커스텀용 보드를 만들기 위한 생성자
     *
     * @param pieces 보드의 넣을 기물의 정보
     */
    public Board(Map<Position, Piece> pieces) {
        this.pieces.putAll(pieces);
    }

    public Board() {
    }

    // db에서 기존 게임 가져올 시 사용
    public Board(Map<Position, Piece> pieces, boolean isGameOver) {
        this.pieces.putAll(pieces);
        this.isGameOver = isGameOver;
    }

    public void move(Position from, Position to, PieceType pieceType, Team team) {
        Piece piece = validateMovablePiece(from, to, pieceType, team);
        validateCanMove(from, to, piece);
        movePiece(from, to, piece);
    }

    public double calculateScore(Team team) {
        double score = this.pieces.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(Piece::getPieceScore)
                .sum();

        if (team == Team.HAN) {
            score += SECOND_PLAYER_HANDICAP;
        }
        return score;
    }

    public boolean canNextTurn() {
        return !isGameOver;
    }

    public Map<Position, Piece> getPieces() {
        return new HashMap<>(pieces);
    }

    /**
     * 플레이어가 입력한 기물의 이동 가능 여부를 판단한다
     *
     * 판단 요소 :
     * from 의 위치에 기물이 있는지
     * from 의 위치의 해당 기물이 있는지
     * from 위치의 기물이 같은 팀 기물인지
     * to 의 범위가 보드의 범위를 넘어가지 않는지
     *
     * @param from 이동하길 원하는 기물의 위치
     * @param to 이동하고 싶은 목적지
     * @param pieceType 이동하기 원하는 기물의 타입
     * @return 이동하는 해당 기물
     * @throws IllegalArgumentException 위 판단 요소를 하나라도 통과하지 못하면
     *  예외 발생
     */
    private Piece validateMovablePiece(Position from, Position to, PieceType pieceType, Team team) {
        Piece piece = pieces.get(from);

        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에 피스가 없습니다.");
        }

        if (!piece.isSameType(pieceType)) {
            throw new IllegalArgumentException("해당 위치에 해당 타입이 없습니다.");
        }

        if (!piece.isSameTeam(team)) {
            throw new IllegalArgumentException("같은 팀 기물이 아닙니다.");
        }

        if (!to.isPossiblePosition(MAX_ROW, MIN_ROW, MAX_COLUMN, MIN_COLUMN)) {
            throw new IllegalArgumentException("기물의 도착지점이 판 범위를 넘어섰습니다.");
        }

        return piece;
    }

    /**
     * 기물의 이동 가능 여부를 판단한다
     *
     * 판단 요소 :
     * from 에서 to로 기물이 이동 규칙을 준수하여 이동할 수 있는지
     *
     * @param from 이동하길 원하는 기물의 위치
     * @param to 이동하고 싶은 목적지
     * @param piece 이동하는 기물
     * @throws IllegalArgumentException 기물의 이동 규칙을 준수하지 못하면 예외 발생
     */
    private void validateCanMove(Position from, Position to, Piece piece) {
        if (!piece.canMove(from, to, this)) {
            throw new IllegalArgumentException("해당 위치로 옮길 수 없습니다.");
        }
    }

    private void movePiece(Position from, Position to, Piece piece) {
        pieces.remove(from);

        // 킹을 잡았을 경우
        if (!isEmpty(to) && isKing(to)) {
            isGameOver = true;
        }

        pieces.put(to, piece);
    }

    public boolean isExistSameType(Position position, Piece piece) {
        if (hasPieceInPosition(position)) {
            return pieces.get(position).isSameType(piece);
        }

        return false;
    }

    public boolean isExistSameType(Position position, PieceType pieceType) {
        if (hasPieceInPosition(position)) {
            return pieces.get(position).isSameType(pieceType);
        }

        return false;
    }

    public boolean isEmpty(Position position) {
        return !pieces.containsKey(position);
    }

    private boolean isKing(Position position) {
        return isExistSameType(position, PieceType.KING);
    }

    public boolean hasSameTeamOn(Position position, Piece piece) {
        if (hasPieceInPosition(position)) {
            return pieces.get(position).isSameTeam(piece);
        }

        return false;
    }

    private boolean hasPieceInPosition(Position position) {
        return pieces.containsKey(position);
    }

    private void initTeamBoard(InitializeStrategy strategy, Team team) {
        pieces.putAll(strategy.initialize(team));
    }

    public Piece findPiece(Position position) {
        return pieces.get(position);
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
