package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Card {
	// Should make a .. something for card types so that an individual card can have many types
	public enum Type {
		ARTIFACT, BASIC, CREATURE, ENCHANTMENT, INSTANT, LAND, LEGENDARY, 
		ONGOING, PHENOMENON, PLANE, PLANESWALKER, SCHEME, SNOW, SORCERY, 
		TRIBAL, VANGUARD, WORLD;
	}

	public enum Expansion {

	}

	private final URL imageURL;
	private final String name;
	private final String rulesText;
	private final String flavorText;
	private final Expansion cardExpansion;
	private final int multiverseId;

	private static final String queryString = "http://gatherer.wizards.com/Pages/Search/Default.aspx?";

	public static class CardBuilder {
		private URL imageURL;
		private String name;
		private String rulesText;
		private String flavorText;
		private Expansion cardExpansion;
		private URL cardURL;
		private Integer multiverseId;
		private String pageContents;
		
		private CardBuilder(String cardName ) {
			this.name = cardName;
		}
		
		private CardBuilder(int multiverseId) {
			this.multiverseId = multiverseId;
		}
		
		public static CardBuilder newBuilderFromId(int multiverseId) {
			return new CardBuilder(multiverseId);
		}
		
		public static CardBuilder newBuilerFromName(String name) {
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

		/*
		 * This method requires that the multiverse id or card name are submitted
		 * 
		 */
		public void queryForInfo() {
			if( this.pageContents == null ){
				try {
					URL url = getCardURL();
					URLConnection connection = url.openConnection();
					BufferedReader in = new BufferedReader(new InputStreamReader(
							connection.getInputStream()));
					String inputLine;
					StringBuffer pageLine = new StringBuffer();
					while ((inputLine = in.readLine()) != null)
						pageLine.append(inputLine);
					this.pageContents = pageLine.toString();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public String getPageContent() {
			return this.pageContents;
		}

		private String buildCardURL() {
			if( this.multiverseId != null ) {
				return "http://gatherer.wizards.com/pages/card/Details.aspx?multiverseid=" + this.multiverseId;
			}
			StringBuffer cardNameQuery = new StringBuffer(queryString);
			cardNameQuery.append("name=");
			for (String namePart : name.split(" ")) {
				cardNameQuery.append(namePart);
			}

			return cardNameQuery.toString();

		}

		public Card build() {
			return new Card(this);
		}

	}
	
	@Override
	public String toString() {
		//TODO create a toString method for the Card
		return "";
		
	}
	
	private Card(CardBuilder cb) {
		this.imageURL = cb.imageURL;
		this.name = cb.name;
		this.rulesText = cb.rulesText;
		this.flavorText = cb.flavorText;
		this.cardExpansion = cb.cardExpansion;
		this.multiverseId = cb.multiverseId;
	}

}
