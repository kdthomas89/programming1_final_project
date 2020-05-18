
public class Player {
	
	//declare variables
		private int highScore;
		private int guesses;
		private int score;
		private String initials;
		
		public Player(int h, int g, int s, String i) {
			
			highScore = h;
			guesses = g;
			score = s;
			
		}
		
		

		public int getHighScore() {
			
			return highScore;
			
		}
		
		public void setHighScore(int h) {
			
			highScore = h;
			
		}
		
		public int getGuesses() {
			
			return guesses;
			
		}
		
		public void setGuesses(int g) {
			
			guesses = g;
			
		}
		
		
		public int getScore() {
			
			return score;
			
		}
		
		public void setScore(int s) {
			
			score = s;
			
		}
		
		public String getInitials() {
			
			return initials;
			
		}
		
		public void setInitials(String i) {
			
			initials = i;
			
		}

}
