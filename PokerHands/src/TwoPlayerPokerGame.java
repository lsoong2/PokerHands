import java.util.ArrayList;
import java.util.Collections;
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
	
	public void PlayHand(String player1, ArrayList<Card> hand1, String player2, ArrayList<Card> hand2) {
		assert(hand1.size() == 5 && hand2.size() == 5);

		CardComparator comparator = new CardComparator();
		Collections.sort(hand1, comparator);
		Collections.sort(hand2, comparator);

		printHand(hand1, player1);
		printHand(hand2, player2);
		
		PokerHandResult result1 = new PokerHandResult(hand1);
		PokerHandResult result2 = new PokerHandResult(hand2);

		System.out.println(player1 + " " + result1.Rank.toString() + " " + result1.SecondaryScore + " " +
						   player2 + " " + result2.Rank.toString() + " " + result2.SecondaryScore);
		
    	int compare = PokerHandResult.compare(result1, result2);
    	if (compare > 0) {
    		System.out.println(player1 + " wins!");
    	} else if (compare < 0) {
    		System.out.println(player2 + " wins!");
    	} else {
    		System.out.println("Players have tied hands!");
    	}
	}
	
	public void PlayHand(String line) {
		StringTokenizer st = new StringTokenizer(line);
		int countTokens = st.countTokens();
		if (countTokens != 12) {
			throw new IllegalArgumentException();
		}
		
		ArrayList<Card> hand1 = new ArrayList<Card>();
		ArrayList<Card> hand2 = new ArrayList<Card>();
		String player1 = st.nextToken();
		for (int i = 0; i < 5; i++) {
			hand1.add(ParseCardFromString(st.nextToken()));
		}
		String player2 = st.nextToken();
		for (int i = 0; i < 5; i++) {
			hand2.add(ParseCardFromString(st.nextToken()));
		}

		PlayHand(player1, hand1, player2, hand2);
	}

	public void PlayHand(int iterations) {
    	Deck deck = new Deck();
    	for (int i = 0; i < iterations; i++) {
	    	deck.shuffle();
	    	ArrayList<Card> hand1 = deck.createHand();
	    	ArrayList<Card> hand2 = deck.createHand();
			PlayHand("player1", hand1, "player2", hand2);
    	}
	}
	
	private static void printHand(ArrayList<Card> hand, String label) {
		System.out.print(label + " ");
		for (Card c : hand) {
			System.out.print(c.toString() + " ");
		}
		System.out.println();
	}
}
