// X 1: project must contain ClientSide + ServerSide.
// X 2: ClientSide will include Scanner and client ability to input what they want.
// X 3: the ServerSide will give the user the ability to do and save things
//    including lists, maps, arrays....
// 4: either in the ClientSide or ServerSide there will be an ability to work with files.
// X 5: the passage of info from the ClientSide to the ServerSide and vice versa
//    will be used by using (byte[] buffer) and ByteBuffer.wrap.
// X 6: the project can be about anything, for example - real estate, shopping online,
//    hotel services or even a dating website.
// 7: the project must be able to compile no matter what the user inputs.
// X 8: keep UI and business logic separate + code must be clean and separate in methods
//    and classes in order for easy to read code + easy maintenance.
// X 9: must use inheritance, interface and @Override.
// X 10: ServerSide and ClientSide must be able to run non-stop unless the user wants to stop.
// X 11: write near code little explanations to what it is the code is doing.
// X 12: ServerSide code must be what the client wants (no set info otherwise).

package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static final int PORT = 7241;
    public static final String HOST = "127.0.0.1";
    public static Socket socket = null;
    public static InputStream inputStream = null;
    public static OutputStream outputStream = null;

    public static void main(String[] args) throws IOException, InterruptedException {

        ClientUI clientUI = new ClientUI();
        clientUI.welcome();
    }


    public static Socket connectToServer(){
        try{
            socket = new Socket(HOST, PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public static void disconnectFromServer(){
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }








}
