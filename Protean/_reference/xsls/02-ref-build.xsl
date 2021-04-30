<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

  	<xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="item"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="root">
		<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:apply-templates select="listitem"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="listitem">
		<xsl:variable name="item" select="item"/>
		<xsl:variable name="precedingItems" select="preceding::item"/>

		<xsl:variable name="Exist">
			<xsl:choose>
	        	<xsl:when test="$precedingItems[deep-equal(., $item)]">
					<xsl:value-of select="'yes'" />
	        	</xsl:when>
	        	<xsl:otherwise>
					<xsl:value-of select="'no'" />
	        	</xsl:otherwise>
	        </xsl:choose>
		</xsl:variable>

		<xsl:if test="$Exist = 'no'">
			<xsl:variable name="cnt" select="format-number(count($precedingItems), '0000')"/>
			<xsl:text>&#xA;&#x9;</xsl:text>
			<xsl:copy>
				<xsl:apply-templates select="@* | node()"/>
				<xsl:text>&#xA;&#x9;</xsl:text>
			</xsl:copy>
		</xsl:if>
	</xsl:template>

	<xsl:template match="item">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>