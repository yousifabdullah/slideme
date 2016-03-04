**Aihe:** 8-peli (8-puzzle)

> Toteutetaan interaktiivinen versio 8-pelist� graafisella k�ytt�liittym�ll�. Pelin ajatuksena on j�rjest�� numeroidut ruudut luvuista 1..8 suuruusj�rjestykseen siirt�m�ll� aina yksi ruutu kerrallaan vapaaruudun tilalle, kun se on naapurina pysty- tai vaakatasolla (3x3-ruudun suuruisella alustalla). Pelaaja voi my�s sekoittaa ruudut, kuitenkin niin ettei peli koskaan saata ruutuja tilanteeseen, jota ei ole mahdollista ratkaista. Lis�ksi voi k�ytt�� teko�ly� nykyisen pelitilanteen ratkaisemiseen. Teko�lyn kannalta on olennaista, ett� ratkaisuun p��st��n siirt�en mahdollisimman v�h�n ruutuja. Samalla peli pit�� kirjaa tehdyist� siirroista ja ilmoittaa pelaajalle, kun ruudut ovat taas j�rjestyksess�.

**K�ytt�j�t:** Pelaaja

**Pelaajan toiminnot:**

* Numeroidun ruudun siirto vapaaruudun tilalle ([sekvenssikaavio](kayttotapaus1.png))
  * onnistuu, kun vapaaruutu on naapurina pysty- tai vaakatasolla
* Ruutujen sekoitus uuden pelitilanteen luomiseksi ([sekvenssikaavio](kayttotapaus2.png))
* Simulaation k�ynnistys, jossa teko�ly ratkaisee pelitilanteen ([sekvenssikaavio](kayttotapaus3.png))

**Tarkempi selvitys projektista:**

* Toteutetaan A*- sek� BFS-hakualgoritmi pelitilanteen ratkaisemiseen. Algoritmien aikavaativuuden tavoitteena on O(|V| + |E|). Tilavaativuuden kannalta on olennaisinta s�ilytt�� 8-pelin peliruutujen j�rjestys taulukossa, joka tarvittaessa kopioidaan muistiin, siis O(n).

  * P��asiallinen l�hde projektille: https://www.cs.princeton.edu/courses/archive/fall12/cos226/assignments/8puzzle.html ja erityisesti hakualgoritmien heuristiikalle: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html.

* Valitsin kyseisen projektin, koska se on minulle mielenkiintoinen ongelma: miten erilaiset algoritmit ratkaisevat saman ongelman ja eroavatko ne nopeudessa tai tehokkuudessa olennaisesti. 8-peli suo yksinkertaisen ymp�rist�n t�llaiselle kokeilulle, kun taas toisaalta monimutkaisempi ongelma (esim. 15-peli) rajoittaa k�ytett�vi� algoritmeja huomattavasti. A*-hakualgoritmi kiehtoaa minua my�s silt� osin, ett� se ei ollut osana tira-kurssia, mutta sit� sivuttiin lyhyesti "ylikurssina". Erityisesti teko�ly peleiss� on minulle kiinnostava aihe ja t�st� saan hyv�t ev��t kokeilemaan teko�lyn kehitt�mist� peliss�.

* Peli toteutetaan graafisella k�ytt�liittym�ll� ja pelaaja voi hiirell� osoittaa ruutuja, siirt�en niit� aina vapaaruutuun yksi kerrallaan. Muut toiminnallisuudet ovat peliruutujen sekoitus uuden pelitilanteen luomiseksi, joka toteutetaan algoritmisesti, sek� simulaation k�ynnistys, jossa tek��ly ratkaisee pelitilanteen.

**Luokkakaavio ohjelmakoodin perusteella:**

![Projektin p��luokat ovat: UI, Solver ja Board.](luokkakaavio.png)

**Rakennekuvaus:**

* Projekti rakentuu pitk�lti k�ytt�liittym�n eli UI-luokan ymp�rille, joka vastaa k�ytt�j�n sy�tteest� ja esitt�� teko�lyn simulaation k�ytt�en apunaan Simulation-luokkaa. K�ytt�liittym� k�sittelee pelitilannetta sit� yll�pit�v�n Board-luokan kautta, joka puolestaan toteuttaa kaikki tarvitsemansa metodit Array-luokan avulla. Array-luokka k�sittelee taulukkoa, jollaisena pelitilanne tallennetaan, siis kaikki peliruudut ovat omia alkioitaan yhdess� taulukossa.

* My�s teko�ly eli Solver-luokkaan toteutettu hakualgoritmi k�ytt�� Board-luokan kautta pelitilannetta, eli p��asiallisesti peli el��kin Board-luokassa. Teko�lyll� on my�s omia yhteyksi��n State-luokkaan, joka yll�pit�� teko�lyn v�livaiheita ja pit�� niist� ketjua, sek� UniqueSet- ett� MinHeap-luokkiin, jotka toteuttavat hakualgoritmin k�ytt�m�t tietorakenteet. Niin Solver- kuin State-luokat k�ytt�v�t my�s Array-luokkaa apunaan taulukkojen k�sittelyyn, jollaisina teko�lyn v�livaiheet eli iteraatiot s�ilytet��n. State-luokan muodostamasta ketjusta voidaankin lopulta l�yt�� kaikki tarpeelliset v�livaiheet l�ht�tilanteesta ratkaisuun, mik� mahdollistaa teko�lyn toiminnallisuuden.

**Konsepti graafiselle k�ytt�liittym�lle:**

![Ominaista konseptille on minimaalisuus ja selkeys.](kayttoliittyma.png)