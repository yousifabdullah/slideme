**Aihe:** 8-peli (8-puzzle)

> Toteutetaan interaktiivinen versio 8-pelistä graafisella käyttöliittymällä. Pelin ajatuksena on järjestää numeroidut ruudut luvuista 1..8 suuruusjärjestykseen siirtämällä aina yksi ruutu kerrallaan vapaaruudun tilalle, kun se on naapurina pysty- tai vaakatasolla (3x3-ruudun suuruisella alustalla). Pelaaja voi myös sekoittaa ruudut, kuitenkin niin ettei peli koskaan saata ruutuja tilanteeseen, jota ei ole mahdollista ratkaista. Lisäksi voi käyttää tekoälyä nykyisen pelitilanteen ratkaisemiseen. Tekoälyn kannalta on olennaista, että ratkaisuun päästään siirtäen mahdollisimman vähän ruutuja. Samalla peli pitää kirjaa tehdyistä siirroista ja ilmoittaa pelaajalle, kun ruudut ovat taas järjestyksessä.

**Käyttäjät:** Pelaaja

**Pelaajan toiminnot:**

* Numeroidun ruudun siirto vapaaruudun tilalle ([sekvenssikaavio](kayttotapaus1.png))
  * onnistuu, kun vapaaruutu on naapurina pysty- tai vaakatasolla
* Ruutujen sekoitus uuden pelitilanteen luomiseksi ([sekvenssikaavio](kayttotapaus2.png))
* Simulaation käynnistys, jossa tekoäly ratkaisee pelitilanteen ([sekvenssikaavio](kayttotapaus3.png))

**Tarkempi selvitys projektista:**

* Toteutetaan A*- sekä BFS-hakualgoritmi pelitilanteen ratkaisemiseen. Algoritmien aikavaativuuden tavoitteena on O(|V| + |E|). Tilavaativuuden kannalta on olennaisinta säilyttää 8-pelin peliruutujen järjestys taulukossa, joka tarvittaessa kopioidaan muistiin, siis O(n).

  * Pääasiallinen lähde projektille: https://www.cs.princeton.edu/courses/archive/fall12/cos226/assignments/8puzzle.html ja erityisesti hakualgoritmien heuristiikalle: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html.

* Valitsin kyseisen projektin, koska se on minulle mielenkiintoinen ongelma: miten erilaiset algoritmit ratkaisevat saman ongelman ja eroavatko ne nopeudessa tai tehokkuudessa olennaisesti. 8-peli suo yksinkertaisen ympäristön tällaiselle kokeilulle, kun taas toisaalta monimutkaisempi ongelma (esim. 15-peli) rajoittaa käytettäviä algoritmeja huomattavasti. A*-hakualgoritmi kiehtoaa minua myös siltä osin, että se ei ollut osana tira-kurssia, mutta sitä sivuttiin lyhyesti "ylikurssina". Erityisesti tekoäly peleissä on minulle kiinnostava aihe ja tästä saan hyvät eväät kokeilemaan tekoälyn kehittämistä pelissä.

* Peli toteutetaan graafisella käyttöliittymällä ja pelaaja voi hiirellä osoittaa ruutuja, siirtäen niitä aina vapaaruutuun yksi kerrallaan. Muut toiminnallisuudet ovat peliruutujen sekoitus uuden pelitilanteen luomiseksi, joka toteutetaan algoritmisesti, sekä simulaation käynnistys, jossa teköäly ratkaisee pelitilanteen.

**Luokkakaavio ohjelmakoodin perusteella:**

![Projektin pääluokat ovat: UI, Solver ja Board.](luokkakaavio.png)

**Rakennekuvaus:**

* Projekti rakentuu pitkälti käyttöliittymän eli UI-luokan ympärille, joka vastaa käyttäjän syötteestä ja esittää tekoälyn simulaation käyttäen apunaan Simulation-luokkaa. Käyttöliittymä käsittelee pelitilannetta sitä ylläpitävän Board-luokan kautta, joka puolestaan toteuttaa kaikki tarvitsemansa metodit Array-luokan avulla. Array-luokka käsittelee taulukkoa, jollaisena pelitilanne tallennetaan, siis kaikki peliruudut ovat omia alkioitaan yhdessä taulukossa.

* Myös tekoäly eli Solver-luokkaan toteutettu hakualgoritmi käyttää Board-luokan kautta pelitilannetta, eli pääasiallisesti peli elääkin Board-luokassa. Tekoälyllä on myös omia yhteyksiään State-luokkaan, joka ylläpitää tekoälyn välivaiheita ja pitää niistä ketjua, sekä UniqueSet- että MinHeap-luokkiin, jotka toteuttavat hakualgoritmin käyttämät tietorakenteet. Niin Solver- kuin State-luokat käyttävät myös Array-luokkaa apunaan taulukkojen käsittelyyn, jollaisina tekoälyn välivaiheet eli iteraatiot säilytetään. State-luokan muodostamasta ketjusta voidaankin lopulta löytää kaikki tarpeelliset välivaiheet lähtötilanteesta ratkaisuun, mikä mahdollistaa tekoälyn toiminnallisuuden.

**Konsepti graafiselle käyttöliittymälle:**

![Ominaista konseptille on minimaalisuus ja selkeys.](kayttoliittyma.png)