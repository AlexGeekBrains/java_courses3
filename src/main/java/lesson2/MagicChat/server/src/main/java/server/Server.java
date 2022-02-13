package server;


import service.ServiceMessages;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private ServerSocket server;
    private Socket socket;
    private final int PORT = 8189;
    private List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        LogManager manager = LogManager.getLogManager();
        try {
            manager.readConfiguration(new FileInputStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        clients = new CopyOnWriteArrayList<>();
        authService = new DbAuthService();
        ExecutorService service = Executors.newCachedThreadPool();

        try {
            DataSource.connect();
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.severe("Неудалось подключиться к БД");
            throw new RuntimeException("Неудалось подключиться к БД");
        }

        try {
            server = new ServerSocket(PORT);
            LOGGER.info("Server started!");

            while (true) {
                socket = server.accept();
                LOGGER.info("Client connected: " + socket.getRemoteSocketAddress());
                service.execute(() -> {
                    new ClientHandler(this, socket);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
            DataSource.disconnect();
            LOGGER.info("Server stop");
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        String message = String.format("[ %s ]: %s", sender.getNickname(), msg);
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[ %s ] to [ %s ]: %s", sender.getNickname(), receiver, msg);
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(receiver)) {
                c.sendMsg(message);
                if (!sender.getNickname().equals(receiver)) {
                    sender.sendMsg(message);
                }
                return;
            }
        }
        sender.sendMsg("not found user: " + receiver);
    }

    public boolean isLoginAuthenticated(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder(ServiceMessages.CLIENT_LIST);

        for (ClientHandler c : clients) {
            sb.append(" ").append(c.getNickname());
        }

        String message = sb.toString();

        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }


    public AuthService getAuthService() {
        return authService;
    }


}
