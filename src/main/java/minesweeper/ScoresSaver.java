package minesweeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScoresSaver implements WriteToFile{
    private String file;
	
	public ScoresSaver(String file) {
		this.file = file;
	}
	
	public ScoresSaver() {}

	@Override
	public void writeToFile(double score) throws IOException {
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(score + " sek\n");
			fw.flush();
			fw.close();
		}
		catch (IOException error) {
			error.printStackTrace();
		}
	}
	
	
	@Override
	public double readFromFile() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));
		double bestScore = Double.MAX_VALUE;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String scoreString = line.split("sek")[0];
			double score = Double.parseDouble(scoreString);
			if (score < bestScore) {
				bestScore = score;
			}
		}
		scanner.close();
		return bestScore;
	}

}
