package nGrams;

import java.util.HashMap;
import java.util.Map.Entry;
import main.Main;
import structure.Language;
import utilities.TxtParser;

public abstract class NGram {
	protected HashMap<Language, HashMap<String, Double>> model = new HashMap<Language, HashMap<String, Double>>();
	
	protected abstract void trainLanguage(String text, Language language);
	public abstract String evaluate(String sentence);
	
	public void train(HashMap<Language, String> texts) {
		//For each language
		for(Language language : Language.values())
		{
			String text = texts.get(language);
			HashMap<String, Double> alphabet = model.get(language);

			trainLanguage(text, language);

			//Computation of the probability
			for(Entry<String, Double> symbol : alphabet.entrySet())
			{
				double value = symbol.getValue();
				double total = text.length();
				double smoothing = Main.SMOOTHING;
				
				symbol.setValue( Math.log10( ( value + smoothing ) / ( total + ( alphabet.size() * smoothing ) ) ) );
			}
		}
	}
	
	public void write()
	{
		TxtParser.write(this.getClass().getSimpleName().toLowerCase(), model);
	}
	
	protected String calculateLanguageScores(String symbol, HashMap<String, Double> languageProbs)
	{
		String result = "";
		
		//For each language, we calculate the score
		for(Language language : Language.values())
		{
			String key = language.getKey();
			String value = language.getValue();
			
			double symbolProb = model.get(language).get(symbol);
			double sentenceProb = languageProbs.get(language.getKey()) + symbolProb;
			
			languageProbs.put(key, sentenceProb);
			result += value + ": P(" + symbol + ") = " + symbolProb + " ==> log prob of sentence so far: " + sentenceProb + "\n";
		}
		
		return result;
	}
	
	protected String getBestLanguage(HashMap<String, Double> languageProbs)
	{
		Entry<String, Double> max = null;
		
		for (Entry<String, Double> languageProb : languageProbs.entrySet())
		{
		    if (max == null || languageProb.getValue().compareTo(max.getValue()) > 0)
		    	max = languageProb;
		}
		
		for(Language l : Language.values())
			if(max.getKey().equals(l.getKey()))	return l.getLowerCaseValue();
		
		return null;
	}
}
