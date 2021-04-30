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
		<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/21-normalized.xml')"/>
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
		<xsl:variable name="this" select="."/>
		<xsl:choose>
			<xsl:when test="item/seg">
				<xsl:for-each select="1 to count(item[1]/seg)">
					<xsl:variable name="i" select="."/>
					<xsl:text>&#xA;&#x9;</xsl:text>
					<listitem ID="{$this/@ID}">
			    		<xsl:apply-templates select="$this/@*"/>
			    		<xsl:for-each select="$this/item">
			    			<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
							<xsl:copy>
					    		<xsl:apply-templates select="@*"/>
					    		<xsl:apply-templates select="seg[$i]/node()"/>
							</xsl:copy>
			    		</xsl:for-each>
			    		<xsl:text>&#xA;&#x9;</xsl:text>
					</listitem>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&#xA;&#x9;</xsl:text>
				<xsl:copy>
		    		<xsl:apply-templates select="@* | node()"/>
		    		<xsl:text>&#xA;&#x9;</xsl:text>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="item">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>