package day04.ex01.ImagesToChar.src.java.edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class ImagesToChar {
    private BufferedImage image;
    private char symbolWhite;
    private char SymbolBlack;

    public ImagesToChar(FileInputStream imageFile, char symbolWhite, char symbolBlack) throws IOException {
        this.image = ImageIO.read(imageFile);
        this.symbolWhite = symbolWhite;
        this.SymbolBlack = symbolBlack;
    }

    public Integer getWidth(){
        return this.image.getWidth();
    }

    public Integer getHeight(){
        return this.image.getHeight();
    }

    public Integer getRGB(int x, int y){
        return this.image.getRGB(x, y);
    }

    public char getSymbolBlack() {
        return SymbolBlack;
    }

    public char getSymbolWhite() {
        return symbolWhite;
    }
}
