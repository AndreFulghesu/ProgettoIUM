package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class LeggiLibro extends AppCompatActivity {

    String book = "Harry Potter\n" +
            "\n" +
            "L'opera è divisa in sette libri, tanti quanti gli anni di studio a Hogwarts, l'ultimo dei quali non conseguito dai protagonisti della storia (Harry Potter stesso e i suoi migliori amici Ron Weasley e Hermione Granger, la quale però poi tornerà ad Hogwarts per terminare i suoi studi). Tutto inizia con Albus Silente, il Preside della scuola di Hogwarts, costretto ad affidare il piccolo Harry Potter, rimasto orfano, ai suoi zii materni, Vernon Dursley e Petunia Evans, data l'assenza di altri parenti. Silente stesso è riluttante nel compiere tale scelta, avendo constatato che i Dursley non sono amabili né affettuosi, per quanto nobile il suo intento di garantire al piccolo mago un luogo sicuro dove vivere: infatti Harry è protetto da un'antica magia tenuta in vita dalla presenza di un consanguineo della madre (evocatrice della magia in questione): finché vivrà nello stesso luogo di tale parente, egli non potrà essere colpito dal rivale Lord Voldemort. Le angherie che Harry subisce da parte degli zii e del cugino Dudley riempiono la sua vita per dieci anni, fino a quando apprende di essere un mago grazie al Mezzogigante (nonché il guardiacaccia della scuola di Hogwarts) Rubeus Hagrid, il quale gli racconta che i suoi genitori sono stati assassinati dal più grande mago oscuro di tutti i tempi, Lord Voldemort. Di tutto ciò Harry è all'oscuro, in quanto i Dursley gli hanno sempre tenuto nascoste le vicende, raccontandogli che i suoi genitori erano morti in un incidente d'auto al quale lui era l'unico sopravvissuto. L'undicenne Harry scopre così di aver diritto a frequentare Hogwarts, la scuola di magia e stregoneria del Regno Unito. Prosegue i suoi studi di magia per sei anni fino allo scontro finale con Lord Voldemort, responsabile di altre numerose morti oltre a quella dei suoi genitori.\n" +
            "\n" +
            "La saga di Harry Potter appartiene sia al genere fantasy che al genere classico del romanzo di formazione[3]. Tuttavia le differenze tra questa serie e altre celebri saghe del genere fantasy sono da rimarcarsi: le storie di Harry Potter non sono ambientate in un'epoca immaginaria o differente dalla nostra, né in un altro universo.\n" +
            "\n" +
            "\n" +
            "J. K. Rowling, autrice della saga, legge Harry Potter e la pietra filosofale durante l'Easter Egg Roll del 2010\n" +
            "La saga di Harry Potter è ambientata nel mondo reale e nei decenni contemporanei (1981 - 2020); in altre parole, il mondo magico convivrebbe da sempre con quello delle persone comuni, non magiche (che vengono chiamate babbani), ma da esso si nasconderebbe per motivi di sicurezza e di ordine. Non a caso, in Harry Potter si trovano moltissime citazioni, dalla mitologia celtica a quella greca, dall'alchimia, alla criptozoologia, e si attinge a piene mani dagli stereotipi classici legati alla concezione magica e fantastica dell'uomo. Tutti questi elementi fanno da cornice alle avventure del protagonista, oppure, a volte con ironia, si piegano al volere della trama. Il numero sette, in particolare, ricorre spessissimo: non a caso anche la storia principale è divisa in sette volumi. Tale numero è ritenuto \"magico\" (perfino simbolo del divino) da molte tradizioni e leggende fantastiche.\n" +
            "\n" +
            "Un canone classico è la crescita interiore ed emotiva dei protagonisti attraverso le prove più o meno pericolose che il destino ha loro riservato: in Harry Potter i protagonisti non vivranno un'esperienza magica limitata all'infanzia o all'adolescenza per poi tornare nel mondo reale ordinario, ma diverranno adulti nel mondo magico come le persone comuni. Dagli aspetti prettamente adolescenziali come le ribellioni, la fiducia in se stessi, la curiosità, la scoperta dell'amore, l'impulsività e i relativi errori si passa a elementi molto più maturi come il potere politico, le strumentalizzazioni mediatiche, il razzismo, l'immaturità e le paure degli adulti, l'oppressione del più debole, la vecchiaia e i suoi errori, la depressione e la morte, a detta dell'autrice stessa come tema principale della saga[4]. In questo contesto, all'inizio, la magia è ludica e affascinante, ma quasi subito diventa un'arma temibile e insidiosa. Per questo motivo, anche se agli occhi dei babbani la magia risolverebbe molti problemi, in realtà un'ipotetica vita nel mondo magico di Harry Potter sarebbe molto più pericolosa e complicata. Tutta l'opera è caratterizzata da regole appositamente create per rendere logico l'iter della trama, e l'insieme di queste costituisce un corredo di preziose nozioni messe a disposizione del lettore per risolvere, potenzialmente, i vari enigmi e misteri che libro dopo libro si accumulano fino a risolversi nel finale. Tuttavia, nonostante la ricca componente di norme create ad hoc dall'autrice, una delle principali caratteristiche della serie è proprio quella dell'imprevedibilità dei forti colpi di scena, con cui tutte le regole vengono aggirate.\n" +
            "\n" +
            "I libri della Rowling sono stati confrontati con Le cronache di Narnia (The Chronicles of Narnia) di C. S. Lewis[5], Il Signore degli Anelli (The Lord of the Rings) di J. R. R. Tolkien[5], il Ciclo di Earthsea di Ursula K. Le Guin[6], i romanzi di Diana Wynne Jones[7], di Philip Pullman[8] e di Jill Murphy[9].";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leggi_libro);

        TextView textBook = findViewById(R.id.textBook);

        textBook.setText(book);
    }
}
