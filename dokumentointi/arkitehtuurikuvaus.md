# Arkitehtuuri kuvaus  

## Rakenne  
Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria, ja koodin pakkausrakenne on seuraava:  
<img src="https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/kuvat/arkRakenne.png" width="100">  
Pakkaus ui sisältää JavaFX:llä toteutetun käyttöliittymän, domain sovelluslogiikan ja dao tietojen pysyväistallennuksesta vastaavan koodin.  

## Käyttöliittymä  
Käyttöliittymä sisältää kuusi major näkymää jotka ovat toteuttuneet omina Scene-olioina ja jotka tulevat näkyviin vanhan näkymän päälle aina kun tietty nappi vanhassa (nykyisessä) näkymässä on painettu. Eli päästage-olio on aina sama. 
* kirjautuminen  
* uuden käuttäjän luominen  
* tavallisen käyttäjän sivu  
* admin sivu  
* uuden kirjan lisääminen  
* luettelo tietyn käyttäjän lanatuista kirjoista    
* luettelo jokaisesta lainatusta kirjasta  

noiden lisääksi on vielä kaksi pikku varmistus boxia jotka esintyväät silloin kun käyttäjältä halutaan saada vahvistuksen esim. käyttäjän poistossa. Noilla boxeilla on oma Stage olio jonka pitäisi sulkea vastaamalla varmistus kysymykseen.

## Tietokantarakenne  
Sovelluksen muisti soveltaa sqlite-tietokantaa. Tietokannassa on kaksi tietokantataulua (User, joka vastaa käyttäjää ja Book, joka vastaa kirjaa). Koko sovellus pyörii näiden kahden tietokantataulun suhteiden välillä.  
<img src="https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/kuvat/User-Book.png" width="500">  
