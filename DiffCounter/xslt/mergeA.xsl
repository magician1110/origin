<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:aid="http://ns.adobe.com/AdobeInDesign/4.0/"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast aid"
    version="2.0">

	<xsl:param name="basePath"/>
	<xsl:variable name="base" select="replace($basePath, '\\', '/')"/>
	<xsl:variable name="content" select="collection(concat('file:///', $base, '/XML?select=*.xml'))"/>
	<xsl:variable name="callout" select="collection(concat('file:///', $base, '/%5BCallout%5D/?select=*.xml'))"/>

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:preserve-space elements="*"/>
    <xsl:strip-space elements="Root Story Empty Empty-Indent Table Cell stories"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="dummy">
		<xsl:text>&#xA;</xsl:text>
		<Roots>
			<xsl:for-each select="$content">
				<xsl:apply-templates select="."/>
			</xsl:for-each>
			<xsl:text>&#xA;</xsl:text>
			<xsl:for-each select="$callout">
				<xsl:apply-templates select="."/>
			</xsl:for-each>
			<xsl:text>&#xA;</xsl:text>
		</Roots>
	</xsl:template>

	<xsl:template match="Root">
		<xsl:variable name="filename" select="ast:getFilename(document-uri(/), '/')"/>
		<xsl:if test="not(matches($filename, '_(Cover|TOC)'))">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>

	<xsl:template match="Story | Callout | stories">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="*[parent::Story]">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="text()" priority="10">
		<xsl:choose>
			<xsl:when test="normalize-space(.)='' and not(preceding-sibling::node())">
			</xsl:when>
			<xsl:when test="normalize-space(.)='' and not(following-sibling::node())">
			</xsl:when>
			<xsl:when test="parent::*/parent::Cell and not(following-sibling::node())">
				<xsl:value-of select="replace(replace(., '\s+', '&#x20;'), '\s+$', '')"/>
			</xsl:when>
			<xsl:when test="parent::*/parent::Story and not(following-sibling::node())">
				<xsl:value-of select="replace(replace(., '\s+', '&#x20;'), '\s+$', '')"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="replace(., '\s+', '&#x20;')"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="story">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<story>
			<xsl:apply-templates select="@* | node()"/>
		</story>
	</xsl:template>

	<xsl:function name="ast:getFilename">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="tokenize($str, $char)[last()]" />
	</xsl:function>

</xsl:stylesheet>