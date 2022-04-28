package printer;

import renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private Renderer renderer;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void setPrefix(String prefix) {

    }

    @Override
    public void print(String str) {

    }
}
