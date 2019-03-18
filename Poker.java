import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a Poker class that take multiple inputs from the command line, it
 * would characterise the hand card for each user and decides the winners.
 *
 * @author Jian Shi
 * @email: shijianhzchina@gmail.com
 * @date: 9/21/2017 01:17
 *
 * The program take inputs from the command line, verify whether the number
 * is multiple of 5 cards and verify whether the name of input is valid, then
 * the program would characterise every players' hand card and print out the
 * classification of each players' hand card line by line. Finally, the
 * program would compare players hand card and print out the winners sequence.
 */

public class Poker {

    //Define the Number of card in one hand is 5
    private static final int MAX_HAND_NUM = 5;
    //Define the Number of card in a full poker is 52
    private static final int MAX_CARD_NUM = 52;
    //Define the least priority for a player is 9 while the top priority is 1
    private static final int MIN_PRIORITY = 9;
    //Define the Draw and lose to 0 and -1 respectively
    private static final int DRAW = 0;
    private static final int LOSE = -1;
    //Define the System.exit status
    private static final int EXIT = 1;
    //Define a arraylist to store the whole 52 cards
    private static final ArrayList<String>CARDS = new ArrayList<>(MAX_CARD_NUM);
    //Define the String variable to store the first invalid input card
    private static String invalidCard;


    public static void main(String[] args) {
        //Calculate the number of players.
        int numOfPlayers = args.length / MAX_HAND_NUM;
        int min = MIN_PRIORITY; //Define a min for compare priority for players
        int nextWinnerNum; //Define a variable for using in comparing winners
        //Initialize a Player type array players to store all the players
        Player[] players = new Player[numOfPlayers];
        //Using HashMap to store the priority for each player
        Map<Integer, Integer> priority = new HashMap<>(numOfPlayers);
        //Define 2 arraylist to store candidate winners and winners
        List<Integer> potentialWinners = new ArrayList<>();
        List<Integer> winners = new ArrayList<>();

        initCards();

        if (args.length % MAX_HAND_NUM != 0 || args.length <= 0) {
            /**
             * Situation 1 with wrong number of arguments
             */
            System.out.println("Error: wrong number of arguments; " +
                    "must be a multiple of 5");
        } else if (!validCards(args)) {
            /**
             * Situation 2 with invalid card name
             */
            System.out.println("Error: invalid card name '" + invalidCard +"'");
            System.exit(EXIT);
        } else if (args.length % MAX_HAND_NUM == 0 && numOfPlayers == 1) {
            /**
             * Situation 3 with only one player
             * do not need to compare and do not need print winner
             */
            ArrayList<String> hand = new ArrayList<>(MAX_HAND_NUM);
            for (int i = 0; i < MAX_HAND_NUM; i++) {
                hand.add(args[i].toUpperCase());
            }
            /**
             * create the player instance of the Player class with the input
             * card, all the judgement would be made inside the Player class
             */
            Player player = new Player(hand);
            System.out.println("Player 1: " + player.toString());
        } else {
            /**
             * Situation 4 with multiple players
             * Need to compare
             * Need print winner(s) (have the situation of draw)
             */

            /**
             * First part of situation 4
             * Print the classification of each players' hand cards
             */
            for (int i = 1; i <= numOfPlayers; i++) {
                ArrayList<String> hand = new ArrayList<>(MAX_HAND_NUM);
                for (int k = (i - 1) * MAX_HAND_NUM; k < i * MAX_HAND_NUM; k++){
                    hand.add(args[k].toUpperCase());
                }
                players[i - 1] = new Player(hand);
                System.out.println("Player " + i + ": "
                        + players[i - 1].toString());
                priority.put(i, players[i - 1].getPriority());
            }
            /**
             * Second part of situation 4
             */
            //Decide which players have the top priority.
            for (int i = 1; i <= numOfPlayers; i++) {
                if (priority.get(i) <= min) {
                    min = priority.get(i);
                }
            }
            /**
             * Add all players who have the highest priority into the arraylist
             * potentialWinners as candidate winners
             * Only the players have the highest priority can compete for win
             */
            for (int i = 1; i <= numOfPlayers; i++) {
                if (priority.get(i) == min) {
                    potentialWinners.add(i);
                }
            }
            /**
             * Compare the first candidate winner with other candidates
             * if they are draw, then add the other candidate
             * if first candidate lose, then clear the array, add the winner
             * if first candidate win, do nothing
             */
            winners.add(potentialWinners.get(0));
            for (int i = 1; i < potentialWinners.size(); i++) {
                nextWinnerNum = potentialWinners.get(i) - 1;
                if (players[winners.get(0) - 1]
                        .compareTo(players[nextWinnerNum]) == DRAW) {
                    winners.add(nextWinnerNum + 1);
                } else if (players[winners.get(0) - 1]
                        .compareTo(players[nextWinnerNum]) == LOSE) {
                    winners.clear();
                    winners.add(nextWinnerNum + 1);
                }
            }
            //Print the Winner(s) as requested format
            if (winners.size() == 1) {
                System.out.println("Player " + winners.get(0) + " wins.");
            } else if (winners.size() > 1) {
                System.out.print("Players ");
                for (int winner : winners) {
                    if (winners.indexOf(winner) == winners.size() - 1) {
                        System.out.println(" and " + winner + " draw.");
                    } else if (winners.indexOf(winner) == winners.size() - 2) {
                        System.out.print(winner);
                    } else {
                        System.out.print(winner + ", ");
                    }
                }
            }
        }
    }

    /**
     * This method is to initialize a CRADS arraylist to store the whole 52
     * valid cards.
     */

    private static void initCards() {
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                CARDS.add(rank.getRank() + suit.getSuit());
            }
        }
    }

    /**
     * This method to check whether a input String card is valid or not.
     * if the input card is not contained in the full poker arraylist CARDS,
     * then the input card is invalid.
     *
     * @param input - The array that input from the command line
     * @return true - if all the input card is valid
     */
    private static boolean validCards(String[] input) {
        for (String inputCard : input) {
            if (!CARDS.contains(inputCard.toUpperCase())) {
                invalidCard = inputCard;
                return false;
            }
        }
        return true;
    }
}
