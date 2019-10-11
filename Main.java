

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    
    public static void defaultMessage(){
        System.out.println("Run this program as follows:");
        System.out.println("<program> chess <IP,Port,SessionID>");
        System.out.println("<program> chess <color> [...]");
        System.out.println("<program> cch <window name> [...]");
        System.out.println("<program> cch <window name> robot,<window name>,<time>,<player number>,[...]");
        System.out.println("<program> test");
        System.out.println("Note: If seeing this message, your input is invalid");
        System.exit(0);
    }
    
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> cchIndex = new ArrayList<Integer>();
        ArrayList<Integer> chessIndex = new ArrayList<Integer>();
        ArrayList<String> windowNames = new ArrayList<String>();
        ArrayList<String> chessColors = new ArrayList<String>();
        ArrayList<CCHModel> cchGames = new ArrayList<CCHModel>();
        ArrayList<Integer> cchThreads = new ArrayList<Integer>();
        ArrayList<String> history = new ArrayList<String>();
        ArrayList<String> onlineGame = new ArrayList<String>();
        ArrayList<String> robots = new ArrayList<String>();
        
        Client client = new Client();

        if(args.length == 0)
            defaultMessage();
        
        if(args.length == 1 && args[0].equals("test"))
            Tests.test();

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("cch")){
                cchIndex.add(i);
            }
            if(args[i].equals("chess")){
                chessIndex.add(i);
            }
            if(args[i].contains("robot"))
                robots.add(args[i]);
        }
        
        for(int i = 0; i < cchIndex.size(); i++){
            for(int j = cchIndex.get(i); j < args.length; j++){
                if(args[j].equals("chess"))
                    break;
                if(!args[j].equals("cch") && !args[j].contains("robot"))
                    windowNames.add(args[j]);
            }
        }
        
        for(int i = 0; i < chessIndex.size(); i++){
            for(int j = chessIndex.get(i); j < args.length; j++){
                if(args[j].equals("cch"))
                    break;
                if(args[j].equals("white") || args[j].equals("black")
                        || args[j].equals("kibbitzer"))
                    chessColors.add(args[j]);
                else if(args[j].contains(","))
                    onlineGame.add(args[j]);
            }
        }
        
        for(int i = 0; i < windowNames.size(); i++){
            if(!history.contains(windowNames.get(i))){
                int occ = Collections.frequency(windowNames, windowNames.get(i));
                history.add(windowNames.get(i));
                cchGames.add(new CCHModel(windowNames.get(i), robots));
                cchThreads.add(occ);
            }
        }
        
        for(int i = 0; i < cchGames.size(); i++){
            for(int j = 0; j < cchThreads.get(i); j++){
                Thread t = new Thread(cchGames.get(i));
                t.start();
            }
        }
        
        new RunGame().run(chessColors);
        
        for(int i = 0; i < onlineGame.size(); i++){
            client.runClient(onlineGame.get(i));
        }
    }
}
