<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:sdl="http://sdl.com/FileTypes/SdlXliff/1.0"
    xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2"
    xpath-default-namespace="urn:oasis:names:tc:xliff:document:1.2"
    xmlns:ast="http://www.astkorea.net/"
	exclude-result-prefixes="xsl xs sdl xliff ast"
    version="2.0">

	<xsl:param name="curdir"/>
	<xsl:variable name="path" select="concat('file:///', replace($curdir, '\\', '/'), '/out/', '?select=*.sdlxliff')"/>
	<xsl:variable name="files" select="collection($path)"/>
    <xsl:output method="xml" encoding="UTF-8" byte-order-mark="yes" indent="no" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*" />
	<xsl:preserve-space elements="target mrk" />

	<xsl:template match="/">
		<dummy/>
		<xsl:for-each select="$files">
			<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/12-marked_', ast:getFile(document-uri(.), '/'))"/>
			<xsl:result-document method="xml" href="{$filename}">
				<xsl:apply-templates select="xliff"/>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="target">
		<xsl:copy>
			<xsl:apply-templates />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="mrk[parent::target]">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
			<xsl:text>&#xB6;</xsl:text>
		</xsl:copy>
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