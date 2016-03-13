**Yksikk�testaus:**

* State.java (*com.yousif.slideme.ai*)
  * State-apuluokka yll�pit�� teko�lyn v�livaiheita ja sen keskeinen toimintaperiaate on jatkaa v�livaiheiden ketjua aina seuraavalla mahdollisella iteraatiolla. Koska teko�lyn toimintaperiaate on l�pik�yt�vien iteraatioiden armoilla, on luokan toiminnan oltava takuuvarma. Yksikk�testit keskittyv�tkin l�hinn� uusien iteraatioiden luomiseen sallituissa ja kielletyiss� tapauksissa sek� rajatapauksissa. Kun n�m� ovat katettu, voidaan olla riitt�v�n varmoja luokan toiminnasta eli pystyt��n olettaa, ettei teko�lyn hakualgoritmi k�y l�pi iteraatioita, jotka eiv�t esim. noudata 8-pelin s��nt�j�.

* Array.java (*com.yousif.slideme.core*)
  * Array-apuluokan toimivuus on hyvin t�rke�� koko projektin kannalta, eli laajamittaiset testit ovat my�s t�rkeit�. Luokka m��ritt�� toiminnallisuudet t�rkeimmille metodeille, joilla k�sitell��n int[]-taulukkoja eli erityisesti pelitilannetta sek� teko�lyn v�livaiheita eli iteraatioita. Vaikka luokan sekoitusalgoritmin oikeellisuutta on hyvin vaikea testata kunnolla, on kaikki muu mahdollinen testattu, kuten esim. yksitt�isten alkioiden vaihto kesken��n, taulukon manuaalinen kopiointi ja inversioiden tarkistus.

* MinHeap.java (*com.yousif.slideme.struc*)
  * MinHeap-tietorakenne on hakualgoritmille olennainen ja testit tarkistavat tietorakenteen k�ytt�ytymisen yleisiss� tapauksissa sek� rajatapauksissa. Erityisesti lis�ys ja alimman prioriteetin solmun nouto ovat ensisijaisia toimintoja, joita on testattu.

* UniqueSet.java (*com.yousif.slideme.struc*)
  * UniqueSet-tietorakennetta k�ytet��n my�s osana hakualgoritmia. Se on erikoistietorakenne, joka korvaa hajautustaulun. Tietorakenteen toiminnan osalta hyvin t�rke� optimointi on testattu rajatapausten osalta kattavasti, joka takaa, ett� tarkistettavat iteraatiot varmasti l�ytyv�t taulukosta. Muutoin yleistoimintaa on testattu, vaikka tietorakenne on yksiselitteinen toiminnaltaan.

**Suorituskykytestaus:**

* Teko�lyn hakualgoritmi toteuttaa kaksi eri algoritmia halutun heuristiikan perusteella. Solver-luokasta l�ytyv� USE_BESTFIRST-vakio m��ritt�� k�ytett�v�n heuristiikan (oletus: false eli hakualgoritmi toteuttaa A*-algoritmin, muutoin hakualgoritmi noudattaa BFS-algoritmin toimintaa). Suorituskyky� on testattu pelin graafista k�ytt�liittym�� hy�dynt�en, kuitenkin niin ett�: a) simulaatio j�tet��n huomioimatta ja b) sekuntikello k�ynnistyy ennen findPath()-metodin kutsua ja pys�htyy kutsun j�lkeen Simulation-luokassa.

  * Samoja pelitilanteita ei testattu molemmilla heuristisilla funktioilla, koska n�in v�ltyt��n tilanteelta, jossa v�limuistiin j��v�t aikaisemmin l�pik�ydyt iteraatiot hakualgoritmin ensimm�isen kutsun j�lkeen. Koska Javan roskien keruu ei toimi reaaliajassa, on t�llainen "flow control" bugi mahdollinen. Hakualgoritmin toimintaperiaate on kuitenkin yksinkertainen heuristiikasta riippumatta ja voidaan olettaa, ett� l�pik�ytyjen iteraatioiden m��r� ratkaisee joka tapauksessa.

  * A*-algoritmin aikavaativuus toteutuu odotetusti eli 30 eri pelitilanteen testauksesta muodostuva k�yr� muistuttaa tavoitetta eli O(|V| + |E|)-aikavaativuutta. Erityisesti huomataan, ett� O(|E|) voi vaihdella O(1) ja O(|V|^2) v�lill�.

  ![A*-algoritmin aikavaativuus](aikavaativuus1.png)

  * Vastaavasti BFS-algoritmin aikavaativuus toteutuu samoin kuin edellisess� (my�s 30 eri pelitilanteesta), mutta kesto iteraatioiden m��r�n suhteen vaikuttaa tasaisemmalta. T�m� on perusteltavissa sill�, ett� k�ytetty heuristinen funktio on vakioaikainen toisin kuin A*-algoritmissa, jossa Manhattan-et�isyys vaatii O(n)-aikavaativudella toimivan pelitilanteen l�pik�ynnin.

  ![BFS-algoritmin aikavaativuus](aikavaativuus2.png)

**Yhteenveto:**

* Yksikk�testit ovat aina uudelleenajettavissa ja yksiselitteisi�. PIT-raportin mukaan kaikilla testatuilla luokilla on yleisesti v�hint��n 90%:n rivikattavuus sek� 60%:n mutaatiokattavuus. Suorituskykytestaukseen vaadittava pieni muutos Simulation-luokkaan on yksinkertainen ja silti antaa luotettavan tiedon hakualgoritmin suorituskyvyst�. Iteraatioiden m��r�n saa hakualgoritmin palauttaman taulukon koosta, joka sis�lt�� tarvittavat v�livaiheet simulaatiolle. Testausta suoritettiin jopa 100 eri pelitilanteella, mutta tiedon hajonta oli vaikeasti luettavissa kaaviona. 30 eri pelitilanteen testaus vaikutti sopivalta, tuottaen selke�n kaavion sek� riitt�v�n hajonnan, joka sulkee pois esim. Javan ajoittaisesta roskien keruusta aiheutuvan hidastumisen.