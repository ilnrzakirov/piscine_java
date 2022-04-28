package app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import preProcessor.PreProcessor;
import preProcessor.PreProcessorToLower;
import preProcessor.PreProcessorToUpperImpl;
import printer.Printer;
import printer.PrinterWithDateTimeImpl;
import printer.PrinterWithPrefixImpl;
import renderer.Renderer;
import renderer.RendererErrImpl;
import renderer.RendererStandardImpl;

public class Main {

//    public static void main(String[] args) {
//        PreProcessor preProcessor = new PreProcessorToUpperImpl();
//        PreProcessor preProcessor1 = new PreProcessorToLower();
//        Renderer renderer = new RendererErrImpl(preProcessor);
//        Renderer renderer1 = new RendererStandardImpl(preProcessor1);
//        PrinterWithDateTimeImpl printer1 = new PrinterWithDateTimeImpl(renderer1);
//        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
//        printer1.print("Hello");
//        System.out.println();
//        printer.setPrefix ("Prefix");
//        printer.print ("Hello!") ;
//    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printer = context.getBean("printerWithPrefixERRLower", Printer.class);
        printer.print("Hello!");
    }
}
