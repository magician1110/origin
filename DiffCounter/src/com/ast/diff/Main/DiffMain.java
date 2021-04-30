package com.ast.diff.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.ast.diff.View.DiffView;
import com.deltaxml.api.NoLicenseInstalledException;
import com.deltaxml.core.ComparatorInstantiationException;
import com.deltaxml.core.FilterClassInstantiationException;
import com.deltaxml.core.FilterConfigurationException;
import com.deltaxml.core.ParserInstantiationException;
import com.deltaxml.core.PipelinedComparatorError;
import com.deltaxml.cores9api.ComparisonException;
import com.deltaxml.cores9api.DynamicPDFormatException;
import com.deltaxml.cores9api.FilterProcessingException;
import com.deltaxml.cores9api.LicenseException;
import com.deltaxml.cores9api.PDAdvancedConfigException;
import com.deltaxml.cores9api.PDFilterConfigurationException;
import com.deltaxml.cores9api.PipelineLoadingException;
import com.deltaxml.cores9api.PipelineSerializationException;
import com.deltaxml.cores9api.StaticPDFormatException;


public class DiffMain {
	
	public static void main(String[] args) throws ComparisonException, FilterProcessingException, PipelineLoadingException, PipelineSerializationException, LicenseException, StaticPDFormatException, PDFilterConfigurationException, DynamicPDFormatException, PDAdvancedConfigException, IllegalStateException, PipelinedComparatorError, IOException, ParserInstantiationException, FilterConfigurationException, ComparatorInstantiationException, FilterClassInstantiationException, NoLicenseInstalledException{
	
		DiffView dView = new DiffView();
		dView.diffViewManager();		

		
	}
	
}
