<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast"
    version="2.0">

	<xsl:variable name="job" select="document(resolve-uri('.job.xml', base-uri(.)))" as="document-node()?"/>

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

	<xsl:template match="image">
		<xsl:variable name="hash" select="@href"/>
		<xsl:variable name="href" select="concat('../../images/', ast:getFile($job/job//file[@uri=$hash][1]/@src, '/'))"/>
		<xsl:choose>
			<xsl:when test="ends-with($href, 'next.svg')">
				<xsl:if test="preceding-sibling::node()[1][self::*]">
					<xsl:text>&#x20;</xsl:text>
				</xsl:if>
				<xsl:text>~~</xsl:text>
				<xsl:if test="following-sibling::node()[1][self::*]">
					<xsl:text>&#x20;</xsl:text>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy>
					<xsl:attribute name="href" select="$href"/>
					<xsl:apply-templates select="@* except @href" />
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="xref">
		<xsl:variable name="href" select="@href"/>
		<xsl:copy>
			<xsl:attribute name="rev" select="@rev"/>
			<xsl:attribute name="href" select="concat('../', substring-after($job/job//file[@uri=$href][1]/@src, 'BASIC/'))"/>
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