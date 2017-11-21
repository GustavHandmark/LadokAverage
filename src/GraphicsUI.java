import java.text.DecimalFormat;

import javax.swing.*;

public class GraphicsUI {
	public static void main(String[] args) throws Exception {
		try {
			GradeFetcher average = new GradeFetcher();
			String user = "";
			String pw = "";
			JTextField username = new JTextField();
			JTextField password = new JPasswordField();
			Object[] message = { "Username:", username, "Password:", password };

			int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				user = username.getText();
				pw = password.getText();

			} else {
				System.out.println("Cancelled");
			}
			if (user.length() != 0 && pw.length() != 0) {
				DecimalFormat nf = new DecimalFormat("#.000");
				JOptionPane.showMessageDialog(null, "Average grades: " + nf.format(average.getsnitt(user, pw)));

			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
