package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
			HashMap<String, Integer> main = new HashMap<String, Integer>();
			HashMap<String, Integer> side = new HashMap<String, Integer>();
			
			boolean sideboarding = false;
			while ( (myLine = buffReader.readLine()) != null)	{ 
				if(myLine.trim().isEmpty())
					sideboarding = true;
				else {
					Integer numberOfCard = Integer.parseInt(myLine.substring(0,myLine.indexOf(' ')));
					String cardName = myLine.substring(myLine.indexOf(' '));
					if(!sideboarding) {
						main.put(cardName, numberOfCard);
					}
					else {
						side.put(cardName, numberOfCard);
					}
				}
			}
			buffReader.close();
			return createDeckFromMaps(main,side);
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
	
	public void addCardMain(Card newCard) {
		mainDeck.add(newCard);
	}
	
	public void addCardSide(Card newCard) {
		sideboard.add(newCard);
	}
	
	private static Deck createDeckFromMaps(HashMap<String, Integer> main, HashMap<String ,Integer> side) {
		// initialize the deck
		Deck loadedDeck = new Deck(main.size(),side.size());
		for(Map.Entry<String, Integer> entry : main.entrySet()) {
			Card.CardBuilder cBuild = Card.CardBuilder.newBuilderFromName(entry.getKey());
			for( int i = 0; i < entry.getValue(); i ++ ){
				Card toAdd = cBuild.build();
				loadedDeck.addCardMain(toAdd);
				System.out.println(entry.getKey() + " turning into " + toAdd);
			}
		}
			
		for(Map.Entry<String, Integer> entry : side.entrySet()) {
			Card.CardBuilder cBuild = Card.CardBuilder.newBuilderFromName(entry.getKey());
			for( int i = 0; i < entry.getValue(); i ++ ){
				Card toAdd = cBuild.build();
				loadedDeck.addCardSide(toAdd);
			}
		}
		// one by one load the cards into the deck
		return loadedDeck;
	}
	
}
