package fr.axel;

import java.net.URL;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class App {
	public static void main( final String[] args ) throws Exception {
		final WebClient webClient = new WebClient();
		final String urlString = "http://192.168.1.1/setup.cgi?next_file=Wireless.htm";
		final URL url = new URL(urlString);
		
		final DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
		final String userName = "admin";
		final String userPassword = "admin";
		credentialsProvider.addCredentials(userName, userPassword);
		webClient.setCredentialsProvider(credentialsProvider);
		
		final HtmlPage page = webClient.getPage(url);
		final String contents = page.asText();
		System.out.println(contents);
	}
}
