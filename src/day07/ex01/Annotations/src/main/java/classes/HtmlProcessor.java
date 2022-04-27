package classes;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"classes.HtmlForm", "classes.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Process.class)
public class HtmlProcessor extends AbstractProcessor {

    public static final String FORM_ACTION = "<form action = \"";
    public static final String METHOD = "\" method = \"";
    public static final String STR = "\">\n";
    public static final String INPUT_TYPE = "\t<input type = ";
    public static final String NAME = "\" name = \"";
    public static final String PLACEHOLDER = "\" placeholder = \"";
    public static final String STR1 = "\">\n";
    public static final String INPUT_TYPE_SUBMIT_VALUE_SEND_FORM = "\t<input type = \"submit\" value = \"Send\">\n</form>";
    public static final String TARGET_CLASSES = "target/classes/";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (annotations.isEmpty()) {
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Element userForm : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlFormAnn = userForm.getAnnotation(HtmlForm.class);
            stringBuilder.append(FORM_ACTION);
            stringBuilder.append(htmlFormAnn.action());
            stringBuilder.append(METHOD);
            stringBuilder.append(htmlFormAnn.method());
            stringBuilder.append(STR);
            List<? extends Element> userFormElements = userForm.getEnclosedElements();

            for (Element field : roundEnv.getElementsAnnotatedWith(HtmlInput.class)) {

                if (!userFormElements.contains(field)) {
                    continue;
                }

                HtmlInput htmlInputAnn = field.getAnnotation(HtmlInput.class);
                stringBuilder.append(INPUT_TYPE);
                stringBuilder.append(htmlInputAnn.type());
                stringBuilder.append(NAME);
                stringBuilder.append(htmlInputAnn.name());
                stringBuilder.append(PLACEHOLDER);
                stringBuilder.append(htmlInputAnn.placeholder());
                stringBuilder.append(STR1);
            }
            stringBuilder.append(INPUT_TYPE_SUBMIT_VALUE_SEND_FORM);

            try (BufferedWriter writter = new BufferedWriter(new FileWriter(TARGET_CLASSES +
                    htmlFormAnn.fileName()))) {
                writter.write(stringBuilder.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }
}
