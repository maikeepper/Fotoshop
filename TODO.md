#TODO
- Purchase/Download action zum File Stream bauen
- Einkauf / Paypal
  --> cart/checkout view dann als Paypal-Startseite
  --> mit E-Mail-Eingabe-Feld, um den Download-Link nachher auch per E-Mail zu versenden
  --> und redirect zur Download-Seite purchase/show nach erfolgreichem Kauf

- FotoUpload gibt Command Validation Error beim Upload, obwohl alles geht.
- Gutscheine
- Tags bearbeiten
- Katalog-Druck-Funktion mit den aktuellen Nummern nach dem Speichern
- Maximale Upload-Anzahl sollte vielleicht auf 50 oder so beschränkt werden? Auch, um kein pagination in die 
  multiple-View einbauen zu müssen - das würde nämlich die derzeitige Wiederherstellen-Funktionalität kaputt machen.
- Aber pagination in die Fotos list
- Suche/Filter nach Nummern und nach Tags
- Auswahl zurücksetzen Knopf in der Fotos list (auch automatisch beim Laden der Seite, und beim Logout)
- Fotos sollten nicht gelöscht werden können, sondern nur als deleted markiert und nicht mehr in der Liste auftauchen 
  (jedenfalls nachdem sie hochgeladen und gespeichert und damit auch potenziell gekauft worden sind)
- Login-Code sollte case insensitiv sein
- /purchase/index aufhübschen (fotos -> fotos.size, +dateCreated)

#MAYBE
- image hashcode, um bereits hochgeladene Bilder wiederzuerkennen
- unterschiedliche Einzelpreise an den Bildern
- Thumbnail creation might take too long - wird gerade synchron mit jedem Bild erledigt, weil ich das Thumbnail
  auch mehr oder weniger sofort danach im Anschluss brauche. Muss aber eventuell in einen async Kontext gezogen werden,
  und Javascript lädt die Bilder nach und nach, bis sie fertig erstellt wurden?
- User mit Tags verknüpfen - U1 darf nur Bilder mit Tag T1 sehen (many-to-many)
