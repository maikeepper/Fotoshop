#TODO
- User (=> Codes) Management (inkl. Rollen, fehlt leider in den automatisch erzeugten Views)
    --> user/index + Spalte zum Ändern der Rollen für Admins
    --> neuer User angelegt von STAFF: automatisch Elternrolle vergeben
- Foto delete: method not allowed

- Tags bearbeiten
- Katalog-Druck-Funktion mit den aktuellen Nummern nach dem Speichern
- Maximale Upload-Anzahl sollte vielleicht auf 50 oder so beschränkt werden? Auch, um kein pagination in die 
  multiple-View einbauen zu müssen - das würde nämlich die derzeitige Wiederherstellen-Funktionalität kaputt machen.
- Suche/Filter nach Nummern und nach Tags

#MAYBE
- image hashcode, um bereits hochgeladene Bilder wiederzuerkennen
- unterschiedliche Einzelpreise an den Bildern
- Thumbnail creation might take too long - wird gerade synchron mit jedem Bild erledigt, weil ich das Thumbnail
  auch mehr oder weniger sofort danach im Anschluss brauche. Muss aber eventuell in einen async Kontext gezogen werden,
  und Javascript lädt die Bilder nach und nach, bis sie fertig erstellt wurden?
