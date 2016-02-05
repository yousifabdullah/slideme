**Aihe:** 8-peli (8-puzzle)

> Toteutetaan interaktiivinen versio 8-pelist� graafisella k�ytt�liittym�ll�. Pelin ajatuksena on j�rjest�� numeroidut ruudut luvuista 1..8 suuruusj�rjestykseen siirt�m�ll� aina yksi ruutu kerrallaan vapaaruudun tilalle, kun se on naapurina pysty- tai vaakatasolla (3*3-ruudun suuruisella alustalla). Pelaaja voi my�s pyyt�� ruutujen sekoituksen, kuitenkin niin ettei peli koskaan saata ruutuja tilanteeseen, jota ei ole mahdollista ratkaista. Lis�ksi voi k�ytt�� teko�ly� nykyisen pelitilanteen ratkaisemiseen. Teko�lyn kannalta on olennaista, ett� ratkaisuun p��st��n siirt�en mahdollisimman v�h�n ruutuja. Samalla peli pit�� kirjaa tehdyist� siirroista ja ilmoittaa pelaajalle, kun ruudut ovat taas j�rjestyksess�.

**K�ytt�j�t:** Pelaaja

**Pelaajan toiminnot:**

* Numeroidun ruudun siirto vapaaruudun tilalle
  * onnistuu, kun vapaaruutu on naapurina pysty- tai vaakatasolla
* Ruutujen sekoitus uuden pelitilanteen luomiseksi
* Simulaation k�ynnistys, jossa teko�ly ratkaisee pelitilanteen

**Tarkempi selvitys projektista:**

* Toteutetaan A*-algoritmi sek� BFS-algoritmi pelitilanteen ratkaisemiseen. Algoritmien aikavaativuuden tavoitteena on O(|V| + |E|). Tilavaativuuden kannalta on olennaisinta s�ilytt�� 8-pelin peliruutujen j�rjestys taulukossa, joka tarvittaessa kopioidaan muistiin, siis O(n). P��asiallinen l�hde projektille: https://www.cs.princeton.edu/courses/archive/fall12/cos226/assignments/8puzzle.html ja erityisesti A*-algoritmille: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html.

* Valitsin kyseisen projektin, koska se on minulle mielenkiintoinen ongelma: miten erilaiset algoritmit ratkaisevat saman ongelman ja eroavatko ne nopeudessa tai tehokkuudessa olennaisesti. 8-peli suo yksinkertaisen ymp�rist�n t�llaiselle kokeilulle, kun taas toisaalta monimutkaisempi ongelma (esim. 15-peli) rajoittaa k�ytett�vi� algoritmeja huomattavasti. A*-algoritmi kiehtoaa minua my�s silt� osin, ett� se ei ollut osana tira-kurssia, mutta sit� sivuttiin lyhyesti "ylikurssina". Erityisesti teko�ly peleiss� on minulle kiinnostava aihe ja t�st� saan hyv�t ev��t kokeilemaan teko�lyn kehitt�mist� peliss�.

* Peli toteutetaan graafisella k�ytt�liittym�ll� ja pelaaja voi hiirell� osoittaa ruutuja, siirt�en niit� aina vapaaruutuun yksi kerrallaan. Muut toiminnallisuudet ovat peliruutujen sekoitus uuden pelitilanteen luomiseksi, joka toteutetaan algoritmisesti, sek� simulaation k�ynnistys, jossa tek��ly ratkaisee pelitilanteen.

**M��rittelyvaiheen luokkakaavio:**

![Projektin p��luokat ovat: UI, Solver ja Board.](luokkakaavio.png)

**Konsepti graafiselle k�ytt�liittym�lle:**

![Ominaista konseptille on minimaalisuus ja selkeys.](kayttoliittyma.png)