import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextGuiApp extends JFrame{
	public static void main(String[] args) {
		TextGuiApp test = new TextGuiApp();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	 
	public TextGuiApp()
	{
		super("TestGuiApp");
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		
		jtfInputText = new JTextField(60);
		container.add(jtfInputText);
		
		jtaResultText = new JTextArea("Uneditable text field", 10, 60);
		jtaResultText.setEditable(false);
		jtaResultText.setText(usageString);
		container.add(jtaResultText);
		
		actionHandler = new TextHandler();
		jtfInputText.addActionListener(actionHandler);
		
		setSize(700, 240);
		setVisible(true);
		
		game = new TwoPlayerPokerGame();
	}
	
	private class TextHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String resultString = "";
			if (event.getSource() == jtfInputText) {
				String line = event.getActionCommand();
				try
				{
					TwoPlayerPokerGame.Result result = game.PlayHand(line);
					resultString = TwoPlayerPokerGame.ResultToString(result);
				} catch (IllegalArgumentException e) {
					resultString = usageString;
				}
			}
			jtaResultText.setText(resultString);
		}
	}
	
	protected JTextField jtfInputText;
	protected JTextArea jtaResultText;
	protected TextHandler actionHandler = null;

	private TwoPlayerPokerGame game;
	private final String usageString = "<player1 name> <player1 card1> ... <player1 card5> <player2 name> <player2 card1> ... <player2 card5>\n" +
									   "each card element takes the form <Value><Suit>, where\n" +
									   "value must be: 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A and\n" +
									   "suit must be: C, D, H, S for club, diamond, heart, spade\n\n" +
									   "an example valid string is \"player1 AC KC QC JC 10C player2 AH KH QH JH 10H\"";
}
