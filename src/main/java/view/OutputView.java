package view;

import domain.Country;
import domain.PieceType;
import java.util.List;
import java.util.Map;
import service.dto.BoardDto;
import service.dto.PieceDto;
import service.dto.PositionDto;

public class OutputView {

    private final OutputViewFormatter formatter;

    public OutputView(OutputViewFormatter formatter) {
        this.formatter = formatter;
    }

    public void printGameStartMessage() {
        System.out.println("우테코 장기 게임입니다.");
    }

    public void printErrorMessage(String message) {
        System.out.println(formatter.formatErrorMessage(message));
    }

    public void printCountry(Country country) {
        System.out.println(formatter.formatCountry(country.getName()));
    }

    public void printTurnStartMessage() {
        System.out.println("\n초나라가 먼저 시작합니다.");
    }

    public void printChangeTurnMessage(String countryName) {
        System.out.println(formatter.formatChangeTurn(countryName));
    }

    public void printBoard(BoardDto boardDto) {
        StringBuilder boardResult = new StringBuilder();
        Map<PositionDto, PieceDto> pieces = boardDto.pieces();
        boardResult.append(formatter.formatColNumbers());
        boardResult.append(formatter.formatHorizontalLine());

        for (int x = 1; x <= 10; x++) {
            boardResult.append(formatter.formatRowNumber(x));

            for (int y = 1; y <= 9; y++) {
                PositionDto nowPosition = new PositionDto(x, y);
                if (!pieces.containsKey(nowPosition)) {
                    boardResult.append(formatter.formatEmptyPiece());
                    continue;
                }

                PieceDto pieceDto = pieces.get(nowPosition);
                boardResult.append(
                        formatter.formatPiece(PieceColor.getColorCode(pieceDto.countryName()), pieceDto.pieceName()));
            }

            boardResult.append(formatter.formatRightVerticalLine());
        }

        boardResult.append(formatter.formatHorizontalLine());
        System.out.print(boardResult);
    }

    public void printPiecePossiblePosition(PieceType pieceType, List<PositionDto> positionDtos) {
        StringBuilder result = new StringBuilder(formatter.formatPossiblePositionHeader(pieceType.getName()));
        int num = 1;
        for (PositionDto dto : positionDtos) {
            result.append(formatter.formatPossiblePosition(num++, dto.x(), dto.y()));
        }

        System.out.print(result);
    }
}
