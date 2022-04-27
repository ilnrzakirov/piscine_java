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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (annotations.isEmpty()) {
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Element userForm : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlFormAnn = userForm.getAnnotation(HtmlForm.class);
            stringBuilder.append("<form action = \"");
            stringBuilder.append(htmlFormAnn.action());
            stringBuilder.append("\" method = \"");
            stringBuilder.append(htmlFormAnn.method());
            stringBuilder.append("\">\n");
            List<? extends Element> userFormElements = userForm.getEnclosedElements();

            for (Element field : roundEnv.getElementsAnnotatedWith(HtmlInput.class)) {

                if (!userFormElements.contains(field)) {
                    continue;
                }

                HtmlInput htmlInputAnn = field.getAnnotation(HtmlInput.class);
                stringBuilder.append("\t<input type = ");
                stringBuilder.append(htmlInputAnn.type());
                stringBuilder.append("\" name = \"");
                stringBuilder.append(htmlInputAnn.name());
                stringBuilder.append("\" placeholder = \"");
                stringBuilder.append(htmlInputAnn.placeholder());
                stringBuilder.append("\">\n");
            }
            stringBuilder.append("\t<input type = \"submit\" value = \"Send\">\n</form>");

            try (BufferedWriter writter = new BufferedWriter(new FileWriter("target/classes/" +
                    htmlFormAnn.fileName()))) {
                writter.write(stringBuilder.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }
}
