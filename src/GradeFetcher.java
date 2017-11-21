import java.util.ArrayList;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


public class GradeFetcher {
	public static HtmlTable getTable(String username, String pw) {
		HtmlTable table = null;
		try {
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
			
			WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
			webClient.setJavaScriptEnabled(false);
			HtmlPage page1 = webClient.getPage("http://student.lu.se/uPortal/f/welcome/normal/render.uP");
			HtmlAnchor anchor = page1.getAnchorByHref(
					"https://idpv3.lu.se/idp/profile/cas/login?service=http://student.lu.se/uPortal/Login");
			HtmlPage page2 = anchor.click();
			HtmlForm form1 = page2.getFirstByXPath("//form[@id=\"fm1\"]");
			HtmlTextInput user = form1.getInputByName("j_username");
			HtmlPasswordInput password = form1.getInputByName("j_password");
			HtmlSubmitInput button = form1.getInputByName("_eventId_proceed");
			user.setValueAttribute(username);
			password.setValueAttribute(pw);
			HtmlPage page3 = button.click();
			HtmlAnchor anchor1 = page3.getAnchorByHref("/uPortal/f/u260l1s26/p/TG02.u260l1n79/max/render.uP?pCp");
			HtmlPage page4 = anchor1.click();
			table = page4.getFirstByXPath("//table[@class='lpw-table']");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Check credentials");
		}
		return table;
	}

	public double getsnitt(String username, String password) throws Exception {
		ArrayList<String> credits = new ArrayList<String>();
		ArrayList<String> grades = new ArrayList<String>();
		double sumcredits = 0;
		double sumval = 0;
		HtmlTable table = getTable(username, password);
		for (HtmlTableRow row : table.getRows()) {
			if (row.getCell(1).getTextContent().length() != 0) {
				credits.add(row.getCell(4).getTextContent());
				grades.add(row.getCell(6).getTextContent().trim());
			}
		}

		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		Pattern pattern1 = Pattern.compile("[0-9.]");
		for (int i = 0; i < grades.size(); i++) {
			if (pattern.matcher(credits.get(i)).matches() && pattern1.matcher(grades.get(i)).matches()) {
				sumcredits = sumcredits + Double.parseDouble(credits.get(i));
				sumval = sumval + Double.parseDouble(credits.get(i)) * Double.parseDouble(grades.get(i));
			}

		}
		return sumval / sumcredits;

	}

}

