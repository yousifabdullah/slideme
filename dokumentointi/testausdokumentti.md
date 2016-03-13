**Yksikkötestaus:**

* State.java (*com.yousif.slideme.ai*)
  * State-apuluokka ylläpitää tekoälyn välivaiheita ja sen keskeinen toimintaperiaate on jatkaa välivaiheiden ketjua aina seuraavalla mahdollisella iteraatiolla. Koska tekoälyn toimintaperiaate on läpikäytävien iteraatioiden armoilla, on luokan toiminnan oltava takuuvarma. Yksikkötestit keskittyvätkin lähinnä uusien iteraatioiden luomiseen sallituissa ja kielletyissä tapauksissa sekä rajatapauksissa. Kun nämä ovat katettu, voidaan olla riittävän varmoja luokan toiminnasta eli pystytään olettaa, ettei tekoälyn hakualgoritmi käy läpi iteraatioita, jotka eivät esim. noudata 8-pelin sääntöjä.

* Array.java (*com.yousif.slideme.core*)
  * Array-apuluokan toimivuus on hyvin tärkeää koko projektin kannalta, eli laajamittaiset testit ovat myös tärkeitä. Luokka määrittää toiminnallisuudet tärkeimmille metodeille, joilla käsitellään int[]-taulukkoja eli erityisesti pelitilannetta sekä tekoälyn välivaiheita eli iteraatioita. Vaikka luokan sekoitusalgoritmin oikeellisuutta on hyvin vaikea testata kunnolla, on kaikki muu mahdollinen testattu, kuten esim. yksittäisten alkioiden vaihto keskenään, taulukon manuaalinen kopiointi ja inversioiden tarkistus.

* MinHeap.java (*com.yousif.slideme.struc*)
  * MinHeap-tietorakenne on hakualgoritmille olennainen ja testit tarkistavat tietorakenteen käyttäytymisen yleisissä tapauksissa sekä rajatapauksissa. Erityisesti lisäys ja alimman prioriteetin solmun nouto ovat ensisijaisia toimintoja, joita on testattu.

* UniqueSet.java (*com.yousif.slideme.struc*)
  * UniqueSet-tietorakennetta käytetään myös osana hakualgoritmia. Se on erikoistietorakenne, joka korvaa hajautustaulun. Tietorakenteen toiminnan osalta hyvin tärkeä optimointi on testattu rajatapausten osalta kattavasti, joka takaa, että tarkistettavat iteraatiot varmasti löytyvät taulukosta. Muutoin yleistoimintaa on testattu, vaikka tietorakenne on yksiselitteinen toiminnaltaan.

**Suorituskykytestaus:**

* Tekoälyn hakualgoritmi toteuttaa kaksi eri algoritmia halutun heuristiikan perusteella. Solver-luokasta löytyvä USE_BESTFIRST-vakio määrittää käytettävän heuristiikan (oletus: false eli hakualgoritmi toteuttaa A*-algoritmin, muutoin hakualgoritmi noudattaa BFS-algoritmin toimintaa). Suorituskykyä on testattu pelin graafista käyttöliittymää hyödyntäen, kuitenkin niin että: a) simulaatio jätetään huomioimatta ja b) sekuntikello käynnistyy ennen findPath()-metodin kutsua ja pysähtyy kutsun jälkeen Simulation-luokassa.

  * Samoja pelitilanteita ei testattu molemmilla heuristisilla funktioilla, koska näin vältytään tilanteelta, jossa välimuistiin jäävät aikaisemmin läpikäydyt iteraatiot hakualgoritmin ensimmäisen kutsun jälkeen. Koska Javan roskien keruu ei toimi reaaliajassa, on tällainen "flow control" bugi mahdollinen. Hakualgoritmin toimintaperiaate on kuitenkin yksinkertainen heuristiikasta riippumatta ja voidaan olettaa, että läpikäytyjen iteraatioiden määrä ratkaisee joka tapauksessa.

  * A*-algoritmin aikavaativuus toteutuu odotetusti eli 30 eri pelitilanteen testauksesta muodostuva käyrä muistuttaa tavoitetta eli O(|V| + |E|)-aikavaativuutta. Erityisesti huomataan, että O(|E|) voi vaihdella O(1) ja O(|V|^2) välillä.

  ![A*-algoritmin aikavaativuus](aikavaativuus1.png)

  * Vastaavasti BFS-algoritmin aikavaativuus toteutuu samoin kuin edellisessä (myös 30 eri pelitilanteesta), mutta kesto iteraatioiden määrän suhteen vaikuttaa tasaisemmalta. Tämä on perusteltavissa sillä, että käytetty heuristinen funktio on vakioaikainen toisin kuin A*-algoritmissa, jossa Manhattan-etäisyys vaatii O(n)-aikavaativudella toimivan pelitilanteen läpikäynnin.

  ![BFS-algoritmin aikavaativuus](aikavaativuus2.png)

**Yhteenveto:**

* Yksikkötestit ovat aina uudelleenajettavissa ja yksiselitteisiä. PIT-raportin mukaan kaikilla testatuilla luokilla on yleisesti vähintään 90%:n rivikattavuus sekä 60%:n mutaatiokattavuus. Suorituskykytestaukseen vaadittava pieni muutos Simulation-luokkaan on yksinkertainen ja silti antaa luotettavan tiedon hakualgoritmin suorituskyvystä. Iteraatioiden määrän saa hakualgoritmin palauttaman taulukon koosta, joka sisältää tarvittavat välivaiheet simulaatiolle. Testausta suoritettiin jopa 100 eri pelitilanteella, mutta tiedon hajonta oli vaikeasti luettavissa kaaviona. 30 eri pelitilanteen testaus vaikutti sopivalta, tuottaen selkeän kaavion sekä riittävän hajonnan, joka sulkee pois esim. Javan ajoittaisesta roskien keruusta aiheutuvan hidastumisen.