package service;

import java.util.ArrayList;
import java.util.List;

import domain.Board;
import domain.JanggiGame;
import domain.PieceType;
import domain.Position;
import java.util.Map;
import java.util.stream.Collectors;
import service.dto.BoardDto;
import service.dto.PieceDto;
import service.dto.PositionDto;

public class JanggiService {

    public Board createBoard(List<PieceType> choMasangChoose, List<PieceType> hanMasangChoose) {
        List<PieceType> masang = new ArrayList<>(choMasangChoose);
        masang.addAll(hanMasangChoose);
        return new Board(masang);
    }

    public JanggiGame createJanggiGame(Board board) {
        return new JanggiGame(board);
    }

    public BoardDto createBoardDto(Board board) {
        Map<PositionDto, PieceDto> pieceDtos = board.getPieces().entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(
                        entry -> new PositionDto(entry.getKey().getX(), entry.getKey().getY()),
                        entry -> new PieceDto(entry.getValue().getCountry().name(),
                                entry.getValue().getPieceType().getName())
                ));
        return new BoardDto(pieceDtos);
    }

    public List<PositionDto> getPiecePositions(JanggiGame janggiGame, PieceType pieceType) {
        List<PositionDto> positionDtos = new ArrayList<>();
        for (Position position : janggiGame.getPiecesNowPosition(pieceType)) {
            positionDtos.add(new PositionDto(position.getX(), position.getY()));
        }
        return positionDtos;
    }

    public void applyMove(Position start, Position end, JanggiGame game) {
        game.play(start, end);
    }

    public List<PieceType> createMasang(int num) {
        return switch (num) {
            case 1 -> List.of(PieceType.MA, PieceType.SANG, PieceType.SANG, PieceType.MA);
            case 2 -> List.of(PieceType.MA, PieceType.SANG, PieceType.MA, PieceType.SANG);
            case 3 -> List.of(PieceType.SANG, PieceType.MA, PieceType.SANG, PieceType.MA);
            case 4 -> List.of(PieceType.SANG, PieceType.MA, PieceType.MA, PieceType.SANG);
            default -> throw new IllegalArgumentException("올바르지 않은 입력입니다.");
        };
    }
}
