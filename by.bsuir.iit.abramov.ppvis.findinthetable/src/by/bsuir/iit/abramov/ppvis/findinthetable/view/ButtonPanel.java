package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	private Map<String, JButton> buttons;
	private JLabel label;
	public ButtonPanel() {
		buttons = new HashMap<String, JButton>();
	}
	
	public void updateInterface() {
		for (String key : buttons.keySet()) {
			JButton button = buttons.get(key);
			button.setText(Window.geti18nString(key));
		}
		label.setText(Window.geti18nString(Desktop.MAX));
	}
	
	public void addButton(final String str, JButton button) {
		if (!buttons.containsValue(button)) {
			buttons.put(str, button);
		}
	}
	
	public void setLabel(JLabel label) {
		this.label = label;
	}
}
