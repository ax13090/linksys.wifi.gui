# linksys.wifi.gui
This is a tiny pet project of mine. I use it with my Linksys WAG320N Modem/Router,
in order to **enable or disable the Wifi** capabilities.

It may work on other models, but I only tested it on mine.

## Requirements
The code was written using Java 8, inside Eclipse. It might however get compiled without Eclipse and using a lower
Java version, such as Java 7. The building is done by Maven, as well as the inclusion of the dependencies.

This project heavily relies on the HtmlUnit library, as well as the JavaFX toolkit (included in Java 1.8).

## Building
This should do the trick:
```
mvn clean package
```

## Running
Simply run the über-JAR file produced by Maven. Under Windows, doucle-clicking it should suffice.
