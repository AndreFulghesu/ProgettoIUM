package com.example.myapplication;

import java.util.ArrayList;
import java.util.Iterator;

public class ChapterFactory {

    private static ChapterFactory singleton;


    private ArrayList<Chapter> chapters = new ArrayList<>();
    private ArrayList<Chapter> chaptersModified = new ArrayList<>();
    Chapter chap1= new Chapter(1, 1,

            "L'opera è divisa in sette libri, tanti quanti gli anni di studio a Hogwarts, " +
                    "l'ultimo dei quali non conseguito dai protagonisti della storia (Harry Potter stesso " +
                    "e i suoi migliori amici Ron Weasley e Hermione Granger, la quale però poi tornerà " +
                    "ad Hogwarts per terminare i suoi studi). Tutto inizia con Albus Silente, " +
                    "il Preside della scuola di Hogwarts, costretto ad affidare il piccolo Harry Potter," +
                    " rimasto orfano, ai suoi zii materni, Vernon Dursley e Petunia Evans, data l'assenza " +
                    "di altri parenti. Silente stesso è riluttante nel compiere tale scelta, avendo constatato" +
                    " che i Dursley non sono amabili né affettuosi, per quanto nobile il suo intento di garantire" +
                    " al piccolo mago un luogo sicuro dove vivere: infatti Harry è protetto da un'antica magia tenuta" +
                    " in vita dalla presenza di un consanguineo della madre (evocatrice della magia in questione): " +
                    "finché vivrà nello stesso luogo di tale parente, egli non potrà essere colpito dal rivale Lord " +
                    "Voldemort. Le angherie che Harry subisce da parte degli zii e del cugino Dudley riempiono la sua vita" +
                    " per dieci anni, fino a quando apprende di essere un mago grazie al Mezzogigante (nonché il guardiacaccia " +
                    "della scuola di Hogwarts) Rubeus Hagrid, il quale gli racconta che i suoi genitori sono stati assassinati " +
                    "dal più grande mago oscuro di tutti i tempi, Lord Voldemort. Di tutto ciò Harry è all'oscuro," +
                    " in quanto i Dursley gli hanno sempre tenuto nascoste le vicende, raccontandogli che i suoi genitori" +
                    " erano morti in un incidente d'auto al quale lui era l'unico sopravvissuto. L'undicenne Harry scopre" +
                    " così di aver diritto a frequentare Hogwarts, la scuola di magia e stregoneria del Regno Unito. " +
                    "Prosegue i suoi studi di magia per sei anni fino allo scontro finale con Lord Voldemort, " +
                    "responsabile di altre numerose morti oltre a quella dei suoi genitori. ");

    Chapter chap2 = new Chapter(1, 2, "Testo provvisorio per un capitolo che non verrà mai pubblicato.\n");
    Chapter chap3 = new Chapter(2, 1, "Assassinio sull'Orient Express (Murder on the Orient Express) " +
            "è un film del 2017 diretto, co-prodotto e interpretato da Kenneth Branagh."+

            "Basato sull'omonimo romanzo del 1934 di Agatha Christie, il film è la seconda trasposizione cinematografica " +
            "del libro dopo quella omonima del 1974 diretta da Sidney Lumet. È scritto da Michael Green e prodotto da Branagh," +
            " Ridley Scott (con la sua Scott Free Productions)," +
            " Mark Gordon e Simon Kinberg: il film è stato girato in pellicola Ultra Panavision 70 mm, utilizzata recentemente" +
            " in altri film importanti come The Hateful Eight.[1] La colonna sonora è curata da Patrick Doyle."+

            "Il protagonista Hercule Poirot è interpretato dallo stesso Kenneth Branagh, affiancato da un cast corale che comprende " +
            "Penélope Cruz, Willem Dafoe, Judi Dench, Johnny Depp, Josh Gad, Derek Jacobi, Leslie Odom Jr., Michelle Pfeiffer e Daisy Ridley."+

            "Assassinio sull'Orient Express è stato distribuito nelle sale cinematografiche statunitensi il 10 novembre 2017 dalla 20th " +
            "Century Fox, mentre in quelle italiane ha esordito il 30 novembre 2017 distribuito da 20th Century Fox Italia.");

