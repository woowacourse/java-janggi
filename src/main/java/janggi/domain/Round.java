package janggi.domain;

import janggi.domain.movement.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceName;
import janggi.dto.PieceDto;
import janggi.dto.PositionDto;

import java.util.Map;
import java.util.function.BiConsumer;

public class Round {

    private final static double HANDICAP_POINTS = 1.5;

    private final Board board;
    private Side currentTurn = Side.CHO;

    public Round(Board board) {
        this.board = board;
    }

    public void commence(Position selectedPosition, Position targetPosition, BiConsumer<PositionDto, PieceDto> dataConsumer) {
        board.makeMove(currentTurn, selectedPosition, targetPosition);
        if (board.hasBothGenerals()) {
            changeTurn();
        }
        dataConsumer.accept(selectedPosition.getPositionDto(), getTargetPieceDto(targetPosition));
    }

    private void changeTurn() {
        currentTurn = currentTurn.reverse();
    }

    private PieceDto getTargetPieceDto(Position targetPosition) {
        Piece piece = board.getPiece(targetPosition);
        PositionDto positionDto = targetPosition.getPositionDto();
        return new PieceDto(PieceName.getDatabaseName(piece), piece.getSide().toString(), positionDto.row(), positionDto.column());
    }

    public Map<Position, Piece> getCurrentPieces() {
        return board.getPieces();
    }

    public boolean hasBothGenerals() {
        return board.hasBothGenerals();
    }

    public Side getCurrentTurn() {
        return currentTurn;
    }

    public Map<Side, Double> getCurrentPoints() {
        double choPoints = board.getTotalPoints(Side.CHO);
        double hanPoints = board.getTotalPoints(Side.HAN) + HANDICAP_POINTS;
        return Map.of(Side.CHO, choPoints, Side.HAN, hanPoints);
    }
}
