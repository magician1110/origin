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

	<xsl:template match="Table | Cell">
		<xsl:apply-templates/>
	</xsl:template>

	<xsl:template match="*[parent::Roots]">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="text()" priority="10">
		<xsl:analyze-string select="." regex="(\.)([\s\(])"> 
			<xsl:matching-substring>
				<xsl:value-of select="concat(regex-group(1), '¶', replace(regex-group(2), '^\s+', ''))"/> 
			</xsl:matching-substring> 
			<xsl:non-matching-substring> 
				<xsl:value-of select="."/> 
			</xsl:non-matching-substring> 
		</xsl:analyze-string>
	</xsl:template>

</xsl:stylesheet>