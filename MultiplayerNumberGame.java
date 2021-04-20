import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

					String[][] PlayerData = new String[PlayerCount][10];
					for (int x = 0; x < PlayerCount; x++) {
						System.out.println("Please enter name of Player #" + (x + 1));
						String PlayerName = keyboard.next();
						PlayerData[x][0] = PlayerName;
						int LowerBound = getAnLowerBound(PlayerName);
						int HigherBound = getAHigherBound(PlayerName, LowerBound);
						PlayerData[x][1] = getRandomInt(LowerBound, HigherBound);
						PlayerData[x][2] = "0";
						PlayerData[x][3] = "false";
						PlayerData[x][4] = "1";
						PlayerData[x][5] = "0";
						PlayerData[x][6] = "0";
						PlayerData[x][7] = Integer.toString((HigherBound - LowerBound) + 1);
						PlayerData[x][8] = "0";
						PlayerData[x][9] = "0";

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
								Attempts++;
								PlayerData[n][2] = Integer.toString(Attempts);

								if (Guess == Target) {
									System.out.println("Amazing! That's the number I was thinking!");
									System.out.println("You Win! Great Job!");
									PlayerData[n][3] = "true";
									finishedPlayers++;

								} else if (Guess < Target) {
									System.out.println("Too Low! Guess again!");
									PlayerData[n][6] = Integer.toString(Integer.parseInt(PlayerData[n][6]) + 1);
								} else if (Guess > Target) {
									System.out.println("Too High! Guess again!");
									PlayerData[n][5] = Integer.toString(Integer.parseInt(PlayerData[n][5]) + 1);
								}
							} catch (Exception e) {
								System.out.println("Your entry: \"" + enteredString
										+ "\" is invalid...That unfortunately counts as your turn");
								int Attempts = Integer.parseInt(PlayerData[n][2]);
								Attempts++;
								PlayerData[n][2] = Integer.toString(Attempts);
								GuessComp.add("invalid");
							}
						}

						else if (PlayerData[n][3] == "true") {
							GuessComp.add(Integer.toString(0));
						}
						n++;
						if (n == (PlayerCount)) {
							n = 0;
						}

					} while (finishedPlayers < PlayerCount);

					Arrays.sort(PlayerData,
							(a, b) -> Integer.compare(Integer.parseInt(a[2]), (Integer.parseInt(b[2]))));
					PlayerData[0][8] = "1";
					PlayerData[PlayerCount - 1][9] = "1";

					BufferedReader br = null;
					BufferedWriter bw = null;

					String PreviousTRC = null;
					int NewTRC = 0;

					try {
						br = new BufferedReader(
								new FileReader("/Users/zabeerrahman/Desktop/MULTIPLAYER/TotalRoundCount.txt"));
						PreviousTRC = (br.readLine());

					} catch (IOException e) {
						e.printStackTrace();
						System.out.print(e.getMessage());
					}

					NewTRC = Integer.parseInt(PreviousTRC) + 1;

					try {
						File outfile = new File("/Users/zabeerrahman/Desktop/MULTIPLAYER/TotalRoundCount.txt");
						if (!outfile.exists()) {
							outfile.createNewFile();
						}
						FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
						bw = new BufferedWriter(fw);
						bw.write(Integer.toString(NewTRC));
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println(
							"\nLifetime Statistics for each Round available in 'MULTIPLAYER' Folder on Desktop. This was Round #"
									+ NewTRC);
					System.out.println("Individual lifetime player stats will be displayed at end of Round");
					System.out.println(
							"\nCongratulations! Here is the scoreboard in order from first place to last place: ");

					for (int q = 0; q < PlayerCount; q++) {
						System.out.println(PlayerData[q][0]);
					}

					try {
						String content = "This is the LOG File for Round #";
						File outfile = new File("/Users/zabeerrahman/Desktop/MULTIPLAYER/Round" + NewTRC + ".txt");
						if (!outfile.exists()) {
							outfile.createNewFile();
						}
						FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
						bw = new BufferedWriter(fw);
						bw.write(content);
						bw.write(Integer.toString(NewTRC));
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println("\nHere is the summary of round #" + NewTRC + ":");

					for (int z = 0; z < PlayerCount; z++) {
						if (Integer.parseInt(PlayerData[z][2]) == 1) {
							System.out
									.println("YOU ARE A BEAST " + PlayerData[z][0] + "! You got it on your first try!");
							try {
								String content2 = " came in place ";
								String content3 = "\n";
								String content4 = " made the guess: ";

								File outfile = new File(
										"/Users/zabeerrahman/Desktop/MULTIPLAYER/Round" + NewTRC + ".txt");
								if (!outfile.exists()) {
									outfile.createNewFile();
								}
								FileWriter fw = new FileWriter(outfile.getAbsoluteFile(), true);
								bw = new BufferedWriter(fw);
								bw.write(content3);
								bw.write(PlayerData[z][0]);
								bw.write(content2);
								bw.write(Integer.toString(z + 1));
								bw.write(content3);
								bw.write(PlayerData[z][0]);
								bw.write(content4);
								bw.write(PlayerData[z][1]);
								bw.flush();
								bw.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							System.out.print(PlayerData[z][0] + " it only took you " + PlayerData[z][2] + " tries!");
							System.out.print(" The wrong guesses were: ");

							try {
								String content2 = " came in place ";
								String content3 = "\n";
								String content4 = " made the guesses: ";

								File outfile = new File(
										"/Users/zabeerrahman/Desktop/MULTIPLAYER/Round" + NewTRC + ".txt");
								if (!outfile.exists()) {
									outfile.createNewFile();
								}
								FileWriter fw = new FileWriter(outfile.getAbsoluteFile(), true);
								bw = new BufferedWriter(fw);
								bw.write(content3);
								bw.write(PlayerData[z][0]);
								bw.write(content2);
								bw.write(Integer.toString(z + 1));
								bw.write(content3);
								bw.write(PlayerData[z][0]);
								bw.write(content4);

							} catch (IOException e) {
								e.printStackTrace();
							}

							for (int p = 0; p < (Integer.parseInt(PlayerData[z][2]) - 1); p++) {
								int index = z + p * PlayerCount;
								String content5 = ", ";
								String wrongAttempt = GuessComp.get(index);
								System.out.print(wrongAttempt + ",");
								File outfile = new File(
										"/Users/zabeerrahman/Desktop/MULTIPLAYER/Round" + NewTRC + ".txt");
								if (!outfile.exists()) {
									outfile.createNewFile();
								}
								FileWriter fw = new FileWriter(outfile.getAbsoluteFile(), true);
								bw = new BufferedWriter(fw);
								bw.write(wrongAttempt);
								bw.write(content5);
								bw.flush();
								bw.close();

							}
							System.out.println(".");
							File outfile = new File("/Users/zabeerrahman/Desktop/MULTIPLAYER/Round" + NewTRC + ".txt");
							if (!outfile.exists()) {
								outfile.createNewFile();
							}
							FileWriter fw = new FileWriter(outfile.getAbsoluteFile(), true);
							bw = new BufferedWriter(fw);
							bw.write(PlayerData[z][1]);
							bw.flush();
							bw.close();
						}

						try {
							String[] PlayerTotals = new String[8];
							String[] ThisRound = new String[8];
							String[] NewPlayerSum = new String[8];
							ThisRound[0] = PlayerData[z][4];
							ThisRound[1] = PlayerData[z][2];
							ThisRound[2] = PlayerData[z][5];
							ThisRound[3] = PlayerData[z][6];
							ThisRound[4] = PlayerData[z][7];
							ThisRound[5] = PlayerData[z][8];
							ThisRound[6] = PlayerData[z][9];

							br = new BufferedReader(new FileReader(
									"/Users/zabeerrahman/Desktop/MULTIPLAYER/SCORECARDS/" + PlayerData[z][0] + ".txt"));
							String st;
							while ((st = br.readLine()) != null) {
								PlayerTotals = st.split("-");
							}
							for (int g = 0; g < PlayerTotals.length; g++) {
								int holder = Integer.parseInt(ThisRound[g]) + Integer.parseInt(PlayerTotals[g]);
								NewPlayerSum[g] = Integer.toString(holder);
							}

							File outfile = new File(
									"/Users/zabeerrahman/Desktop/MULTIPLAYER/SCORECARDS/" + PlayerData[z][0] + ".txt");
							if (!outfile.exists()) {
								outfile.createNewFile();
							}
							FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
							bw = new BufferedWriter(fw);
							if (!outfile.exists()) {
								outfile.createNewFile();
							}
							for (int h = 0; h < 7; h++) {
								bw.write(NewPlayerSum[h]);
								bw.write("-");
							}
							bw.flush();
							bw.close();

							int WinRate = (100 * (Integer.parseInt(NewPlayerSum[0]))
									/ (Integer.parseInt(NewPlayerSum[1])));
							int TooHighRate = (100 * (Integer.parseInt(NewPlayerSum[2]))
									/ (Integer.parseInt(NewPlayerSum[1])));
							int TooLowRate = (100 * (Integer.parseInt(NewPlayerSum[3]))
									/ (Integer.parseInt(NewPlayerSum[1])));
							int MeanRange = (Integer.parseInt(NewPlayerSum[4]) / (Integer.parseInt(NewPlayerSum[1])));

							System.out.print("\n" + PlayerData[z][0] + ", here are your lifetime stats:");
							System.out.print(" WINRATE(%):");
							System.out.print(WinRate);
							System.out.print(" TOO HIGH GUESS RATE (%):");
							System.out.print(TooHighRate);
							System.out.print(" TOO LOW GUESS RATE (%):");
							System.out.print(TooLowRate);
							System.out.print(" AVERAGE DIFFICULTY(mean range between bounds):");
							System.out.print(MeanRange);
							System.out.print(" 1st Place Wins:");
							System.out.print(NewPlayerSum[5]);
							System.out.print(" Last Place Losses:");
							System.out.println(NewPlayerSum[6] + "\n");

						}

						catch (IOException e) {
							e.printStackTrace();
							System.out.print(e.getMessage());
						}

					}

				} catch (IOException e) {
					System.out.println("Your entry is invalid...Please try again");
					e.printStackTrace();
					System.out.print(e.getMessage());
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
					} else if (PA != 1) {
						System.out.println("Thanks for Playing! Have a good day.");
						PlayGame = 0;
						ReplayError = false;

					}
				} catch (Exception e) {
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
			} catch (Exception e) {
				System.out.println("Your entry: \"" + enteredString + "\" is invalid... Please try again");
				numberError = true;
			}

		} while (numberError == true);

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
			} catch (Exception e) {
				System.out.println("Your entry: \"" + enteredString + "\" is invalid...Please try again");
				numberError = true;
			}
			if (HigherBound <= LB) {
				System.out.println("Your higher bound must be greater than your lower bound!!");
				numberError = true;
			}

		} while (numberError == true);

		System.out.print("That was a valid Number.");
		System.out.println(" You entered " + HigherBound + " as your higher bound!");

		return HigherBound;
	}

	static String getRandomInt(int a, int b) {
		Random myRandNum = new Random();
		int RandNum = myRandNum.nextInt(b - a) + a;
		String FinalRandNum = Integer.toString(RandNum);

		return FinalRandNum;
	}

}
