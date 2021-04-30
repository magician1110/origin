<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:param name="curdir"/>
	<xsl:variable name="path" select="concat('file:///', replace($curdir, '\\', '/'), '/out/', '?select=13-cleaned*.xml')"/>
	<xsl:variable name="files" select="collection($path)"/>
   	<xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="item"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="/">
		<dummy/>
		<xsl:for-each select="$files">
			<xsl:variable name="filename" select="concat('file:///', replace($curdir, '\\', '/'), '/out/14-untagged_', substring-after(ast:getFile(document-uri(.), '/'), '13-cleaned_'))"/>
			<xsl:result-document method="xml" href="{$filename}">
				<xsl:apply-templates select="root"/>
			</xsl:result-document>
		</xsl:for-each>
	</xsl:template>

    <xsl:template match="@*" mode="segment">
		<xsl:value-of select="concat(name(), '=', '&quot;', ., '&quot;')"/>
    </xsl:template>

    <xsl:template match="ph | b | cmdname | varname | apiname | image | next | xref" mode="segment">
		<xsl:text>&lt;</xsl:text>
		<xsl:value-of select="name()"/>
		<xsl:if test="@*">
			<xsl:for-each select="@*">
				<xsl:text>&#x20;</xsl:text>
				<xsl:apply-templates select="." mode="segment"/>
			</xsl:for-each>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="matches(name(), '^(image|next|xref)$')">
				<xsl:text>/&gt;</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&gt;</xsl:text>
				<xsl:apply-templates select="node()" mode="segment"/>
				<xsl:value-of select="concat('&lt;', '/', name(), '&gt;')"/>
			</xsl:otherwise>
		</xsl:choose>
    </xsl:template>

	<xsl:template match="root">
		<xsl:copy>
    		<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="item">
		<xsl:copy>
    		<xsl:apply-templates select="@*"/>
    		<xsl:variable name="item">
    			<xsl:apply-templates mode="segment"/>
    		</xsl:variable>
    		<xsl:choose>
    			<xsl:when test="contains($item, '&#xB6;')">
					<xsl:analyze-string select="$item" regex="¶\s+">
						<xsl:matching-substring>
						</xsl:matching-substring>
						<xsl:non-matching-substring>
							<seg>
								<xsl:value-of select="."/>
							</seg>
						</xsl:non-matching-substring>
					</xsl:analyze-string>
    			</xsl:when>
    			<xsl:otherwise>
    				<xsl:value-of select="$item"/>
    			</xsl:otherwise>
    		</xsl:choose>
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