package view;

import static domain.board.PlacementSelection.EHEH;
import static domain.board.PlacementSelection.EHHE;
import static domain.board.PlacementSelection.HEEH;
import static domain.board.PlacementSelection.HEHE;

import domain.board.BoardLocation;
import domain.board.PlacementSelection;
import domain.piece.Piece;
import java.util.Map;
import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, PlacementSelection> PLACEMENT_SELECTION_SET_UP = Map.of(
            1, HEHE,
            2, EHEH,
            3, HEEH,
            4, EHHE
    );

    public String requestStartLocation() {
        System.out.println("움직일 기물의 좌표를 입력해주세요. ex) 1,2");
        return scanner.nextLine();
    }

    public String requestDestination() {
        System.out.println("목표 좌표를 입력해주세요. ex) 1,2");
        return scanner.nextLine();
    }

    public Map<BoardLocation, Piece> requestHanPlacements() {
        System.out.println("한나라는 아래 중 하나를 선택해주세요.");
        System.out.print("""
                1. 馬象馬象(마상마상)
                2. 象馬象馬(상마상마)
                3. 馬象象馬(마상상마)
                4. 象馬馬象(상마마상)
                """);
        int selectNumber = Integer.parseInt(scanner.nextLine());
        if (!PLACEMENT_SELECTION_SET_UP.containsKey(selectNumber)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 번호입니다.");
        }
        return PLACEMENT_SELECTION_SET_UP.get(selectNumber).getHan();
    }

    public Map<BoardLocation, Piece> requestChoPlacements() {
        System.out.println("초나라는 아래 중 하나를 선택해주세요");
        System.out.print("""
                1. 馬象馬象(마상마상)
                2. 象馬象馬(상마상마)
                3. 馬象象馬(마상상마)
                4. 象馬馬象(상마마상)
                """);
        int selectNumber = Integer.parseInt(scanner.nextLine());
        if (!PLACEMENT_SELECTION_SET_UP.containsKey(selectNumber)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 번호입니다.");
        }
        return PLACEMENT_SELECTION_SET_UP.get(selectNumber).getCho();
    }
}
