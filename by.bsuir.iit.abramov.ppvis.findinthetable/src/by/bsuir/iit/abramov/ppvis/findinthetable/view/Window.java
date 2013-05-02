package by.bsuir.iit.abramov.ppvis.findinthetable.view;

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

	private void initialize() {

		menu = new Menu(this);
		setJMenuBar(menu);
		contentPane = new ContentPane(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setExtendedState(Frame.MAXIMIZED_BOTH);
		setContentPane(contentPane);
		setBounds(Window.defaultX, Window.defaultY, Window.defaultWidth,
				Window.defaultHeight);
	}
}
