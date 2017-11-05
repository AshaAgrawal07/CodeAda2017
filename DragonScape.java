import java.io.File;
import java.util.Scanner;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class DragonScape {
	public static String[][] landscape = {{"princess", "0", "gold", "hint1", "hedge", "hedge", "hedge", "hedge"}, 
			{"0", "hedge", "0", "hedge", "gold", "hedge", "0", "0"}, 
			{"hint2", "mountain", "hedge", "hedge", "0", "mountain", "hedge", "0"},
			{"0", "0", "gold", "0", "0", "hint5", "mountain", "0"},
			{"0", "0", "0", "mountain", "hint4", "0", "wizard", "castle"}, 
			{"hedge", "0", "0", "gold", "gold", "mountain", "0", "hedge"},
			{"0", "0", "hedge", "hedge", "mountain", "0", "hedge", "hedge"},
			{"hint3", "hedge", "0", "hedge", "0", "0", "0", "hedge"}};
	
	
	public static int countGold = 0;
	public static boolean endGame = false;
	public static boolean hasMadHatter = false;
	public static boolean hasBeenToHintFour = false;
	public static boolean hasBeatWizard = false;
	
	
	public static void move(char c) {
		int x = 0;
		int y = 0;
		boolean canStop = false;
		for (int i = 0; i < landscape.length; i++) {
			for (int j = 0; j < landscape[0].length; j++) {
				if (landscape[i][j].equals("princess")) {
					x = i;
					y = j;
					canStop = true;
				}
				if (canStop) break;
			} if (canStop) break;
		} 
		
		if (c == 'w') {
			helperMove('w', x, y, x, y + 1);
		} else if (c == 'a') {
			helperMove('a', x, y, x - 1, y);
		} else if (c == 'd') {
			helperMove('d', x, y, x + 1, y);
		} else if (c == 's' ) {
			helperMove('s', x, y, x, y - 1);
		}
	}
	
	
	public static void helperMove(char c, int x, int y, int newX, int newY) {
		if (newY == 8 || newY == -1 || newX == 8 || newX == -1) {
			System.out.println("You are at the edge of Dragon Scape. You cannot move.");
		} else if (!landscape[newX][newY].equals("0")) {
			if (landscape[newX][newY].equals("hint1")
					|| landscape[newX][newY].equals("hint2")
					|| landscape[newX][newY].equals("hint3")
					|| landscape[newX][newY].equals("hint4")
					|| landscape[newX][newY].equals("hint5")) {
				hint(c, landscape[newX][newY]);
				landscape[x][y] = "0";
				landscape[newX][newY] = "princess";
			} else if (landscape[newX][newY].equals("hedge")
					|| landscape[newX][newY].equals("mountain")) {
				System.out.println("You cannot move because there is a " + landscape[newX][newY]);
			} else if (landscape[newX][newY].equals("gold")) {
				landscape[x][y] = "0";
				landscape[newX][newY] = "princess";
				countGold++;
				System.out.println("You got a piece of gold! Current Gold Count: " + countGold);	
			} else if (landscape[newX][newY].equals("wizard")){
				wizard(c);
				if (hasBeatWizard) {
					landscape[x][y] = "0";
					landscape[newX][newY] = "princess";
				}
			} else if (landscape[newX][newY].equals("castle")) {
				System.out.println("Congratulations on finishing this quest, Princess 007! You have successfully freed the hopeless knight"
						+ " \nand proven that you are one of the strongest of the lands!");
				endGame = true;
			}
		} else {
			landscape[x][y] = "0";
			landscape[newX][newY] = "princess";
		}
	
		
	}
	public static void hint(char c, String s) {
		if (s.equals("hint1")) {
			System.out.println("Go back to where you had come from.");
		} else if (s.equals("hint2")) {
			System.out.println("Congrats on continuing your journey Princess 007."
					+ " \nYou have a long and convoluted journey from here on. I wish you "
					+ " \nbest of luck.");
		} else if (s.equals("hint3")) {
			System.out.println("Welcome to the Rabbit Hole, Ms. Princess 007. Although many"
					+ " \ncan find what they want down the rabbit hole, I assure you that what you want"
					+ " \nis not here. However, you can find more gold as long as you hug the edges"
					+ " \nof the hedge and mountains on this side of the country. I also present you with "
					+ " \nthe Mad Hatter.");
			hasMadHatter = true;
		} else if (s.equals("hint4")) {
			System.out.println("Congratulations for coming around the volcano.");
			if (countGold < 5 && !hasMadHatter) {
				System.out.println("You shoudl go to the right side of the volcano to be honest."
						+ " You will never defeat the wizard in your current state.");
			} else {
				System.out.println("You are almost done with your journey. Keep moving forward to your"
						+ " destiny and rescue the hopeless knight");
				hasBeenToHintFour = true;
			}
		} else {
			if (countGold < 5 && !hasMadHatter) {
				System.out.println("You should go to the right side of the volcano to be honest. "
						+ "You will never defeat the wizard in your current state.");
			} else if (hasBeenToHintFour) {
				System.out.println("Princess, the wizard is to your diagonal right.");
			}
		}
	}
	
	public static void wizard(char c) {
		if (countGold < 5 && !hasMadHatter) {
			System.out.println("You have neither sufficient gold to bribe me"
					+ " nor the power to beat me. Go back.");
		} else if (countGold == 5) {
			System.out.println("Nice doing business with you, Princess. "
					+ "The hopeless knight is straight ahead at the castle.");
			endGame = true;
		} else if (hasMadHatter) {
			System.out.println("I surrender! The hopeless knight is straight ahead"
					+ " at the castle.");
			hasBeatWizard = true;
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome princess. The hopeless knight that set out to rescue you has"
				+ " \nbeen captured by the dark wizard who was friends with the dragon protecting you."
				+ " \nYour mission is to rescue the knight by bribing the wizard. The wizard is willing to "
				+ " \nlet go of the knight if you can give him five gold pieces. Do you accept this mission?"
				+ " \nY or N");
		
		boolean shouldContinue;
		String bip = "mainMusic.mp3";
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		
		Scanner scanner = new Scanner(System.in);
		String answer = scanner.nextLine();
		if (answer.equalsIgnoreCase("Y")) 
			shouldContinue = true;
		else shouldContinue = false;
		
		if (shouldContinue) {
			System.out.println("Congratulations on accepting the mission, Princess 007. To move: 'w'"
					+ " \nis to go forward one space, 'a' is to go left one space, 'd' is to right one"
					+ " \nspace, and 's' is to go back one space. Your first hint is that somewhere to your"
					+ " \ndirect north, there is some gold.");
			while (!endGame) {
				System.out.println("Which way do you want to move?");
				String s = scanner.next();
				while (s.length() > 1 && (s.equals("w") 
						|| s.equals("a")
						|| s.equals("d")
						|| s.equals("s"))) {
					System.out.println("Invalid input. w for forward, a for "
							+ "left, d for right, and s for backward.");
					s = scanner.next();
				}
				char c = s.charAt(0);
				move(c);
			}
			
		}
		scanner.close();
	}
	
}
