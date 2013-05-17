package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyErrorHandler implements ErrorHandler {
	private final Logger	LOG	= Logger.getLogger(MyErrorHandler.class.getName());

	@Override
	public void error(final SAXParseException exception) throws SAXException {

		// exception.printStackTrace();
		LOG.log(Level.SEVERE, "Error in parsing: " + exception.getMessage(), exception);
	}

	@Override
	public void fatalError(final SAXParseException exception) throws SAXException {

		// exception.printStackTrace();
		LOG.log(Level.SEVERE, "Error in parsing: " + exception.getMessage(), exception);
	}

	@Override
	public void warning(final SAXParseException exception) throws SAXException {

		// exception.printStackTrace();
		LOG.log(Level.SEVERE, "Error in parsing: " + exception.getMessage(), exception);
	}
}
