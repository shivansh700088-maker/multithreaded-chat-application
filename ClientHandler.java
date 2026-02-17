import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            writer = new PrintWriter(
                    socket.getOutputStream(), true);

            writer.println("Enter your username:");
            username = reader.readLine();

            Server.broadcast(username + " joined the chat!", this);

        } catch (IOException e) {
            closeConnection();
        }
    }

    @Override
    public void run() {
        String message;

        try {
            while ((message = reader.readLine()) != null) {
                Server.broadcast(username + ": " + message, this);
            }
        } catch (IOException e) {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    private void closeConnection() {
        try {
            Server.removeClient(this);
            Server.broadcast(username + " left the chat!", this);

            if (socket != null) socket.close();
        } catch (IOException ignored) {}
    }
}
