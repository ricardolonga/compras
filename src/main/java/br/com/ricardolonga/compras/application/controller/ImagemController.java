package br.com.ricardolonga.compras.application.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class ImagemController implements Serializable {

    private static final long serialVersionUID = -4953253518112250551L;

    @Inject
    private Logger logger;

    private StreamedContent streamedImage;

    public StreamedContent getStreamedImage() {
        byte[] imagemArray = (byte[]) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("imagem");

        if (imagemArray == null) {
            imagemArray = criaPNGBranco();
        }

        streamedImage = new DefaultStreamedContent(new ByteArrayInputStream(imagemArray), "image/png");

        return streamedImage;
    }

    public void setStreamedImage(StreamedContent streamedImage) {
        this.streamedImage = streamedImage;
    }

    private byte[] criaPNGBranco() {
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        byte[] imageBytes = null;
        String extension = "png";

        int width = 20;
        int height = 20;

        try {
            BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = buffer.createGraphics();

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            g2d.dispose();

            ImageIO.write(buffer, extension, imgOutputStream);
            imageBytes = imgOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Erro ao criar o PNG em branco.", e);
        }

        return imageBytes;
    }

}
