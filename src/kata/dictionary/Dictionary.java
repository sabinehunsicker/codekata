package kata.dictionary;

import java.util.List;

public abstract class Dictionary {
	
	public Dictionary(String dictionaryPath) {};
	
	public Dictionary() {};

	public abstract boolean contains(String tempWord);

	public abstract List<String> getWordsByLength(int length);

}
