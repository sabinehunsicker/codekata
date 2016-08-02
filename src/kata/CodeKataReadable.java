package kata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import kata.dictionary.Dictionary;
import kata.dictionary.ListDictionary;

/**
 * The readable/extendible implementation.
 * 
 * @author sabine
 *
 */
public class CodeKataReadable implements CodeKata {

	private Dictionary dict;
	private static HashMap<String, List<String>> concatWords = new HashMap<>();

	public CodeKataReadable(String dictionaryPath) {
		setDict(new ListDictionary(dictionaryPath));
	}

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		CodeKataReadable ck = new CodeKataReadable(args[0]);
		ck.process(Integer
				.parseInt(args[1]));
		ck.write(getConcatWords(), String.format("%s.out", args[0]));
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000; 
		System.out.println(duration);
	}

	/**
	 * Writes result list to target file.
	 * @param result HashMap of words & their concatenated parts
	 * @param filename filepath for the target file
	 */
	private void write(HashMap<String, List<String>> result, String filename) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

			for (String word : result.keySet()) {
				bw.write(String.format("%s: %s\n", word, result.get(word)));
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Processes the dictionary for words of the given length and returns a
	 * HashMap with the results.
	 * @param length length of the words to be processed
	 * @return HashMap with results
	 */
	public void process(int length) {
		//the result HashMap contains the word and its parts
		HashMap<String, List<String>> result = new HashMap<>();
		// retrieve a list of all tokens with the given length from the dictionary
		List<String> wordsToProcess = getDict().getWordsByLength(length);
		
		// if not empty, process it
		if (!wordsToProcess.isEmpty()) {
			for (String word : wordsToProcess) {
				for (int cut = 1; cut < length - 1; cut++) {
					String firstWord = word.substring(0, cut);
					String secondWord = word.substring(cut);

					if (dict.contains(firstWord) && dict.contains(secondWord)) {
						// if we find a match, we add it to the results
						List<String> parts = new LinkedList<String>();
						parts.add(firstWord);
						parts.add(secondWord);
						result.put(word, parts);
						break;
					}
				}
			}
		}
		
		// result list is returned and can be used for further processing
		setConcatWords(result);
	}

	public Dictionary getDict() {
		return dict;
	}

	public void setDict(Dictionary dictionary) {
		this.dict = dictionary;
	}

	public static HashMap<String, List<String>> getConcatWords() {
		return concatWords;
	}

	public static void setConcatWords(HashMap<String, List<String>> concatWords) {
		CodeKataReadable.concatWords = concatWords;
	}
}
