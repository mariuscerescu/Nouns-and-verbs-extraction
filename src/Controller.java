import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;

public class Controller {

    @FXML
    private TextArea textTextArea;

    @FXML
    private TextArea nounsTextArea;

    @FXML
    private TextArea verbsTextArea;

    @FXML
    void extractBTN(ActionEvent event) {
        String text = textTextArea.getText();
        StringBuffer nounsBuffer = new StringBuffer();
        StringBuffer verbsBuffer = new StringBuffer();

        
        // Create Stanford CoreNLP pipeline with only POS tagging and lemmatization
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Create an Annotation object to hold the input text
        Annotation annotation = new Annotation(text);

        // Run the pipeline on the input text
        pipeline.annotate(annotation);

        // Extract nouns and verbs from the annotated text
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.word();
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                if (pos.startsWith("N")) {
                    nounsBuffer.append(word + ", ");
                }
                if (pos.startsWith("V")) {
                    verbsBuffer.append(word + ", ");
                }
            }
        }

        nounsTextArea.insertText(0, nounsBuffer.toString());
        verbsTextArea.insertText(0, verbsBuffer.toString());
    }

}

