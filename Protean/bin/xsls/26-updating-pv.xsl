<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:param name="curdir"/>
	<xsl:variable name="pvdb" select="document(concat('file:///', replace($curdir, '\\', '/'), '/_pvdb/Paragraph_Variable.xml'))" as="document-node()"/>
	<xsl:variable name="trs" select="document(concat('file:///', replace($curdir, '\\', '/'), '/_translate/25-merged-tr.xml'))" as="document-node()"/>
	<xsl:variable name="today" select="format-dateTime(current-dateTime(), '[Y0001][M01][D01]')"/>

   	<xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="item"/>

	<xsl:template match="/">
		<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/_pvdb/', $today, '/Paragraph_Variable.xml')"/>
		<dummy/>
		<xsl:result-document method="xml" href="{$filename}">
			<root>
				<xsl:apply-templates select="$pvdb/root/listitem"/>
				<xsl:apply-templates select="$trs/root/listitem"/>
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
    		<xsl:apply-templates select="@* | node()"/>
    		<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
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