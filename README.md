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

## Benutzung

1. `start.sh` (Linux) bzw. `start.bat` (Windows) auf dem Server ausführen
2. (optional) Auf dem Pi: `python3 data_aggr.py <server ip> <server port>`
3. Nun werden automatisch neue Empfehlungen per Telegram verschickt.

## Architektur

Das Projekt besteht aus einer Server-JAR, die auf einem beliebigen Server mit statischer IP läuft, und optional aus
einem Python-Skript, das auf dem Raspberry Pi läuft. Letzteres Skript verbindet sich über TCP mit dem Server; ein
hierfür entwickeltes Protokoll garantiert den kontinuierlichen Datenaustausch.

[pipe: viele daten ströme -> werden zusammengefasst in einem json objekt -> regeln dazu -> in den recommender rein -> es kommen nachrichten raus -> an viele clienten möglich -> vllt bild hinzufügen
]

## Bauen der Server-JAR

Die JAR-Datei kann selbst gebaut werden. Hierzu kann ein beliebiges Build-System genutzt werden. Folgende Dependencies
werden verwendet:

- OkHttp: https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
- org.json (JSON in Java): https://github.com/stleary/JSON-java

### Autoren

- Denis Hessel, d.hessel@wwu.de
- Julian Koch, julian.koch@tum.de
