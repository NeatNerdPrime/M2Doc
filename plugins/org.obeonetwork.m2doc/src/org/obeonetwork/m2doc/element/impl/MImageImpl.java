/*******************************************************************************
 *  Copyright (c) 2017, 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.obeonetwork.m2doc.element.impl;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.batik.ext.awt.image.codec.png.PNGTranscoderInternalCodecWriteAdapter;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.resources.Messages;
import org.apache.poi.hemf.usermodel.HemfPicture;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.obeonetwork.m2doc.element.MImage;
import org.obeonetwork.m2doc.element.PictureType;

/**
 * An image that can be returned by services.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MImageImpl implements MImage {

    /**
     * A {@link PNGTranscoder} that doesn't call <code>Class.forName(className)</code>.
     * 
     * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
     */
    private final class M2DocPNGTranscoder extends PNGTranscoder {

        @Override
        public void writeImage(BufferedImage img, TranscoderOutput output) throws TranscoderException {

            OutputStream ostream = output.getOutputStream();
            if (ostream == null) {
                throw new TranscoderException(Messages.formatMessage("png.badoutput", null));
            }

            //
            // This is a trick so that viewers which do not support the alpha
            // channel will see a white background (and not a black one).
            //
            boolean forceTransparentWhite = false;

            if (hints.containsKey(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE)) {
                forceTransparentWhite = (Boolean) hints.get(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE);
            }

            if (forceTransparentWhite) {
                SinglePixelPackedSampleModel sppsm;
                sppsm = (SinglePixelPackedSampleModel) img.getSampleModel();
                forceTransparentWhite(img, sppsm);
            }

            WriteAdapter adapter = new PNGTranscoderInternalCodecWriteAdapter();
            adapter.writeImage(this, img, output);
        }

    }

    /**
     * The {@link URI} to retrieve the content of the image.
     */
    private final URI uri;

    /**
     * The image width.
     */
    private int width;

    /**
     * The image height.
     */
    private int height;

    /**
     * The relative width.
     */
    private int relativeWidth = -1;

    /**
     * The relative height.
     */
    private int relativeHeight = -1;

    /**
     * Tells if we keep the aspect ratio.
     */
    private boolean conserveRatio;

    /**
     * The aspect ratio.
     */
    private double ratio;

    /**
     * The type.
     */
    private final PictureType type;

    /**
     * The URI converter to use.
     */
    private final URIConverter uriConverter;

    /**
     * A buffer to store the image content instead of using the {@link URIConverter} and the {@link URI}.
     */
    private final byte[] buffer;

    /**
     * Constructor.
     * <p>
     * The image type is deducted from the image {@link URI} file extension. If the extension is unknown, jpeg format will be selected by
     * default.
     * </p>
     * 
     * @param uriConverter
     *            the {@link URIConverter uri converter} to use
     * @param uri
     *            the {@link URI}
     */
    public MImageImpl(URIConverter uriConverter, URI uri) {
        this(uriConverter, uri, PictureType.toType(uri));
    }

    /**
     * Constructor.
     * 
     * @param buffer
     *            the image buffer
     * @param type
     *            the picture {@link PictureType type}
     */
    public MImageImpl(byte[] buffer, PictureType type) {
        this(buffer, null, URI.createURI("memory_buffer." + type.name().toLowerCase()), type);
    }

    /**
     * Constructor with enforced image type.
     * 
     * @param uriConverter
     *            the {@link URIConverter uri converter} to use
     * @param uri
     *            the {@link URI}
     * @param type
     *            the picture {@link PictureType type}
     */
    public MImageImpl(URIConverter uriConverter, URI uri, PictureType type) {
        this(null, uriConverter, uri, type);
    }

    /**
     * Constructor with enforced image type.
     * 
     * @param buffer
     *            optional image buffer
     * @param uriConverter
     *            the {@link URIConverter uri converter} to use
     * @param uri
     *            the {@link URI}
     * @param type
     *            the picture {@link PictureType type}
     */
    public MImageImpl(byte[] buffer, URIConverter uriConverter, URI uri, PictureType type) {
        this.buffer = buffer;
        this.uriConverter = uriConverter;
        this.uri = uri;
        this.type = type;
        if (type == PictureType.EMF) {
            try {
                try (InputStream is = getInputStream()) {
                    HemfPicture emfPicture = new HemfPicture(is);
                    final Rectangle2D bounds = emfPicture.getBounds();
                    width = (int) bounds.getWidth();
                    width = (int) bounds.getWidth();
                }
            } catch (IOException e) {
                // will continue with out ratio and width x height preset
                ratio = -1;
            }
        } else {
            try (InputStream input = getInputStream()) {
                final BufferedImage image = ImageIO.read(input);
                if (image != null) {
                    width = image.getWidth();
                    height = image.getHeight();
                    conserveRatio = true;
                    ratio = ((double) width) / ((double) height);
                } else {
                    conserveRatio = false;
                    ratio = -1;
                }
            } catch (IOException e) {
                // will continue with out ratio and width x height preset
                ratio = -1;
            }
        }
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
        if (conserveRatio) {
            height = (int) (1 / getRatio() * width);
        }
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
        if (conserveRatio) {
            width = (int) (getRatio() * height);
        }
    }

    @Override
    public int getRelativeWidth() {
        return relativeWidth;
    }

    @Override
    public void setRelativeWidth(int relativeWidth) {
        this.relativeWidth = relativeWidth;
    }

    @Override
    public int getRelativeHeight() {
        return relativeHeight;
    }

    @Override
    public void setRelativeHeight(int relativeHeight) {
        this.relativeHeight = relativeHeight;
    }

    @Override
    public boolean conserveRatio() {
        return conserveRatio;
    }

    @Override
    public void setConserveRatio(boolean conserveRatio) {
        if (conserveRatio && ratio < 0) {
            throw new IllegalStateException("Can't conserve ratio: unknown ratio");
        }
        this.conserveRatio = conserveRatio;
    }

    @Override
    public double getRatio() {
        return ratio;
    }

    @Override
    public URI getURI() {
        return uri;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        final InputStream res;

        if (getType() == PictureType.SVG) {
            // svg to png conversion.
            final M2DocPNGTranscoder transcoder = new M2DocPNGTranscoder();
            try (InputStream is = createInputStream(); ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                final TranscoderInput input = new TranscoderInput(is);
                final TranscoderOutput output = new TranscoderOutput(os);
                transcoder.transcode(input, output);
                res = new ByteArrayInputStream(os.toByteArray());
            } catch (TranscoderException e) {
                e.printStackTrace();
                throw new IOException("SVG to PNG transcode issue", e);
            }
        } else {
            res = createInputStream();
        }

        return res;
    }

    /**
     * Creates the image {@link InputStream}.
     * 
     * @return the image {@link InputStream}
     * @throws IOException
     *             if the {@link InputStream} can't be created
     */
    protected InputStream createInputStream() throws IOException {
        final InputStream res;

        if (buffer != null) {
            res = new ByteArrayInputStream(buffer);
        } else {
            res = uriConverter.createInputStream(uri);
        }

        return res;
    }

    @Override
    public PictureType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Image " + uri.toString();
    }

    /**
     * Gets the buffer.
     * 
     * @return the buffer if nay, <code>null</code> otherwise
     */
    public byte[] getBuffer() {
        return buffer;
    }

}
