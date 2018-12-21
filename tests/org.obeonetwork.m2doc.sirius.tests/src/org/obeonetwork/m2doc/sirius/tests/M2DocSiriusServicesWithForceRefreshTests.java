/*******************************************************************************
 *  Copyright (c) 2016 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.obeonetwork.m2doc.sirius.tests;

import java.io.IOException;
import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;
import org.obeonetwork.m2doc.genconf.GenconfUtils;
import org.obeonetwork.m2doc.genconf.Generation;
import org.obeonetwork.m2doc.genconf.Option;
import org.obeonetwork.m2doc.parser.DocumentParserException;
import org.obeonetwork.m2doc.sirius.M2DocSiriusUtils;
import org.obeonetwork.m2doc.template.Bookmark;
import org.obeonetwork.m2doc.template.Link;

/**
 * Tests {@link Bookmark} and {@link Link}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class M2DocSiriusServicesWithForceRefreshTests extends AbstractTemplatesTestSuite {

    /**
     * Constructor.
     * 
     * @param testFolder
     *            the test folder path
     * @throws IOException
     *             if the tested template can't be read
     * @throws DocumentParserException
     *             if the tested template can't be parsed
     */
    public M2DocSiriusServicesWithForceRefreshTests(String testFolder) throws IOException, DocumentParserException {
        super(testFolder);
    }

    @Override
    protected void setTemplateFileName(Generation gen, String templateFileName) {
        super.setTemplateFileName(gen, templateFileName);
        final Option option = GenconfUtils.getOrCreateOption(gen, M2DocSiriusUtils.SIRIUS_FORCE_REFRESH);
        option.setValue(Boolean.TRUE.toString());
    }

    /**
     * Gets the {@link Collection} of test folders.
     * 
     * @return the {@link Collection} of test folders
     */
    @Parameters(name = "{0}")
    public static Collection<Object[]> retrieveTestFolders() {
        return retrieveTestFolders("resources/asImageWithSiriusForceRefresh");
    }

}
