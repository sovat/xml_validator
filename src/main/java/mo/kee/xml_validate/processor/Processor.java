package mo.kee.xml_validate.processor;

import name.dmaus.schxslt.Result;
import name.dmaus.schxslt.Schematron;
import name.dmaus.schxslt.SchematronException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.List;

@Service
public class Processor {



    StreamSource getResourceAsStream (String resource)
    {
        InputStream resourceAsStream = getClass().getResourceAsStream(resource);
        return new StreamSource(resourceAsStream, resource);
    }

    @PostConstruct
    private void validate() {
        Schematron schematron = null;
        try {
            schematron = new Schematron(getResourceAsStream("/schematron.sch"));
            Result result = schematron.validate(getResourceAsStream("/sample.xml"));
            List<String> validationMessages = result.getValidationMessages();
            validationMessages.forEach(System.out::println);
            System.out.println(result.isValid());
        } catch (SchematronException e) {
            e.printStackTrace();
        }

    }
}
