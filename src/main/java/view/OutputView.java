package view;

import domain.Country;
import domain.PieceType;
import java.util.List;
import java.util.Map;
import service.dto.BoardDto;
import service.dto.PieceDto;
import service.dto.PositionDto;

public class OutputView {
    public static final String PREFIX_ERROR_MESSAGE = "[ERROR]";
    public static final String EMPTY_PIECE = "＋";

    private final OutputViewFormatter formatter;

    public OutputView(OutputViewFormatter formatter) {
        this.formatter = formatter;
    }

    public void printGameStartMessage() {
        System.out.println("우테코 장기 게임입니다.");
    }

    public void printErrorMessage(String message) {
        System.out.println(PREFIX_ERROR_MESSAGE + message);
    }

    public void printCountry(Country country) {
        System.out.println(formatter.formatCountry(country.getName()));
    }

    public void printTurnStartMessage() {
        System.out.println("\n초나라가 먼저 시작합니다.");
    }

    public void printChangeTurnMessage(String countryName) {
        System.out.printf("\n차례 : %s\n", countryName);
    }

    public void printBoard(BoardDto boardDto) {
        StringBuilder boardResult = new StringBuilder();
        Map<PositionDto, PieceDto> pieces = boardDto.pieces();

        for (int x = 1; x <= 10; x++) {
            for (int y = 1; y <= 9; y++) {
                PositionDto nowPosition = new PositionDto(x, y);
                if (!pieces.containsKey(nowPosition)) {
                    boardResult.append(String.format("%-3s", EMPTY_PIECE));
                    continue;
                }

                PieceDto pieceDto = pieces.get(nowPosition);
                boardResult.append(
                        String.format("%s%-3s%s", PieceColor.getColorCode(pieceDto.countryName()),
                                pieceDto.pieceName()
                                , PieceColor.getColorCode(PieceColor.NONE.name())));
            }
            boardResult.append('\n');
        }

        System.out.print(boardResult);
    }

    public void printPiecePossiblePosition(PieceType pieceType,List<PositionDto> positionDtos) {
        System.out.printf("\n['%s' 기물의 현재 좌표 목록]\n",pieceType.getName());
        int num = 1;
        for (PositionDto dto : positionDtos) {
            System.out.printf("%d. [%d,%d]\n", num++, dto.x(), dto.y());
        }
    }

}
