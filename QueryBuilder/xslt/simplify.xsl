<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    xmlns:xhtml="http://www.w3.org/1999/xhtml"
    xmlns:xfa="http://www.xfa.org/schema/xfa-data/1.0/"
    exclude-result-prefixes="xs ast xhtml xfa"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />

    <!-- template to copy elements -->
    <xsl:template match="*">
        <xsl:element name="{local-name()}">
            <xsl:apply-templates select="@* | node()"/>
        </xsl:element>
    </xsl:template>

    <!-- template to copy attributes -->
    <xsl:template match="@*">
        <xsl:attribute name="{local-name()}">
            <xsl:value-of select="."/>
        </xsl:attribute>
    </xsl:template>

    <xsl:template match="root">
    	<xsl:text>&#xA;</xsl:text>
    	<xsl:copy>
			<xsl:apply-templates/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="toc | cmt">
    	<xsl:text>&#xA;&#x9;</xsl:text>
    	<xsl:copy>
			<xsl:apply-templates/>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="item">
    	<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="xhtml:body">
		<xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="@xfa:APIVersion | @xfa:spec">
    </xsl:template>

    <xsl:template match="@style">
    	<xsl:choose>
    		<xsl:when test="contains(., 'font-weight:bold')">
    			<xsl:attribute name="style">bold</xsl:attribute>
    		</xsl:when>
    		<xsl:otherwise>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>

    <xsl:template match="text()" priority="10">
    	<xsl:choose>
    		<xsl:when test="parent::*[local-name()='span']">
    		    <xsl:value-of select="replace(replace(replace(., '[&#xA;&#xD;]', '¶'), '&#x9;', ''), '&#xA;', '')"/>
    		</xsl:when>
    		<xsl:otherwise>
    			<xsl:value-of select="replace(replace(replace(., '[&#xD;]', '¶'), '&#x9;', ''), '&#xA;', '')"/>
    		</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>

</xsl:stylesheet>