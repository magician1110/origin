<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="*"/>

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

    <xsl:template match="cmt">
    	<xsl:text>&#xA;&#x9;</xsl:text>
    	<xsl:copy>
			<xsl:for-each-group select="item" group-by="@page">
				<xsl:choose>
					<xsl:when test="current-grouping-key()">
						<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
						<item page="{current-grouping-key()}">
							<xsl:apply-templates select="current-group()/node()"/>
						</item>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="current-group()"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each-group>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
    </xsl:template>

    <xsl:template match="item">
    	<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
    	<xsl:copy>
            <xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
    </xsl:template>

</xsl:stylesheet>