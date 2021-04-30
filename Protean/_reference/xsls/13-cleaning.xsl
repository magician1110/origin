<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:param name="curdir"/>
	<xsl:variable name="path" select="concat('file:///', replace($curdir, '\\', '/'), '/out/', '?select=targeted_*.xml')"/>
	<xsl:variable name="files" select="collection($path)"/>
   	<xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="item"/>

	<xsl:template match="/">
		<dummy/>
		<xsl:for-each select="$files">
			<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/13-cleaned_', substring-after(ast:getFile(document-uri(.), '/'), 'targeted_'))"/>
			<xsl:result-document method="xml" href="{$filename}">
				<xsl:apply-templates select="root"/>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="root">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="text()" priority="10">
		<xsl:variable name="text0">
			<xsl:choose>
				<xsl:when test="matches(., ':¶\s+')">
					<xsl:value-of select="replace(., ':¶\s+', ':&#x20;')"/>
				</xsl:when>
				<xsl:when test="preceding-sibling::node()[1][self::*] and starts-with(., '¶') and following-sibling::node()[1][self::*]">
					<xsl:value-of select="replace(., '^¶', '')"/>
				</xsl:when>
				<xsl:when test="starts-with(., '¶') and preceding-sibling::node()[1][self::*]">
					<xsl:value-of select="replace(., '^¶', '')"/>
				</xsl:when>
				<xsl:when test="ends-with(., '.&#x20;') and following-sibling::node()[1][self::*] and following-sibling::node()[2][self::text()][starts-with(., '¶')]">
					<xsl:value-of select="replace(., '\.\s+', '.¶&#x20;')"/>
				</xsl:when>
				<xsl:when test="parent::item and not(following-sibling::node())">
					<xsl:value-of select="replace(replace(., '(\.\.\.)¶', '$1'), '¶$', '')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="."/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="text1">
			<xsl:value-of select="replace($text0, '\s+(ceux|celle)(\-ci\.)\s+', '&#x20;$1$2¶&#x20;')"/>
		</xsl:variable>
		<xsl:variable name="text2">
			<xsl:value-of select="replace($text1, '(de l’appareil\.)¶\s+', '$1&#x20;')"/>
		</xsl:variable>
		<xsl:variable name="text3">
			<xsl:value-of select="replace($text2, '&#x104B;&#x20;', '&#x104B;¶&#x20;')"/>
		</xsl:variable>
		<xsl:analyze-string select="$text3" regex="~~">
			<xsl:matching-substring>
				<image href="../../images/next.svg" otherprops="alt:yes"/>
			</xsl:matching-substring>
			<xsl:non-matching-substring>
				<xsl:value-of select="replace(replace(replace(., '&lt;', '%lt;'), '&gt;', '%gt;'), '&amp;', '%amp;')"/>
			</xsl:non-matching-substring>
		</xsl:analyze-string>
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