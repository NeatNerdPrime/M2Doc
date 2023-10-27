/*******************************************************************************
 *  Copyright (c) 2016, 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.obeonetwork.m2doc.services;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.obeonetwork.m2doc.element.MElement;
import org.obeonetwork.m2doc.element.MImage;
import org.obeonetwork.m2doc.element.MList;
import org.obeonetwork.m2doc.element.MParagraph;
import org.obeonetwork.m2doc.element.MTable;
import org.obeonetwork.m2doc.element.MTable.MCell;
import org.obeonetwork.m2doc.element.MTable.MRow;
import org.obeonetwork.m2doc.element.PictureType;
import org.obeonetwork.m2doc.element.impl.MImageAWTImpl;
import org.obeonetwork.m2doc.element.impl.MImageImpl;

//@formatter:off
@ServiceProvider(
  value = "Services available for Images. See [document examples](https://github.com/ObeoNetwork/M2Doc/tree/master/tests/org.obeonetwork.m2doc.tests/resources/imageServices)."
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class ImageServices {

    /**
     * The URI converter to use.
     */
    private final URIConverter uriConverter;

    /**
     * The template URI.
     */
    private final URI templateURI;

    /**
     * Constructor.
     * 
     * @param uriConverter
     *            the {@link URIConverter uri converter} to use.
     * @param templateURI
     *            the template {@link URI}
     */
    public ImageServices(URIConverter uriConverter, URI templateURI) {
        this.uriConverter = uriConverter;
        this.templateURI = templateURI;
    }

    // @formatter:off
    @Documentation(
        value = "Convert a String representing an URI to an Image.",
        params = {
            @Param(name = "uri", value = "The Image uri, it can be relative to the template"),
        },
        result = "insert the image",
        examples = {
            @Example(expression = "'image.png'.asImage()", result = "insert the image 'image.png'"),
        }
    )
    // @formatter:on
    public MImage asImage(String uriStr) {
        final URI imageURI = URI.createURI(uriStr, true);
        return asImage(uriStr, PictureType.toType(imageURI));
    }

    // @formatter:off
    @Documentation(
        value = "Convert a String representing an URI to an Image and serialize it in the given format.",
        params = {
            @Param(name = "uri", value = "The Image uri, it can be relative to the template"),
        },
        result = "insert the image",
        examples = {
            @Example(expression = "'image.png'.asImage('jpg')", result = "insert the image 'image.jpg'"),
        }
    )
    // @formatter:on
    public MImage asImage(String uriStr, String type) {
        return asImage(uriStr, PictureType.valueOf(type.toUpperCase()));
    }

    /**
     * Gets the {@link MImage} corresponding to the given path.
     * 
     * @param uriStr
     *            the uri.
     * @param type
     *            the picture {@link PictureType type}.
     * @return the {@link MImage} corresponding to the given path
     */
    private MImage asImage(String uriStr, PictureType type) {
        final URI imageURI = URI.createURI(uriStr, true);
        final URI uri = imageURI.resolve(templateURI);

        return new MImageImpl(uriConverter, uri, type);
    }

    // @formatter:off
    @Documentation(
        value = "Gets the width of the image.",
        params = {
            @Param(name = "image", value = "The Image"),
        },
        result = "gets the width of the image",
        examples = {
            @Example(expression = "myImage.getWidth()", result = "300"),
        }
    )
    // @formatter:on
    public Integer getWidth(MImage image) {
        return image.getWidth();
    }

    // @formatter:off
    @Documentation(
        value = "Sets the width of the image.",
        params = {
            @Param(name = "image", value = "The Image"),
            @Param(name = "width", value = "The width"),
        },
        result = "sets the width of the image",
        examples = {
            @Example(expression = "myImage.setWidth(300)", result = "set the witdh to 300"),
        }
    )
    // @formatter:on
    public MImage setWidth(MImage image, Integer width) {
        image.setWidth(width);

        return image;
    }

    // @formatter:off
    @Documentation(
        value = "Gets the height of the image.",
        params = {
            @Param(name = "image", value = "The Image"),
        },
        result = "gets the height of the image",
        examples = {
            @Example(expression = "myImage.getHeight()", result = "300"),
        }
    )
    // @formatter:on
    public Integer getHeight(MImage image) {
        return image.getHeight();
    }

    // @formatter:off
    @Documentation(
        value = "Sets the height of the image.",
        params = {
            @Param(name = "image", value = "The Image"),
            @Param(name = "height", value = "The height"),
        },
        result = "sets the height of the image",
        examples = {
            @Example(expression = "myImage.setHeight(300)", result = "set the height to 300"),
        }
    )
    // @formatter:on
    public MImage setHeight(MImage image, Integer height) {
        image.setHeight(height);

        return image;
    }

    // @formatter:off
    @Documentation(
        value = "Sets the conserve ratio of the image.",
        params = {
            @Param(name = "image", value = "The Image"),
            @Param(name = "conserve", value = "A Boolean"),
        },
        result = "sets the conserve ratio of the image",
        examples = {
            @Example(expression = "myImage.setConserveRatio(false)", result = "set the conserve ratio to false"),
        }
    )
    // @formatter:on
    public MImage setConserveRatio(MImage image, Boolean conserve) {
        image.setConserveRatio(conserve);

        return image;
    }

    // @formatter:off
    @Documentation(
        value = "Fits the Image in the given rectangle width and height. same as myImage.fit(width, height, true)",
        params = {
            @Param(name = "image", value = "The Image"),
            @Param(name = "width", value = "The width to fit"),
            @Param(name = "height", value = "The height to fit"),
        },
        result = "the image with new dimensions",
        examples = {
            @Example(expression = "myImage.fit(200, 300)", result = "will fit the image in a rectangle (width=200, height=300)"),
        }
    )
    // @formatter:on
    public MImage fit(MImage image, Integer width, Integer height) {
        image.setWidth(width);
        if (!image.conserveRatio() || image.getHeight() > height) {
            image.setHeight(height);
        }

        return fit(image, width, height, true);
    }

    // @formatter:off
    @Documentation(
        value = "Fits the Image in the given rectangle width and height.",
        params = {
            @Param(name = "image", value = "The Image"),
            @Param(name = "width", value = "The width to fit"),
            @Param(name = "height", value = "The height to fit"),
            @Param(name = "zoomIn", value = "The image will be zoomed in if smaller"),
        },
        result = "the image with new dimensions",
        examples = {
            @Example(expression = "myImage.fit(200, 300, false)", result = "will fit the image in a rectangle (width=200, height=300) if the original image size is smaller it will not be zoomed in"),
        }
    )
    // @formatter:on
    public MImage fit(MImage image, Integer width, Integer height, boolean zoomIn) {
        if (zoomIn) {
            image.setWidth(width);
            if (!image.conserveRatio() || image.getHeight() > height) {
                image.setHeight(height);
            }
        } else {
            if (image.getWidth() > width) {
                image.setWidth(width);
                if (image.getHeight() > height) {
                    image.setHeight(height);
                }
            } else if (image.getHeight() > height) {
                image.setHeight(height);
                if (image.getWidth() > width) {
                    image.setWidth(width);
                }
            }
        }

        return image;
    }

    // @formatter:off
    @Documentation(
        value = "Resizes the Image by the given factor.",
        params = {
            @Param(name = "image", value = "The Image"),
            @Param(name = "factor", value = "The resize factor"),
        },
        result = "resize the image",
        examples = {
            @Example(expression = "myImage.resize(0.5)", result = "will resize the image by a factor 0.5"),
        }
    )
    // @formatter:on
    public MImage resize(MImage image, Double factor) throws IOException {
        final BufferedImage bufferedImage = MImageAWTImpl.getBufferedImage(image);

        final BufferedImage resized = new BufferedImage((int) (bufferedImage.getWidth() * factor),
                (int) (bufferedImage.getHeight() * factor), bufferedImage.getType());

        final AffineTransform zoomTransfort = AffineTransform.getScaleInstance(factor, factor);
        final AffineTransformOp retaillerImage = new AffineTransformOp(zoomTransfort, AffineTransformOp.TYPE_BILINEAR);
        retaillerImage.filter(bufferedImage, resized);

        return new MImageAWTImpl(resized, image.getURI());
    }

    // @formatter:off
    @Documentation(
        value = "Rotates the Image by the given angle in degres.",
        params = {
            @Param(name = "image", value = "The Image"),
            @Param(name = "angle", value = "The angle in degres"),
        },
        result = "rotate the image",
        examples = {
            @Example(expression = "myImage.rotate(90)", result = "will rotate the image by an angle of 90 degres"),
        }
    )
    // @formatter:on
    public MImage rotate(MImage image, Integer angle) throws IOException {
        final BufferedImage bufferedImage = MImageAWTImpl.getBufferedImage(image);

        final double rads = Math.toRadians(angle);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        final int x = width / 2;
        final int y = height / 2;
        final int newWidth = (int) Math.floor(width * cos + height * sin);
        final int newHeight = (int) Math.floor(height * cos + width * sin);

        final BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        final AffineTransform translateTransform = new AffineTransform();
        translateTransform.translate((newWidth - width) / 2, (newHeight - height) / 2);
        translateTransform.rotate(rads, x, y);

        final Graphics2D g2d = rotated.createGraphics();
        g2d.setTransform(translateTransform);
        g2d.drawImage(bufferedImage, 0, 0, null);
        g2d.dispose();

        return new MImageAWTImpl(rotated, image.getURI());
    }

    // @formatter:off
    @Documentation(
            value = "Fits all Images of the given MElement in the given rectangle width and height.",
            params = {
                @Param(name = "element", value = "The MElement"),
                @Param(name = "width", value = "The width to fit"),
                @Param(name = "height", value = "The height to fit"),
                @Param(name = "zoomIn", value = "The image will be zoomed in if smaller"),
            },
            result = "the MElement with resized images",
            examples = {
                @Example(expression = "myImage.fitAll(200, 300, false)", result = "will fit all images in a rectangle (width=200, height=300) if the original image size is smaller it will not be zoomed in"),
            }
        )
    // @formatter:on
    public MElement fitAll(MElement element, Integer width, Integer height, boolean zoomIn) {
        final MElement res;

        if (element instanceof MList) {
            final List<MElement> newElements = new ArrayList<>();
            for (MElement e : (MList) element) {
                newElements.add(fitAll(e, width, height, zoomIn));
            }
            ((MList) element).clear();
            ((MList) element).addAll(newElements);
            res = element;
        } else if (element instanceof MParagraph) {
            final MElement newContent = fitAll(((MParagraph) element).getContents(), width, height, zoomIn);
            if (newContent != ((MParagraph) element).getContents()) {
                ((MParagraph) element).setContents(newContent);
            }
            res = element;
        } else if (element instanceof MTable) {
            for (MRow row : ((MTable) element).getRows()) {
                for (MCell cell : row.getCells()) {
                    final MElement newContent = fitAll(cell.getContents(), width, height, zoomIn);
                    if (newContent != cell.getContents()) {
                        cell.setContents(newContent);
                    }
                }
            }
            res = element;
        } else if (element instanceof MImage) {
            final MImage image = (MImage) element;
            fit(image, width, height, zoomIn);
            res = image;
        } else {
            res = element;
        }

        return res;
    }

}
