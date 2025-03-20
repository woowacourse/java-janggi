import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public List<Dot> readPieceMovement(Dynasty dynasty) {
        System.out.printf("""
                %n%s의 차례입니다. 말을 움직여주세요
                ex) 기물의 x좌표, 기물의 y좌표 > 이동할 x좌표, 이동할 y좌표
                """, dynasty.getName());

        String movementInput = scanner.nextLine();

        String[] originAndDestination = movementInput.split(">");
        String originInput = originAndDestination[0];
        String destinationInput = originAndDestination[1];

        String[] originXY = originInput.split(",");
        int originX = Integer.parseInt(originXY[0].trim());
        int originY = Integer.parseInt(originXY[1].trim());

        String[] destinationXY = destinationInput.split(",");
        int destinationX = Integer.parseInt(destinationXY[0].trim());
        int destinationY = Integer.parseInt(destinationXY[1].trim());

        Dot origin = Dot.of(originX, originY);
        Dot destination = Dot.of(destinationX, destinationY);

        return List.of(origin, destination);
    }
}

//(0, 3), (0, 4)