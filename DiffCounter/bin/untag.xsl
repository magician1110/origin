<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:strip-space elements="Roots"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="Roots">
		<xsl:text>&#xA;</xsl:text>
		<Roots>
			<xsl:apply-templates select="@* | node()"/>
			<xsl:text>&#xA;</xsl:text>
		</Roots>
	</xsl:template>

	<xsl:template match="*[parent::Roots]">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="*[count(ancestor::*) &gt;= 2][not(parent::group)]">
		<xsl:choose>
			<xsl:when test="name()='xref'">
				<xsl:value-of select="concat('&lt;', name())"/>
				<xsl:value-of select="if ( @status ) then concat(' status=&quot;', @status, '&quot;/&gt;') else '/&gt;'"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="concat('&lt;', name())"/>
				<xsl:value-of select="if ( @status ) then concat(' status=&quot;', @status, '&quot;&gt;') else '&gt;'"/>
				<xsl:apply-templates select="node()"/>
				<xsl:value-of select="concat('&lt;/', name(), '&gt;')"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>