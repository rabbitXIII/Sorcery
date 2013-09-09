package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Deck {
	
	/* the cards held in the main deck
	 * 
	 * 
	 */
	ArrayList<Card> mainDeck;
	
	/* The cards held in the sideboard
	 * 
	 */
	ArrayList<Card> sideboard;
	
	private Deck(int initialSize, int initialSideBoard) {
		mainDeck = new ArrayList<Card>(initialSize);
		sideboard = new ArrayList<Card>(initialSideBoard);
	}

	public Deck createEmptyDeck() {
		return new Deck(0, 0);
	}
	
	public static Deck loadDeckFromFile(String fileName) {
		/* This should check the extension of the filename and decide which method to use */
		return generateFromTxtFile(fileName);
	}
	
	private static Deck generateFromTxtFile(String fileName) {
		try {
			// defaults to reading the txt files downloaded from wizards
			// should add support to read mwDeck and other file types as well. and also ability to auto-detect data formatting
			
			FileReader input = new FileReader(fileName);
			BufferedReader buffReader = new BufferedReader(input);
			String myLine = null;
			
			boolean sideboarding = false;
			int main = 0, side = 0;
			while ( (myLine = buffReader.readLine()) != null)	{ 
				if(myLine.trim().isEmpty())
					sideboarding = true;
				else if(!sideboarding)
					main++;
				else
					side++;
				
			}
			System.out.println("Main unique: " + main + " side unique: " + side);
			return createDeckFromMaps(null,null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new Deck(0,0);
		} catch (IOException e) {
			e.printStackTrace();
			return new Deck(0,0);
		}
	}
	
	public static Deck loadDeckFromURL(URL url) {
		try {
			Document doc = Jsoup.parse(url, 1000);
			System.out.println(doc);
			return createDeckFromMaps(null,null);
		} catch (IOException e) {
			e.printStackTrace();
			return new Deck(0,0);
		}
		
		
	}
	
	private static Deck createDeckFromMaps(HashMap<Card, Integer> mainDeck, HashMap<Card,Integer> sideBoard) {
		// initialize the deck
		Deck loadedDeck = new Deck(0,0);
		
		// one by one load the cards into the deck
		return loadedDeck;
	}
	
}
