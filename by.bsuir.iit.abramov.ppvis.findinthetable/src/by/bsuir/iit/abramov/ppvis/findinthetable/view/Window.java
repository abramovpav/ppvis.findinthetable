package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

public class Window extends JFrame {
	public static final int		defaultX		= 200;
	public static final int		defaultY		= 100;
	public static final int		defaultWidth	= 995;
	public static final int		defaultHeight	= 600;
	public static final String	TITLE			= "title";
	
	private static Locale enLocale = new Locale("en","US");
	private static Locale ruLocale = new Locale("ru","RU");
	private static ResourceBundle resourseBundle = ResourceBundle.getBundle("MessagesBundle", enLocale);
	

	private ContentPane			contentPane;
	private Menu				menu;

	public Window() {

		super(geti18nString(TITLE));
		initialize();
	}
	
	public static final String geti18nString(final String key) {
		String str = "";
		if (resourseBundle.containsKey(key)) {
			str = resourseBundle.getString(key);
		}
		else {
			str = "null";
		}
		return str;
	}

	public Window(final String title) {

		super(title);
		initialize();
	}
	
	public void setEnLocale() {
		resourseBundle = ResourceBundle.getBundle("MessagesBundle", enLocale);
		updateInteface();
	}
	
	
	
	public void setRuLocale() {
		resourseBundle = ResourceBundle.getBundle("MessagesBundle", ruLocale);
		updateInteface();
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
	
	public void updateInteface() {
		
		setTitle(geti18nString(TITLE));
		menu = new Menu(contentPane);
		setJMenuBar(menu);
		contentPane.updateInteface();
		menu.revalidate();
		menu.repaint();
		repaint();
	}
	

	public void openXML(final File file) {

		contentPane.openXML(file);
	}

	public void saveXML(final File file) {

		contentPane.saveXML(file);
	}
}
