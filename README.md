# Kirjastojärjestelmä  
## Dokumentaatio  
[Käyttöohje](https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/k%C3%A4ytt%C3%B6ohje.md)    
[Määrittelydokumentti](https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/maarittelydokumentti.md)    
[Release](https://github.com/alemati/otm-harjoitustyo/releases/tag/otm-library-1.1)  
[Arkitehtuurikuvaus](https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/arkitehtuurikuvaus.md)  

## Komentorivitoiminnot 

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _otm-kirjasto-1.0-SNAPSHOT.jar_



### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/mluukkai/OtmTodoApp/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```
