package view;

import java.util.List;
import java.util.Optional;
import model.piece.Piece;

import java.util.Scanner;
import model.position.Position;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public Optional<Position> choiceDeparture() {
        System.out.println("이동할 말을 선택해주세요. ex) 1,4 / 결과 계산을 하고 싶다면, 종료를 입력해주세요.");
        String choiceDeparture = scanner.nextLine();
        if (choiceDeparture.equals("종료")) {
            return Optional.empty();
        }
        return Optional.of(createPosition(choiceDeparture));
    }

    public Position choiceArrivalOf(Piece piece) {
        System.out.println(piece.getName() +"를 선택했습니다. 이동할 위치를 선택해주세요.");
        String choiceArrival = scanner.nextLine();
        return createPosition(choiceArrival);
    }

    private Position createPosition(String choiceDeparture) {
        List<Integer> columnAndRowOfDeparture = splitAndConvert(choiceDeparture);
        return new Position(columnAndRowOfDeparture.get(0), columnAndRowOfDeparture.get(1));
    }

    private  List<Integer> splitAndConvert(String userInput) {
        List<String> splitResult = List.of(userInput.split(","));
        return splitResult.stream()
            .map(Integer::parseInt)
            .toList();
    }
}
