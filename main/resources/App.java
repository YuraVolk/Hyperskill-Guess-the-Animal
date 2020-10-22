import java.util.ListResourceBundle;

public class App extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"system-intro", "Welcome to the animals expert system!"},
                {"bye", "Have a nice day!"},
                {"fact-start", "Specify a fact that distinguishes"},
                {"fact-middle", "from"},
                {"examples", "The sentence should be of the following templates:\n" +
                        "- It can ...\n" +
                        "- It has ...\n" +
                        "- It is a/an ...\n\n"},
                {"yes-or-no", "Come on, yes or no?"},
                {"can-verb", "can"},
                {"has-verb", "has"},
                {"is-verb", "is"},
                {"can-verb-negation", "cannot"},
                {"has-verb-negation", "doesn't have"},
                {"is-verb-negation", "isn't"},
                {"it-statement", "It"},
                {"example-statements", "Some examples of a statement are:\n" +
                        "Is it a shy animal?\nDoes it have a horn?\n"},
                {"can-question", "Can it"},
                {"has-question", "Does it have"},
                {"is-question", "Is it"},
                {"menu", "What do you want to do:\n" +
                        "1. Play the guessing game\n" +
                        "2. List of all animals\n" +
                        "3. Search for an animal\n" +
                        "4. Knowledge Tree stats\n" +
                        "0. Exit"},
                {"animal-input", "Enter the animal:"},
                {"tree-stats-title", "The Knowledge Tree stats\n\n"},
                {"tree-stats-root", "root node                    "},
                {"tree-stats-nodes", "total number of nodes        "},
                {"tree-stats-animals", "total number of animals      "},
                {"tree-stats-statements", "total number of statements   "},
                {"tree-stats-max-depth", "height of the tree           "},
                {"tree-stats-min-depth", "minimum depth                "},
                {"tree-stats-avr-depth", "average depth                "},
                {"known-animals", "Here are the animals I know:"},
                {"no-facts", "No facts about the"},
                {"no-facts-boolean", "false"},
                {"statement-correctness", "Is the statement correct for the"},
                {"definite-article", "the"},
                {"indefinite-article-vowel", "an"},
                {"indefinite-article-consonant", "a"},
                {"play-again", "Should we play again?"},
                {"starter-question", "Good morning!\n" +
                        "\n" +
                        "I want ot learn about animals.\n" +
                        "Which animal do you like most?"},
                {"learnt-facts", "I remember the following facts about animals: "},
                {"starter-game-question", "Let's play a game!\n" +
                                "You think of an animal, and I guess it.\n" +
                                "Press enter when you're ready."},
                {"learn-finish-starter", "Nice! I've learned so much about animals!\n" +
                                "Would you like to play again?"},
                {"learn-finish", "Nice! I've learned so much about animals!"},
                {"loss", "I give up. What animal do you have in mind?"},
                {"victory", "I won!"},
                {"positive-answers", new String[] {"y", "yes", "yeah", "yep", "sure", "right", "affirmative", "correct", "indeed", "you bet", "exactly", "you said it"}},
                {"negative-answers", new String[] {"n", "no", "no way", "nope", "nah", "negative", "i don't think so", "yeah no"}},
                {"error-menu", "Please enter the number from 0 up to 4"}
        };
    }
}