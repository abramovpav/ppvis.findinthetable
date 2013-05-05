package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.Controller;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.Util;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ADialog;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;

public class Model {
	class Bool {
		public boolean	bool;
	}

	private static final String		PROBLEM_PARSING_THE_FILE			= "Problem parsing the file: ";
	private static final String		ERROR_ERROR_IN_CREATING_DOC			= "***ERROR***: Error in creating doc";
	private static final String		ERROR_INCORRECT_QUANTITY_IN_EXAMS	= "***ERROR***: Incorrect quantity in exams";
	private static final String		FIELD_MARK							= "mark";
	private static final String		FIELD_STUDENTS						= "students";
	private static final String		FIELD_STUDENT						= "student";
	private static final String		FIELD_EXAM							= "exam";
	private static final String		FIELD_EXAMS							= "exams";
	private static final String		FIELD_GROUP							= "group";

	private static final String		FIELD_NAME							= "name";

	private static final String		ERROR_FILE_INCORRECT				= "***ERROR***: File incorrect";
	private final Vector<Student>	students;
	private JTextField				observer;
	private final List<Controller>	observers;
	public static final int			DEFAULT_VIEWSIZE					= 10;
	private Integer					viewSize							= Model.DEFAULT_VIEWSIZE;

	private int						currPage							= -1;

	public Model() {

		students = new Vector<Student>();
		observers = new ArrayList<Controller>();
	}