    Chapter chap4 = new Chapter(2, 2, "Gerusalemme, 1934. Il detective belga Hercule Poirot risolve un furto alla Basilica" +
            " del Santo Sepolcro. Poirot è ossessionato dall'equilibrio e dall'ordine nella vita: in grado di vedere il mondo"+
            "per come dovrebbe essere e non per come è, considera questa sua capacità molto utile nel risolvere i casi specie quando" +
            " si tratta di smascherare una bugia. A seguito del caso a Gerusalemme, "+
            "Poirot desidera riposare a Istanbul, ma deve tornare inaspettatamente a Londra per un altro caso. Il suo amico Bouc, " +
            "direttore dell'Orient Express, gli offre una cabina sul suo treno."+
            "Sull'Orient Express conosce un sinistro uomo d'affari - Samuel Ratchett - che desidera assumerlo come sua guardia del " +
            "corpo durante i tre giorni di viaggio che deve compiere, poiché Ratchett ha ricevuto " +
            "delle anonime lettere minatorie. Dal momento che Poirot sa che gli affari dell'uomo sono illegali" +
            " (traffici di opere d'arte, per lo più false) e che Ratchett si è fatto molti nemici, il detective rifiuta l'offerta" +
            " non solo perché non lo considera un innocente, ma soprattutto perché non vuole essere coinvolto da un individuo " +
            "come lui. Quella stessa notte Poirot sente strani rumori provenienti dallo scompartimento di Ratchett e vede " +
            "qualcuno con un kimono rosso correre lungo il corridoio. Durante la medesima notte, una valanga fa deragliare il treno," +
            " costringendolo a fermarsi in attesa di soccorso dalla stazione più vicina.");

    Chapter chap5 = new Chapter(3, 1, "Un giorno Fulghenzio incontrò quattro Gigi ricoperti di filigrane d'oro," +
            " ma non si fermò a salutarli.\nFine.");

    Chapter chap6 = new Chapter(3, 2, "Specify an optional color filter for the drawable.\n" +
            "\n" +
            "If a Drawable has a ColorFilter, each output pixel of the Drawable's drawing contents will be modified " +
            "by the color filter before it is blended onto the render target of a Canvas.\n" +
            "\n" +
            "Pass null to remove any existing color filter. " +
            " ma non si fermò a salutarli.\nFine.");

    Chapter chap7 = new Chapter (4,1,"Nel 1765 c'era una specie protetta di foglie chiamata Fabrizio. Faceva la clorofila fotosintosa.\n" +
            "Da allora decise di fermarsi perché era troppo faticoso. Tutto il pianeta morì.");


    private ChapterFactory(){
    }

    public static ChapterFactory getInstance(){
        if(singleton == null){
            singleton = new ChapterFactory();
        }
        return singleton;
    }

    public ArrayList<Chapter> getChapters(){



        chapters.clear();


        chapters.add(chap1);
        chapters.add(chap2);
        chapters.add(chap3);
        chapters.add(chap4);
        chapters.add(chap5);
        chapters.add(chap6);
        chapters.add(chap7);

        if(!chaptersModified.isEmpty()) {
            for (int i = 0; i < chaptersModified.size(); i++) {
                if (this.chaptersModified.get(i).getText().equals(this.chapters.get(i).getText())) {
                    this.chapters.remove(i);
                    this.chapters.add(i, this.chaptersModified.get(i));
                }
            }
        }

        return this.chapters;
    }

    public ArrayList<Chapter> getChaptersByBookId (int id) {
        ArrayList<Chapter> chaptersBook = new ArrayList<>();
        ArrayList<Chapter> chapters = this.getChapters();
        for (Chapter c : chapters) {
            if (c.getBookId()==id) {
                chaptersBook.add(c);
            }
        }
        return chaptersBook;
    }

    public Chapter getChapterByChapNum (int chapNum, int bookId) {
        ArrayList<Chapter> chapters = this.getChapters();
        for (Chapter c : chapters) {
            if (c.getBookId()==bookId && c.getChaptNum()==chapNum) {
                return c;
            }
        }
        return null;
    }

    public void addChapterModify (Chapter c) {
        this.chaptersModified.add(c);
    }





}
