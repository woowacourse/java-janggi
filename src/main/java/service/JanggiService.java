package service;

import java.util.ArrayList;
import java.util.List;

import domain.Board;
import domain.JanggiGame;
import domain.PieceType;
import domain.Position;
import service.dto.BoardDto;
import service.dto.PositionDto;

public class JanggiService {

    public Board createBoard(int choMasangChoice, int hanMasangChoice){
        List<PieceType> masang = new ArrayList<>(createMasang(choMasangChoice));
        masang.addAll(createMasang(hanMasangChoice));
        return new Board(masang);
    }

    public JanggiGame createJanggiGame(Board board){
        return new JanggiGame(board);
    }


    public BoardDto getBoard(Board board) {
        List<BoardDto.Row> boardAll = new ArrayList<>();
        for (int x = 1; x<= Position.MAX_ROW; x++){
            List<String> values= new ArrayList<>();
            for (int y = 1; y <= Position.MAX_COL; y++) {
                PieceType pieceType = board.getPiece(Position.create(x, y));
                values.add(pieceType.getName());
            }
            boardAll.add(new BoardDto.Row(values));
        }
        return new BoardDto(boardAll);
    }

    public List<PositionDto> getPiecePositions(JanggiGame janggiGame, PieceType pieceType){
        List<PositionDto> positionDtos = new ArrayList<>();
        for (Position position : janggiGame.getPiecesNowPosition(pieceType)){
            positionDtos.add(new PositionDto(position.getX(), position.getY()));
        };
        return positionDtos;
    }

    private List<PieceType> createMasang(int num) {
        return switch (num) {
            case 1 -> List.of(PieceType.MA,PieceType.SANG,PieceType.SANG,PieceType.MA);
            case 2 -> List.of(PieceType.MA,PieceType.SANG,PieceType.MA,PieceType.SANG);
            case 3 -> List.of(PieceType.SANG,PieceType.MA,PieceType.SANG,PieceType.MA);
            case 4 -> List.of(PieceType.SANG,PieceType.MA,PieceType.MA,PieceType.SANG);
            default -> throw new IllegalArgumentException("올바르지 않은 입력입니다.");
        };
    }

}
