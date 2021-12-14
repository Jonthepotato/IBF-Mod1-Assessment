package test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    Socket socket;
    ServerSocket serverSocket;
    int port;
    String fileName;

    HttpServer(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
        this.socket = serverSocket.accept();
    }

    
    
        

    }

