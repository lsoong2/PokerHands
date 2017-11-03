import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ConsoleApp {
	public ConsoleApp() {
		game = new TwoPlayerPokerGame();
	}
	
	private void HandleInput(String input) {
		if (input == null || input.isEmpty()) {
			return;
		}
		
		if (input.startsWith(";")) {
			return;
		}
		
		try
		{
			game.PlayHand(input);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	public void Run() {
		//game.PlayHand("6S 5S 2D 2H 2C QH JS 6D 5H 3S");
		//game.PlayHand(20);
    	String line;
    	InputStreamReader in = null;
    	BufferedReader br = null;
    	try {
        	in = new InputStreamReader(System.in);
        	br = new BufferedReader(in);
	    	do {
	    		line = br.readLine();
	    		System.out.println(line);
	    		HandleInput(line);
	    	} while (!line.isEmpty());
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		if (in != null) {
    			try {
    				in.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		if (br != null) {
    			try {
    				br.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
	}
	
	private TwoPlayerPokerGame game;
	
    public static void main(String[] args) {
    	ConsoleApp app = new ConsoleApp();
    	app.Run();
        //new GuiApp1();
    }
}
