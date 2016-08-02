package kata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import kata.dictionary.Dictionary;
import kata.dictionary.TrieDictionary;

/**
 * The fast implementation.
 * 
 * @author sabine
 * 
 */
public class CodeKataFast implements CodeKata {

	private Dictionary dict;
	private String outputFile;
	
	public CodeKataFast(String dictionaryPath, int length) {
		dict = new TrieDictionary(dictionaryPath, length);
	}

	public static void main(String[] args) {
		// create dictionary only with the words of size 6 and less
		long startTime = System.nanoTime();
		CodeKataFast ck = new CodeKataFast(args[0], Integer.parseInt(args[1]));
		ck.outputFile = String.format("%s.out", args[0]);
		ck.process(Integer.parseInt(args[1]));
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000; 
		System.out.println(duration);
	}

	/**
	 * Processes the dictionary for words of the given length and writes the
	 * output to the given filepath.
	 * 
	 * @param length
	 *            length of the words to be processed
	 * @param filename
	 *            filepath for the target file
	 */
	public void process(int length) {
		// retrieve a list of all tokens with the given length from the
		// dictionary
		List<String> wordsToProcess = dict.getWordsByLength(length);

		// if not empty, process it
		if (!wordsToProcess.isEmpty()) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

				for (String word : wordsToProcess) {
					for (int cut = 1; cut < length - 1; cut++) {
						String firstWord = word.substring(0, cut);
						
						// check if the first word exists... 
						if (dict.contains(firstWord)) {
							String secondWord = word.substring(cut);
							// only then check the second word
							if (dict.contains(secondWord)) {
								// if we find a match, immediately write it to the
								// output file & break (not interested in all
								// permutations for this word)
								bw.write(String.format("%s: (%s, %s)\n", word,
										firstWord, secondWord));
								break;
							}
						}
					}
				}

				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
