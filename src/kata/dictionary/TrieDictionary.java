package kata.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.trie.PatriciaTrie;

public class TrieDictionary extends Dictionary {

	// create a PatriciaTrie for efficient storage of dictionary entries and
	// length
	PatriciaTrie<Object> words = new PatriciaTrie<>();
	private List<String> wordsToProcess = new LinkedList<String>();
	
	public TrieDictionary(String dictionaryPath) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(dictionaryPath), "iso-8859-1"));
			String line = br.readLine();
			while (line != null) {
				line = line.trim();
				words.put(line, line.length());
				line = br.readLine();
			}
			br.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TrieDictionary(String dictionaryPath, int length) {
		try {
			// ideally encoding would be a parameter as well
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(dictionaryPath), "iso-8859-1"));
			String line = br.readLine();
			while (line != null) {
				line = line.trim();
				
				if (line.length() < length) {
					words.put(line, line.length());
				}
				else if (line.length() == length) {
					wordsToProcess.add(line);
				}
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
	public List<String> getWordsByLength(int length) {
		List<String> result = new LinkedList<>();

		if (!words.isEmpty() && words.containsValue(length)) {
			MapIterator<String, Object> it = words.mapIterator();
			while (it.hasNext()) {
				String key = it.next();
				Integer value = (Integer) it.getValue();
				if (value == length) {
					result.add(key);
				}
			}
		}

		return result;
	}

	@Override
	public boolean contains(String tempWord) {
		return words.containsKey(tempWord);
	}

	public List<String> getWordsToProcess() {
		return wordsToProcess;
	}

	public void setWordsToProcess(List<String> wordsToProcess) {
		this.wordsToProcess = wordsToProcess;
	}

}
