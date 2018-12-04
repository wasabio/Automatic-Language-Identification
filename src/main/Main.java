package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import nGrams.BiGram;
import nGrams.NGram;
import nGrams.UniGram;
import structure.Language;
import utilities.TxtParser;

public class Main {
	public static final double SMOOTHING = 0.5;

	public static void main(String[] args) throws IOException {
		HashMap<Language, String> texts = new HashMap<Language, String>();
		ArrayList<String> sentences = new ArrayList<String>();
		NGram unigram = new UniGram();
		NGram bigram = new BiGram();
				
		// Reading text files
		for(Language language : Language.values())
			texts.put(language, TxtParser.readFile("input", "train" + language + ".txt"));
				
		sentences = TxtParser.readSentences("input", "sentences.txt");		

		// Training
		unigram.train(texts);
		bigram.train(texts);
		
		// Evaluating
		int i = 1;
		for(String sentence : sentences)
		{
			String output = unigram.evaluate(sentence);
			output += bigram.evaluate(sentence);
			TxtParser.write("out" + i++ + ".txt", output);
		}
		
		unigram.write();
		bigram.write();
	}
}
