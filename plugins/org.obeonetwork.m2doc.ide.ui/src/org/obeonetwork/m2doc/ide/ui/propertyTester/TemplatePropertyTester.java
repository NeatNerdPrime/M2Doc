/*******************************************************************************
 *  Copyright (c) 2016, 2025 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.obeonetwork.m2doc.ide.ui.propertyTester;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.obeonetwork.m2doc.ide.ui.M2DocUIPlugin;
import org.obeonetwork.m2doc.properties.TemplateCustomProperties;
import org.obeonetwork.m2doc.util.M2DocUtils;

/**
 * Test if handler is launched on docx file.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 */
public class TemplatePropertyTester extends PropertyTester {

    @Override
    public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
        final boolean res;

        if (receiver instanceof List) {
            boolean allValid = true;
            for (Object object : (List<?>) receiver) {
                if (!test(object, property, args, expectedValue)) {
                    allValid = false;
                    break;
                }
            }
            res = allValid;
        } else {
            switch (property) {
                case "isTemplate":
                    res = isTemplate(receiver);
                    break;

                case "hasLibraryTemplate":
                    if (receiver instanceof EObject) {
                        final EObject eObj = (EObject) receiver;

                        final IQueryEnvironment queryEnvironment = Query.newEnvironment();
                        queryEnvironment.registerEPackage(eObj.eClass().getEPackage());
                        for (EClass superECls : eObj.eClass().getEAllSuperTypes()) {
                            queryEnvironment.registerEPackage(superECls.getEPackage());
                        }

                        final Set<IType> variableTypes = new HashSet<>();
                        variableTypes.add(new EClassifierType(queryEnvironment, eObj.eClass()));

                        final Map<File, TemplateCustomProperties> compatibleTemplates = M2DocUIPlugin.getDefault()
                                .getCompatibleTemplatesFromLibraries(queryEnvironment, variableTypes);

                        res = !compatibleTemplates.isEmpty();
                    } else {
                        res = false;
                    }
                    break;

                case "needMigration":
                    boolean needMigration;
                    try {
                        needMigration = isTemplate(receiver) && M2DocUtils.needMigration(URIConverter.INSTANCE,
                                URI.createPlatformResourceURI(((IFile) receiver).getFullPath().toString(), true));
                    } catch (IOException e) {
                        needMigration = false;
                    }
                    res = needMigration;
                    break;

                default:
                    res = false;
                    break;
            }
        }

        return res;
    }

    /**
     * Tells if the given {@link Object} is a template.
     * 
     * @param receiver
     *            the receiver
     * @return <code>true</code> if the given {@link Object} is a template, <code>false</code> otherwise
     */
    private boolean isTemplate(Object receiver) {
        return receiver instanceof IFile
            && M2DocUtils.DOCX_EXTENSION_FILE.equals(((IFile) receiver).getFileExtension());
    }

}
