package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpClientConnection implements Runnable{

    static String directoryName;
    private Socket socket;
    private List<String> command = new ArrayList<String>();
    private String response;
    private DataInputStream dis;
    private DataOutputStream dos;


    @Override
    public void run() {

        try(InputStream is = socket.getInputStream()){
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            dis.readUTF();
            String line = dis.readUTF();
            command = Arrays.asList(line.split(" "));
            
        } catch(Exception e){

        }


        try(OutputStream os = socket.getOutputStream() ){
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

        } catch(Exception e){
        }
        String line = "";
        //GET /index.html HTTP/1.1

        if (!command.get(0).equals("GET")) {
            response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n "+command.get(0) + "not supported\r\n";
            try {
                dos.writeUTF(response);
                dos.flush();
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
            else{
                directoryName = command.get(1);
                if (directoryName.equals("/")) {
                    directoryName = "/index.html";

                }else {directoryName = command.get(1);}
            }
                    for (String listID: Main.fileList){
                        File directory = Paths.get(listID + directoryName).toFile();
                        if (!directory.exists()) {
                            response = "HTTP/1.1 404 Not Found\r\n\r\n"+ directory+" not found\r\n";
                            try {
                                dos.writeUTF(response);
                                dos.flush();
                                socket.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                    }else{
                        if(command.get(1).contains(".png")){
                            response = "HTTP/1.1 200 OK\r\n Content-Type: image/png\r\n";
                            try {
                                dos.writeUTF(response);
                                dos.flush();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            
                            byte[] bytearray = new byte[(int)directory.length()];
                            try {
                                bytearray = Files.readAllBytes(directory.toPath());
                                dos.write(bytearray);
                                dos.flush();
                                socket.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }else{
                            response = "HTTP/1.1 200 OK\r\n\r\n";
                            try {
                                dos.writeUTF(response);
                                dos.flush();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            byte[] bytearray = new byte[(int)directory.length()];
                            try {
                                bytearray = Files.readAllBytes(directory.toPath());
                                dos.write(bytearray);
                                dos.flush();
                                socket.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }




                            }
                        }

                    
                    
                }
            }
        