<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

   	<xsl:output method="xml" encoding="UTF-8" standalone="yes" indent="no"/>
    <xsl:preserve-space elements="*"/>

    <xsl:template match="@*">
		<xsl:value-of select="concat(name(), '=', '&quot;', ., '&quot;')"/>
    </xsl:template>

    <xsl:template match="*">
		<xsl:text>&lt;</xsl:text>
		<xsl:value-of select="name()"/>
		<xsl:if test="@*">
			<xsl:for-each select="@*">
				<xsl:text>&#x20;</xsl:text>
				<xsl:apply-templates select="."/>
			</xsl:for-each>
		</xsl:if>
		<xsl:text>&gt;</xsl:text>
		<xsl:apply-templates select="node()"/>
		<xsl:value-of select="concat('&lt;', '/', name(), '&gt;')"/>
    </xsl:template>

    <xsl:template match="root">
    	<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()" />
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="style[count(*)=1][figure[ends-with(@linkpath, 'next-RtoL.ai')]]">
    	<xsl:text>~~</xsl:text>
    </xsl:template>

    <xsl:template match="text()">
    	<xsl:value-of select="replace(replace(., '&amp;', '&amp;amp;'), '&lt;', '&amp;lt;')"/>
    </xsl:template>

</xsl:stylesheet>