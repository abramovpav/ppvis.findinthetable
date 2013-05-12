package by.bsuir.iit.abramov.ppvis.findinthetable;

import java.io.IOException;
import java.util.logging.LogManager;

import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

public class Start {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		try {
			LogManager.getLogManager().readConfiguration(
					Start.class.getResourceAsStream("/logging.properties"));
			final Window window = new Window();
			window.setVisible(true);
		} catch (final IOException e) {
			System.err.println("Could not setup logger configuration: " + e.toString());
		}

	}

}
