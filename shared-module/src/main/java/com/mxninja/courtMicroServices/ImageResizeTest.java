package com.mxninja.courtMicroServices;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 9/6/2018
 *
 * @author Mohammad Ali
 */
public class ImageResizeTest {

    public static void main(String[] args) {

        /*  var sourceWidth = imgPhoto.Width;
            var sourceHeight = imgPhoto.Height;
            var sourceX = 0;
            var sourceY = 0;
            var destX = 0;
            var destY = 0;

            float nPercent;

            var nPercentW = (newWidth / sourceWidth);
            var nPercentH = (newHeight / sourceHeight);
            if (nPercentH < nPercentW)
            {
                nPercent = nPercentH;
                destX = Convert.ToInt16((newWidth -
                                         (sourceWidth * nPercent)) / 2);
            }
            else
            {
                nPercent = nPercentW;
                destY = Convert.ToInt16((newHeight -
                                         (sourceHeight * nPercent)) / 2);
            }

            var destWidth = (int) (sourceWidth * nPercent);
            var destHeight = (int) (sourceHeight * nPercent);

            var bmPhoto = new Bitmap((int) newWidth, (int)newHeight, System.Drawing.Imaging.PixelFormat.Format24bppRgb);*/
        try {
            File file = new File("C:\\files\\image.jpg");
            BufferedImage img = ImageIO.read(file);
            int height = 100;
            int width = 100;
            if (height == 0) {
                height = (width * img.getHeight()) / img.getWidth();
            }
            if (width == 0) {
                width = (height * img.getWidth()) / img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, Color.WHITE, null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            InputStream inputStream = new ByteArrayInputStream(buffer.toByteArray());
            Files.copy(inputStream, new File("C:\\files\\size.jpg").toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
