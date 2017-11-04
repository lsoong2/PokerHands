import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TwoPlayerPokerGame {
	public TwoPlayerPokerGame() {
	}
	
	private Card ParseCardFromString(String str) {
		char c[] = str.toCharArray();
		if (c.length < 2) {
			throw new IllegalArgumentException();
		}
		
		CardSuit suit;
		switch(c[c.length - 1]) {
			case 'C':
				suit = CardSuit.Club;
				break;
			case 'D':
				suit = CardSuit.Diamond;
				break;
			case 'H':
				suit = CardSuit.Heart;
				break;
			case 'S':
				suit = CardSuit.Spade;
				break;
			default:
				throw new IllegalArgumentException();
		}
		
		CardValue value;
		if (c.length == 2) {
			switch (c[0]) {
				case '2':
					value = CardValue.Two;
					break;
				case '3':
					value = CardValue.Three;
					break;
				case '4':
					value = CardValue.Four;
					break;
				case '5':
					value = CardValue.Five;
					break;
				case '6':
					value = CardValue.Six;
					break;
				case '7':
					value = CardValue.Seven;
					break;
				case '8':
					value = CardValue.Eight;
					break;
				case '9':
					value = CardValue.Nine;
					break;
				case 'J':
					value = CardValue.Jack;
					break;
				case 'Q':
					value = CardValue.Queen;
					break;
				case 'K':
					value = CardValue.King;
					break;
				case 'A':
					value = CardValue.Ace;
					break;
				default:
					throw new IllegalArgumentException();
			}
		} else if (c.length == 3) {
			if (c[0] == '1' && c[1] == '0') {
				value = CardValue.Ten;
			} else {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
		Card card = new Card();
		card.SetCard(suit, value);
		return card;
	}
	
	public Result PlayHand(String player1, ArrayList<Card> hand1, String player2, ArrayList<Card> hand2) {
		assert(hand1.size() == 5 && hand2.size() == 5);

		CardComparator comparator = new CardComparator();
		Collections.sort(hand1, comparator);
		Collections.sort(hand2, comparator);

		Result result = new Result();
		result.Player1Name = player1;
		result.Player2Name = player2;
		result.Player1Hand = handToString(hand1);
		result.Player2Hand = handToString(hand2);		
		result.Player1Result = new PokerHandResult(hand1);
		result.Player2Result = new PokerHandResult(hand2);
		
    	int compare = PokerHandResult.compare(result.Player1Result, result.Player2Result);
    	if (compare > 0) {
    		result.Winner = player1;
    	} else if (compare < 0) {
    		result.Winner = player2;
    	} else {
    		result.Winner = "";
    	}
    	
    	return result;
	}
	
	public Result PlayHand(String line) {
		StringTokenizer st = new StringTokenizer(line);
		int countTokens = st.countTokens();
		if (countTokens != 12) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<Card> hand2 = new ArrayList<Card>();
		ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
		ArrayList<String> players = new ArrayList<String>();
		HashMap<String,Integer> cardsSeen = new HashMap<String,Integer>();
		
		for (int i = 0; i < 2; i++) {
			ArrayList<Card> hand = new ArrayList<Card>();
			players.add(st.nextToken());
			for (int j = 0; j < 5; j++) {
				Card card = ParseCardFromString(st.nextToken());
				String cardString = card.toString();
				if (!cardsSeen.containsKey(cardString)) {
					hand.add(card);
					cardsSeen.put(cardString, 1);
				} else {
					throw new IllegalArgumentException();
				}
			}
			hands.add(hand);
		}

		return PlayHand(players.get(0), hands.get(0), players.get(1), hands.get(1));
	}

	public void PlayHand(int iterations) {
    	Deck deck = new Deck();
    	for (int i = 0; i < iterations; i++) {
	    	deck.shuffle();
	    	ArrayList<Card> hand1 = deck.createHand();
	    	ArrayList<Card> hand2 = deck.createHand();
	    	Result result = PlayHand("player1", hand1, "player2", hand2);
	    	System.out.println(ResultToString(result));
    	}
	}
	
	public static String ResultToString(Result result) {
		StringBuilder sb = new StringBuilder();
		
		final String lineSeparator = System.getProperty("line.separator");
		
		sb.append(result.Player1Name);
		sb.append(": ");
		sb.append(result.Player1Hand);
		sb.append(" ");
		sb.append(result.Player1Result.Rank.toString());
		sb.append(" secondary:");
		sb.append(result.Player1Result.SecondaryScore);
		sb.append(lineSeparator);

		sb.append(result.Player2Name);
		sb.append(": ");
		sb.append(result.Player2Hand);
		sb.append(" ");
		sb.append(result.Player2Result.Rank.toString());
		sb.append(" secondary:");
		sb.append(result.Player2Result.SecondaryScore);
		sb.append(lineSeparator);
		
    	if (!result.Winner.isEmpty()) {
    		sb.append(result.Winner + " won the hand!");
    	} else {
    		sb.append("Hand was a draw!");
    	}
		sb.append(lineSeparator);
    	
    	return sb.toString();
	}
	
	private static String handToString(ArrayList<Card> hand) {
		String handString = "";
		for (Card c : hand) {
			handString += c.toString() + " ";
		}
		return handString;
	}
	
	public class Result {
		public String Winner;
		public String Player1Name;
		public String Player2Name;
		public String Player1Hand;
		public String Player2Hand;
		public PokerHandResult Player1Result;
		public PokerHandResult Player2Result;
	}
}
