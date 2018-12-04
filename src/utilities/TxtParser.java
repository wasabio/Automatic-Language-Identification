package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import structure.Language;

public class TxtParser {

	/**
	 * To format a String to a specific format without any spaces, accents, cedillas.
	 * All the characters are converted to lowercases.
	 * @param s The String to format.
	 * @return	String formatted.
	 */
	public static String format(String s) 
	{
		//Remove accents and cedillas
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s.replaceAll("[^a-zA-Z]", "").toLowerCase();
	}
	
	/**
	 * To write an output file.
	 * @param fileName The name of the file to create. Must include the extension.
	 * @param content The content of the file.
	 */
	public static void write(String fileName, String content) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			
			out.write(content);
			out.newLine();

            out.close();
        } catch (IOException e) {
        	System.out.println(e);
        }
	}
	
	/**
	 * To read a text file, format it and put it into a String.
	 * @param folderName The folder name.
	 * @param fileName The file name.
	 * @return A String containing all the text from the text file.
	 * @throws IOException  Exception
	 */
	public static String readFile(String folderName, String fileName) throws IOException
	{	
		String text = new String(Files.readAllBytes(Paths.get(folderName, fileName)), StandardCharsets.UTF_8);
		return format(text);
	}

	public static void write(String name, HashMap<Language, HashMap<String, Double>> ngram) {
		for(Language language : Language.values())
		{
			HashMap<String, Double> alphabet = ngram.get(language);
			String content = "";
			
			for(Entry<String, Double> symbol : alphabet.entrySet())
				content += "P(" + symbol.getKey() + ") = " + symbol.getValue() + "\n";
			
			write(Paths.get("outputNgrams", name + language + ".txt").toString(), content);
		}
	}

	public static ArrayList<String> readSentences(String folderName, String fileName) throws IOException
	{
		return (ArrayList<String>) Files.readAllLines(Paths.get(folderName, fileName));
	}

	public static ArrayList<String> format(ArrayList<String> sentences) {
		ArrayList<String> formattedSentences = new ArrayList<String>();
		
		for(String sentence : sentences)
			formattedSentences.add(format(sentence));

		return formattedSentences;
	}
}
