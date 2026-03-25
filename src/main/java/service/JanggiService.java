package service;

import java.util.ArrayList;
import java.util.List;

import domain.Board;
import domain.Piece;
import domain.PieceType;
import domain.Position;
import service.dto.BoardDto;

public class JanggiService {

    public BoardDto getBoard(Board board) {

        List<BoardDto.Row> boardAll = new ArrayList<>();
        for (int x = 1; x< Position.MAX_ROW; x++){
            List<String> values= new ArrayList<>();
            for (int y=1; y<Position.MAX_COL;y++){
                PieceType pieceType = board.getPiece(new Position(x,y));
                values.add(pieceType.name());
            }
            boardAll.add(new BoardDto.Row(values));
        }
        return new BoardDto(boardAll);
    }
}
