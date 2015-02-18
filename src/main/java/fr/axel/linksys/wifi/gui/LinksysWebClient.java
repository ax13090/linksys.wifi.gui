package fr.axel.linksys.wifi.gui;

import java.io.IOException;
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

public class LinksysWebClient {

	private final URL url;
	private final WebClient webClient;

	public static LinksysWebClient create() throws Exception {
		final String urlString = "http://192.168.1.1/setup.cgi?next_file=Wireless.htm";
		final URL url = new URL(urlString);

		final WebClient webClient = new WebClient();
		final DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
		final String userName = "admin";
		final String userPassword = "admin";
		credentialsProvider.addCredentials(userName, userPassword);
		webClient.setCredentialsProvider(credentialsProvider);

		return new LinksysWebClient(url, webClient);
	}

	private LinksysWebClient(final URL url, final WebClient webClient) {
		this.url = url;
		this.webClient = webClient;
	}

	public Map<String, String> fetchAvailableWifiModeChoices() throws IOException {
		final HtmlPage page = webClient.getPage(url);
		final List<HtmlOption> options = findOptions(page);

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
		
		return optionMap;
	}

	private List<HtmlOption> findOptions(final HtmlPage page) {
		System.out.println(page.asXml());

		final DomElement selectDomElement = page.getElementByName("wl_gmode");
		final HtmlSelect selectElement = (HtmlSelect) selectDomElement;
		System.out.println(selectElement);

		final List<HtmlOption> options = selectElement.getOptions();
		return options;
	}

	public void switchWifiMode(final String value) throws IOException {
		final HtmlPage page = webClient.getPage(url);
		final List<HtmlOption> options = findOptions(page);
	}
}
