package com.dnl.extension;

import java.lang.reflect.Constructor;


import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer2;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Parameters;
import java.lang.annotation.Annotation;

import com.dnl.repository.DataRepository;


public class DataTransformer implements IAnnotationTransformer2{

	@Override
	public void transform(ITestAnnotation testAnnotation, Class testClass, Constructor testConstructor,
			Method testMethod ) {
		// TODO Auto-generated method stub
		
		if(testMethod!=null){
			Annotation[] a= testMethod.getAnnotations();
			if(a.length!=0){
				 for(Annotation val : a){
					 if(val instanceof Parameters){
						//Test method has parameters 
						//Set the DataProvider Annotation on the test methodtestAnnotation
						testAnnotation.setDataProvider("create");
						testAnnotation.setDataProviderClass(DataRepository.class);
					 }
				 }
			 }
		}
	}

	@Override
	public void transform(IDataProviderAnnotation dataProviderAnnotation, Method testMethod) {
		// TODO Auto-generated method stub
	}

	@Override
	public void transform(IFactoryAnnotation factoryAnnotation, Method testMethod) {
		// TODO Auto-generated method stub
	}

	@Override
	public void transform(IConfigurationAnnotation configurationAnnotation, Class testClass,
			Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub
	}

}
