/*******************************************************************************
 *  Copyright (c) 2017, 2025 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.obeonetwork.m2doc.cdo.tests;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;

/**
 * Tests with authenticated CDO server and template on server and generate to the server.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ServerWithAuthenticationTemplateOnServerGenerateToServer extends ServerWithAuthenticationTemplateOnServer {

    /**
     * Constructor.
     * 
     * @param testFolder
     *            the test folder
     * @throws IOException
     *             if the tested template can't be read
     * @throws Exception
     *             if something went wrong
     */
    public ServerWithAuthenticationTemplateOnServerGenerateToServer(String testFolder) throws Exception {
        super(testFolder);
    }

    @Override
    protected URI getGenerationOutputURI(String testFolderPath) {
        final URI templateURI = getTemplateURI(new File(testFolderPath));

        return URI.createURI(templateURI.toString()
                .replaceAll("5ef25598-0af9-4436-b7df-5764732e4c0b/",
                        "5ef25598-0af9-4436-b7df-5764732e4c0b/" + testFolderPath)
                .replaceAll(".docx", "-generation-test.docx"), false);
    }

}
