﻿<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:param name="curdir"/>
   	<xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="seg item"/>

	<xsl:template match="@* | node()" mode="#all">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/19-confirmed.xml')"/>
		<dummy/>
		<xsl:result-document method="xml" href="{$filename}">
			<xsl:apply-templates/>
		</xsl:result-document>
	</xsl:template>

	<xsl:template match="root">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
    		<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="listitem">
		<xsl:variable name="listitem">
			<xsl:copy>
	    		<xsl:apply-templates select="@* | node()" mode="cnt"/>
			</xsl:copy>
		</xsl:variable>

		<xsl:if test="count(distinct-values($listitem/listitem/item/@segs)) &gt; 1">
			<xsl:text>&#xA;&#x9;</xsl:text>
			<xsl:copy>
 		    	<xsl:apply-templates select="@* except @mismatch"/>
    			<xsl:attribute name="mismatch">yes</xsl:attribute>
			</xsl:copy>
		</xsl:if>
	</xsl:template>

	<xsl:template match="item" mode="cnt">
		<xsl:copy>
    		<xsl:apply-templates select="@*"/>
    		<xsl:attribute name="segs" select="count(seg)"/>
    		<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>