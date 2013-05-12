package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

public class XMLWriter {
	private static Logger	LOG	= Logger.getLogger(Desktop.class.getName());

	public Document createDocument(final List<Student> students) {

		Document doc = null;
		try {
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.newDocument();
			final Element root = doc.createElement(Model.FIELD_STUDENTS);
			for (final Student student : students) {
				final Element studentElement = doc.createElement(Model.FIELD_STUDENT);
				root.appendChild(studentElement);
				newElement(doc, Model.FIELD_NAME, studentElement, student.getName());
				newElement(doc, Model.FIELD_GROUP, studentElement, student.getGroup()
						.toString());
				final List<Exam> exams = student.getExams();
				final Element examsElement = doc.createElement(Model.FIELD_EXAMS);
				studentElement.appendChild(examsElement);
				for (final Exam exam : exams) {
					if (!exam.isEmpty()) {
						final Element examElement = doc.createElement(Model.FIELD_EXAM);
						examsElement.appendChild(examElement);
						newElement(doc, Model.FIELD_NAME, examElement,
								exam.getName() != null ? exam.getName() : " ");
						newElement(doc, Model.FIELD_MARK, examElement,
								exam.getMark() != null ? exam.getMark().toString() : " ");
					}
				}
			}
			doc.appendChild(root);
		} catch (final Exception e) {
			XMLWriter.LOG
					.log(Level.SEVERE,
							Window.geti18nString(Model.PROBLEM_PARSING_THE_FILE)
									+ e.getMessage(), e);
		}
		return doc;
	}

	private void newElement(final Document doc, final String name,
			final Element studentElement, final String text) {

		Element element;
		Text textElement;
		element = doc.createElement(name);
		textElement = doc.createTextNode(text);
		studentElement.appendChild(element);
		element.appendChild(textElement);
	}

	public void saveXML(final File file, final List<Student> students) {

		final XMLWriter xmlWriter = new XMLWriter();
		final Document doc = createDocument(students);
		if (doc != null) {
			writeToFile(file.getPath(), doc);
		} else {
			JOptionPane.showMessageDialog(null,
					Window.geti18nString(Model.ERROR_ERROR_IN_CREATING_DOC));
			XMLWriter.LOG.info(Window.geti18nString(Model.ERROR_ERROR_IN_CREATING_DOC));
		}

	}

	private void writeToFile(final String xml, final Document doc)
			throws TransformerFactoryConfigurationError {

		try {
			final Transformer tr = TransformerFactory.newInstance().newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "students.dtd");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			// send DOM to file
			tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(xml)));

		} catch (final TransformerException te) {
			XMLWriter.LOG.log(Level.SEVERE, te.getMessage(), te);
		} catch (final IOException ioe) {
			XMLWriter.LOG.log(Level.SEVERE, ioe.getMessage(), ioe);
		}
	}
}
