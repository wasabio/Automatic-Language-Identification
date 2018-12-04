package nGrams;

import java.util.HashMap;
import structure.Language;
import utilities.TxtParser;

public class UniGram extends NGram {
	
	public UniGram()
	{		
		for(Language language : Language.values())
		{
			HashMap<String, Double> alphabet = new HashMap<String, Double>();
			for(int ascii = 'a'; ascii <= 'z'; ascii++)
			{
				String symbol = ((char) ascii + "");
				alphabet.put(symbol, 0.0);
			}
			model.put(language, alphabet);
		}
	}

	public String evaluate(String sentence) {
		String result = sentence + "\nUNIGRAM MODEL:\n";
		String formattedSentence = TxtParser.format(sentence);
		HashMap<String, Double> languageProbs = new HashMap<String, Double>();
		
		//Initialize probability of each language
		for(Language language : Language.values())
			languageProbs.put(language.getKey(), 0.0);
		
		//For each character in the sentence
		for (char c : formattedSentence.toCharArray()) {
			String symbol = c + "";
			result += "\nUNIGRAM: " + symbol + "\n";
			result += calculateLanguageScores(symbol, languageProbs);
		}
		
		return result + "\nAccording to the unigram model, the sentence is in " + getBestLanguage(languageProbs);
	}

	@Override
	protected void trainLanguage(String text, Language language) {
		HashMap<String, Double> alphabet = model.get(language);
		
		//For each character in the text
		for (char c : text.toCharArray()) {
			String symbol = c + "";
			alphabet.put(symbol, alphabet.get(symbol) + 1);
		}
	}
}
