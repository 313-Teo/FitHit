# FitHit by Biasiolo & Dalla Valeria
*Consegna del 22/03*

WebService: https://github.com/313-Teo/FitnessWebService

Funzionalità implementate: 
Login, Register, Home (Atleta e Trainer), Profilo Utente, 
Scheda Atleta (Atleta), Nuovo Workout (Atleta), Workout Preview (Atleta)
Crea nuovo Workout (Trainer), I tuoi atleti (Trainer)

Implementata anche una pagina DEV nel MainActivity.kt per velocizzare i test dell'app, 
terminato il development sarà l'open page dell'app.

Funzionalità presenti ma non implementate: Impostazioni, Statistiche, Inizia Workout

Utenti con dati già presenti nel database per testare l'app:

Atleta: email: biasi@email.com pw:1234

Trainer: email dalla@email.com pw:5678

Istruzioni di installazione:

Scaricare il progetto per Android Studio, il Web Service (link della repo in alto) e il database FitHit.sql sempre nella repo del Web Service

WEBSERVICE:

Aprire XAMPP e avviare sia "Apache Web Server" che "MySQL Database"

Tramire la pagina phpMyAdmin creare il database "FitHit"
Importare le tabelle tramite il file FitHit.sql nella sezione "importa" di phpMyAdmin

Aprire IntelliJ e importare il progetto "WebService"

Avviare il WebService dal file "WebServiceApplication.java"

ANDROID STUDIO:

Aprire Android Studio e importare il progetto "Monitoraggio-Fitness"

Cambiare l'indirizzo IP in string.xml con il proprio indirizzo IP locale
[ es. <string name="url"> http://192.168.15.110:8080/ </string> ]

Aggiungere un Pixel 4XL API 33 con l'implementazione di Tiramisù

