

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends ChessBoard {
    
    Client(){}
    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;
    
    public void runClient(String input) throws UnknownHostException, IOException {
        String[] split = input.split(",");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        String sessionID = split[2];

        socket = new Socket(ip, port);
        
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        
        out.writeLong(0x5c34a15e25c9a63dL);
        out.writeInt(3);
        out.writeUTF("chess");
        out.writeInt(1);
        out.writeUTF(sessionID);
        out.write(0);
        
        try {
            String response;
            while (true) {
                response = in.readUTF();
                if(response.equals("start")) {
                    char r = in.readChar();
                    switch (r) {
                        case 'w':
                            Thread t1 = new Thread(new LaunchUI(true, false));
                            t1.start();
                            break;
                        case 'b':
                            Thread t2 = new Thread(new LaunchUI(false, false));
                            t2.start();
                            break;
                        default:
                            Thread t3 = new Thread(new LaunchUI(true, true));
                            t3.start();
                            break;
                    }
                }
                else if(response.equals("board")){
                    response = in.readUTF();
                    LaunchUI.updateBoard(response);
                }
            }
        } catch (IOException ex) {}
        
        in.close();
        out.close();
        socket.close();
    }
    
}
