import java.util.Optional;
import java.util.Scanner;
import model.Cannon;
import model.Piece;
import model.Pieces;
import model.Position;

public class Application {

    public static void main(String[] args) {
        Pieces pieces = Pieces.createAndInit();
        Scanner sc = new Scanner(System.in);
        System.out.println("장기 시작");
        while (true) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    Optional<Piece> pieceOfView = pieces.findPieceOfView(new Position(i, j));
                    if (pieceOfView.isPresent()) {
                        System.out.print(pieceOfView.get());
                    } else {
                        System.out.print("－");
                    }
                }
                System.out.println();
            }

            System.out.println("이동할 말을 선택해주세요. ex) 1,4");
            String choiceDirection = sc.nextLine();

            String[] split = choiceDirection.split(",");
            Piece piece = pieces.findPiece(
                new Position(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            System.out.println(piece + "를 선택했습니다. 이동할 위치를 선택해주세요.");
            String moveDirection = sc.nextLine();

            String[] split1 = moveDirection.split(",");
            Position destinationDirection = new Position(Integer.parseInt(split1[0]),
                Integer.parseInt(split1[1]));
            if (piece instanceof Cannon) {
                pieces.validateCannonMove(piece, destinationDirection);
            } else {
                pieces.validateCanMove(piece, destinationDirection);
            }
        }
    }

}
