# Käyttöohje  
Lataa tiedosto [otm-library-1.1.jar](https://github.com/alemati/otm-harjoitustyo/releases/tag/otm-library-1.1) ja lataa tietokanta [otmLibraryDatabase.db](https://github.com/alemati/otm-harjoitustyo/releases/tag/otm-library-1.1). Varmista että molemmat tiedostot ovat samassa kansiossa.  
## Ohjelman käynnistäminen  
Ohjelma käynnistetään komennolla 
```
java -jar otm-library-1.1.jar
```  
## Kirjautuminen kirjaston työntekijänä
Kirjautuminen onnistuu kahdella tavalla. Tarkistetaan nyt tilannetta jossa järjeltelmään kirjaudutaan krjaston työntekijänä. Saadakseen admin-oikeudet _Username_ kentään pitäisi syötää _admin_ ja Password kentään pitäisi syöttää _1234_  
<img src="https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/kuvat/adminLogin.png" width="500">

## Kirjaston työntekijän (admin) mahdollisuudet  
Kirjaston työntekijä näkee kaikkien järjestelmän käyttävien henkilöiden tiedot, voi tarkista mitkä kirjat ovat nyt lainassa (Show all borrowed books-nappi) ja halutessa voi tsekata käyttäjän henkilökohtainen sivu josta näkyy hänen lainatut kirjat. Henkilökohtaisella sivulla admin voi lisäksi poistaa käyttäjän. Tehdessä niin, kaikki poistettavan käyttäjän lainatut kirjat myöskin poistetaan tietokannasta.  
<img src="https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/kuvat/adminPage.png" width="500">    
<img src="https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/kuvat/adminDeleteUser.png" width="500">  
Kirjaston työntekijä voi myös lisätä kirjaston tietokantaan uusia kirjoja (Add book-nappi) tai poistaa jo käytettävissä olevia kirjoja (Delete selected book-nappi).  
<img src="https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/kuvat/adminAddBook.png" width="500">
