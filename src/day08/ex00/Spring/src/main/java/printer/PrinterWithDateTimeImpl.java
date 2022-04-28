package printer;

import renderer.Renderer;

public class PrinterWithDateTimeImpl implements Printer {

    private Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void setPrefix(String prefix) {
    }

    @Override
    public void print(String str) {

    }
}
