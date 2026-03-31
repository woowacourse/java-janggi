package view;

import java.util.List;

import domain.enums.Country;
import domain.enums.PieceType;
import domain.Position;
import service.dto.BoardDto;
import service.dto.ColorDto;
import service.dto.PositionDto;

public class OutputView {
    public static final String PREFEIX_ERROR_MESSAGE = "[ERROR] ";
    public static final String DEFAULT_COLOR = Country.NONE.getColor();


    private final OutputViewFormatter formatter;

    public OutputView(OutputViewFormatter formatter) {
        this.formatter = formatter;
    }

    public void printGameStartMessage() {
        System.out.println("우테코 장기 게임입니다.");
    }

    public void printErrorMessage(String message) {
        System.out.println(PREFEIX_ERROR_MESSAGE+message);
    }

    public void printCountry(Country country) {
        System.out.println(formatter.formatCountry(country.getName()));
    }

    public void printTurnStartMessage() {
        System.out.println("\n초나라가 먼저 시작합니다.");
    }

    public void printChangeTurnMessage(String countryName) {
        System.out.printf("\n\n---------------------------\n");
        System.out.printf("\n차례 : %s\n", countryName);
    }

    public void printBoard(BoardDto boardDto, ColorDto colorDto) {
        System.out.println();
        for (int y = 0; y< Position.MAX_ROW; y++){
            BoardDto.Row boardRow = boardDto.rows().get(y);
            ColorDto.Row colorRow = colorDto.rows().get(y);

            for (int x=0;x<Position.MAX_COL;x++){
                String color = colorRow.colors().get(x);
                System.out.printf(color+"%-3s"+DEFAULT_COLOR, boardRow.pieces().get(x));
            }
            System.out.println();
        }
    }

    public void printPiecePossiblePosition(PieceType pieceType,List<PositionDto> positionDtos) {
        System.out.printf("\n['%s' 기물의 현재 좌표 목록]\n",pieceType.getName());
        System.out.printf("%d. 홈으로 돌아가기\n",InputView.CHOICE_QUIT_NUMBER);
        int num = 1;
        for (PositionDto dto : positionDtos) {
            System.out.printf("%d. [%d,%d]\n", num++, dto.x(), dto.y());
        }
    }

}
