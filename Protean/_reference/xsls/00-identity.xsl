<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<!--<xsl:param name="lang_code"/>-->
	<xsl:param name="LV_name"/>
	<xsl:param name="status"/>

	<xsl:variable name="mappath" select="ast:getPath(base-uri(), '/')"/>
	<xsl:variable name="mapname" select="substring-after(ast:getFile(base-uri(), '/'), '_')"/>

	<xsl:variable name="Access.LangTable" select="document(concat(ast:getPath(base-uri(document('')), '/'), '/../', 'language_codes/lang_code_tables.xml'))" />
	<xsl:variable name="lang_code" select="$Access.LangTable/root/language[@LV_name = $LV_name]/@lang_code" />

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="*"/>

	<xsl:template match="/">
		<xsl:result-document href="{concat($mappath, '/', $mapname)}">
			<xsl:text>&#xA;</xsl:text>
			<xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE map PUBLIC "-//OASIS//DTD DITA Map//EN" "map.dtd"&gt;</xsl:text>
	    	<xsl:apply-templates/>
	    </xsl:result-document>
	   <dummy/>
	</xsl:template>

    <xsl:template match="map">
    	<xsl:text>&#xA;</xsl:text>
    	<xsl:copy>
    		<xsl:if test="string($status)">
    			<xsl:attribute name="status" select="$status" />
    		</xsl:if>
    		<xsl:apply-templates select="@*" />
			<xsl:attribute name="xml:lang" select="$lang_code"/>
			<xsl:attribute name="otherprops" select="$LV_name"/>
    		<xsl:apply-templates select="node()" />
    		<xsl:text>&#xA;</xsl:text>
    	</xsl:copy>
    </xsl:template>

    <xsl:template match="mapref">
    	<xsl:text>&#xA;&#x9;</xsl:text>
    	<xsl:copy>
    		<xsl:apply-templates select="@* | node()" />
    	</xsl:copy>
    </xsl:template>

    <xsl:template match="@* | node()">
    	<xsl:copy>
    		<xsl:apply-templates select="@* | node()" />
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