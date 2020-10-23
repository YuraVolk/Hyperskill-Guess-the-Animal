import java.util.ListResourceBundle;

public class App_eo extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"system-intro", "Bonvenon al la sperta sistemo de la besto!"},
                {"bye", "Ĝis!"},
                {"fact-start", "Indiku fakton, kiu distingas"},
                {"fact-middle", "de"},
                {"examples", "La frazo devas esti de la formato: 'Ĝi <povas/havas/estas/...> ...'.\n\n"},
                {"yes-or-no", "Amuza, mi ankoraŭ ne komprenas, ĉu jes aŭ ne?"},
                {"can-verb", "povas"},
                {"has-verb", "havas"},
                {"is-verb", "estas"},
                {"live-verb", "loĝas"},
                {"can-verb-negation", "ne povas"},
                {"has-verb-negation", "ne havas"},
                {"is-verb-negation", "ne estas"},
                {"live-verb-negation", "ne loĝas"},
                {"it-statement", "Ĝi"},
                {"example-statements", "Iuj ekzemploj de aserto estas:\n" +
                        "Ĉu ĝi estas timema besto?\nĈu ĝi havas kornjon?\n"},
                {"can-question", "Ĉu ĝi povas"},
                {"has-question", "Ĉu ĝi havas"},
                {"is-question", "Ĉu ĝi estas"},
                {"live-question", "Ĉu ĝi loĝas"},
                {"menu", "Kion vi volas fari:\n" +
                        "1. Ludu la divenludon\n" +
                        "2. Listo de ĉiuj bestoj\n" +
                        "3. Serĉi beston\n" +
                        "4. La statistikoj pri Sciarbo\n" +
                        "0. Eliri"},
                {"animal-input", "Enigu la nomon de besto:"},
                {"tree-stats-title", "La statistiko de la Scio-Arbon\n"},
                {"tree-stats-root", "radika nodo                  "},
                {"tree-stats-nodes", "tuta nombro de nodoj         "},
                {"tree-stats-animals", "totala nombro de bestoj      "},
                {"tree-stats-statements", "totala nombro de deklaroj    "},
                {"tree-stats-max-depth", "alteco de la arbo            "},
                {"tree-stats-min-depth", "minimuma profundo            "},
                {"tree-stats-avr-depth", "averaĝa profundo             "},
                {"known-animals", "Jen la bestoj, kiujn mi konas:"},
                {"no-facts", "La besto ne estas en mia sciarbo."},
                {"no-facts-boolean", "true"},
                {"statement-correctness", "Ĉu la aserto ĝustas por la"},
                {"definite-article", "la"},
                {"definite-article-uppercase", "La"},
                {"indefinite-article-vowel", ""},
                {"indefinite-article-consonant", ""},
                {"play-again", "Ĉu vi volas ludi denove?"},
                {"starter-question", "Bonan matenon!\n" +
                        "\n" +
                        "Mi volas lerni pri bestoj.\n" +
                        "Kiun beston vi plej ŝatas?"},
                {"learnt-facts", "Mi lernis la jenajn faktojn pri bestoj:"},
                {"starter-game-question", "Ni ludu ludon!\n" +
                        "Vi pensas pri besto, kaj mi supozas ĝin.\n\n" +
                        "Premu enen kiam vi pretas."},
                {"learn-finish-starter", "Bela! Nun mi scias multe pli pri bestoj!\n" +
                        "Ĉu vi volas ludi denove?"},
                {"learn-finish", "Bela! Nun mi scias multe pli pri bestoj!"},
                {"loss", "Mi rezignas. Kiun beston vi havas en la kapo?"},
                {"victory", "Mi gajnis!"},
                {"positive-answers", new String[]{"j", "jes", "certe"}},
                {"negative-answers", new String[]{"n", "ne"}},
                {"error-menu", "Bonvolu enigi la numeron de 0 ĝis 4"},
                {"prefix", "_eo"},
                {"facts", "Faktoj pri la"}
        };
    }
}