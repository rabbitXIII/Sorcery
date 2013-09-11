package rgopal;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFileChooser;

import resources.Card;
import resources.Deck;

public class PlayGround {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub
		
		Card darkConfidant = Card.CardBuilder.newBuilderFromId(370413).build();
		System.out.println(darkConfidant);
		
		Card tarm = Card.CardBuilder.newBuilderFromName("Tarmogoyf").build();
		System.out.println(tarm);
/*		JFileChooser fChoose = new JFileChooser();
		int returnVal = fChoose.showOpenDialog(null);	
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fChoose.getSelectedFile();
            Deck d = Deck.loadDeckFromFile(file.getName());
        }
		*/
		
		// looks like the newBuilderFromName doesn't work if the card name isn't unique to any other card
	}

}
