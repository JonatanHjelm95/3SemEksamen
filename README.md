# Velcommen til CA3 af en gruppe?
## Mål med opgaven:
Lave en template der kan bruges til hurtigt at starte et project op, dvs. at det inkludere en react frontend (med router), en backend med REST-api, opsat swagger til at documentere API'en, sikkerhed gennem toke, Login og Logud funktion.   

## Deployment.
Vi bruger Travis til at deploye vores endelige version af projektet til en droplet. 
Denne er standard til [nfmbang.dk](https://nfmbang.dk/ca3). Men kan også findes på [matheradical.dk ](https://matheradical.dk/ca3 ) og [jmhdat3.com](https://jmhdat3.com/ca3).

## Sikkerhed:
Vi bruger en kombination af hashing og salt til at beskytte vores passwords i databasen, til dette har vi anvendt bcrypt. Ellers anvender vi HTTPS gennem nginx.   

