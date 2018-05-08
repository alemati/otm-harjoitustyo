# Kirjastojärjestelmä  

## Dokumentaatio  
[Käyttöohje](https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/k%C3%A4ytt%C3%B6ohje.md)    
[Määrittelydokumentti](https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/maarittelydokumentti.md)   
[Arkitehtuurikuvaus](https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/arkitehtuurikuvaus.md)    
[Testidokumentti](https://github.com/alemati/otm-harjoitustyo/blob/master/dokumentointi/Testidokumentti.md)  
[Työaikakiejanpito](https://github.com/alemati/otm-harjoitustyo/blob/master/ty%C3%B6aikakirjanpito.md)

## Release  
[Release.Vol.1.1](https://github.com/alemati/otm-harjoitustyo/releases/tag/otm-library-1.1)

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

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _otm-kirjasto-1.0-SNAPSHOT.jar_



### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/alemati/otm-harjoitustyo/blob/master/otm-kirjasto/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```
