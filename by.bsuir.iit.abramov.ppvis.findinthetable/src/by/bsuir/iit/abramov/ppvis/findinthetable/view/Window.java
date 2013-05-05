package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.io.File;

import javax.swing.JFrame;

public class Window extends JFrame {
	public static final int		defaultX		= 200;
	public static final int		defaultY		= 100;
	public static final int		defaultWidth	= 995;
	public static final int		defaultHeight	= 600;
	public static final String	TITLE			= "FindInTheTable";

	private ContentPane			contentPane;
	private Menu				menu;

	public Window() {

		super(Window.TITLE);
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
		// setExtendedState(Frame.MAXIMIZED_BOTH);
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
}
