import java.util.*;

public class Deck {

	public Deck() {
		this.cards = new ArrayList<Card>();
		this.usedCards = new ArrayList<Card>();
		
		for (CardSuit suit : CardSuit.values()) {
			for (CardValue value : CardValue.values()) {
				Card card = new Card();
				card.SetCard(suit, value);
				cards.add(card);
			}
		}
	}
	
	public void shuffle() {
		assert(usedCards.size() == 0);
		for (Card card : usedCards) {
			cards.add(card);
		}
		usedCards.clear();
		Collections.shuffle(cards);
	}
	
	public ArrayList<Card> createHand() {
		ArrayList<Card> hand = new ArrayList<Card>();
		for (int i = 0; i < 5; i++) {
			Card c = cards.remove(0);
			usedCards.add(c);
			hand.add(c);
		}
		return hand;
	}
	
	private ArrayList<Card> cards;
	private ArrayList<Card> usedCards;
	
}
