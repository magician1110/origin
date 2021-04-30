<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    xmlns:functx="http://www.functx.com"
    exclude-result-prefixes="xs ast functx"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="*"/>
    <xsl:preserve-space elements="p"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="Root">
		<xsl:text>&#xA;</xsl:text>
		<Root>
			<xsl:apply-templates />
			<xsl:text>&#xA;</xsl:text>
		</Root>
	</xsl:template>

	<xsl:template match="p">
		<xsl:choose>
			<xsl:when test="functx:is-node-in-sequence-deep-equal(., following-sibling::p)"/>
			<xsl:otherwise>
				<xsl:text>&#xA;&#x9;</xsl:text>
				<p>
					<xsl:apply-templates/>
				</p>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:function name="functx:is-node-in-sequence-deep-equal" as="xs:boolean">
		<xsl:param name="node" as="node()?"/>
		<xsl:param name="seq" as="node()*"/>
		<xsl:sequence select="some $nodeInSeq in $seq satisfies deep-equal($nodeInSeq, $node)"/>
	</xsl:function>

</xsl:stylesheet>