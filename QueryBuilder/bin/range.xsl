<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="p span"/>

    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="root">
    	<xsl:text>&#xA;</xsl:text>
    	<xsl:copy>
			<xsl:apply-templates/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="toc">
    	<xsl:text>&#xA;&#x9;</xsl:text>
    	<xsl:copy>
			<xsl:apply-templates/>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="item">
    	<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:choose>
	            <xsl:when test="count(p) &gt; 1">
            		<xsl:apply-templates select="title"/>
            		<xsl:apply-templates select="p[1]">
            			<xsl:with-param name="style" select="'many'"/>
            		</xsl:apply-templates>
	            </xsl:when>
	            <xsl:otherwise>
            		<xsl:apply-templates select="title"/>
            		<xsl:apply-templates select="p[1]">
            			<xsl:with-param name="style" select="'one'"/>
            		</xsl:apply-templates>
	            </xsl:otherwise>
	       	</xsl:choose>
	       	<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="title">
    	<xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
			<xsl:apply-templates/>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="p[not(preceding-sibling::p)]">
    	<xsl:param name="style"/>
    	<xsl:text>&#xA;&#x9;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
			<xsl:if test="$style='many'">
				<xsl:attribute name="style">many</xsl:attribute>
			</xsl:if>
			<xsl:value-of select="concat('p.', @page, '¶')"/>
			<xsl:apply-templates/>
			<xsl:apply-templates select="following-sibling::p" mode="merge"/>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="p" mode="merge" priority="10">
		<span><xsl:value-of select="concat('¶¶', 'p.', @page, '¶')"/></span>
		<xsl:apply-templates/>
    </xsl:template>

</xsl:stylesheet>