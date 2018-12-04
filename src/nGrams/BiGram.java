package nGrams;

import java.util.HashMap;
import structure.Language;
import utilities.TxtParser;

public class BiGram extends NGram {
	
	public BiGram()
	{
		for(Language language : Language.values())
		{
			HashMap<String, Double> alphabet = new HashMap<String, Double>();
			for(int ascii = 'a'; ascii <= 'z'; ascii++)
			{
				for(int ascii2 = 'a'; ascii2 <= 'z'; ascii2++)
				{
					String symbol = ((char) ascii + "" + (char) ascii2);
					alphabet.put(symbol, 0.0);
				}
			}
			model.put(language, alphabet);
		}
	}

	@Override
	protected void trainLanguage(String text, Language language) {
		HashMap<String, Double> alphabet = model.get(language);
		
		//For each 2 characters in the text
		for(int i = 0; i < text.length() - 1; i++)
		{
			String symbol = Character.toString(text.charAt(i)) + Character.toString(text.charAt(i + 1));
			alphabet.put(symbol, alphabet.get(symbol) + 1);
		}
	}

	@Override
	public String evaluate(String sentence) {
		String result = "\n----------------\nBIGRAM MODEL:\n";
		String formattedSentence = TxtParser.format(sentence);
		HashMap<String, Double> languageProbs = new HashMap<String, Double>();
		
		//Initialize probability of each language
		for(Language language : Language.values())
			languageProbs.put(language.getKey(), 0.0);
			
		//For each 2 characters in the text
		for(int i = 0; i < formattedSentence.length() - 1; i++)
		{
			String symbol = Character.toString(formattedSentence.charAt(i)) + Character.toString(formattedSentence.charAt(i + 1));
			result += "\nBIGRAM: " + symbol + "\n";
			result += calculateLanguageScores(symbol, languageProbs);
		}
				
		return result + "\nAccording to the bigram model, the sentence is in " + getBestLanguage(languageProbs);
	}
}
