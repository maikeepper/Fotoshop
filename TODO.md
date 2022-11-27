#TODO
- Warenkorb
  --> cart/index oder cart/checkout dann als Paypal-Startseite
  --> mit E-Mail-Eingabe-Feld, um den Download-Link nachher auch per E-Mail zu versenden
  --> und redirect zur Download-Seite nach erfolgreichem Kauf

- Foto-Downloadseite mit Käufen, die auch aus einer E-Mail später noch einmal erreichbar sind. 
  --> Domainklasse mit UUID als Id 
  --> Show-View mit Download-als-Zip und Einzeldownloads der Originalbilder.

- FotoUpload gibt Command Validation Error beim Upload, obwohl alles geht.
- Gutscheine
- Tags bearbeiten
- Katalog-Druck-Funktion mit den aktuellen Nummern nach dem Speichern
- Maximale Upload-Anzahl sollte vielleicht auf 50 oder so beschränkt werden? Auch, um kein pagination in die 
  multiple-View einbauen zu müssen - das würde nämlich die derzeitige Wiederherstellen-Funktionalität kaputt machen.
- Suche/Filter nach Nummern und nach Tags
- FotoController und FotoService sind wahrscheinlich überflüssig

#MAYBE
- image hashcode, um bereits hochgeladene Bilder wiederzuerkennen
- unterschiedliche Einzelpreise an den Bildern
- Thumbnail creation might take too long - wird gerade synchron mit jedem Bild erledigt, weil ich das Thumbnail
  auch mehr oder weniger sofort danach im Anschluss brauche. Muss aber eventuell in einen async Kontext gezogen werden,
  und Javascript lädt die Bilder nach und nach, bis sie fertig erstellt wurden?
