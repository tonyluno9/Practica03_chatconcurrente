package Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Cliente de chat concurrente.
 * Permite conectarse al servidor, enviar y recibir mensajes.
 */
public class Client implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username = "Desconocido";
    private boolean connected = false;

    // ✅ Constructor corregido
    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            connected = true;
            System.out.println("✅ Conectado al servidor en " + ip + ":" + port);
        } catch (IOException e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        if (!connected) {
            System.out.println("No se pudo establecer la conexión.");
            return;
        }

        // 🧵 Hilo para leer mensajes entrantes
        Thread readerThread = new Thread(() -> {
            try {
                String serverMsg;
                while ((serverMsg = in.readLine()) != null) {
                    System.out.println(serverMsg);
                }
            } catch (IOException e) {
                System.out.println("📴 Desconectado del servidor.");
            }
        });
        readerThread.start();

        // 💬 Hilo principal para enviar mensajes
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Introduce tu nombre de usuario: ");
            username = scanner.nextLine();
            send(username);

            System.out.println("\n💬 Comandos disponibles:");
            System.out.println("   /name nuevoNombre  → Cambia tu nombre");
            System.out.println("   /msg usuario msg   → Envía mensaje privado");
            System.out.println("   /global msg        → Envía mensaje global");
            System.out.println("   /users             → Solicita lista de usuarios");
            System.out.println("   /exit              → Salir del chat\n");

            String message;
            do {
                message = scanner.nextLine();
                send(message);
            } while (!message.equalsIgnoreCase("/exit"));

        } catch (Exception e) {
            System.out.println("❌ Error en el cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar conexión.");
            }
        }
    }

    /** ✉️ Envía mensaje al servidor */
    private void send(String msg) {
        out.println(msg);
        out.flush();
    }
}
