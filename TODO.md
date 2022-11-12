#TODO
- Logging (File + Console)
- Katalog-Druck-Funktion mit den aktuellen Nummern nach dem Speichern
- Thumbnail creation might take too long - wird gerade synchron mit jedem Bild erledigt, weil ich das Thumbnail
  auch mehr oder weniger sofort danach im Anschluss brauche. Muss aber eventuell in einen async Kontext gezogen werden,
  und Javascript lädt die Bilder nach und nach, bis sie fertig erstellt wurden?
- Maximale Upload-Anzahl sollte vielleicht auf 50 oder so beschränkt werden? Auch, um kein pagination in die 
  multiple-View einbauen zu müssen - das würde nämlich die derzeitige Wiederherstellen-Funktionalität kaputt machen.
- Es werden mit Sicherheit unterschiedliche Preise kommen - je nach Gruppenbild etc. Also beim Upload schon "Gruppen"
  (iSv Tags) angeben können.

#MAYBE
- image hashcode, um bereits hochgeladene Bilder wiederzuerkennen
- unterschiedliche Einzelpreise an den Bildern
- Tags und Suche nach Tags