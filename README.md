# FitHit by Biasiolo & Dalla Valeria
*Consegna del 22/03*

WebService: https://github.com/313-Teo/FitnessWebService

Funzionalità implementate: 
Login, Register, Home (Atleta e Trainer), Profilo Utente, 
Scheda Atleta (Atleta), Nuovo Workout (Atleta), Workout Preview (Atleta)
Crea Nuovo Workout (Trainer), I Tuoi Atleti (Trainer)

Implementata anche una pagina DEV nel MainActivity.kt per velocizzare i test, 
terminato il development sarà l'open page dell'app.

Funzionalità presenti ma non implementate: Impostazioni, Statistiche, Inizia Workout

Utenti con dati già presenti nel database per testare l'app:

Atleta: email: biasi@email.com pw:1234

Trainer: email: dalla@email.com pw:5678

-O-

Istruzioni di installazione:

Scaricare il progetto per Android Studio, il Web Service (link della repo in alto) e il database FitHit.sql sempre nella repo del Web Service

-

-WEBSERVICE:

Aprire XAMPP e avviare sia "Apache Web Server" che "MySQL Database"

Tramire la pagina phpMyAdmin creare il database "FitHit"
Importare le tabelle tramite il file FitHit.sql nella sezione "importa" di phpMyAdmin

Aprire IntelliJ e importare il progetto "WebService"

Avviare il WebService dal file "WebServiceApplication.java"

-ANDROID STUDIO:

Aprire Android Studio e importare il progetto "MonitoraggioFitness"

-

Cambiare l'indirizzo IP in string.xml con il proprio indirizzo IP locale
[ es. <string name="url"> http://192.168.15.110:8080/ </string> ]

(puoi trovare il tuo indirizzo IP locale scrivendo il comando ifconfig o ipconfig sul terminale)

-

Assicurarsi di avere un Pixel 4 XL API 33 con Tiramisù API 33 nella sezione Device Manager (sulla sinistra di Android Studio)

In alto premere Add Configurations -> Edit Configurations e aggiungere una nuova configuration:

Selezionare Android App e creare una nuova configuration chiamata "app" con Module "Montioraggio.Fitness.app.main"

Successivamente premere Apply e successivamente ok

-

Tramite il menu "Tools" accedere alla sezione SDK Manager -> Android SDK

Nella sezione Android SDK Location premere "Edit", premere Next per due volte

Attendere il download e premere Finish

Successivamente premere Apply e ok

-

Avviare l'emulatore e avviare l'app 


