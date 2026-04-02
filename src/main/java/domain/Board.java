package domain;

import domain.pieces.Piece;
import dto.BoardStatusDto;
import dto.PositionStatusDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board implements BoardReader {

    private final Map<Position, Piece> board = new HashMap<>();

    public void generatePiecesBy(Camp camp, ElephantFormation elephantFormation) {
        Map<Position, Piece> pieces = PieceGenerator.generatePieces(camp, elephantFormation);

        board.putAll(pieces);
    }

    public void locatePiece(Position position, Piece piece) {
        if (!isExist(position)) {
            board.put(position, piece);
            return;
        }

        if (getPieceFrom(position).isSameCamp(piece)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀은 잡을 수 없습니다!");
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

    public void move(Position fromPosition, Position toPosition) {
        if (fromPosition.equals(toPosition)) {
            throw new IllegalArgumentException("[ERROR] 제자리 이동은 불가능합니다.");
        }

        Piece piece = board.get(fromPosition);
        boolean canMove = piece.canMove(fromPosition, toPosition, this);
        if (canMove) {
            locatePiece(toPosition, piece);
            board.remove(fromPosition);
            return;
        }

        throw new IllegalArgumentException("[ERROR] 이동할 수 없습니다");
    }

    @Override
    public boolean isDifferentPieceType(Position position, Piece piece) {
        if (!board.containsKey(position)) {
            return true;
        }

        return board.get(position).isDifferentPieceType(piece);
    }

    public BoardStatusDto getBoardStatus() {
        List<List<PositionStatusDto>> boardStatusDto = new ArrayList<>();
        for (int y = Position.MIN_Y_VALUE; y <= Position.MAX_Y_VALUE; y++) {
            List<PositionStatusDto> xPositionStatus = new ArrayList<>();
            for (int x = Position.MIN_X_VALUE; x <= Position.MAX_X_VALUE; x++) {
                Position position = new Position(x, y);

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
}
