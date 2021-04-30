<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
	xmlns:aid="http://ns.adobe.com/AdobeInDesign/4.0/"
    exclude-result-prefixes="xs ast aid"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:preserve-space elements="*"/>
    <xsl:strip-space elements="Roots Root Story Callout stories"/>

	<xsl:template match="@*">
	</xsl:template>

	<xsl:template match="*">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@* | node()"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="Roots">
		<xsl:text>&#xA;</xsl:text>
		<Root>
			<xsl:apply-templates />
			<xsl:text>&#xA;</xsl:text>
		</Root>
	</xsl:template>

	<xsl:template match="Root | Story | Callout | stories">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="*[parent::Story] | *[parent::stories]">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<p>
			<xsl:apply-templates/>
		</p>
	</xsl:template>

</xsl:stylesheet>