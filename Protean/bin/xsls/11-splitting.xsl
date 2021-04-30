<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

   	<xsl:output method="xml" encoding="UTF-8"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="item"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<xsl:variable name="root" select="root"/>
		<xsl:for-each select="root/listitem[1]/item/@LV_name">
			<xsl:variable name="LV_name" select="."/>
			<xsl:result-document href="{concat('out\', ., '.xml')}">
				<xsl:text>&#xA;</xsl:text>
				<root LV_name="{.}">
					<xsl:apply-templates select="$root//item[@LV_name=$LV_name]"/>
					<xsl:text>&#xA;</xsl:text>
				</root>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="item">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
		    <xsl:attribute name="ID" select="parent::listitem/@ID"/>
    		<xsl:apply-templates/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>