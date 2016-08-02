package kata.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

/**
 * Simple dictionary implementation. Stores tokens in list.
 * @author sabine
 *
 */
public class ListDictionary extends Dictionary {
	List<String> words = new LinkedList<>();

	public ListDictionary() {
		super();
	}

	public ListDictionary(String dictionaryPath) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(dictionaryPath), "iso-8859-1"));
			String line = br.readLine();
			while (line != null) {
				line = line.trim();
				words.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean contains(String word) {
		return words.contains(word);
	}

	@Override
	public List<String> getWordsByLength(int length) {
		List<String> wordsToProcess = new LinkedList<String>();
		
		for (String word: words) {
			if (word.length() == length) {
				wordsToProcess.add(word);
			}
		}
		return wordsToProcess;
	}

}
