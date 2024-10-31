package telran.net;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandardIO;

public class Main {
    private static EchoClient echoClient;
    public static void main(String[] args) {
        Item[] items = {
            Item.of("Start session", Main::startSession),
            Item.of("Exit", Main::stopSession, true),
        };
        Menu menu = new Menu("Echo Application", items);
        menu.perform(new StandardIO());

        echoClient = new EchoClient(null, 0);
        
    }

    private static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port:", "Wrong port", 3000, 50000).intValue();

        if (echoClient != null) {
            echoClient.close();
        }

        echoClient = new EchoClient(host, port);
        Menu menu = new Menu(
            "Run session",
            Item.of("Enter string", Main::stringProcessing),
            Item.ofExit()
        );
        menu.perform(io);
    }

    private static void stringProcessing(InputOutput io) {
        String str = io.readString("Enter any string");
        String response = echoClient.sendAndReceive(str);
        io.writeLine(response);
    }

    private static void stopSession(InputOutput io) {
        if (echoClient != null) {
            echoClient.close();
        }
    }
}