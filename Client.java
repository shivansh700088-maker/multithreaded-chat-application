import java.io.*;
import java.net.*;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST, PORT)) {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter writer = new PrintWriter(
                    socket.getOutputStream(), true);

            BufferedReader console = new BufferedReader(
                    new InputStreamReader(System.in));

            new Thread(() -> {
                String msg;
                try {
                    while ((msg = reader.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException ignored) {}
            }).start();

            String input;
            while ((input = console.readLine()) != null) {
                writer.println(input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
