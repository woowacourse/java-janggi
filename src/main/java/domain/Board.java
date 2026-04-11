package domain;

import domain.pieces.Piece;
import dto.BoardStatusDto;
import dto.PositionStatusDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Board implements BoardChecker {

    private final Map<Position, Piece> board = new HashMap<>();

    public void generatePiecesBy(Camp camp, int elephantFormation) {
        PieceGenerator pieceGenerator = new PieceGenerator();
        Map<Position, Piece> pieces = pieceGenerator.generateInitialPieces(camp, elephantFormation);

        board.putAll(pieces);
    }

    public void locatePiece(Position position, Piece piece) {
        if (isExist(position) && getPieceFrom(position).isSameCamp(piece)) {
            throw new InvalidMoveException("[ERROR] 같은 팀은 잡을 수 없습니다!");
        }

        board.put(position, piece);
    }

    public Piece getPieceFrom(Position position) {
        return board.get(position);
    }

    @Override
    public boolean isExist(Position position) {
        return board.containsKey(position);
    }

    @Override
    public boolean isNotCannon(Position position) {
        return board.get(position).getPieceType() != PieceType.CANNON;
    }

    public void move(Position fromPosition, Position toPosition) {
        if (fromPosition.equals(toPosition)) {
            throw new InvalidMoveException("[ERROR] 제자리 이동은 불가능합니다.");
        }

        Piece piece = board.get(fromPosition);
        boolean canMove = piece.canMove(fromPosition, toPosition, this);
        if (canMove) {
            locatePiece(toPosition, piece);
            board.remove(fromPosition);
            return;
        }
        throw new InvalidMoveException("[ERROR] 이동할 수 없습니다");
    }

    public BoardStatusDto getBoardStatus() {
        List<List<PositionStatusDto>> boardStatusDto = new ArrayList<>();
        for (int row = Position.MIN_ROW_VALUE; row <= Position.MAX_ROW_VALUE; row++) {
            List<PositionStatusDto> xPositionStatus = new ArrayList<>();
            for (int col = Position.MIN_COL_VALUE; col <= Position.MAX_COL_VALUE; col++) {
                Position position = new Position(col, row);

                PositionStatusDto positionStatusDto = getPositionStatusDto(position);
                xPositionStatus.add(positionStatusDto);
            }
            boardStatusDto.add(xPositionStatus);
        }
        return new BoardStatusDto(boardStatusDto);
    }

    private PositionStatusDto getPositionStatusDto(Position position) {
        if (!board.containsKey(position)) {
            return new PositionStatusDto(position, PieceType.NONE, Camp.NONE);
        }

        Piece piece = board.get(position);
        PieceType pieceType = piece.getPieceType();
        Camp camp = piece.getCamp();
        return new PositionStatusDto(position, pieceType, camp);
    }

    public boolean isPieceOfCamp(Position position, Camp camp) {
        if (!board.containsKey(position)) {
            return false;
        }

        return board.get(position).isSameCamp(camp);
    }

    public boolean isGameOver() {
        return leftOneGeneral();
    }

    private boolean leftOneGeneral() {
        return board.values().stream().filter(piece -> piece.getPieceType() == PieceType.GENERAL)
                .count() == 1;
    }

    public double calculateScoreByCamp(Camp camp) {
        List<Piece> pieces = getPiecesByCamp(camp);
        return pieces.stream()
                .mapToInt(p -> p.getPieceType().getScore())
                .sum() + getBonusScore(camp);
    }

    private double getBonusScore(Camp camp) {
        if (camp == Camp.HAN) {
            return 1.5;
        }
        return 0;
    }

    public List<Piece> getPiecesByCamp(Camp camp) {
        return board.values().stream().filter(p -> p.isSameCamp(camp)).collect(Collectors.toList());
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
