# Capgemini Office Watch

---

Mit Capgemini Office Watch behalten Sie Ihre Office-Gebäude immer Blick:
Die aus verschiedensten Quellen aggregierten Daten
werden anhand eines flexibel gestalteten Regelsystem in Handlungsempfehlungen umgewandelt.
Unser Messenger-Bot informiert Sie sofort auf Ihrem Handy,
wenn es Handlungsbedarf gibt.

**Highlights:**
Als Datenquelle dienen beliebig viele Web-APIs oder physische Sensoren.
Regeln, nach denen Handlungsempfehlungen ausgesprochen werden,
können völlig frei und flexibel gestaltet werden.
Dazu wird von der JSON Query und Transformation Sprache JSONata Gebrauch gemacht.
Handlungsempfehlungen können flexibel an verschiedene Clients verschickt werden.
Hier implementieren wir exemplarisch einen Telegram Bot,
aber es sind auch viele andere Formen denkbar, wie Mail, Web App, etc.


## Installation

**Auf dem Server:**
1. GitHub-Repository klonen
2. Python dependencies auf dem Server installieren:
   1. `pip3 install pyjsonata`
   2. `pip3 install python-telegram-bot --upgrade`
3. `start.sh` (Linux) oder `start.bat` (Windows) konfigurieren
   (Eintragung von Port, Pfad zu `python3`)

**Auf dem Raspberry Pi:**
1. (optional) Dateien `data_aggr.py` und `ccs811LIBRARY.py` in einen beliebigen Ordner des Raspberry Pi legen

Wenn gewünscht: Telegram Bot erstellen -> mehr Infos hier: https://github.com/python-telegram-bot/python-telegram-bot

#### Bauen der Server-JAR (optional)

Die JAR-Datei kann selbst gebaut werden.
Hierzu kann ein beliebiges Build-System genutzt werden.
Folgende Dependencies werden verwendet:
- OkHttp: https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
- org.json (JSON in Java): https://github.com/stleary/JSON-java


## Benutzung

1. `start.sh` (Linux) bzw. `start.bat` (Windows) auf dem Server ausführen
2. (optional) Auf dem Pi: `python3 data_aggr.py <server ip> <server port>`
3. Nun werden automatisch neue Empfehlungen per Telegram verschickt.

## Architektur


Das Projekt besteht aus einer Server-JAR, die auf einem beliebigen Server mit statischer IP läuft, und optional aus einem Python-Skript, das auf dem Raspberry Pi läuft und Daten mittels Sensoren sammelt. Letzteres Skript verbindet sich über TCP mit dem Server; ein hierfür entwickeltes Protokoll garantiert den kontinuierlichen Datenaustausch.

Die aus den verschieden Quellen gesammelten Daten werden zu einem JSON Objekt zusammengefasst und an ein recommendation system weitergeleitet. Dieses System prüft die übergebenen Daten anhand von frei anpassbaren Regeln und erzeugt daraus Handlungsempfehlungen.

Diese Handlungsempfehlungen werden dann verschickt an definierbare Clienten. Hier haben wir exemplarisch einen Telegram Bot  implementiert.


### Autoren

- Denis Hessel, d.hessel@wwu.de
- Julian Koch, julian.koch@tum.de
