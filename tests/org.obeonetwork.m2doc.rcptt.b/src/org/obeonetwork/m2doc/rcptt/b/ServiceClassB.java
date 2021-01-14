/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.obeonetwork.m2doc.rcptt.b;

import org.eclipse.emf.ecore.EObject;

/**
 * A service test class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ServiceClassB {

    /**
     * A service.
     * 
     * @param string
     *            the {@link String}
     * @param eObj
     *            the {@link EObject}
     * @return a {@link String}
     */
    public String serviceB(String string, EObject eObj) {
        final StringBuilder builder = new StringBuilder();

        builder.append("ServiceClassB.serviceB");
        builder.append('\n');
        builder.append("String parameter is a String: " + (string instanceof String));
        builder.append('\n');
        builder.append(string);
        builder.append('\n');
        builder.append("EObject parameter is a EObject: " + (eObj instanceof EObject));
        builder.append('\n');
        builder.append(eObj.toString());
        builder.append('\n');

        return builder.toString();
    }

}
