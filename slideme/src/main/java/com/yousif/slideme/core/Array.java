package com.yousif.slideme.core;

/**
 * Apuluokka int[]-taulukon käsittelylle.
 * 
 * @author Yousif Abdullah <yousif.abdullah@helsinki.fi>
 */
class Array {
    
    /**
     * Sekoittaa taulukon alkiot satunnaiseen järjestykseen käyttäen
     * Durstenfeldin algoritmia, joka on modernimpi vedos Fisher-Yates
     * -algoritmista. Taulukon alkiot käydään kerran läpi, eli aika-
     * vaativuus on O(n).
     * 
     * Todellinen aikavaativuus tässä tapauksessa on kuitenkin O(n^2),
     * koska sekoituksen lisäksi lasketaan inversioiden määrä taulukossa.
     * 
     * @param array sekoitettava int[]-taulukko
     * @see com.yousif.slideme.core.Array#countInversions(int[])
     */
    static void shuffleWithEvenInversions(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            /*
            Huom. algoritmi toimii halutulla tavalla, kun kaikki
            mahdolliset permutaatiot sallitaan. Toisin sanoen sallitaan
            kahden saman indeksin alkioiden vaihto, minkä takia
            Math.random()-metodin tuottama satunnaisarvo kerrotaan
            arvolla (i + 1) eikä vain i:llä.
            */
            Array.swap(array, i, (int) Math.floor(Math.random() * (i + 1)));
        }
        
