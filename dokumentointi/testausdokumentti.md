**29.1.2016:**

* Laadittu kattavat yksikk�testit Array-apuluokalle. Luokan toimivuus on hyvin t�rke�� koko projektin kannalta, eli laajamittaiset testit ovat my�s t�rkeit�. Apuluokan kaikkia metodeja ei kuitenkaan voi testata, esim. sekoitusalgoritmin toimintaa on vaikea testata mill��n tavalla eik� sen oikeellisuutta pysty nyt kunnolla arvioimaan. Yksikk�testej� ei laadittu Board-luokalle lainkaan, koska siin� ei ole mit��n erityist�, mit� pit�isi testata ja se l�hinn� kutsuu Array-apuluokan metodeja omissa metodeissaan.

**5.2.2016:**

* Yksikk�testej� ei laadittu lainkaan. Koska ohjelmakoodi muokkautui p��osin k�ytt�liittym�n osalta, kului aikaa manuaaliseen k�ytt�liittym�n testaamiseen. Erikseen yksikk�testej� k�ytt�liittym�lle ei siis laadittu. Projektin asetuksia on my�s muutettu niin, ettei k�ytt�liittym�n luokkia sis�llytet� PIT-raportteihin. Manuaaliseen k�ytt�liittym�n testaamiseen kuului mm. eri toiminnallisuuksien kokeilu siin� m��rin, mik� on sallittua ja mik� ei ole sallittua. Peli� on pelattu useita tunteja, kun samalla k�ytt�liittym�� on paranneltu aina sen mukaisesti, kun on parannuskeinoja ja ep�toivottua toimintaa havaittu. K�ytt�kokemukseen on panostettu erityisesti, ettei peli vaikuta kankealta k�yt�n kannalta. Mielipiteit� k�ytt�liittym�st�, komponenttien sijoittelusta sek� toiminnallisuudesta on my�s kysytty l�hipiirist� ja k�ytt�liittym�� muokattu vastaavasti.

**12.2.2016:**

* Array-apuluokan osalta muutama yksikk�testi on laadittu testaamaan uutta aputoimintoa. Muutoin ohjelmakoodi on muokkautunut l�hinn� teko�lyn kannalta, mutta on yh� varhaisessa vaiheessa sen osalta, mink� vuoksi teko�lyyn liittyvi� uusia luokkia ei ole erikseen testattu. Yksikk�testej� on luvasssa, mutta ensiksi on minun ymm�rrett�v� haluamani teko�lyn toiminnallisuus yleisell� tasolla. Halutun toiminnallisuuden aikaansaamiseksi on viel� opittava lis�� teko�lyn toteutuksesta ja viimein toteuttaa se. Mahdollinen ratkaisu mm. teko�lyn v�livaiheiden yll�pitoon on saanut alkunsa. Mik�li toteutus etenee odotetusti, on seuraava looginen askel testata toiminnallisuutta.

**15.2.2016:**

* Laadittu kattavasti uusia testej� State-luokalle, joka yll�pit�� teko�lyn v�livaiheita. Erityisesti tuli huomioida testeiss�, ettei esim. A*-algoritmi k�y l�pi v��r�nlaisia iteraatioita, jolloin kaikki rajatapaukset oli testattava. Erilaisia pelitilanteita testasin empiirisesti ja mittasin teko�lyn suorituskyky� tunnetusti hankalissa tilanteissa. Hiomista on viel� teht�v� ja erityisesti omat tietorakenteet laadittava korvaamaan nykyisen toteutuksen HashSet- ja PriorityQueue-tietorakenteet. Kun n�m� ovat kiitett�v�ss� kunnossa, mit� luultavimmin jo seuraavassa p�ivityksess�, on aika pit�� kirjaa algoritmin tuloksista ja suorituskyvyst�. Viel� ei ole sen saralta mit��n ihmeellist� raportoitavaa, muuta kuin ett� teko�ly nykyisell��n toimii!

**24.2.2016:**

* Uusia yksikk�testej� laadittu teko�lyn toiminnallisuuksien puolelle, mm. UniqueSet-tietorakenteen osalta. MinHeap-luokan yksikk�testit uupuvat viel�, mutta ovat seuraavaksi vastassa heti kun viimeisetkin optimoinnit luokkaan ovat tehty. Suorituskykytestausta laadittu kattavasti, tulosten tutkiminen on viel� kesken, koska paikoin huomasin poikkeuksellisia tuloksia. Niiden osalta t�ytyy selvitt��, onko kyseess� "bugi vai ominaisuus" ja miksi niin tapahtuu. Esim. pelitilanteet, joiden ratkaisuun vaaditaan v�hemm�n kuin 20 siirtoa, vaativat jostain syyst� nyt enemm�n aikaa kuin pelitilanteet, joiden ratkaisuun siirtoja vaaditaan enemm�n. Samanlainen k�yt�s oli havaittavissa jo silloin, kun k�yt�ss� olivat Javan omat HashSet sek� PriorityQueue -tietorakenteet. Toteutettuani niiden korvaajat, UniqueSet ja MinHeap, on suorituskyky muuttunut huomattavasti parempaan suuntaan, mutta vaikuttaa edelleen silt�, ett� osassa pelitilanteita menee teko�lyll� enemm�n aikaa p��ty� ratkaisuun kuin olisi ehk� tarpeen. T�m� voi liitty� my�s Javan omaan toimintatapaan, eli suorituskyky� on testattava enemm�n, jotta luvut saadaan julkaisukelpoisiksi.

**2.3.2016:**

* Edellisen testauksen yhteydess� esiin tullut "bugi vai ominaisuus" on nyt korjattu. Samoin on koodia hiottu hieman entisest��n ja suorituskyky� testattu oleellisesti. Eri heuristiikalla toimivat algoritmit muodostavat nyt odotuksen mukaiset k�yr�t eli BFS-algoritmi kasvaa "lineaarisesti", mutta A*-algoritmi eksponentiaalisesti. Hakualgoritmin aikavaativuus on tosiaan O(|V| + |E|), jossa O(|E|) on v�lilt� O(1) ja O(|V|^2) sy�tteest� riippuen. Aluksi ker�sin tietoa 30 eri pelitilanteesta kullekin algoritmille, joista kaaviot n�ytt�v�t seuraavanlaisilta:

![A*-algoritmin aikavaativuus](aikavaativuus1.png)
![BFS-algoritmin aikavaativuus](aikavaativuus2.png)