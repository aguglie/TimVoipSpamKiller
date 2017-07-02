# **VoIP Spam Killer**

Semplice script per liberarsi dei fastidiosi callcenters su **linea fissa TIM VoIP.**

Se il numero chiamante appartiene alla _blocklist Tellows_, la chiamata viene rifiutata.

## Installazione
### Estrarre i paramentri SIP dal Router
E' necessario essere in possesso di username e password per connettersi in SIP al Router TIM (Fibra)

Si possono facilmente ricavarli lanciando

```bash
curl -k -u 8Z6PlbuD6VFR8KLr:admin --data "Action=GetConfig&ClientID=XX:XX:XX:XX:XX:XX&Cli=XXXXXXXXXX" https://192.168.1.1:8443/SIPGwConfig
```
_Se la password del router è stata cambiata, modifica 'admin' con quella attuale_

- **ClientID=XX:XX:XX:XX:XX:XX**  dove XX:XX:XX:XX:XX:XX è il MAC Address della macchina che farà da centralino e si connetterà al router.
- **Cli=XXXXXXXXXX** dove XXXXXXXXXX è il tuo numero di telefono fisso (senza +39)

Dopo qualche secondo il router risponderà con un XML contenente i dati cercati

```xml
<?xml version="1.0" encoding="UTF-8"?>
<SIPClientConfig>
   <AuthUserName>**04</AuthUserName>
   <AuthPassword>i3uCDw3r432fd3kdso4odob9we456fez05iiW2IrR143HHykQnkS7JK90k7dXqnwdVr</AuthPassword>
   <AuthRealm>modemtelecom.homenet.telecomitalia.it</AuthRealm>
   <Registrar>modemtelecom.homenet.telecomitalia.it</Registrar>
   <RegistrarPort>5065</RegistrarPort>
   <OutboundProxy>modemtelecom.homenet.telecomitalia.it</OutboundProxy>
   <OutboundProxyPort>5065</OutboundProxyPort>
   <TransportProtocol>UDP</TransportProtocol>
</SIPClientConfig>
```

### Lanciare i Container Docker
E' necessario un PC con SO **Linux** _(più info a piè pagina)_

Creiamo un file **docker-compose.yml** sostituendo USERNAME e PASSWORD con quelle ricevute dal router:
```yaml
version: '2'
services:
  moby:
    build: ./Asterisk
    image: guglio/asterisk
    container_name: asteriskTIM
    environment:
      - ROUTER_IP=192.168.1.1
      - USERNAME=**04
      - PASSWORD=i3uCDw3r432fd3kdso4odob9we456fez05iiW2IrR143HHykQnkS7JK90k7dXqnwdVr
    network_mode: "host"
    restart: always

  fastagi:
    build: ./FastAGI
    image: guglio/fastagi
    container_name: fastagi
    ports:
        - "127.0.0.1:4573:4573"
    restart: always
```


Ora basta solo avviare il container:
```bash
docker-compose up -d
```

## Commenti
Utilizzare Java per fare un semplice controllo online è molto dispendioso in termini di risorse;

Si potrebbe eliminare la dipendenza da Java perdendo la possibilità di aggiungere facilmente nuove funzionalità al progetto

## Known Issues
Il router TIM rifiuta tutte le connessioni in SIP da parte di clients che non sono nella sua stessa rete. E' quindi necessario avviare il container di Asterisk in __network_mode: "host"__ all'interno di un PC nella stessa sottorete del router.

Su Windows e macOS la Docker Machine gira su una VM la cui interfaccia di rete **è in NAT** con quella del pc host. Per questo motivo non è possibile lanciare il container su una macchina che non sia Linux.
