**Aihe:** 8-peli (8-puzzle)

> Toteutetaan interaktiivinen versio 8-pelistä graafisella käyttöliittymällä. Pelin ajatuksena on järjestää numeroidut ruudut luvuista 1..8 suuruusjärjestykseen siirtämällä aina yksi ruutu kerrallaan vapaaruudun tilalle, kun se on naapurina pysty- tai vaakatasolla. Pelaaja voi myös pyytää ruutujen sekoituksen, kuitenkin niin ettei peli koskaan saata ruutuja tilanteeseen, jota ei ole mahdollista ratkaista. Lisäksi on mahdollista käyttää tekoälyä nykyisen pelitilanteen ratkaisemiseen. Tekoälyn kannalta on olennaista, että ratkaisuun päästään siirtäen mahdollisimman vähän ruutuja. Samalla peli pitää kirjaa tehdyistä siirroista ja ilmoittaa pelaajalle, kun ruudut ovat taas järjestyksessä.

**Käyttäjät:** Pelaaja

**Pelaajan toiminnot:**

* Numeroidun ruudun siirto vapaaruudun tilalle
  * onnistuu, kun vapaaruutu on naapurina pysty- tai vaakatasolla
* Ruutujen sekoitus uuden pelitilanteen luomiseksi
* Simulaation käynnistys, jossa tekoäly ratkaisee pelitilanteen