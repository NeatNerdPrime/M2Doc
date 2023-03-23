/*******************************************************************************
 *  Copyright (c) 2019, 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.obeonetwork.m2doc.html.services;

import java.awt.Color;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.obeonetwork.m2doc.element.MElement;
import org.obeonetwork.m2doc.element.MElementContainer.HAlignment;
import org.obeonetwork.m2doc.element.MHyperLink;
import org.obeonetwork.m2doc.element.MImage;
import org.obeonetwork.m2doc.element.MList;
import org.obeonetwork.m2doc.element.MPagination;
import org.obeonetwork.m2doc.element.MParagraph;
import org.obeonetwork.m2doc.element.MStyle;
import org.obeonetwork.m2doc.element.MTable;
import org.obeonetwork.m2doc.element.MTable.MCell;
import org.obeonetwork.m2doc.element.MTable.MRow;
import org.obeonetwork.m2doc.element.MText;
import org.obeonetwork.m2doc.element.impl.MHyperLinkImpl;
import org.obeonetwork.m2doc.element.impl.MImageImpl;
import org.obeonetwork.m2doc.element.impl.MListImpl;
import org.obeonetwork.m2doc.element.impl.MParagraphImpl;
import org.obeonetwork.m2doc.element.impl.MStyleImpl;
import org.obeonetwork.m2doc.element.impl.MTableImpl;
import org.obeonetwork.m2doc.element.impl.MTableImpl.MCellImpl;
import org.obeonetwork.m2doc.element.impl.MTableImpl.MRowImpl;
import org.obeonetwork.m2doc.element.impl.MTextImpl;
import org.obeonetwork.m2doc.services.PaginationServices;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLevelText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTNumFmt;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTNumbering;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMultiLevelType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;

/**
 * Parse HTML to {@link MElement}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class M2DocHTMLParser extends Parser {

    /**
     * The ol HTML tag.
     */
    private static final String OL_TAG = "ol";

    /**
     * The ul HTML tag.
     */
    private static final String UL_TAG = "ul";

    /**
     * The blockquote HTML tag.
     */
    private static final String BLOCKQUOTE_TAG = "blockquote";

    /**
     * Courier New font.
     */
    private static final String COURIER_NEW_FONT = "Courier New";

    /**
     * Nebula code CSS class color.
     */
    private static final int CODE_GREEN = 0x004000;

    /**
     * Wingdings font.
     */
    private static final String WINGDINGS_FONT = "Wingdings";

    /**
     * Symbol font.
     */
    private static final String SYMBOL_FONT = "Symbol";

    /**
     * The default font size.
     */
    private static final int DEFAULT_FONT_SIZE = 11;

    /**
     * The big tag ratio.
     */
    private static final double BIG_RATIO = 1.33;

    /**
     * The small tag ratio.
     */
    private static final double SMALL_RATIO = 0.8;

    /**
     * The height attribute.
     */
    private static final String HEIGHT_ATTR = "height";

    /**
     * The width attribute.
     */
    private static final String WIDTH_ATTR = "width";

    /**
     * The style attribute.
     */
    private static final String STYLE_ATTR = "style";

    /**
     * The class attribute.
     */
    private static final String CLASS_ATTR = "class";

    /**
     * The dir attribute.
     */
    private static final String DIR_ATTR = "dir";

    /**
     * The indentation left.
     */
    private static final int INDENT_LEFT = 720;

    /**
     * The indentation hanging.
     */
    private static final int INDENT_HANGING = 180;

    /**
     * The type attribute.
     */
    private static final String TYPE_ATTR = "type";

    /**
     * The disc symbol for bullet list.
     */
    private static final String DISC_SYMBOL = "\uF0B7";

    /**
     * The square symbol for bullet list.
     */
    private static final String SQUARE_SYMBOL = "\uF0A7";

    /**
     * The circle symbol for bullet list.
     */
    private static final String CIRCLE_SYMBOL = "o";

    /**
     * The H6 tag font size.
     */
    private static final int H6_FONT_SIZE = 8;

    /**
     * The H5 tag font size.
     */
    private static final int H5_FONT_SIZE = 10;

    /**
     * The H4 tag font size.
     */
    private static final int H4_FONT_SIZE = 12;

    /**
     * The H3 tag font size.
     */
    private static final int H3_FONT_SIZE = 14;

    /**
     * The H2 tag font size.
     */
    private static final int H2_FONT_SIZE = 18;

    /**
     * The H1 tag font size.
     */
    private static final int H1_FONT_SIZE = 24;

    /**
     * Block quote {@link MParagraph#getMarginLeft() margin left}.
     */
    private static final int BLOCKQUOTE_DEFAULT_MARGING_LEFT = 40;

    /**
     * The {@link Context} for {@link M2DocHTMLParser#walkNodeTree(MElement, MStyle, Node) tree walking}.
     * 
     * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
     */
    protected static final class Context {

        /**
         * The text direction (dir) attribute.
         * 
         * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
         */
        private enum Dir {
            /** Left to right. */
            LTR,
            /** Right to left. */
            RTL;
        }

        /**
         * The current {@link MStyle}.
         */
        private final MStyle style;

        /**
         * The current numbering.
         */
        private CTAbstractNum numbering;

        /**
         * The base URI.
         */
        private final URI baseURI;

        /**
         * The current link target if any, <code>null</code> otherwise.
         */
        private URI linkTargetURI;

        /**
         * The numbering ID.
         */
        private BigInteger numberingID;

        /**
         * The current numbering level.
         */
        private long numberingLevel;

        /**
         * The current direction of the text.
         */
        private Dir dir;

        /**
         * CSS properties.
         */
        private final Map<String, List<String>> cssProperties = new HashMap<String, List<String>>();

        /**
         * The stack of margin left.
         */
        private Stack<Integer> marginLefts = new Stack<Integer>();

        /**
         * Constructor.
         * 
         * @param baseURI
         *            the base {@link URI}
         * @param style
         *            the current {@link MStyle}.
         */
        private Context(URI baseURI, MStyle style) {
            this.baseURI = baseURI;
            this.style = style;
        }

        /**
         * Copies this {@link Context}.
         * 
         * @return the copy of this {@link Context}
         */
        private Context copy() {
            final MStyleImpl mStyle = new MStyleImpl(style.getFontName(), style.getFontSize(),
                    style.getForegroundColor(), style.getBackgroundColor(), style.getFontModifiers());
            Context res = new Context(baseURI, mStyle);

            res.linkTargetURI = linkTargetURI;
            res.numbering = numbering;
            res.numberingID = numberingID;
            res.numberingLevel = numberingLevel;
            res.dir = dir;
            res.cssProperties.putAll(cssProperties);
            res.marginLefts = marginLefts;

            return res;
        }

        /**
         * Pushes the given margin left value.
         * 
         * @param value
         *            the margin left value
         */
        public void pushMarginLeft(Integer value) {
            marginLefts.push(value);
        }

        /**
         * Pops the last {@link #pushMarginLeft(int) pushed} margin left value.
         * 
         * @return the last {@link #pushMarginLeft(int) pushed} margin left value
         */
        public Integer popMarginLeft() {
            return marginLefts.pop();
        }

        /**
         * Replaces the last {@link #pushMarginLeft(int) pushed} margin left value if it's <code>null</code>.
         * 
         * @param value
         *            the new margin left value
         */
        public void replaceLastDefaultMarginLeft(Integer value) {
            if (marginLefts.peek() == null) {
                popMarginLeft();
                pushMarginLeft(value);
            }
        }

        /**
         * Gets the current margin left.
         * 
         * @return the current margin left if any, <code>null</code> otherwise
         */
        public Integer getMarginLeft() {
            int sum = 0;

            boolean hasValue = false;
            for (Integer currentMarginLeft : marginLefts) {
                if (currentMarginLeft != null) {
                    hasValue = true;
                    sum += currentMarginLeft;
                }
            }

            final Integer res;
            if (hasValue) {
                res = Integer.valueOf(sum);
            } else {
                res = null;
            }

            return res;
        }
    }

    /**
     * The {@link MHyperLink} {@link Color}.
     */
    private static final Color LINK_COLOR = Color.BLUE;

    /**
     * The {@link M2DocCSSParser}.
     */
    private static final M2DocCSSParser CSS_PARSER = new M2DocCSSParser();

    /**
     * The {@link URIConverter}.
     */
    private final URIConverter uriConverter;

    /**
     * The destination {@link XWPFDocument}.
     */
    private XWPFDocument destinationDocument;

    /**
     * Constructor.
     * 
     * @param uriConverter
     *            the {@link URIConverter}
     * @param destinationDocument
     *            the destination XWPFDocument
     */
    public M2DocHTMLParser(URIConverter uriConverter, XWPFDocument destinationDocument) {
        this.uriConverter = uriConverter;
        this.destinationDocument = destinationDocument;
    }

    /**
     * Parses the given HTML {@link String} with the given base URI.
     * 
     * @param baseURI
     *            the base {@link URI}
     * @param htmlString
     *            the HTML {@link String}
     * @return the {@link List} of parsed {@link MElement}
     */
    public List<MElement> parse(URI baseURI, String htmlString) {
        final MList res = new MListImpl();

        final Document document = Jsoup.parse(htmlString, baseURI.toString());
        document.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        document.outputSettings().charset("UTF-8");

        final MStyle defaultStyle = new MStyleImpl(null, -1, null, null, -1);
        if (document.body().hasAttr("bgcolor")) {
            defaultStyle.setBackgroundColor(htmlToColor(document.body().attr("bgcolor").toLowerCase()));
        }

        final Context context = new Context(baseURI, defaultStyle);
        context.pushMarginLeft(null);
        try {
            applyGlobalAttibutes(context, document.body());
        } finally {
            context.popMarginLeft();
        }

        walkNodeTree(res, context, document.body(), null);

        return res;
    }

    /**
     * Walks the given {@link Node} recursibly to produce {@link MElement}.
     * 
     * @param parent
     *            the parent {@link MList}
     * @param context
     *            the current {@link Context}
     * @param node
     *            the {@link Node} to walk
     * @param lastElement
     *            the last {@link Element} if any, <code>null</code> otherwise
     */
    private void walkNodeTree(MList parent, Context context, Node node, Element lastElement) {
        final Context contextCopy = context.copy();
        if (node instanceof Element) {
            if ("table".equals(node.nodeName())) {
                Node tBody = null;
                for (Node child : node.childNodes()) {
                    if ("tbody".equals(child.nodeName())) {
                        tBody = child;
                        break;
                    }
                }
                if (tBody != null) {
                    insertTable(parent, context, tBody);
                }
            } else {
                if (UL_TAG.equals(node.nodeName()) || OL_TAG.equals(node.nodeName())) {
                    // remove inherited list-style-type
                    contextCopy.cssProperties.remove(M2DocCSSParser.CSS_LIST_STYLE_TYPE);
                }
                contextCopy.pushMarginLeft(null);
                applyGlobalAttibutes(contextCopy, node);
                if (BLOCKQUOTE_TAG.equals(node.nodeName())) {
                    contextCopy.replaceLastDefaultMarginLeft(BLOCKQUOTE_DEFAULT_MARGING_LEFT);
                }
                final MList element = startElement(parent, contextCopy, (Element) node);
                try {
                    walkChildren(node, contextCopy, element);
                } finally {
                    contextCopy.popMarginLeft();
                }
                endElement(parent, element);
            }
        } else if (node instanceof TextNode) {
            final boolean needNewParagraph = lastElement != null && (UL_TAG.equals(lastElement.nodeName())
                || OL_TAG.equals(lastElement.nodeName()) || BLOCKQUOTE_TAG.equals(lastElement.nodeName()));
            insertText(parent, contextCopy, (TextNode) node, needNewParagraph);
        }
    }

    /**
     * Walks all the children {@link Node} of the given {@link Node}.
     * 
     * @param node
     *            the {@link Node}
     * @param context
     *            the current {@link Context}
     * @param parent
     *            the parent {@link MList}
     */
    private void walkChildren(Node node, Context context, MList parent) {
        Element lastElement = null;
        for (Node child : node.childNodes()) {
            final boolean needQuotes = "q".equals(child.nodeName());
            if (needQuotes) {
                final MText mText = new MTextImpl("«", null);
                parent.add(mText);
            }
            walkNodeTree(parent, context, child, lastElement);
            if (needQuotes) {
                final MText mText = new MTextImpl("»", null);
                parent.add(mText);
            }
            if (!"div".equals(child.nodeName())) {
                if (child instanceof Element) {
                    lastElement = (Element) child;
                } else {
                    lastElement = null;
                }
            }
        }
    }

    /**
     * Inserts a table.
     * 
     * @param parent
     *            the parent {@link MList}
     * @param context
     *            the current {@link Context}
     * @param node
     *            the table {@link Node};
     */
    private void insertTable(MList parent, Context context, Node node) {
        final MTable table = new MTableImpl();
        parent.add(table);
        final Map<Integer, Integer> rowSpans = new HashMap<>();
        final Map<Integer, List<MCell>> vMergeCopies = new HashMap<>();
        for (Node child : node.childNodes()) {
            if ("tr".equals(child.nodeName())) {
                final MRow row = new MRowImpl();
                table.getRows().add(row);
                int currentColumn = 0;
                for (Node rowChild : child.childNodes()) {
                    if ("th".equals(rowChild.nodeName()) || "td".equals(rowChild.nodeName())) {
                        final MList contents = new MListImpl();
                        final MCell cell = new MCellImpl(contents, null);
                        final Context localContext;
                        if ("th".equals(rowChild.nodeName())) {
                            cell.setHAlignment(HAlignment.CENTER);
                            localContext = context.copy();
                            setModifiers(localContext.style, MStyle.FONT_BOLD);
                        } else {
                            localContext = context;
                        }
                        walkChildren(rowChild, localContext, contents);
                        currentColumn = insertMergedCells(row, rowChild, cell, rowSpans, vMergeCopies, currentColumn);
                    }
                }
                insertRowspans(row, rowSpans, vMergeCopies);
            }
        }
    }

    /**
     * Inserts the {@link MCell} corresponding to rowspans in the given {@link MRow}.
     * 
     * @param row
     *            the {@link MRow}
     * @param rowSpans
     *            the {@link Map} from the starting column to the number of remaining cells to merge
     * @param vMergeCopies
     *            the {@link Map} from the starting column to the {@link List} of {@link MCell} to copy
     */
    private void insertRowspans(final MRow row, final Map<Integer, Integer> rowSpans,
            final Map<Integer, List<MCell>> vMergeCopies) {
        int currentColumn;
        currentColumn = row.getCells().size();
        Integer remainingRowSpan = rowSpans.remove(currentColumn);
        while (remainingRowSpan != null) {
            final List<MCell> toCopy = vMergeCopies.get(currentColumn);
            row.getCells().addAll(toCopy);
            final int newRemainingRowSpan = remainingRowSpan - 1;
            if (newRemainingRowSpan > 0) {
                rowSpans.put(currentColumn, newRemainingRowSpan);
            } else {
                vMergeCopies.remove(currentColumn);
            }
            currentColumn = row.getCells().size();
            remainingRowSpan = rowSpans.remove(currentColumn);
        }
    }

    /**
     * Inserts merged {@link MCell}.
     * 
     * @param row
     *            the current {@link MRow}
     * @param rowChild
     *            the current row child (th or td)
     * @param cell
     *            the current {@link MCell}
     * @param rowSpans
     *            the {@link Map} from the starting column to the number of remaining cells to merge
     * @param vMergeCopies
     *            the {@link Map} from the starting column to the {@link List} of {@link MCell} to copy
     * @param currentColumn
     *            the current column
     * @return the new current column
     */
    private int insertMergedCells(final MRow row, Node rowChild, final MCell cell, Map<Integer, Integer> rowSpans,
            Map<Integer, List<MCell>> vMergeCopies, int currentColumn) {
        int res = currentColumn;

        final int rowSpan = getRowSpan(rowChild);
        final boolean restartVMerge;
        if (rowSpan > -1) {
            cell.setVMerge(MCell.Merge.RESTART);
            restartVMerge = true;
            if (rowSpan == 0) {
                rowSpans.put(currentColumn, Integer.MAX_VALUE);
            } else {
                rowSpans.put(currentColumn, rowSpan - 1);
            }
        } else {
            restartVMerge = false;
            insertRowspans(row, rowSpans, vMergeCopies);
        }

        row.getCells().add(cell);
        res++;

        final int colSpan = getColSpan(rowChild);
        final boolean restartHMerge;
        if (colSpan > 1) {
            cell.setHMerge(MCell.Merge.RESTART);
            restartHMerge = true;
            for (int i = 1; i < colSpan; i++) {
                final MList hMergedContents = new MListImpl();
                final MCell hMergedCell = new MCellImpl(hMergedContents, null);
                hMergedCell.setHMerge(MCell.Merge.CONTINUE);
                hMergedCell.setVMerge(cell.getVMerge());
                row.getCells().add(hMergedCell);
                res++;
            }
        } else {
            restartHMerge = false;
        }

        if (restartVMerge) {
            final List<MCell> vMergeCopy = new ArrayList<>();
            final MList vMergedContents = new MListImpl();
            final MCell vMergedCell = new MCellImpl(vMergedContents, null);
            vMergedCell.setVMerge(MCell.Merge.CONTINUE);
            if (restartHMerge) {
                vMergedCell.setHMerge(MCell.Merge.RESTART);
            }
            vMergeCopy.add(vMergedCell);

            for (int i = 0; i < res - currentColumn - 1; i++) {
                final MList hMergedContents = new MListImpl();
                final MCell hMergedCell = new MCellImpl(hMergedContents, null);
                hMergedCell.setVMerge(MCell.Merge.CONTINUE);
                hMergedCell.setHMerge(MCell.Merge.CONTINUE);
                vMergeCopy.add(hMergedCell);
            }
            vMergeCopies.put(currentColumn, vMergeCopy);
        }

        return res;
    }

    /**
     * Gets the colspan attribute of the given {@link Node}.
     * 
     * @param node
     *            the {@link Node}
     * @return the colspan attribute of the given {@link Node} if any, <code>-1</code> otherwise
     */
    private int getColSpan(Node node) {
        int res;

        if (node.hasAttr("colspan")) {
            try {
                res = Integer.valueOf(node.attr("colspan"));
            } catch (NumberFormatException e) {
                res = -1;
            }
        } else {
            res = -1;
        }

        return res;
    }

    /**
     * Gets the rowspan attribute of the given {@link Node}.
     * 
     * @param node
     *            the {@link Node}
     * @return the rowspan attribute of the given {@link Node} if any, <code>-1</code> otherwise
     */
    private int getRowSpan(Node node) {
        int res;

        if (node.hasAttr("rowspan")) {
            try {
                res = Integer.valueOf(node.attr("rowspan"));
            } catch (NumberFormatException e) {
                res = -1;
            }
        } else {
            res = -1;
        }

        return res;
    }

    /**
     * Ends the given {@link MElement}.
     * 
     * @param parent
     *            the parent {@link MList}
     * @param element
     *            the {@link MElement} to end
     */
    private void endElement(MList parent, MElement element) {
        if (element instanceof MList && ((MList) element).isEmpty()) {
            for (MElement child : parent) {
                if (child instanceof MParagraph && ((MParagraph) child).getContents() == element) {
                    parent.remove(child);
                    break;
                }
            }
        }
    }

    /**
     * Inserts the text of the given {@link TextNode}.
     * 
     * @param parent
     *            the parent {@link MList}
     * @param context
     *            the {@link Context}
     * @param node
     *            the {@link TextNode}
     * @param needNewParagraph
     *            tells if a new paragraph is needed
     */
    private void insertText(MList parent, final Context context, TextNode node, boolean needNewParagraph) {
        final String text = node.text();
        if (!text.trim().isEmpty()) {
            final String textToInsert;
            if (needNewParagraph) {
                createMParagraph(context, parent, null, null, null);
                textToInsert = trimFirst(text);
            } else {
                textToInsert = text;
            }
            if (context.linkTargetURI == null) {
                final MText mText = new MTextImpl(textToInsert, context.style);
                parent.add(mText);
            } else {
                context.style.setForegroundColor(LINK_COLOR);
                final MHyperLink mLink = new MHyperLinkImpl(textToInsert, context.style,
                        context.linkTargetURI.toString());
                parent.add(mLink);
            }
        }
    }

    /**
     * Trims the begining of the given {@link String}.
     * 
     * @param text
     *            the {@link String}
     * @return the trimed {@link String}
     */
    private String trimFirst(String text) {
        final String res;

        if (text != null && !text.isEmpty()) {
            int subStringStart = 0;
            final int textLength = text.length();
            while (subStringStart < textLength && Character.isWhitespace(text.charAt(subStringStart))) {
                subStringStart++;
            }
            res = text.substring(subStringStart);
        } else {
            res = text;
        }

        return res;
    }

    /**
     * Starts the given {@link Element}.
     * 
     * @param parent
     *            the parent {@link MList}
     * @param context
     *            the current {@link Context}
     * @param element
     *            the {@link Element}
     * @return the new parent {@link MList} for {@link Element#children() children}
     */
    private MList startElement(MList parent, Context context, Element element) {
        final MList res;

        final String nodeName = element.nodeName();
        boolean isNumbering = false;
        if ("p".equals(nodeName)) {
            res = createMParagraph(context, parent, element, null, null);
        } else if (BLOCKQUOTE_TAG.equals(nodeName)) {
            if (element.childNodeSize() > 0 && element.childNode(0) instanceof TextNode) {
                TextNode textNode = (TextNode) element.childNode(0);
                String newText = trimFirst(textNode.text());
                textNode.text(newText);
                if (!newText.isEmpty()) {
                    res = createMParagraph(context, parent, element, null, null);
                } else {
                    res = parent;
                }
            } else {
                res = parent;
            }
        } else if ("strong".equals(nodeName) || "b".equals(nodeName)) {
            setModifiers(context.style, MStyle.FONT_BOLD);
            res = parent;
        } else if ("em".equals(nodeName) || "i".equals(nodeName) || "var".equals(nodeName) || "cite".equals(nodeName)) {
            setModifiers(context.style, MStyle.FONT_ITALIC);
            res = parent;
        } else if ("s".equals(nodeName) || "strike".equals(nodeName) || "del".equals(nodeName)) {
            setModifiers(context.style, MStyle.FONT_STRIKE_THROUGH);
            res = parent;
        } else if ("u".equals(nodeName) || "ins".equals(nodeName)) {
            setModifiers(context.style, MStyle.FONT_UNDERLINE);
            res = parent;
        } else if ("sub".equals(nodeName)) {
            setModifiers(context.style, MStyle.SUBSCRIPT);
            res = parent;
        } else if ("sup".equals(nodeName)) {
            setModifiers(context.style, MStyle.SUPERSCRIPT);
            res = parent;
        } else if ("font".equals(nodeName)) {
            if (element.hasAttr("color")) {
                context.style.setForegroundColor(htmlToColor(element.attr("color").toLowerCase()));
            }
            if (element.hasAttr("face")) {
                // TODO double check this
                context.style.setFontName(element.attr("face"));
            }
            if (element.hasAttr("size")) {
                context.style.setFontSize(fontSizeToPoint(Integer.valueOf(element.attr("size"))));
            }
            res = parent;
        } else if ("a".equals(nodeName)) {
            context.linkTargetURI = URI.createURI(element.attr("href")).resolve(context.baseURI);
            res = parent;
        } else if ("br".equals(nodeName)) {
            parent.add(MPagination.ligneBreak);
            res = parent;
        } else if ("li".equals(nodeName)) {
            res = createMParagraph(context, parent, element, context.numberingID.longValue(),
                    context.numberingLevel - 1);
            isNumbering = true;
        } else if (OL_TAG.equals(nodeName)) {
            setOrderedListNumbering(context, element);
            isNumbering = true;
            res = parent;
        } else if (UL_TAG.equals(nodeName)) {
            setUnorderedListNumbering(context, element);
            isNumbering = true;
            res = parent;
        } else if ("img".equals(nodeName)) {
            final MImage mImage = createMImage(context, element);
            parent.add(mImage);
            res = parent;
        } else if ("big".equals(nodeName)) {
            setBigFont(context);
            res = parent;
        } else if ("small".equals(nodeName)) {
            setSmallFont(context);
            res = parent;
        } else
            if ("tt".equals(nodeName) || "code".equals(nodeName) || "samp".equals(nodeName) || "kbd".equals(nodeName)) {
                context.style.setFontName(COURIER_NEW_FONT);
                res = parent;
            } else if ("h1".equals(nodeName)) {
                res = createHeading(parent, context, element, H1_FONT_SIZE);
            } else if ("h2".equals(nodeName)) {
                res = createHeading(parent, context, element, H2_FONT_SIZE);
            } else if ("h3".equals(nodeName)) {
                res = createHeading(parent, context, element, H3_FONT_SIZE);
            } else if ("h4".equals(nodeName)) {
                res = createHeading(parent, context, element, H4_FONT_SIZE);
            } else if ("h5".equals(nodeName)) {
                res = createHeading(parent, context, element, H5_FONT_SIZE);
            } else if ("h6".equals(nodeName)) {
                res = createHeading(parent, context, element, H6_FONT_SIZE);
            } else {
                res = parent;
            }

        if (!isNumbering) {
            context.numbering = null;
            context.numberingLevel = 0;
        }

        return res;
    }

    /**
     * Applies the global attributes from the given {@link Node} to the given {@link Context}.
     * 
     * @param context
     *            the {@link Context}
     * @param node
     *            the {@link Node}
     */
    private void applyGlobalAttibutes(Context context, Node node) {
        applyCSSStyle(node, context);
        applyMarkerClass(context, node);
        applyCodeClass(context, node);
        applyDir(context, node);
    }

    /**
     * Applies the marker class. This is specific to the nebula rich text editor used in Capella.
     * 
     * @param context
     *            the {@link Context}
     * @param node
     *            the {@link Node}
     */
    private void applyMarkerClass(Context context, Node node) {
        if (node.hasAttr(CLASS_ATTR) && "marker".equals(node.attr(CLASS_ATTR))) {
            context.style.setBackgroundColor(Color.YELLOW);
        }
    }

    /**
     * Applies the code class. This is specific to the nebula rich text editor used in Capella.
     * 
     * @param context
     *            the {@link Context}
     * @param node
     *            the {@link Node}
     */
    private void applyCodeClass(Context context, Node node) {
        if (node.hasAttr(CLASS_ATTR) && "code".equals(node.attr(CLASS_ATTR))) {
            context.style.setForegroundColor(new Color(CODE_GREEN));
            setModifiers(context.style, MStyle.FONT_BOLD);
            context.style.setFontName(COURIER_NEW_FONT);
        }
    }

    /**
     * Applies the direction attribute of the given {@link Node} to the given {@link Context}.
     * 
     * @param context
     *            the {@link Context}
     * @param node
     *            the {@link Node}
     */
    private void applyDir(Context context, Node node) {
        if (node.hasAttr(DIR_ATTR)) {
            if ("ltr".equals(node.attr(DIR_ATTR))) {
                context.dir = Context.Dir.LTR;
            } else if ("rtl".equals(node.attr(DIR_ATTR))) {
                context.dir = Context.Dir.RTL;
            }
        }
    }

    /**
     * Sets the size of the font to big in the given {@link Context}.
     * 
     * @param context
     *            the {@link Context}
     */
    private void setBigFont(Context context) {
        final int fontSize;
        if (context.style.getFontSize() == -1) {
            fontSize = (int) (DEFAULT_FONT_SIZE * BIG_RATIO);
        } else {
            fontSize = (int) (context.style.getFontSize() * BIG_RATIO);
        }
        context.style.setFontSize(fontSize);
    }

    /**
     * Sets the size of the font to small in the given {@link Context}.
     * 
     * @param context
     *            the {@link Context}
     */
    private void setSmallFont(Context context) {
        final int fontSize;
        if (context.style.getFontSize() == -1) {
            fontSize = (int) (DEFAULT_FONT_SIZE * SMALL_RATIO);
        } else {
            fontSize = (int) (context.style.getFontSize() * SMALL_RATIO);
        }
        context.style.setFontSize(fontSize);
    }

    /**
     * Applies the CSS from the style attribute of the given {@link Element} to the given {@link Context}.
     * 
     * @param node
     *            the {@link Node}
     * @param context
     *            the current {@link Context}
     */
    private void applyCSSStyle(Node node, Context context) {
        if (node.hasAttr(STYLE_ATTR)) {
            final Map<String, List<String>> cssProperties = CSS_PARSER.parse(node.attr(STYLE_ATTR));
            CSS_PARSER.setStyle(cssProperties, context.style);
            context.cssProperties.putAll(cssProperties);
        }
    }

    /**
     * Creates the heading with the given font size.
     * 
     * @param parent
     *            the parent {@link MList}
     * @param context
     *            the current {@link Context}
     * @param element
     *            the {@link Element}
     * @param fontSize
     *            the font size
     * @return the new parent {@link MList} for {@link Element#children() children}
     */
    private MList createHeading(MList parent, Context context, Element element, int fontSize) {
        final MList res;

        res = createMParagraph(context, parent, element, null, null);
        context.style.setFontSize(fontSize);
        setModifiers(context.style, MStyle.FONT_BOLD);

        return res;
    }

    /**
     * Creates a {@link MImage} for the given {@link Context} and {@link Element}.
     * 
     * @param context
     *            the {@link Context}
     * @param element
     *            the {@link Element}
     * @return the created {@link MImage}
     */
    private MImage createMImage(Context context, Element element) {
        final URI imageURI = URI.createURI(element.attr("src")).resolve(context.baseURI);
        final MImage mImage = new MImageImpl(uriConverter, imageURI);

        if (element.hasAttr(WIDTH_ATTR)) {
            if (element.hasAttr(HEIGHT_ATTR)) {
                mImage.setConserveRatio(false);
                mImage.setWidth(Integer.valueOf(element.attr(WIDTH_ATTR)));
                mImage.setHeight(Integer.valueOf(element.attr(HEIGHT_ATTR)));
            } else {
                mImage.setWidth(Integer.valueOf(element.attr(WIDTH_ATTR)));
            }
        } else if (element.hasAttr(HEIGHT_ATTR)) {
            mImage.setHeight(Integer.valueOf(element.attr(HEIGHT_ATTR)));
        }

        return mImage;
    }

    /**
     * Sets the unordered list numbering.
     * 
     * @param context
     *            the {@link Context}
     * @param element
     *            the ol {@link Element}
     */
    private void setUnorderedListNumbering(Context context, Element element) {
        final String symbol;
        final Enum type;
        final String typeAttr = element.attr(TYPE_ATTR);
        if ("disc".equals(typeAttr)
            || CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "disc")) {
            symbol = DISC_SYMBOL;
            type = STNumberFormat.BULLET;
        } else if ("square".equals(typeAttr)
            || CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "square")) {
                symbol = SQUARE_SYMBOL;
                type = STNumberFormat.BULLET;
            } else if ("circle".equals(typeAttr)
                || CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "circle")) {
                    symbol = CIRCLE_SYMBOL;
                    type = STNumberFormat.BULLET;
                } else if (!CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "none")) {
                    symbol = DISC_SYMBOL;
                    type = STNumberFormat.BULLET;
                } else {
                    symbol = "";
                    type = STNumberFormat.NONE;
                }

        if (context.numbering == null) {
            createNumbering(context);
        }
        incrementNumberingLevel(context, type, 1, symbol, false);
    }

    /**
     * Sets the ordered list numbering.
     * 
     * @param context
     *            the {@link Context}
     * @param element
     *            the ol {@link Element}
     */
    private void setOrderedListNumbering(Context context, Element element) {
        final STNumberFormat.Enum type;
        final String typeStr;
        if (element.hasAttr(TYPE_ATTR)) {
            typeStr = element.attr(TYPE_ATTR);
        } else {
            typeStr = null;
        }
        if ("1".equals(typeStr)
            || CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "decimal")) {
            type = STNumberFormat.DECIMAL;
        } else if ("A".equals(typeStr)
            || CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "upper-alpha")) {
                type = STNumberFormat.UPPER_LETTER;
            } else if ("a".equals(typeStr)
                || CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "lower-alpha")) {
                    type = STNumberFormat.LOWER_LETTER;
                } else if ("I".equals(typeStr)
                    || CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "upper-roman")) {
                        type = STNumberFormat.UPPER_ROMAN;
                    } else if ("i".equals(typeStr) || CSS_PARSER.hasCSS(context.cssProperties,
                            M2DocCSSParser.CSS_LIST_STYLE_TYPE, "lower-roman")) {
                                type = STNumberFormat.LOWER_ROMAN;
                            } else
                        if (!CSS_PARSER.hasCSS(context.cssProperties, M2DocCSSParser.CSS_LIST_STYLE_TYPE, "none")) {
                            type = STNumberFormat.DECIMAL;
                        } else {
                            type = STNumberFormat.NONE;
                        }

        final long start;
        if (element.hasAttr("start")) {
            start = Long.valueOf(element.attr("start"));
        } else {
            start = 1;
        }
        final boolean reversed = element.hasAttr("reversed");
        if (context.numbering == null) {
            createNumbering(context);
        }
        incrementNumberingLevel(context, type, start, "", reversed);
    }

    /**
     * Creates a numbering for the given {@link Context}.
     * 
     * @param context
     *            the {@link Context}
     */
    private void createNumbering(Context context) {
        final CTAbstractNum res;
        final XWPFNumbering numbering = destinationDocument.createNumbering();
        final CTNumbering ctNumbering = PaginationServices.getCTNumbering(numbering);
        res = ctNumbering.addNewAbstractNum();
        res.addNewMultiLevelType().setVal(STMultiLevelType.HYBRID_MULTILEVEL);
        BigInteger id = BigInteger.valueOf(ctNumbering.sizeOfAbstractNumArray() - 1);
        res.setAbstractNumId(id);
        final CTNum ctNum = ctNumbering.addNewNum();
        ctNum.setNumId(BigInteger.valueOf(ctNumbering.sizeOfNumArray()));
        ctNum.addNewAbstractNumId().setVal(id);

        context.numbering = res;
        context.numberingID = ctNum.getNumId();
    }

    /**
     * Increments the level for the given {@link CTAbstractNum}.
     * 
     * @param context
     *            the current {@link Context}
     * @param type
     *            the {@link STNumberFormat#enumValue()}
     * @param start
     *            the start
     * @param symbol
     *            the symbol
     * @param reversed
     *            tell if the numbering is reversed
     */
    private void incrementNumberingLevel(Context context, STNumberFormat.Enum type, long start, String symbol,
            boolean reversed) {
        if (context.numbering.getLvlList().size() <= context.numberingLevel) {
            final CTLvl level = context.numbering.addNewLvl();
            level.setIlvl(BigInteger.valueOf(context.numberingLevel));
            final CTDecimalNumber strt = level.addNewStart();
            strt.setVal(BigInteger.valueOf(start));
            final CTNumFmt fmt = level.addNewNumFmt();
            fmt.setVal(type);
            CTLevelText text = level.addNewLvlText();
            if (type == STNumberFormat.BULLET) {
                text.setVal(symbol);
                CTFonts font = level.addNewRPr().addNewRFonts();
                if (symbol == DISC_SYMBOL) {
                    font.setAscii(SYMBOL_FONT);
                    font.setHAnsi(SYMBOL_FONT);
                } else if (symbol == SQUARE_SYMBOL) {
                    font.setAscii(WINGDINGS_FONT);
                    font.setHAnsi(WINGDINGS_FONT);
                } else if (symbol == DISC_SYMBOL) {
                    font.setAscii(COURIER_NEW_FONT);
                    font.setHAnsi(COURIER_NEW_FONT);
                }
                level.addNewLvlJc().setVal(STJc.LEFT);
                final CTInd indentation = level.addNewPPr().addNewInd();
                indentation.setHanging(BigInteger.valueOf(INDENT_HANGING * 2));
                indentation.setLeft(BigInteger.valueOf(INDENT_LEFT * (context.numberingLevel + 1)));
            } else {
                final CTInd indentation = level.addNewPPr().addNewInd();
                if (type == STNumberFormat.NONE) {
                    text.setVal("%" + (context.numberingLevel + 1));
                } else {
                    text.setVal("%" + (context.numberingLevel + 1) + ".");
                }
                if (context.numberingLevel > 0) {
                    final STOnOff onOff = STOnOff.Factory.newInstance();
                    onOff.setStringValue("1");
                    level.xsetTentative(onOff);
                }
                if (type == STNumberFormat.UPPER_ROMAN) {
                    level.addNewLvlJc().setVal(STJc.RIGHT);
                    indentation.setHanging(BigInteger.valueOf(INDENT_HANGING * 2));
                } else if (type == STNumberFormat.LOWER_ROMAN) {
                    level.addNewLvlJc().setVal(STJc.RIGHT);
                    indentation.setHanging(BigInteger.valueOf(INDENT_HANGING));
                } else {
                    level.addNewLvlJc().setVal(STJc.LEFT);
                    indentation.setHanging(BigInteger.valueOf(INDENT_HANGING * 2));
                }
                indentation.setLeft(BigInteger.valueOf(INDENT_LEFT * (context.numberingLevel + 1)));
                // TODO reversed
            }
        }

        context.numberingLevel = context.numberingLevel + 1;
    }

    /**
     * Creates a {@link MParagraph}.
     * 
     * @param context
     *            the current {@link Context}
     * @param parent
     *            the parent {@link MList}
     * @param element
     *            the {@link Element}
     * @param numberingID
     *            the numbering ID
     * @param numberingLevel
     *            the numbering level
     * @return the created {@link MParagraph}
     */
    private MList createMParagraph(Context context, MList parent, Element element, Long numberingID,
            Long numberingLevel) {
        final MList res = new MListImpl();

        final MParagraph paragraph = new MParagraphImpl(res, null);
        parent.add(paragraph);
        paragraph.setNumberingID(numberingID);
        paragraph.setNumberingLevel(numberingLevel);
        if (element != null && element.hasAttr("align")) {
            final String align = element.attr("align");
            if ("left".equals(align)) {
                paragraph.setHAlignment(HAlignment.LEFT);
            } else if ("right".equals(align)) {
                paragraph.setHAlignment(HAlignment.RIGHT);
            } else if ("center".equals(align)) {
                paragraph.setHAlignment(HAlignment.CENTER);
            } else if ("justify".equals(align)) {
                paragraph.setHAlignment(HAlignment.DISTRIBUTE);
            }
        }
        if (context.dir == Context.Dir.LTR) {
            paragraph.setTextDirection(MParagraph.Dir.LTR);
        } else if (context.dir == Context.Dir.RTL) {
            paragraph.setTextDirection(MParagraph.Dir.RTL);
        } else {
            paragraph.setTextDirection(null);
        }

        CSS_PARSER.setStyle(context.cssProperties, context, paragraph);

        return res;
    }

}
