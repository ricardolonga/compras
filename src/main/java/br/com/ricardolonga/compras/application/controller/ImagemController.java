package br.com.ricardolonga.compras.application.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class ImagemController implements Serializable {

    private static final long serialVersionUID = -4953253518112250551L;

    private StreamedContent imageStream;

    public StreamedContent getImageStream() {
        byte[] imagemArray = (byte[]) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("imagem");

        if (imagemArray == null) {
            imagemArray = criaPNGBranco();
        }

        imageStream = new DefaultStreamedContent(new ByteArrayInputStream(imagemArray), "image/png");

        return imageStream;
    }

    public void setImageStream(StreamedContent imageStream) {
        this.imageStream = imageStream;
    }

    private byte[] criaPNGBranco() {
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        byte[] captchaBytes = null; // imageBytes
        String sImgType = "png";

        int width = 20;
        int height = 20;

        try {
            BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = bufImage.createGraphics();

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            g2d.dispose();

            ImageIO.write(bufImage, sImgType, imgOutputStream);
            captchaBytes = imgOutputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("Image failed #0001 ");
        }

        return captchaBytes;
    }

}
