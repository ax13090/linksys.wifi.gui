package fr.axel;

import java.net.URL;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.google.common.collect.ImmutableMap;

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
		
		System.out.println(page.asXml());
		
		final DomElement selectDomElement = page.getElementByName("wl_gmode");
		final HtmlSelect selectElement = (HtmlSelect) selectDomElement;
		System.out.println(selectElement);
		
		final List<HtmlOption> options = selectElement.getOptions();
		
		final Map<String, String> optionMap;
		{
			final ImmutableMap.Builder<String, String> optionMapBuilder = ImmutableMap.builder();
			for (final HtmlOption htmlOption : options) {
				final String optionHumanName = htmlOption.asText();
				final String optionHtmlValue = htmlOption.getValueAttribute();
				optionMapBuilder.put(optionHumanName, optionHtmlValue);
			}
			optionMap = optionMapBuilder.build();
		}
	}
}
