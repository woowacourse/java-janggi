public class Application {
    public static void main(String[] args) {
        try {
            Janggi janggi = new Janggi();
            janggi.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
