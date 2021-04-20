import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

public class MultiplayerGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Zab's Multiplayer Number Guessing Game!");
		System.out.println("Let's set up.");

		int PlayGame = 1;
		do {

		boolean entryError = false;

	do {
		entryError = false;
		try {
			Scanner keyboard = new Scanner(System.in);
			System.out.print("How many players?  ");
			int PlayerCount = keyboard.nextInt();


		String [][] PlayerData = new String[PlayerCount][4];
		for (int x = 0; x < PlayerCount; x++) {
			System.out.println("Please enter name of Player #" + (x+1));
			String PlayerName = keyboard.next();
			PlayerData[x][0] = PlayerName;
			int LowerBound = getAnLowerBound(PlayerName);
			int HigherBound = getAHigherBound(PlayerName, LowerBound);
			PlayerData[x][1] = getRandomInt(LowerBound, HigherBound);
			PlayerData[x][2] = Integer.toString(0);
			PlayerData[x][3] = "false";
		}
		ArrayList<String> GuessComp = new ArrayList<String>();




		String enteredString = "";
		int finishedPlayers = 0;

		int n = 0;
		do {


		if (PlayerData[n][3] == "false") {
			try {



			int Guess = 0;
			System.out.print(PlayerData[n][0] + ", please enter Guess: ");
			enteredString = keyboard.next();
			Guess = Integer.parseInt(enteredString.trim());
			GuessComp.add(enteredString);

			int Target = Integer.parseInt(PlayerData[n][1]);
			int Attempts = Integer.parseInt(PlayerData[n][2]);
				Attempts ++;
				PlayerData[n][2] = Integer.toString(Attempts);

			if (Guess == Target) {
				System.out.println("Amazing! That's the number I was thinking!");
				System.out.println("You Win! Great Job!");
				PlayerData[n][3] = "true";
				finishedPlayers ++;


			} else if (Guess < Target) {
				System.out.println("Too Low! Guess again!");
			} else if (Guess > Target) {
				System.out.println("Too High! Guess again!");
			}
			}
		 catch(Exception e) {
			System.out.println("Your entry: \"" + enteredString + "\" is invalid...That unfortunately counts as your turn");
			int Attempts = Integer.parseInt(PlayerData[n][2]);
			Attempts ++;
			PlayerData[n][2] = Integer.toString(Attempts);
			GuessComp.add("invalid");
		}
		}

		else if (PlayerData[n][3] == "true") {
			GuessComp.add(Integer.toString(0));
		}
		n++;
		if (n == (PlayerCount)){
				n = 0;
		}

	} while (finishedPlayers < PlayerCount);



		System.out.println("\nCongratulations! Here is the summary: \n");

		for (int z = 0; z < PlayerCount; z++) {
			if (Integer.parseInt(PlayerData[z][2]) == 1) {
				System.out.println("YOU ARE A BEAST " + PlayerData[z][0] + "! You got it on your first try!");
			} else {
				System.out.print(PlayerData[z][0] + " it only took you " + PlayerData[z][2] + " tries!");
				System.out.print(" The wrong guesses were: ");
				for(int p = 0; p < (Integer.parseInt(PlayerData[z][2])-1); p++) {
					int index = z + p*PlayerCount;
					String wrongAttempt = GuessComp.get(index);
					System.out.print(wrongAttempt + ",");


				}
				System.out.println(".");
			}
		}

		System.out.println("\nScoreboard in order from first place to last place: ");

		Arrays.sort(PlayerData, (a,b) -> Integer.compare(Integer.parseInt(a[2]), (Integer.parseInt(b[2]))));

		for (int q = 0; q < PlayerCount; q++) {
			System.out.println(PlayerData[q][0]);
		}
		} catch(Exception e) {
			System.out.println("Your entry is invalid...Please try again");
			entryError = true;
		}

	} while (entryError == true);

	boolean ReplayError = false;

	do {
		try {

		Scanner myScanner = new Scanner(System.in);
		System.out.println("\nWould you like to play again?");
		System.out.println("Enter 1 for yes, any other number terminate game.");
		String PlayAgain = myScanner.next();
		int PA = Integer.parseInt(PlayAgain.trim());
		ReplayError = false;



		if (PA == 1) {
			System.out.println("Yay! Let's Play again!");
			PlayGame = 1;
			ReplayError = false;
		}	else if (PA != 1 ) {
			System.out.println("Thanks for Playing! Have a good day.");
			PlayGame = 0;
			ReplayError = false;

		}
	} catch(Exception e) {
		System.out.println("Your entry is invalid... Please try again");
		ReplayError = true;
	}


	} while (ReplayError == true);
		} while (PlayGame == 1);

	}


	static int getAnLowerBound(String Name) {
		int LowerBound = 0;
		Scanner myScanner = new Scanner(System.in);
		boolean numberError = false;
		String enteredString = "";

	do {
		try {
			System.out.print(Name + ", please enter an integer as your lower bound: ");
			enteredString = myScanner.next();
			LowerBound = Integer.parseInt(enteredString.trim());
			numberError = false;
		} catch(Exception e) {
			System.out.println("Your entry: \"" + enteredString + "\" is invalid... Please try again");
			numberError = true;
		}

	} while (numberError == true );


		System.out.print("That was a valid Number.");
		System.out.println(" You entered " + LowerBound + " as your lower bound!");

		return LowerBound;
	}

	static int getAHigherBound(String Name, int LB) {
		int HigherBound = 0;
		Scanner myScanner = new Scanner(System.in);
		boolean numberError = false;
		String enteredString = "";

	do {
		try {
			System.out.print(Name + ", please enter a larger integer as your higher bound to set difficulty: ");
			enteredString = myScanner.next();
			HigherBound = Integer.parseInt(enteredString.trim());
			numberError = false;
		} catch(Exception e) {
			System.out.println("Your entry: \"" + enteredString + "\" is invalid...Please try again");
			numberError = true;
		}
		if (HigherBound <= LB) {
			System.out.println("Your higher bound must be greater than your lower bound!!");
			numberError = true;
		}

	} while (numberError == true );


		System.out.print("That was a valid Number.");
		System.out.println(" You entered " + HigherBound + " as your higher bound!");

		return HigherBound;
	}

	static String getRandomInt(int a, int b) {
		Random myRandNum = new Random();
		int RandNum = myRandNum.nextInt(b-a) + a;
		String FinalRandNum = Integer.toString(RandNum);

		return FinalRandNum;
	}

}
