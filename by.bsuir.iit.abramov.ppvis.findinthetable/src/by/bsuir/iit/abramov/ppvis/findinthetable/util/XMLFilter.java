package by.bsuir.iit.abramov.ppvis.findinthetable.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XMLFilter extends FileFilter {
	private static final String	XML_DESCRIPTION	= "XML (*.xml)";
	public final static String	XML				= "xml";

	@Override
	public boolean accept(final File f) {

		if (f.isDirectory()) {
			return true;
		}

		final String extension = getExtension(f);
		if (extension != null) {
			return XMLFilter.XML.equals(extension);
		}

		return false;
	}

	@Override
	public String getDescription() {

		return XMLFilter.XML_DESCRIPTION;
	}

	/*
	 * Get the extension of a file.
	 */
	public String getExtension(final File f) {

		String ext = null;
		final String s = f.getName();
		final int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
}
