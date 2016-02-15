**Yleisrakenne:**

* Ohjelman suoritus alkaa graafisen k‰yttˆliittym‰n k‰ynnistyksell‰, joka tuo esiin sekoitetussa j‰rjestyksess‰ olevat peliruudut, jotka on saatava taas j‰rjestykseen. Pelaaja voi siirt‰‰ peliruutuja sek‰ sekoittaa niiden j‰rjestyst‰ uuden pelitilanteen luomiseksi.

  * Sekoitusalgoritmina k‰ytin Durstenfeldin algoritmia, joka on modernimpi vedos Fisher-Yates-algoritmista ja toimii O(n) aikavaativuudella. Koska pelkk‰ sekoitus ei kuitenkaan riit‰ ja on taattava parillinen inversioiden m‰‰r‰ lopulliselle j‰rjestykselle, k‰ytin bubble sort -j‰rjestysalgoritmia tukena.

  * 8-pelin kaikki pelitilanteet voidaan jakaa kahteen ekvivalenssiluokkaan: niihin, joissa on parillinen m‰‰r‰ inversioita ja niihin, joissa on pariton m‰‰r‰ inversioita. Siis kaikki ratkaistavissa olevat pelitilanteet noudattavat rakennetta, jossa on parillinen m‰‰r‰ inversioita. T‰m‰n takia pahimmassa tapauksessa O(n^2) aikavaativuudella toimiva bubble sort -algoritmi tuli tarpeen. En toteuttanut muuta algoritmia, koska erityisesti projektiani varten se soveltui hyvin. Esim. on huomioitava, ett‰ pelitilanne s‰ilytet‰‰n taulukkona, jossa 0 merkitsee vapaaruutua, eik‰ lukua 0. Toisin sanoen inversioita ei lasketa niille pareille, joissa i < j, A[i] > A[j] ja A[j] == 0 annetulla taulukolla A.

* Pelaaja voi myˆs k‰ynnist‰‰ teko‰lyn simulaation, joka visuaalisesti ratkaisee pelitilanteen askel askeleelta k‰ytt‰en A*-algoritmia.
  * A*-algoritmin toteutus k‰ytt‰en peliruutujen Manhattan-et‰isyytt‰ heuristiikkana on keskeinen osa projektia. Sen aikavaativuus on O(|V| + |E|) ja toimii k‰yt‰nnˆss‰ nopeasti. K‰ytin erityisesti algoritmin suunnittelua varten seuraavaa l‰hdett‰: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html.