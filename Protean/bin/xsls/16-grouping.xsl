<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:character-map name="cm">
	    <xsl:output-character character="&amp;" string="&amp;amp;"/>
	    <xsl:output-character character="&lt;" string="&amp;lt;"/>
	    <xsl:output-character character="&gt;" string="&amp;gt;"/>
	</xsl:character-map>

	<xsl:param name="curdir"/>
	<xsl:variable name="path" select="concat('file:///', replace($curdir, '\\', '/'), '/out/', '?select=15-retagged*.xml')"/>
	<xsl:variable name="files" select="collection($path)"/>
   	<xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes" use-character-maps="cm"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="item seg"/>

	<xsl:template match="@* | node()" mode="#all">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<dummy/>
		<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/16-grouped.xml')"/>
		<xsl:result-document method="xml" href="{$filename}">
			<root>
				<xsl:for-each select="$files[1]/root/item">
					<xsl:variable name="ID" select="@ID"/>
					<xsl:text>&#xA;&#x9;</xsl:text>
					<listitem ID="{$ID}">
						<xsl:apply-templates select="$files/root/item[@ID=$ID]"/>
						<xsl:text>&#xA;&#x9;</xsl:text>
					</listitem>
				</xsl:for-each>
				<xsl:text>&#xA;</xsl:text>
			</root>
		</xsl:result-document>
	</xsl:template>

	<xsl:template match="item">
		<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
		<item LV_name="{parent::root/@LV_name}">
			<xsl:apply-templates/>
		</item>
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