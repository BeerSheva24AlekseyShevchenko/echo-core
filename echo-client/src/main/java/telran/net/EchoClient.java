package telran.net;

import java.net.*;
import java.io.*;

public class EchoClient {
    private Socket socket;
    private PrintStream writer;
    private BufferedReader reader;

    public EchoClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintStream(socket.getOutputStream());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String sendAndReceive(String str) {
        try {
            writer.println(str);
            return reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
