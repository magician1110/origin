<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:character-map name="cm">
	    <xsl:output-character character="&amp;" string="&amp;"/>
	    <xsl:output-character character="&lt;" string="&lt;"/>
	    <xsl:output-character character="&gt;" string="&gt;"/>
	</xsl:character-map>

	<xsl:param name="curdir"/>
	<xsl:variable name="path" select="concat('file:///', replace($curdir, '\\', '/'), '/out/', '?select=14-untagged*.xml')"/>
	<xsl:variable name="files" select="collection($path)"/>
   	<xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes" use-character-maps="cm"/>
	<xsl:strip-space elements="*"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<dummy/>
		<xsl:for-each select="$files">
			<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/15-retagged_', substring-after(ast:getFile(document-uri(.), '/'), '14-untagged_'))"/>
			<xsl:result-document method="xml" href="{$filename}">
				<xsl:apply-templates select="root"/>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="text()" priority="10">
		<xsl:value-of select="replace(replace(replace(., '%lt;', '&amp;lt;'), '%gt;', '&amp;gt;'), '%amp;', '&amp;amp;')"/>
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