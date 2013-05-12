package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

public class Window extends JFrame {
	public static final int			defaultX		= 200;
	public static final int			defaultY		= 100;
	public static final int			defaultWidth	= 995;
	public static final int			defaultHeight	= 600;
	public static final String		TITLE			= "title";

	private static Locale			enLocale		= new Locale("en", "US");
	private static Locale			ruLocale		= new Locale("ru", "RU");
	private static ResourceBundle	resourseBundle	= ResourceBundle.getBundle(
															"MessagesBundle",
															Window.enLocale);

	public static List<String> getExams() {

		final ResourceBundle resourseBundle = ResourceBundle.getBundle("exams",
				Window.resourseBundle.getLocale());
		final List<String> exams = new ArrayList<String>();
		final Enumeration<String> keys = resourseBundle.getKeys();
		while (keys.hasMoreElements()) {
			final String key = keys.nextElement();
			exams.add(resourseBundle.getString(key));

		}
		return exams;
	}

	public static final String geti18nString(final String key) {

		String str = "";
		if (Window.resourseBundle.containsKey(key)) {
			str = Window.resourseBundle.getString(key);
		} else {
			str = "null";
		}
		return str;
	}

	private ContentPane	contentPane;

	private Menu		menu;

	public Window() {

		super(Window.geti18nString(Window.TITLE));
		initialize();
	}

	public Window(final String title) {

		super(title);
		initialize();
	}

	public void exit() {

		setVisible(false);
		dispose();
	}

	public final ContentPane getContPane() {

		return contentPane;
	}

	private void initialize() {

		contentPane = new ContentPane(this);
		menu = new Menu(contentPane);
		setJMenuBar(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		setBounds(Window.defaultX, Window.defaultY, Window.defaultWidth,
				Window.defaultHeight);
	}

	public void openXML(final File file) {

		contentPane.openXML(file);
	}

	public void saveXML(final File file) {

		contentPane.saveXML(file);
	}

	public void setEnLocale() {

		Window.resourseBundle = ResourceBundle.getBundle("MessagesBundle",
				Window.enLocale);
		updateInteface();
	}

	public void setRuLocale() {

		Window.resourseBundle = ResourceBundle.getBundle("MessagesBundle",
				Window.ruLocale);
		updateInteface();
	}

	public void updateInteface() {

		setTitle(Window.geti18nString(Window.TITLE));
		menu = new Menu(contentPane);
		setJMenuBar(menu);
		contentPane.updateInteface();
		menu.revalidate();
		menu.repaint();
		repaint();
	}
}
