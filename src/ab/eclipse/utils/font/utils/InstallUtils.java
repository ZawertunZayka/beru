package ab.eclipse.utils.font.utils;



import ab.eclipse.EclipseFuntime;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class InstallUtils {
    public static Font installFont(String link, int size) {
        Font result;

        BufferedInputStream b = new BufferedInputStream(EclipseFuntime.class.getResourceAsStream("/assets/minecraft/eclipsefuntime/fonts/" + link));

        try {
            result = Font
                    .createFont(Font.TRUETYPE_FONT, b)
                    .deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
