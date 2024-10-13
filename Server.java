import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            // Criar um ServerSocket na porta 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando por conexões na porta 12345...");

            // Aguardar a conexão de um cliente
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket);

            // Criar threads para lidar com entrada e saída de mensagens
            Thread inputThread = new Thread(new InputHandler(clientSocket));
            Thread outputThread = new Thread(new OutputHandler(clientSocket));

            // Iniciar as threads
            inputThread.start();
            outputThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class InputHandler implements Runnable {
    private Socket clientSocket;

    public InputHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Cliente: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class OutputHandler implements Runnable {
    private Socket clientSocket;

    public OutputHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = consoleReader.readLine()) != null) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}