	public void addObserver(final Controller observer) {

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void addStudent(final Student student) {

		if (student != null) {
			students.add(student);
		}
	}

	public Document createDocument() {

		Document doc = null;
		try {
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.newDocument();
			final Element root = doc.createElement(Model.FIELD_STUDENTS);
			for (int i = 0; i < students.size(); ++i) {
				final Student student = students.get(i);
				final Element studentElement = doc.createElement(Model.FIELD_STUDENT);
				root.appendChild(studentElement);
				newElement(doc, Model.FIELD_NAME, studentElement, student.getName());
				newElement(doc, Model.FIELD_GROUP, studentElement, student.getGroup()
						.toString());
				final Exam[] exams = student.getExams();
				final Element examsElement = doc.createElement(Model.FIELD_EXAMS);
				studentElement.appendChild(examsElement);
				for (int j = 0; j < exams.length; ++j) {
					final Exam exam = exams[j];
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
			System.out.print(Model.PROBLEM_PARSING_THE_FILE + e.getMessage());
		}
		return doc;
	}

	public void deleteStudents(final Student[] delStudents) {

		for (int i = 0; i < delStudents.length; ++i) {
			if (students.contains(delStudents[i])) {
				students.remove(delStudents[i]);
			}
		}
		update();
	}

	public final int getCurrPage() {

		return currPage;
	}

	public final Student[] getCurrPageOfStudent() {

		if (getCurrPage() < 0) {
			resetCurrPage();
		}
		return getPageOfStudents();
	}

	private int getMaxPage() {

		final double max = (double) students.size() / (double) viewSize;
		int result = students.size() / viewSize;
		if (max % 2 != 0 && max % 2 != 1) {
			result += 1;
		}
		return result;
	}

	public Student[] getNextPageOfStudents() {

		if (currPage < getMaxPage() - 1) {
			currPage++;
		}
		return getPageOfStudents();
	}

	private Student[] getPageOfStudents() {

		if (students.size() == 0) {
			return null;
		}
		if (currPage < 0 || currPage >= getMaxPage()) {
			return null;
		}
		int size = 0;
		if (students.size() - viewSize * currPage < viewSize) {
			size = students.size() - viewSize * currPage;
		} else {
			size = viewSize;
		}
		if (size == 0) {
			return null;
		}
		final Student[] pageStudents = new Student[size];
		for (int i = 0; i < size; ++i) {
			pageStudents[i] = students.get(i + viewSize * currPage);
		}
		return pageStudents;
	}

	public Student[] getPrevPageOfStudents() {

		if (currPage > 0) {
			currPage--;
		}
		return getPageOfStudents();
	}

	public final Integer getViewSize() {

		return viewSize;
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

	public void notifyObserver() {

		if (observer != null) {
			observer.setText(Integer.toString(viewSize));
		}
	}

	public void openXML(final File file) {

		final Document doc = parseForDOM(file);
		parse(doc);
		update();
	}

	private void parse(final Document doc) {

		final Vector<Student> students = new Vector<Student>();
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
		this.students.clear();
		this.students.addAll(students);
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

		return new Exam(name, ADialog.isNumeric(mark) ? Integer.parseInt(mark) : null);
	}

	private Document parseForDOM(final File docFile) {

		Document doc = null;
		try {
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			final Bool hasError = new Bool();
			dbf.setValidating(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();
			db.setErrorHandler(new ErrorHandler() {
				@Override
				public void error(final SAXParseException exception) throws SAXException {

					// do something more useful in each of these handlers
					exception.printStackTrace();
					hasError.bool = true;
				}

				@Override
				public void fatalError(final SAXParseException exception)
						throws SAXException {

					exception.printStackTrace();
					hasError.bool = true;
				}

				@Override
				public void warning(final SAXParseException exception)
						throws SAXException {

					exception.printStackTrace();
					hasError.bool = true;
				}
			});
			doc = db.parse(docFile);
			if (!hasError.bool) {
				return doc;
			}
		} catch (final Exception e) {
			System.out.print(Model.PROBLEM_PARSING_THE_FILE + e.getMessage());
		}
		return null;
	}

	private Document parseForDOM(final String xml) {

		return parseForDOM(new File(xml));
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
			JOptionPane.showMessageDialog(null, Model.ERROR_INCORRECT_QUANTITY_IN_EXAMS
					+ "(>" + Desktop.EXAMS_COUNT + ") of student " + name);
		}
		return new Student(name, ADialog.isNumeric(group) ? Integer.parseInt(group)
				: null, exams);
	}

	public void removeObserver() {

		observer = null;
	}

	public void removeObserver(final Controller observer) {

		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	public void resetCurrPage() {

		currPage = 0;
	}

	public void saveXML(final File file) {

		final Document doc = createDocument();
		if (doc != null) {
			writeToFile(file, doc);
		} else {
			JOptionPane.showMessageDialog(null, Model.ERROR_ERROR_IN_CREATING_DOC);
		}

	}

	public Vector<Student> search(final String name, final Integer group) {

		final Vector<Student> studentsVector = new Vector<Student>();
		for (final Student student : students) {
			if (student.getName().indexOf(name) != -1) {
				if (group != null) {
					if (student.getGroup().toString().indexOf(group.toString()) != -1) {
						studentsVector.add(student);
					}
				} else {
					studentsVector.add(student);
				}
			}
		}
		return studentsVector;
	}

	public Vector<Student> search(final String name, final String botStr,
			final String topStr) {

		final Vector<Student> studentsVector = new Vector<Student>();
		final int bot = Util.isNumeric(botStr) ? Integer.parseInt(botStr) : 0;
		final int top = Util.isNumeric(topStr) ? Integer.parseInt(topStr) : 10;
		for (final Student student : students) {
			if (student.getAverageMark() >= bot && student.getAverageMark() <= top) {
				if (student.getName().indexOf(name) != -1) {
					studentsVector.add(student);
				}
			}
		}
		return studentsVector;
	}

	public Vector<Student> search(final String name, final String examStr,
			final String botStr, final String topStr) {

		final Vector<Student> studentsVector = new Vector<Student>();
		final int bot = Util.isNumeric(botStr) ? Integer.parseInt(botStr) : 0;
		final int top = Util.isNumeric(topStr) ? Integer.parseInt(topStr) : 10;
		for (final Student student : students) {
			if (student.isExam(examStr) >= bot && student.isExam(examStr) <= top) {
				if (student.getName().indexOf(name) != -1) {
					studentsVector.add(student);
				}
			}
		}
		return studentsVector;
	}

	public void setObserver(final JTextField observer) {

		this.observer = observer;
	}

	public void setViewSize(final Integer viewSize) {

		if (viewSize != null) {
			if (viewSize > 0) {
				this.viewSize = viewSize;
				notifyObserver();
			}
		}
	}

	public void update() {

		final Iterator<Controller> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}

	}

	private void writeToFile(final File file, final Document doc) {

		writeToFile(file.getPath(), doc);
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
			System.out.println(te.getMessage());
		} catch (final IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
}
