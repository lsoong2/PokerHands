# PokerHands
This is an implementation of PokerHands as stipulated on http://itrellisrecruiting.azurewebsites.net, using a Native GUI interface with Java.

For development, I used Eclipse Oxygen.1a Release (4.7.1a), against the JRE Version 8 Update 151 (build 1.8.0_151-b12). I implemented two applications ConsoleApp and TextGuiApp.

ConsoleApp was written first to test the functionality and uses STDIN and STDOUT, allowing for the test cases under the tests subfolder to be piped. The verified output of each &lt;test&gt;.txt were kept as &lt;test&gt;output.txt.

TextGuiApp was written to provide a Native GUI interface for the functionality.

In both cases a string is provided in the following form:
    <p>&lt;player1 name&gt; &lt;player1 card1&gt; ... &lt;player1 card5&gt; &lt;player2 name&gt; &lt;player2 card1&gt; ... &lt;player2 card5&gt;</p>
    <p>each card element takes the form &lt;Value&gt;&lt;Suit&gt;, where</p>
    <p>value must be: 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A and</p>
    <p>suit must be: C, D, H, S for club, diamond, heart, spade</p>

    <p>an example valid string is "player1 AC KC QC JC 10C player2 AH KH QH JH 10H"</p>

The mechanics of the logic to compare two hands of poker were:
1. Sort the cards with the value (2-10, Jack, Queen, King, Ace) as the first-order comparator, and the suit as the second. Ace is treated as high in this sorting. The ordering is descending, starting with the highest valued cards.
2. Walk through the cards, looking for matching values. This determines if the hand is a pair, two pair, three of a kind, full house, or four of a kind.
3. As we walk, we maintain two scores: matched values and unmatched values. In each case, if there are multiple values contributing to each, we treat each value as a digit on a base 15 number system. When aces are treated as low, it takes the value of 0. 2 takes the value of 1, 3 takes 2, and so on. 14 is unused. 
4. If no matched values are found, the hand can potentially be a straight, flush, or both. In the case of a straight, the score will need to be adjusted if the ace is used as a low card, rather than high. This ensures a 6 high straight beats a 5 high straight.
5. If no straight or flush is found, the hand must be a high card.
6. With the scores computed for each hand, the comparison between two hands is first against the rank (e.g. pair vs straight), second against the computed score.

This was written with an eye towards possible next steps rather than a completed software package. The scoring mechanism allows multiple hands to be compared quickly, although only two hands are currently allowed. There are many potential improvements, such as turning this into an actual poker game. As such, I decided to implement a quick interface rather than an elegant interface over the simplistic two poker hand comparison. Improvements to the capabilities of the underlying functionality could require drastic changes to the UI. Since the functionality is currently simplistic, a simplistic UI that demonstrates the logic is sufficient.
