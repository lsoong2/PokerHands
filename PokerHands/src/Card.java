public class Card {
    CardSuit Suit;
    CardValue Value;

    public Card() {
    	this.Suit = CardSuit.Club;
    	this.Value = CardValue.Two;
    }
    
    public void SetCard(CardSuit suit, CardValue value) {
    	this.Suit = suit;
    	this.Value = value;
    }
    
    public String toString() {
    	return this.Value.toString() + this.Suit.toString();
    }
}
