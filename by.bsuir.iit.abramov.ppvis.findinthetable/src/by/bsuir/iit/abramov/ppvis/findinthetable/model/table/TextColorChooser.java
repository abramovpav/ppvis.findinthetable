/*
 * (swing1.1beta3)
 */

package by.bsuir.iit.abramov.ppvis.findinthetable.model.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

class ColorChooserDialog extends JDialog {
	private Color				initialColor;
	private Color				retColor;
	private final JColorChooser	chooserPane;

	public ColorChooserDialog(final Component c, final String title,
			final JColorChooser chooserPane) {

		super(JOptionPane.getFrameForComponent(c), title, true);
		setResizable(false);

		this.chooserPane = chooserPane;

		final String okString = UIManager.getString("ColorChooser.okText");
		final String cancelString = UIManager.getString("ColorChooser.cancelText");
		final String resetString = UIManager.getString("ColorChooser.resetText");

		final Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(chooserPane, BorderLayout.CENTER);

		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		final JButton okButton = new JButton(okString);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				retColor = chooserPane.getColor();
				setVisible(false);
			}
		});
		buttonPane.add(okButton);

		final JButton cancelButton = new JButton(cancelString);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				retColor = null;
				setVisible(false);
			}
		});
		buttonPane.add(cancelButton);

		final JButton resetButton = new JButton(resetString);
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {

				chooserPane.setColor(initialColor);
			}
		});
		buttonPane.add(resetButton);
		contentPane.add(buttonPane, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(c);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {

				setVisible(false);
			}
		});
	}

	public Color getColor() {

		return retColor;
	}

}

public class TextColorChooser extends JColorChooser {

	public TextColorChooser(final Color target, final Color reference,
			final boolean isForgroundSelection) {

		super(target);
		if (isForgroundSelection) {
			setPreviewPanel(new TextPreviewLabel(target, reference, isForgroundSelection));
		} else {
			setPreviewPanel(new TextPreviewLabel(reference, target, isForgroundSelection));
		}
		updateUI();
	}

	public Color showDialog(final Component component, final String title) {

		final ColorChooserDialog dialog = new ColorChooserDialog(component, title, this);
		dialog.show();
		final Color col = dialog.getColor();
		dialog.dispose();
		return col;
	}

}

/**
 * @version 1.0 11/22/98
 */
class TextPreviewLabel extends JLabel {
	private final String	sampleText	= "  Sample Text  Sample Text  ";
	boolean					isForgroundSelection;

	public TextPreviewLabel() {

		this(Color.black, Color.white, true);
	}

	public TextPreviewLabel(final Color fore, final Color back,
			final boolean isForgroundSelection) {

		setOpaque(true);
		setForeground(fore);
		setBackground(back);
		this.isForgroundSelection = isForgroundSelection;
		setText(sampleText);
	}

	@Override
	public void setForeground(final Color col) {

		if (isForgroundSelection) {
			super.setForeground(col);
		} else {
			super.setBackground(col);
		}
	}

}
