<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:preserve-space elements="*"/>
    <xsl:strip-space elements="Roots"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="Roots">
		<xsl:text>&#xA;</xsl:text>
		<xsl:copy>
			<xsl:for-each-group select="*" group-adjacent="matches(@diff, '^(A|B)$')">
				<xsl:choose>
					<xsl:when test="current-grouping-key()">
						<xsl:text>&#xA;&#x9;</xsl:text>
                        <group diff="{current-group()[1]/@diff}">
                            <xsl:apply-templates select="current-group()"/>
                            <xsl:text>&#xA;&#x9;</xsl:text>
                        </group>
					</xsl:when>
					<xsl:otherwise>
						<xsl:apply-templates select="current-group()"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each-group>
			<xsl:text>&#xA;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="*[parent::Roots]">
		<xsl:value-of select="if ( matches(@diff, '^(A|B)$') ) then '&#xA;&#x9;&#x9;' else '&#xA;&#x9;'"/>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="@diff[matches(., '^(A|B)$')]">
	</xsl:template>

</xsl:stylesheet>