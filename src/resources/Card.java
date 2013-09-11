package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.EnumSet;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Card {
	// Should make a .. something for card types so that an individual card can have many types
	public enum Type {
		ARTIFACT, BASIC, CREATURE, ENCHANTMENT, INSTANT, LAND, LEGENDARY, 
		ONGOING, PHENOMENON, PLANE, PLANESWALKER, SCHEME, SNOW, SORCERY, 
		TRIBAL, VANGUARD, WORLD;
	}

	public enum Expansion {
		MMA;
	}

	private final URL imageURL;
	private final String name;
	private final String rulesText;
	private final String flavorText;
	private final Expansion cardExpansion;
	private final Integer multiverseId;
	private final EnumSet<Type> types;

	private static final String queryString = "http://gatherer.wizards.com/Pages/Search/Default.aspx?";
	private static final String multiverseIdQueryString = "http://gatherer.wizards.com/pages/card/Details.aspx?multiverseid=";

	public static class CardBuilder {
		
		private enum FieldID {
			NAME("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow"),
			TYPE("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_typeRow"),
			TEXT("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_textRow"),
			FLAVOR("#ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_FlavorText");
			private String id;
			private FieldID(String id) {
				this.id = id;
			}
			
			@Override
			public String toString() {
				return id;
			}
		}
		
		private URL imageURL;
		private String name;
		private String rulesText;
		private String flavorText;
		private Expansion cardExpansion;
		private URL cardURL;
		private Integer multiverseId;
		private EnumSet<Type> types;
		
		private Document doc;
		
		private CardBuilder(String cardName ) {
			this.name = cardName;
		}
		
		private CardBuilder(int multiverseId) {
			this.multiverseId = multiverseId;
		}
		
		public static CardBuilder newBuilderFromId(int multiverseId) {
			return new CardBuilder(multiverseId);
		}
		
		public static CardBuilder newBuilderFromName(String name) {
			return new CardBuilder(name);
		}

		public CardBuilder imageURL(URL imageURL) {
			try {
				this.imageURL = new URL(imageURL.toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return this;
		}

		public CardBuilder rulesText(String rulesText) {
			this.rulesText = rulesText;
			return this;
		}

		public CardBuilder flavorText(String flavorText) {
			this.flavorText = flavorText;
			return this;
		}

		public CardBuilder cardExpansion(Expansion cardExpansion) {
			this.cardExpansion = cardExpansion;
			return this;
		}

		public CardBuilder cardURL(URL cardURL) {
			this.cardURL = cardURL;
			return this;
		}
		
		public CardBuilder multiverseId(int id) {
			this.multiverseId = id;
			return this;
		}
		
		public CardBuilder type(Type cardType) {
			this.types.add(cardType);
			return this;
		}

		public URL getCardURL() {
			if (cardURL == null) {
				try {
					cardURL = new URL(buildCardURL());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			if (cardURL != null)
				return cardURL;
			else
				return null;

		}

		private void queryForInfo() {
			if( this.doc == null){
				try {
					/* Instead of querying gatherer, use the cards.xml from woogerworks / cockatrice client. 
					 * the xml file is in the main directory
					 * 
					 */
					URL url = getCardURL();
					doc = Jsoup.parse(url,5000);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void parseDocForInfo() {
			queryForInfo();
			// name
			System.out.println(doc);
			this.name = this.doc.select(FieldID.NAME.toString()).select(".value").text();
			this.rulesText = this.doc.select(FieldID.TEXT.toString()).select(".value").text();
			this.flavorText = this.doc.select(FieldID.FLAVOR.toString()).select(".value").text();
			
			// hardcoded as CREATURE so that toString works from Card
			this.types = EnumSet.of(Type.CREATURE);
				
		}

		private String buildCardURL() {
			if( this.multiverseId != null ) 
				return multiverseIdQueryString + this.multiverseId;
			
			StringBuffer cardNameQuery = new StringBuffer(queryString);
			cardNameQuery.append("name=");
			for (String namePart : name.split(" ")) {
				cardNameQuery.append(namePart);
			}

			return cardNameQuery.toString();

		}

		public Card build() {
			parseDocForInfo();
			return new Card(this);
		}

	}
	
	@Override
	public String toString() {
		//TODO create a toString method for the Card
		return this.name + " " + this.types.toString() + " " + this.rulesText;
		
	}
	
	private Card(CardBuilder cb) {
		this.imageURL = cb.imageURL;
		this.name = cb.name;
		this.rulesText = cb.rulesText;
		this.flavorText = cb.flavorText;
		this.cardExpansion = cb.cardExpansion;
		this.multiverseId = cb.multiverseId;
		this.types = cb.types;
	}
	

}
