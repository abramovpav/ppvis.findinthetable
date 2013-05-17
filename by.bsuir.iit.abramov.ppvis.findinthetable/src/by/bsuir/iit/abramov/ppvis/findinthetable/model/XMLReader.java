package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.Util;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

class XMLReader {

	private static final String	PROBLEM_PARSING_THE_FILE			= "Problem parsing the file: ";
	private static final String	ERROR_INCORRECT_QUANTITY_IN_EXAMS	= "***ERROR***: Incorrect quantity in exams";
	private static Logger		LOG									= Logger.getLogger(XMLReader.class
																			.getName());

	public void openXML(final File file, final Model model) {

		final Document doc = parseForDOM(file);
		final List<Student> students = parse(doc);
		model.setStudents(students);
		model.update();
	}

	private List<Student> parse(final Document doc) {

		final List<Student> students = new Vector<Student>();
		if (doc == null) {
			return students;
		}
		final Element root = doc.getDocumentElement();
		final NodeList nodeStudents = root.getChildNodes();
		if (nodeStudents != null) {
			if (nodeStudents.getLength() != 0) {
				for (int i = 0; i < nodeStudents.getLength(); ++i) {
					final Node nodeStudent = nodeStudents.item(i);
					if (nodeStudent != null) {
						if (nodeStudent.getNodeType() == Node.ELEMENT_NODE) {
							final Student student = parseStudent(nodeStudent);
							students.add(student);
						}
					}
				}
			}
		}
		return students;
	}

	private Exam parseExam(final Node exam) {

		String name = "";
		String mark = "";
		final NodeList fields = exam.getChildNodes();
		for (int i = 0; i < fields.getLength(); ++i) {
			final Node field = fields.item(i);
			if (field != null) {
				if (field.getNodeType() == Node.ELEMENT_NODE) {
					final Node item = field.getChildNodes().item(0);
					if (item != null) {
						if (field.getNodeName() == Model.FIELD_NAME) {
							name = item.getNodeValue();
						}
						if (field.getNodeName() == Model.FIELD_MARK) {
							mark = item.getNodeValue();
						}
					}
				}
			}
		}

		return new Exam(name, Util.isNumeric(mark) ? Integer.parseInt(mark) : null);
	}

	private Document parseForDOM(final File docFile) {

		Document document = null;
		try {
			final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			documentBuilderFactory.setValidating(true);
			final DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			documentBuilder.setErrorHandler(new MyErrorHandler());
			document = documentBuilder.parse(docFile);
			return document;
		} catch (Exception e) {
			XMLReader.LOG.log(
					Level.SEVERE,
					Window.geti18nString(XMLReader.PROBLEM_PARSING_THE_FILE)
							+ e.getMessage(), e);
			e = null;
			return null;
		}
	}

	private Student parseStudent(final Node nodeStudent) {

		boolean quantityError = false;
		String name = "";
		String group = "";
		final Exam[] exams = new Exam[Desktop.EXAMS_COUNT];
		int index = 0;
		final NodeList fields = nodeStudent.getChildNodes();
		if (fields != null) {
			for (int i = 0; i < fields.getLength(); ++i) {
				final Node field = fields.item(i);
				if (field != null) {
					if (field.getNodeType() == Node.ELEMENT_NODE) {
						final NodeList childFields = field.getChildNodes();
						if (childFields.getLength() == 1) {
							final Node item = childFields.item(0);
							if (item != null) {
								if (field.getNodeName() == Model.FIELD_NAME) {
									name = item.getNodeValue();
								}
								if (field.getNodeName() == Model.FIELD_GROUP) {
									group = item.getNodeValue();
								}
							}
						} else {
							int counter = 0;
							for (int j = 0; j < childFields.getLength(); ++j) {
								final Node examField = childFields.item(j);

								if (examField != null) {
									if (examField.getNodeType() == Node.ELEMENT_NODE) {
										if (counter < Desktop.EXAMS_COUNT) {
											final Exam exam = parseExam(examField);
											exams[index] = exam;
											index++;
											counter++;
										} else {
											quantityError = true;
										}
									}
								}

							}
						}
					}
				}
			}
		}
		for (; index < Desktop.EXAMS_COUNT; ++index) {
			exams[index] = new Exam("", null);
		}
		if (quantityError) {
			JOptionPane.showMessageDialog(null,
					XMLReader.ERROR_INCORRECT_QUANTITY_IN_EXAMS + "(>"
							+ Desktop.EXAMS_COUNT + ") of student " + name);
		}
		return new Student(name, Util.isNumeric(group) ? Integer.parseInt(group) : null,
				exams);
	}

}
