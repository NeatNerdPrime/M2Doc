/**
 */
package org.obeonetwork.m2doc.genconf.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.obeonetwork.m2doc.genconf.BooleanDefinition;
import org.obeonetwork.m2doc.genconf.BooleanOrderedSetDefinition;
import org.obeonetwork.m2doc.genconf.BooleanSequenceDefinition;
import org.obeonetwork.m2doc.genconf.Definition;
import org.obeonetwork.m2doc.genconf.GenconfPackage;
import org.obeonetwork.m2doc.genconf.Generation;
import org.obeonetwork.m2doc.genconf.IntegerDefinition;
import org.obeonetwork.m2doc.genconf.IntegerOrderedSetDefinition;
import org.obeonetwork.m2doc.genconf.IntegerSequenceDefinition;
import org.obeonetwork.m2doc.genconf.ModelDefinition;
import org.obeonetwork.m2doc.genconf.ModelOrderedSetDefinition;
import org.obeonetwork.m2doc.genconf.ModelSequenceDefinition;
import org.obeonetwork.m2doc.genconf.Option;
import org.obeonetwork.m2doc.genconf.RealDefinition;
import org.obeonetwork.m2doc.genconf.RealOrderedSetDefinition;
import org.obeonetwork.m2doc.genconf.RealSequenceDefinition;
import org.obeonetwork.m2doc.genconf.StringDefinition;
import org.obeonetwork.m2doc.genconf.StringOrderedSetDefinition;
import org.obeonetwork.m2doc.genconf.StringSequenceDefinition;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * 
 * @see org.obeonetwork.m2doc.genconf.GenconfPackage
 * @generated
 */
public class GenconfAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static GenconfPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    public GenconfAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = GenconfPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * 
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    protected GenconfSwitch<Adapter> modelSwitch = new GenconfSwitch<Adapter>() {
        @Override
        public Adapter caseGeneration(Generation object) {
            return createGenerationAdapter();
        }

        @Override
        public Adapter caseDefinition(Definition object) {
            return createDefinitionAdapter();
        }

        @Override
        public Adapter caseModelDefinition(ModelDefinition object) {
            return createModelDefinitionAdapter();
        }

        @Override
        public Adapter caseModelSequenceDefinition(ModelSequenceDefinition object) {
            return createModelSequenceDefinitionAdapter();
        }

        @Override
        public Adapter caseModelOrderedSetDefinition(ModelOrderedSetDefinition object) {
            return createModelOrderedSetDefinitionAdapter();
        }

        @Override
        public Adapter caseStringDefinition(StringDefinition object) {
            return createStringDefinitionAdapter();
        }

        @Override
        public Adapter caseStringSequenceDefinition(StringSequenceDefinition object) {
            return createStringSequenceDefinitionAdapter();
        }

        @Override
        public Adapter caseStringOrderedSetDefinition(StringOrderedSetDefinition object) {
            return createStringOrderedSetDefinitionAdapter();
        }

        @Override
        public Adapter caseIntegerDefinition(IntegerDefinition object) {
            return createIntegerDefinitionAdapter();
        }

        @Override
        public Adapter caseIntegerSequenceDefinition(IntegerSequenceDefinition object) {
            return createIntegerSequenceDefinitionAdapter();
        }

        @Override
        public Adapter caseIntegerOrderedSetDefinition(IntegerOrderedSetDefinition object) {
            return createIntegerOrderedSetDefinitionAdapter();
        }

        @Override
        public Adapter caseRealDefinition(RealDefinition object) {
            return createRealDefinitionAdapter();
        }

        @Override
        public Adapter caseRealSequenceDefinition(RealSequenceDefinition object) {
            return createRealSequenceDefinitionAdapter();
        }

        @Override
        public Adapter caseRealOrderedSetDefinition(RealOrderedSetDefinition object) {
            return createRealOrderedSetDefinitionAdapter();
        }

        @Override
        public Adapter caseBooleanDefinition(BooleanDefinition object) {
            return createBooleanDefinitionAdapter();
        }

        @Override
        public Adapter caseBooleanSequenceDefinition(BooleanSequenceDefinition object) {
            return createBooleanSequenceDefinitionAdapter();
        }

        @Override
        public Adapter caseBooleanOrderedSetDefinition(BooleanOrderedSetDefinition object) {
            return createBooleanOrderedSetDefinitionAdapter();
        }

        @Override
        public Adapter caseOption(Option object) {
            return createOptionAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.Generation <em>Generation</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.Generation
     * @generated
     */
    public Adapter createGenerationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.Definition <em>Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.Definition
     * @generated
     */
    public Adapter createDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.ModelDefinition <em>Model Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.ModelDefinition
     * @generated
     */
    public Adapter createModelDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.ModelSequenceDefinition <em>Model Sequence
     * Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.ModelSequenceDefinition
     * @generated
     */
    public Adapter createModelSequenceDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.ModelOrderedSetDefinition <em>Model Ordered Set
     * Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.ModelOrderedSetDefinition
     * @generated
     */
    public Adapter createModelOrderedSetDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.StringDefinition <em>String Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.StringDefinition
     * @generated
     */
    public Adapter createStringDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.StringSequenceDefinition <em>String Sequence
     * Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.StringSequenceDefinition
     * @generated
     */
    public Adapter createStringSequenceDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.StringOrderedSetDefinition <em>String Ordered Set
     * Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.StringOrderedSetDefinition
     * @generated
     */
    public Adapter createStringOrderedSetDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.IntegerDefinition <em>Integer Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.IntegerDefinition
     * @generated
     */
    public Adapter createIntegerDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.IntegerSequenceDefinition <em>Integer Sequence
     * Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.IntegerSequenceDefinition
     * @generated
     */
    public Adapter createIntegerSequenceDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.IntegerOrderedSetDefinition <em>Integer Ordered
     * Set Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.IntegerOrderedSetDefinition
     * @generated
     */
    public Adapter createIntegerOrderedSetDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.RealDefinition <em>Real Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.RealDefinition
     * @generated
     */
    public Adapter createRealDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.RealSequenceDefinition <em>Real Sequence
     * Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.RealSequenceDefinition
     * @generated
     */
    public Adapter createRealSequenceDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.RealOrderedSetDefinition <em>Real Ordered Set
     * Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.RealOrderedSetDefinition
     * @generated
     */
    public Adapter createRealOrderedSetDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.BooleanDefinition <em>Boolean Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.BooleanDefinition
     * @generated
     */
    public Adapter createBooleanDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.BooleanSequenceDefinition <em>Boolean Sequence
     * Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.BooleanSequenceDefinition
     * @generated
     */
    public Adapter createBooleanSequenceDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.BooleanOrderedSetDefinition <em>Boolean Ordered
     * Set Definition</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.BooleanOrderedSetDefinition
     * @generated
     */
    public Adapter createBooleanOrderedSetDefinitionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.obeonetwork.m2doc.genconf.Option <em>Option</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @see org.obeonetwork.m2doc.genconf.Option
     * @generated
     */
    public Adapter createOptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * 
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // GenconfAdapterFactory
