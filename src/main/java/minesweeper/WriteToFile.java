package minesweeper;


import java.io.FileNotFoundException;
import java.io.IOException;


public interface WriteToFile {
    void writeToFile(double score) throws IOException;
	double readFromFile() throws FileNotFoundException;
}
