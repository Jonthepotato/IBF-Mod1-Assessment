package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static Socket socket;
    static ServerSocket serverSocket;
    int port;
    String fileName;
    static List<String> fileList = new ArrayList<String>();
    
    public static void main(String[] args) throws IOException {
        //Check docRootpath
        String fileName;
        int port = 0;
     

        if (args[0]== "") {
            port = 3000;
            fileName = "C:\\Gitfolder\\IBF Mod1 Assessment\\demo\\static";
            fileList.add(fileName);
            // File file = new File(fileName);

        }else if (args[0].equals("--port") && args.length == 2) {
            port = Integer.parseInt(args[1]);
            fileName = "C:\\Gitfolder\\IBF Mod1 Assessment\\demo\\static";
            fileList.add(fileName);
            // File file = new File(fileName);
            
        }
        else if(args[0].equals("--docRoot:") && args.length == 2){
            port = 3000;
            if (!args[1].contains(":")) {
                fileName = args[1];
                fileList.add(fileName);
            }
            else {
                fileList = Arrays.asList(args[1].split(":"));
            }
            // fileName = args[1];
            // File file = new File(fileName);
            // Main server = new Main(port, fileName);

        } else if(args[0].equals("--port") && args[2].equals("--docRoot") && args.length == 4){
            port = Integer.parseInt(args[1]);
            if (!args[3].contains(":")) {
                fileName = args[3];
                fileList.add(fileName);
            }
            else {
                fileList = Arrays.asList(args[3].split(":"));
            }
        }

            HttpServer server = new HttpServer(port);

            for (String fileID: fileList) {
                File file= Paths.get(fileID).toFile();
                if(!file.canRead())
                {
                    System.out.println("File cannot be read");
                    System.exit(1);
                }

                else if(!file.exists()){
                    System.out.println("File does not exist");
                    System.exit(1);
                }

                else if (file.isDirectory()){
                    System.out.println("File is not in directory");
                    System.exit(1);
                }
                }

                // HTTPServer startexecutorservice
                ExecutorService threadPool = Executors.newFixedThreadPool(3);
                serverSocket = new ServerSocket(port);
                System.out.println("Listening at port: "+port);

                while (true) {
                    socket = serverSocket.accept();
                    HttpClientConnection worker = new HttpClientConnection();
                    threadPool.submit(worker);
                }


                




                // HttpHandler handler = new HttpHandler();
            

            



        }
        
    }
