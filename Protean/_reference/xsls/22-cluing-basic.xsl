<?xml version="1.0" encoding="UTF-8"?>
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

	<xsl:template match="/">
		<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/22-clued-basic.xml')"/>
		<dummy/>
		<xsl:result-document method="xml" href="{$filename}">
			<root>
				<xsl:apply-templates select="root/listitem"/>
				<xsl:text>&#xA;</xsl:text>
			</root>
		</xsl:result-document>
	</xsl:template>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="listitem">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
    		<xsl:apply-templates select="@* except @mismatch"/>
    		<xsl:for-each select="item">
    			<xsl:choose>
    				<xsl:when test="matches(@LV_name, '^(ENG|ENG-US|KOR)$')">
    					<xsl:apply-templates select="." mode="basic"/>
    				</xsl:when>
    				<xsl:otherwise>
						<xsl:apply-templates select="." mode="foreign"/>
    				</xsl:otherwise>
    			</xsl:choose>
    		</xsl:for-each>
    		<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item" mode="basic">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
		    <xsl:apply-templates select="@*"/>
		    <xsl:for-each select="node()">
		    	<xsl:choose>
		    		<xsl:when test="self::*[name() != 'seg']">
						<xsl:copy>
				    		<xsl:apply-templates select="@*"/>
				    		<xsl:attribute name="x" select="count(preceding-sibling::*) + 1"/>
				    		<xsl:apply-templates select="node()"/>
						</xsl:copy>
		    		</xsl:when>
		    		<xsl:otherwise>
		    			<xsl:apply-templates select="."/>
		    		</xsl:otherwise>
		    	</xsl:choose>
		    </xsl:for-each>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item" mode="foreign">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
		    <xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>