# Testidokumentti  

## Yksikkö- ja integraatiotestaus  
Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

### DAO-luokat  
DAO-luokkien toiminnalisuuden testausta varten on luotu erillinen tietokanta. Sen avulla JUnit testit tarkastavat kuinka DAO-luokkien metodit toimivat käytännössä testi syötteillä.  

### Testauskattavuus  
Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 94% ja haarautumakattavuus 96%

## Järjestelmätestaus  
Sovelluksen järjestelmätestaus on suoritettu manuaalisesti. Kaikki [käyttöohjeessa](https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/k%C3%A4ytt%C3%B6ohje.md) esitetyt toimenpiteet on testattu manuaalisesti. Toimiakseen sovellus vaatii sopivan otmLibraryDatabase.db tietokannan, jonka saa ladattua [tästä](https://github.com/alemati/otm-harjoitustyo/releases/tag/otm-library-1.1).  

## Sovellukseen jääneet laatuongelmat  
Toimiakseen sovellus vaatii valmin SQLite tietokannan, joka on nimetty oikein ja jossa on jo luotu tarpellisia tietokantatauluja. Ongelman voisi ratkaistaa luomalla tietokannan ja täyttämällä sen sopivilla tietokantatauluilla ohjelman käynnistys hetkellä, jos sopivaa tietokantaa ei löyty heti.
