package renderer;

import preProcessor.PreProcessor;

public class RendererErrImpl implements Renderer {

    private PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void print(String str) {
        System.err.print(preProcessor.preProcess(str));
    }
}
