Il **Dockerfile** crea un container basato su _OpenJDK_ eseguendovi all' interno _out/artifacts/app/app.js_

Per aggiungere velocemente nuove funzionalità al centralino può risultare comodo alterare il file **docker-compose.yml** 
rimuovendo la parte relativa a __fastagi__.

In questo modo si può avviare l'istanza di Asterisk in un container ed avviare il server FastAGI comodamente da un IDE.

**N.B:** Ogni nuovo controller AGI che viene creato deve essere aggiunto nel file  **fastagi-mapping.properties**
