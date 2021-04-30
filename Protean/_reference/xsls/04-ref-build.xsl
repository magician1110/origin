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
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="root">
		<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="listitem">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="apiname">
		<xsl:copy>
			<xsl:apply-templates select="@* except (@outputclass, @otherprops)" />
			<xsl:apply-templates/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="xref">
		<xsl:variable name="href" select="ast:getFile(@href, '/')"/>
		<xsl:copy>
			<xsl:attribute name="href" select="$href"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="text()" priority="10">
		<xsl:value-of select="replace(replace(., '\(\s+', '('), '\s+\)', ')')"/>
	</xsl:template>

	<xsl:function name="ast:getPath">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="string-join(tokenize($str, $char)[position() ne last()], '/')" />
	</xsl:function>

	<xsl:function name="ast:getFile">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="tokenize($str, $char)[last()]" />
	</xsl:function>

</xsl:stylesheet>