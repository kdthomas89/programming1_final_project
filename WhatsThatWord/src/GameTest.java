import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class GameTest {
	//static ArrayList<Player> player = new ArrayList<Player> (1);
		static Player player = new Player(0, 0, 0, "AAA");


		public static void main(String[] args) {
			
			//calls the play method
			play();
			
		}
			
		
		//play method that contains all code outside of main method enabling the ability to run again after the game ends
		public static void play() {
			
			//declare variables
			Scanner input = new Scanner(System.in);
			String word, guessWord;
			int count, difficulty, again;
			char guess;
			boolean[] previousGuesses = new boolean[26];
			
		
			//call displayWelcome method to display the welcome screen
			displayWelcome();
			
			//get desired difficulty from user and store value in difficulty variable
			difficulty = input.nextInt();
			
			//calls the getDifficulty method using the value of difficulty to populate wordBank appropriately
			String wordBank[] = getDifficulty(difficulty);
			
			//calls getWord method to generate a random word from the word bank
			word = getWord(wordBank);
			
			//create 2 arrays the length of the selected word
			char wordArray[] = getArray(word);
			char hiddenArray[] = getArray(word);
			
			
			//populate hidden array with characters that spell selected word
			for (int i = 0; i < word.length(); i++) {
				hiddenArray[i] = word.charAt(i);
			}
			
			//populate other array with asterisks
			for (int i = 0; i < wordArray.length; i++) {
				wordArray[i] = '*';
			}
			
			//initialize count variable to be length of the selected word
			count = word.length();
			
			//while loop that loops until either the player guesses the word or the player runs out of guesses
			while (count > 0 && player.getGuesses() != 0) {
				
				//print asterisk array and prompt user to guess
				System.out.print("Your word is ");
				
				for (int i = 0; i < wordArray.length; i++) {
					System.out.print(wordArray[i]);
				}
				
				System.out.println(". Please guess a letter. Press 1 to guess the word or press 2 to display a hint at the cost of points.");
				System.out.println("Wrong guesses left: " + player.getGuesses());
				
				
				//read guess from console
				guess = input.next().charAt(0);
				
				//allows the user to guess the entire word if they enter 1
				if (guess == '1') {
					
					//prompt user to guess, get next string from user
					System.out.println("Please enter your guess:");
					guessWord = input.next();
					
					//checks if the entered string equals the word, reducing the count to 0, giving the player a score bonus and exiting the while loop if it does
					if (guessTheWord(word, guessWord) == true) {
						player.setScore(player.getScore() + (150 * count));
						count = 0;
						}
					//informs user that their guess was wrong, subtracts points if they have more than 100, or setting their points to 0 if they have less to prevent having negative points
					else {
						System.out.println("Sorry, that is not the correct word.");
						player.setGuesses(player.getGuesses() - 1);
						player.setScore(player.getScore() - 100);
					}
				}
				//displays appropriate hint for the word if the user enters 2, and subtracts 200 points from score as a cost
				else if (guess == '2') {
					getHint(word);
					player.setScore(player.getScore()-200);
				}
				//if user has already entered that char it will inform them and prompt them to guess again
				else if(previousGuesses[guess-'a'] == true) {
					System.out.println("You've already guessed " + guess + ". Please guess again");
				}
				//sets the index of the entered char to true to represent that the guess of the letter has been "used"
				
				if (guess >= 'a' && guess <= 'z' && !previousGuesses[guess-'a']) {
					
					int incorrectGuess = 1;
					//populates the location of the letter in the boolean array to true, so that the program will know the user has entered that letter when it asks for input again
					previousGuesses[guess - 'a'] = true;
						
					//check for guess in array and replace asterisk with character in the other array if its in there
					for (int i = 0; i < wordArray.length; i++) {
						
						//replaces the appropriate asterisk with the guess if that guess is correct and gives the player points
						if (guess == hiddenArray[i]) {
							wordArray[i] = guess;
							count--;
							incorrectGuess = 0;
							if (guess == 'a' || guess == 'e' || guess == 'i' || guess == 'o' || guess == 'u') {
								player.setScore(player.getScore() + 75);
							}
							else {
								player.setScore(player.getScore() + 100);								
							}
							//break;
						}
					}
				
					//if the player guesses incorrectly, reduce the number of guesses by one and subtract points
					if (incorrectGuess == 1) {
						player.setGuesses(player.getGuesses() - 1);
						if (guess == 'a' || guess == 'e' || guess == 'i' || guess == 'o' || guess == 'u') {
							player.setScore(player.getScore() - 35);
						}
						else {
							player.setScore(player.getScore() - 50);	
						}
					}
				}
				
			}
			
			//if the character runs out of guesses, display the game over screen
			if (player.getGuesses() == 0) {
				displayGameOver(word);
			}
			
			//when the count decrements to zero, the game will be over and the game will declare that the user is a winner
			if (count == 0)	{
				displayVictory(word);
			}
			
			//prompt user to enter initials
			player.setInitials(input.next());
			
			//calls the save game method to save user initials and score to a text file
			saveGame();
			
			
			System.out.println("Play again?");
			System.out.println("Press 1 to play again");
			System.out.println("Press 2 to quit");
			
			again = input.nextInt();
			
			//if the user presses 1, the game starts over, otherwise the game exits
			if (again == 1) {
				play();
			}
			else {
				System.exit(0);
			}
		}
			

			
		
		//displays the welcome message and prompts the user to enter desired difficulty
		public static void displayWelcome() {
			System.out.println("Welcome to Word Disguise!");
			System.out.println("Choose the difficulty, then guess the word and try to get the high score. You have limited guesses, however, so think carefully!");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("Please choose difficulty level:");
			System.out.println("Press 1 for easy");
			System.out.println("Press 2 for medium");
			System.out.println("Press 3 for hard");
		}
		
		//checks which difficulty the user has selected and returns the word bank for the selected difficulty
		public static String[] getDifficulty(int difficulty) {
			
			String[] wordBank = null;
			
				//the 3 word banks for the 3 difficulties from which the random word to be played is chosen
				if (difficulty == 1) {
					wordBank = new String[] {"amber", "piano", "album", "bacon", "quiet", "bling", "party", "grape", "nurse", "slime", "raven", "brick", "frost", "eight", "dream", "zebra", "yacht", "joint", "quest", "wagon", "axis", "phone"};
					player.setGuesses(5);
				}
				else if (difficulty == 2) {
					wordBank = new String[] {"agonize", "aftershock", "lightsaber", "volkswagen", "boulder", "crusade", "destiny", "encrypt", "faction", "glacier", "harvest", "jukebox", "keyword", "machine", "network", "outrage", "pickaxe", "question", "replica", "shotgun", "thyroid", "utensil", "variety", "wendigo"};
							
					player.setGuesses(5);
				}
				else if (difficulty == 3) {
					wordBank = new String[] {"ambidextrous", "bankruptcy", "demographic", "housewarming", "lycanthrope", "malnourished", "problematic", "thunderclap", "unforgivable", "incomputable", "metalworking", "blacksmith", "background", "binoculars", "journalist", "microwave", "trampoline", "republican", "playground"};
					player.setGuesses(5);
				}
				
			return wordBank;
			
		}
		
		//chooses and returns a random word from the appropriate word bank
		public static String getWord(String[] wordBank) {
			
			String word = wordBank[(int)(Math.random() * wordBank.length)];
			
			return word;
			
		}
		
		//method that checks word and guessWord and returns true if equal
		public static boolean guessTheWord(String word, String guessWord) {
			
			if (guessWord.equals(word)) {
				return true;
			}
			else {
				return false;
			}
		}
		
		//displays the victory message, score for that game, and high score when the player wins
		public static void displayVictory(String word) {
			
			if (player.getScore() > player.getHighScore()) {
				player.setHighScore(player.getScore());
			}
			
			System.out.println("The word was " + word + ". Congratulations! You won! You're score was " + player.getScore() + ". Your all time high score is " + player.getHighScore());
			System.out.println("Please enter your initials:");
			
			
		}
		
		//displays the game over message for when the player loses
		public static void displayGameOver(String word) {
			
			System.out.println("Sorry, you ran out of guesses. The word was " + word + ". Thanks for playing!");
			
		}
		
		//creates and returns a new array the length of the chosen word
		public static char[] getArray(String word) {
			
			char array[] = new char[word.length()];
			
			return array;
			
		}
		
		//prints the hint for the chosen word
		public static void getHint(String word) {
			
			switch (word) {
				
				case "amber":
					System.out.println("The hint is: \"fossil\"");
					break;
				case "monkey":
					System.out.println("The hint is: \"primate\"");
					break;
				case "piano":
					System.out.println("The hint is: \"music\"");
					break;
				case "album":
					System.out.println("The hint is: \"collection of music\"");
					break;
				case "bacon":
					System.out.println("The hint is: \"food\"");
					break;
				case "quiet":
					System.out.println("The hint is: \"silence\"");
					break;
				case "grape":
					System.out.println("The hint is: \"food\"");
					break;
				case "nurse":
					System.out.println("The hint is: \"healthcare professional\"");
					break;
				case "slime":
					System.out.println("The hint is: \"slippery/gooey substance\"");
					break;
				case "raven":
					System.out.println("The hint is: \"bird\"");
					break;
				case "brick":
					System.out.println("The hint is: \"building material\"");
					break;
				case "frost":
					System.out.println("The hint is: \"cold\"");
					break;
				case "eight":
					System.out.println("The hint is: \"number\"");
					break;
				case "dream":
					System.out.println("The hint is: \"sleep\"");
					break;
				case "zebra":
					System.out.println("The hint is: \"animal\"");
					break;
				case "yacht":
					System.out.println("The hint is: \"aquatic vehicle\"");
					break;
				case "joint":
					System.out.println("The hint is: \"connection of two things\"");
					break;
				case "quest":
					System.out.println("The hint is: \"mission\"");
					break;
				case "wagon":
					System.out.println("The hint is: \"vehicle\"");
					break;
				case "axis":
					System.out.println("The hint is: \"rotation point\"");
					break;
				case "phone":
					System.out.println("The hint is: \"transmit sound and voice\"");
					break;
				case "agonize":
					System.out.println("The hint is: \"worry\"");
					break;
				case "boulder":
					System.out.println("The hint is: \"stone\"");
					break;
				case "crusade":
					System.out.println("The hint is: \"religious mission\"");
					break;
				case "destiny":
					System.out.println("The hint is: \"fate\"");
					break;
				case "encrypt":
					System.out.println("The hint is: \"cipher/code\"");
					break;
				case "faction":
					System.out.println("The hint is: \"group within group\"");
					break;
				case "glacier":
					System.out.println("The hint is: \"ice formation\"");
					break;
				case "harvest":
					System.out.println("The hint is: \"farming\"");
					break;
				case "jukebox":
					System.out.println("The hint is: \"music player\"");
					break;
				case "keyword":
					System.out.println("The hint is: \"hint/word of significance\"");
					break;
				case "machine":
					System.out.println("The hint is: \"device/contraption\"");
					break;
				case "network":
					System.out.println("The hint is: \"system/link\"");
					break;
				case "outrage":
					System.out.println("The hint is: \"anger\"");
					break;
				case "pickaxe":
					System.out.println("The hint is: \"mining\"");
					break;
				case "question":
					System.out.println("The hint is: \"doubt\"");
					break;
				case "replica":
					System.out.println("The hint is: \"exact copy\"");
					break;
				case "shotgun":
					System.out.println("The hint is: \"firearm\"");
					break;
				case "thyroid":
					System.out.println("The hint is: \"gland\"");
					break;
				case "variety":
					System.out.println("The hint is: \"diversity\"");
					break;
				case "wendigo":
					System.out.println("The hint is: \"yeti\"");
					break;
				case "ambidextrous":
					System.out.println("The hint is: \"left and right\"");
					break;
				case "bankruptcy":
					System.out.println("The hint is: \"insolvent\"");
					break;
				case "demographic":
					System.out.println("The hint is: \"sector of population/group of people\"");
					break;
				case "housewarming":
					System.out.println("The hint is: \"welcome home\"");
					break;
				case "lycanthrope":
					System.out.println("The hint is: \"werewolf\"");
					break;
				case "malnourished":
					System.out.println("The hint is: \"poor diet/hungry\"");
					break;
				case "problematic":
					System.out.println("The hint is: \"difficult/troublesome\"");
					break;
				case "thunderclap":
					System.out.println("The hint is: \"storm event\"");
					break;
				case "unforgivable":
					System.out.println("The hint is: \"inexcusable\"");
					break;
				case "incomputable":
					System.out.println("The hint is: \"unable to be calcualted\"");
					break;
				case "metalworking":
					System.out.println("The hint is: \"crafting of malleable substances\"");
					break;
				case "blacksmith":
					System.out.println("The hint is: \"creates and repairs metal objects\"");
					break;
				case "background":
					System.out.println("The hint is: \"not prominent scenery\"");
					break;
				case "binoculars":
					System.out.println("The hint is: \"object to see from afar\"");
					break;
				case "journalist":
					System.out.println("The hint is: \"newspaper\"");
					break;
				case "microwave":
					System.out.println("The hint is: \"cooking\"");
					break;
				case "trampoline":
					System.out.println("The hint is: \"jumping\"");
					break;
				case "republican":
					System.out.println("The hint is: \"Abe Lincoln or John McCain\"");
					break;
				case "playground":
					System.out.println("The hint is: \"school/park\"");
					break;
				case "bling":
					System.out.println("The hint is: \"expensive jewelry\"");
					break;
				case "party":
					System.out.println("The hint is: \"social gathering\"");
					break;
				case "aftershock":
					System.out.println("The hint is: \"happens after an earthquake\"");
					break;
				case "lightsaber":
					System.out.println("The hint is: \"laser beam sword\"");
					break;
				case "volkswagen":
					System.out.println("The hint is: \"das auto\"");
					break;
			}	
		}
	
	//saves the users initials and score to a text file
	public static void saveGame() {
		try {
			PrintWriter out = new PrintWriter("Scores.txt");
			out.println(player.getInitials());
			out.println(player.getScore());
			 out.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
}
