package server;

import service.ServiceMessages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean authenticated;
    private String nickname;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            try {
                socket.setSoTimeout(120000);
                //цикл аутентификации
                while (true) {
                    String str = in.readUTF();

                    if (str.equals(ServiceMessages.END)) {
                        sendMsg(ServiceMessages.END);
                        break;
                    }

                    if (str.startsWith(ServiceMessages.AUTH)) {
                        String[] token = str.split(" ", 3);
                        if (token.length < 3) {
                            continue;
                        }
                        String newNick = server.getAuthService()
                                .getNicknameByLoginAndPassword(token[1], token[2]);
                        login = token[1];
                        if (newNick != null) {
                            if (!server.isLoginAuthenticated(login)) {
                                authenticated = true;
                                nickname = newNick;
                                sendMsg(ServiceMessages.AUTH_OK + " " + nickname + " " + login);
                                server.subscribe(this);
                                System.out.println("Client: " + nickname + " authenticated");
                                break;
                            } else {
                                sendMsg("С этим логином уже зашли в чат");
                            }
                        } else {
                            sendMsg("Неверный логин / пароль");
                        }
                    }
                    if (str.startsWith(ServiceMessages.REG)) {
                        String[] token = str.split(" ", 4);
                        if (token.length < 4) {
                            continue;
                        }
                        if (server.getAuthService()
                                .registration(token[1], token[2], token[3])) {
                            sendMsg(ServiceMessages.REG_OK);
                        } else {
                            sendMsg(ServiceMessages.REG_NO);
                        }
                    }
                }
                //цикл работы
                socket.setSoTimeout(0);
                while (authenticated) {
                    String str = in.readUTF();

                    if (str.startsWith(ServiceMessages.SERVICE_MSG)) {
                        if (str.equals(ServiceMessages.END)) {
                            sendMsg(ServiceMessages.END);
                            break;
                        }
                        if (str.startsWith(ServiceMessages.PRIVAT_MSG)) {
                            String[] token = str.split(" ", 3);
                            if (token.length < 3) {
                                continue;
                            }
                            server.privateMsg(this, token[1], token[2]);
                        }
                        if (str.startsWith(ServiceMessages.CH_NICK)) {
                            String[] token = str.split(" ", 2);
                            if (token.length < 2) {
                                continue;
                            }
                            if (token[1].contains(" ")) {
                                sendMsg("Ник не должен содержать пробелов");
                                continue;
                            }
                            if (server.getAuthService().ChangeNick(this.nickname, token[1])) {
                                sendMsg(ServiceMessages.NEW_NICK + token[1]);
                                sendMsg("Ваш ник изменен на " + token[1]);
                                this.nickname = token[1];
                                server.broadcastClientList();
                            } else {
                                sendMsg(token[1] + "Такой ник уже существует");
                            }
                        }

                    } else {
                        server.broadcastMsg(this, str);
                    }
                }

            } catch (SocketTimeoutException e) {
                sendMsg(ServiceMessages.END);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (getNickname() == null) {
                    System.out.println("Client unauthorized disconnect!");
                } else {
                    System.out.println(String.format("Client %s disconnect!", getNickname()));
                }
                server.unsubscribe(this);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }
}
