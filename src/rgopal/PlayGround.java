package rgopal;
import java.net.MalformedURLException;
import java.net.URL;

import resources.Card;

public class PlayGround {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		
		Card.CardBuilder builder = Card.CardBuilder.newBuilderFromId(370413);
		builder.queryForInfo();
		System.out.println(builder.getPageContent());
	}

}