        /*
        Sekoituksen jälkeen korjataan taulukon alkioiden järjestys
        swapFirstInversion()-metodilla, mikäli inversioiden määrä on
        pariton. Muutoin sallitaan pelitilanteet, jotka ovat mahdottomia
        ratkaista.
        
        Koska 8-pelissä käytetään 3*3-ruudun suuruista alustaa ja
        ratkaistussa pelitilanteessa on 0 inversiota, eli parillinen
        määrä, voidaan pelitilanteet jakaa kahteen ekvivalenssi-
        luokkaan: niihin, joissa on parillinen määrä inversioita ja
        niihin, joissa on pariton määrä inversioita. Siis kaikki
        ratkaistavissa olevat pelitilanteet noudattavat rakennetta,
        jossa on parillinen määrä inversioita.
        */
        if (Array.countInversions(array) % 2 != 0) {
            Array.swapFirstInversion(array);
        }
    }
    
    /**
     * Laskee taulukossa esiintyvien inversioiden määrän käyttäen
     * bubble sort -algoritmia. Tässä algoritmissa hyödynnetään tieto
     * siitä, että bubble sort käyttää aina vähimmän mahdollisen määrän
     * vierekkäisten alkioiden vaihtoja taulukkoa järjestäessään.
     * 
     * Pahimmassa tapauksessa bubble sortin aikavaativuus on O(n^2),
     * mutta kuitenkin soveltuu paremmin haluttuun käyttötarkoitukseen,
     * kuin esim. merge sort, jolla inversiot voidaan laskea O(n log n)
     * aikavaativuudella. Erityisesti on huomioitava, että pelitilanne
     * säilytetään int[]-taulukossa, jossa 0 merkitsee vapaaruutua, eikä
     * lukua 0. Toisin sanoen inversioita ei lasketa niille pareille,
     * joissa {@literal i < j, A[i] > A[j] ja A[j] == 0} annetulla taulukolla A.
     * 
     * @param array tarkistettava int[]-taulukko
     * @see com.yousif.slideme.core.Array#swapFirstInversion(int[])
     * @return inversioiden määrä int-arvona
     */
    static int countInversions(int[] array) {
        /*
        Koska inversiot lasketaan bubble sortia käyttäen, joka
        oikeastaan järjestää taulukon, kopioidaan annettu taulukko,
        jotta alkuperäistä sekoitettua järjestystä ei menetetä.
        
        Tämän takia algoritmin tilavaativuus on O(n).
        */
        int[] copy = Array.copy(array);
        
        int inversions = 0;
        boolean swapped = true;
        
        while (swapped) {
            swapped = false;
            
            for (int i = 0; i < copy.length - 1; i++) {
                if (copy[i] > copy[i + 1]) {
                    // Huom. 0 merkitsee vapaaruutua, ei lukua 0!
                    if (copy[i + 1] != 0) {
                        inversions++;
                    }
                    
                    Array.swap(copy, i, i + 1);
                    swapped = true;
                }
            }
        }
        
        return inversions;
    }
    
    /**
     * Aputoiminto shuffleWithEvenInversions()-metodille, joka korjaa
     * taulukon alkioiden järjestyksen etsimällä ensimmäisen inversion
     * muodostavan indeksiparin ja vaihtaa alkiot keskenään.
     * 
     * Siis inversioiden määrä vähenee yhdellä, mikä on toivottua, kun
     * inversioita on aluksi pariton määrä. Tällöin päädytään järjestykseen,
     * joka sisältää parillisen määrän inversioita eli on ratkaistavissa
     * oleva pelitilanne.
     * 
     * Koska taulukossa n - 1 alkiota käydään enimmillään n - 1 kertaa
     * läpi, on aikavaativuus O(n^2). Tämä ei kuitenkaan vastaa todellista
     * aikavaativuutta algoritmin käyttötarkoituksen kannalta, koska annettu
     * pelitilanne aina vastaa joukkoa {0, 1, 2, 3, 4, 5, 6, 7, 8}, siis
     * kaiken kaikkiaan n + 1 alkiota käydään läpi pahimmassa tapauksessa.
     * Toisin sanoen algoritmin aikavaativuus on Ө(n).
     * 
     * @param array korjattava int[]-taulukko
     * @see com.yousif.slideme.core.Array#shuffleWithEvenInversions(int[])
     */
    static void swapFirstInversion(int[] array) {
        boolean swapped = false;
        
        for (int i = 0; i < array.length - 1; i++) {
            /*
            Kun ensimmäinen löydetty indeksipari on käsitelty, loppuu
            algoritmin toiminta heti.
            */
            if (swapped) {
                break;
            }
            
            /*
            Mikäli ensimmäisen vertailtavan indeksin alkio on 0, eli
            vapaaruutu, siirrytään silmukassa seuraavaan iteraatioon.
            */
            if (array[i] == 0) {
                continue;
            }
            
            for (int j = (i + 1); j < array.length; j++) {
                /*
                Jos ensimmäisen vertailtavan indeksin alkio on pienempi
                kuin seuraava alkio, kyseessä ei ole inversio ja silmukka
                keskeytetään. Muussa tapauksessa jos seuraava alkio ei ole
                vapaaruutu, vaihdetaan alkiot keskenään ja keskeytetään
                silmukka sekä merkataan ensimmäinen löydetty indeksipari
                käsitellyksi.
                */
                if (array[i] < array[j]) {
                    break;
                } else if (array[j] != 0) {
                    Array.swap(array, i, j);
                    swapped = true;
                        
                    break;
                }
            }
        }
    }
    
    /**
     * Vertailee kahta taulukkoa keskenään ja palauttaa arvon true,
     * mikäli molemmat annetut taulukot ovat identtiset.
     * 
     * Koska taulukkojen vertailu tapahtuu yhdellä läpikäynnillä, on
     * aikavaativuus O(n).
     * 
     * @param a ensimmäinen vertailtava int[]-taulukko
     * @param b toinen vertailtava int[]-taulukko
     * @return true, kun taulukot ovat identtiset ja muutoin false
     */
    static boolean matches(int[] a, int[] b) {
        /*
        Taulukkojen vertailu voidaan lopettaa heti, jos niiden pituus
        eroaa eli taulukoissa on eri määrä alkioita.
        */
        if (a.length != b.length) {
            return false;
        }
        
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Manuaalisesti kopioi annetun taulukon ja palauttaa uuden taulukon
     * paluuarvona. Taulukon alkiot käydään kerran läpi eli aikavaativuus
     * on O(n).
     * 
     * @param kopioitava int[]-taulukko
     * @return kopioitu int[]-taulukko
     */    
    static int[] copy(int[] array) {
        int[] copy = new int[array.length];
        
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        
        return copy;
    }
    
    /**
     * Aputoiminto, joka vaihtaa annetun indeksiparin alkiot keskenään.
     * Toiminnon aikavaativuus on O(1).
     * 
     * @param array käsiteltävä int[]-taulukko
     * @param a ensimmäisen alkion indeksi
     * @param b toisen alkion indeksi
     */
    static void swap(int[] array, int a, int b) {
        // Huom. aluksi tarkistetaan, että indeksit löytyvät taulukosta.
        if (0 <= a && a <= array.length - 1) {
            if (0 <= b && b <= array.length - 1) {
                int tmp = array[a];
                
                array[a] = array[b];
                array[b] = tmp;
            }
        }
    }
}