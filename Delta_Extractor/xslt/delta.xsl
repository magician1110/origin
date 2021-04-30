<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:ast="http://www.astkorea.net/"
    xmlns:functx="http://www.functx.com"
    exclude-result-prefixes="xs ast functx"
    version="2.0">

    <xsl:param name="zzzPath"/>
    <xsl:variable name="zzz" select="replace($zzzPath, '\\', '/')"/>

    <xsl:variable name="docAPs" select="document(concat($zzz, '/uniqueA.xml'))//p" as="element()*"/>
    <xsl:variable name="PIDs" select="document(concat($zzz, '/mergedB.xml'))//*[@pid]" as="element()*"/> 
 
    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:preserve-space elements="*"/>
    <xsl:strip-space elements="Roots Root Story Empty Table Cell"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="@pid">
	</xsl:template>

	<xsl:template match="Root">
		<xsl:text>&#xA;</xsl:text>
		<Root>
			<xsl:apply-templates />
			<xsl:text>&#xA;</xsl:text>
		</Root>
	</xsl:template>

	<xsl:template match="p">
		<xsl:variable name="netP" as="element()">
			<xsl:copy>
				<xsl:apply-templates/>
			</xsl:copy>
		</xsl:variable>
		<xsl:variable name="followingPs" as="element()*">
			<xsl:for-each select="following-sibling::p">
				<xsl:copy>
					<xsl:apply-templates/>
				</xsl:copy>
			</xsl:for-each>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="functx:is-node-in-sequence-deep-equal($netP, $followingPs) or
							functx:is-node-in-sequence-deep-equal($netP, $docAPs)"/>
			<xsl:otherwise>
				<xsl:variable name="pid" select="@pid"/>
				<xsl:variable name="theP" select="$PIDs[@pid=$pid]"/>
				<xsl:text>&#xA;&#x9;</xsl:text>
				<xsl:apply-templates select="$theP"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:function name="functx:is-node-in-sequence-deep-equal" as="xs:boolean">
		<xsl:param name="node" as="node()?"/>
		<xsl:param name="seq" as="node()*"/>
		<xsl:sequence select="some $nodeInSeq in $seq satisfies deep-equal($nodeInSeq, $node)"/>
	</xsl:function>

</xsl:stylesheet>