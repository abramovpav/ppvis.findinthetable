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
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.Controller;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ADialog;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;

public class Model {
	class Bool {
		public boolean	bool;
	}

	private static final String		ERROR_FILE_INCORRECT	= "***ERROR***: File incorrect";
	private final Vector<Student>	students;
	private JTextField				observer;
	private final List<Controller>	observers;
	public static final int			DEFAULT_VIEWSIZE		= 10;
	private Integer					viewSize				= Model.DEFAULT_VIEWSIZE;

	private int						currPage				= -1;

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
		int result = result = students.size() / viewSize;
		if (max % 2 != 0 && max % 2 != 1) {
			result += 1;
		}
		return result;
	}

	public Student[] getNextPageOfStudents() {

		System.out.println(currPage);
		if (currPage < getMaxPage() - 1) {
			currPage++;
		}
		return getPageOfStudents();
	}

	private Student[] getPageOfStudents() {

		if (students.size() == 0) {
			return null;
		}
		System.out.println(getMaxPage());
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
		System.out.println("size = " + size);
		final Student[] pageStudents = new Student[size];
		for (int i = 0; i < size; ++i) {
			pageStudents[i] = students.get(i + viewSize * currPage);
		}
		return pageStudents;
	}

	public Student[] getPrevPageOfStudents() {

		System.out.println(currPage);
		if (currPage > 0) {
			currPage--;
		}
		return getPageOfStudents();
	}

	public final Integer getViewSize() {

		return viewSize;
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
		// start(file.getPath());

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
		for (int i = 0; i < students.size(); ++i) {
			final Student student = students.get(i);
			final Exam[] exams = student.getExams();
			for (int j = 0; j < exams.length; ++j) {
				if (exams[j] != null) {
					System.out.println(student.getName() + " " + student.getGroup() + " "
							+ (exams[j].getName() != null ? exams[j].getName() : "null")
							+ " "
							+ (exams[j].getMark() != null ? exams[j].getMark() : "null"));
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
						if (field.getNodeName() == "name") {
							name = item.getNodeValue();
						}
						if (field.getNodeName() == "mark") {
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
			System.out.print("Problem parsing the file: " + e.getMessage());
		}
		return null;
	}

	private Document parseForDOM(final String xml) {

		return parseForDOM(new File(xml));
	}

	private Student parseStudent(final Node nodeStudent) {

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
								if (field.getNodeName() == "name") {
									name = item.getNodeValue();
								}
								if (field.getNodeName() == "group") {
									group = item.getNodeValue();
								}
							}
						} else {
							for (int j = 0; j < childFields.getLength(); ++j) {
								final Node examField = childFields.item(j);
								if (examField != null) {
									if (examField.getNodeType() == Node.ELEMENT_NODE) {
										final Exam exam = parseExam(examField);
										exams[index] = exam;
										index++;
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
		return new Student(name, ADialog.isNumeric(group) ? Integer.parseInt(group)
				: null, exams);
	}

	private void readXML(final Node node) {

		final NodeList children = node.getChildNodes();
		System.out.println(node);
		if (children != null) {
			if (children.getLength() > 1) {
				System.out.println("fields:");
				for (int i = 0; i < children.getLength(); ++i) {
					final Node child = children.item(i);
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						readXML(child);
					}
				}
			} else {
				final Node child = children.item(0);
				if (child != null) {
					System.out.println(child);
				}
			}
		}
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

	private void start(String xml) throws TransformerFactoryConfigurationError {

		xml = "c:\\students.xml";
		final Document doc = parseForDOM(xml);
		if (doc != null) {
			final Element root = doc.getDocumentElement();
			readXML(root);
			writeToFIle(xml, doc);
		} else {
			System.out.println(Model.ERROR_FILE_INCORRECT);
			JOptionPane.showMessageDialog(null, Model.ERROR_FILE_INCORRECT);
		}
	}

	public void update() {

		final Iterator<Controller> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}

	}

	private void writeToFIle(final String xml, final Document doc)
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
