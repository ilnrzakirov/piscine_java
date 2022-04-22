package day04.ex00.ImagesToChar.src.java.edu.school21.printer.logic;

import javax.imageio.IIOImage;
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
}
