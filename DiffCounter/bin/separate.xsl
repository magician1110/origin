<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">

	<xsl:character-map name="map"> 
		<xsl:output-character character="&lt;" string="&lt;"/>
		<xsl:output-character character="&gt;" string="&gt;"/>
	</xsl:character-map>

    <xsl:output method="xml" encoding="UTF-8" indent="no"/>
    <!--<xsl:output method="xml" encoding="UTF-8" indent="yes" use-character-maps="map"/>-->
    <xsl:strip-space elements="Roots group each"/>

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

	<xsl:template match="group | each">
		<xsl:text>&#xA;&#x9;</xsl:text>
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
			<xsl:text>&#xA;&#x9;</xsl:text>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="seg">
		<xsl:choose>
			<xsl:when test="*[ends-with(name(), '-Cell')]">
				<xsl:for-each select="*">
					<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
					<seg>
						<xsl:apply-templates select="."/>
					</seg>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&#xA;&#x9;&#x9;</xsl:text>
				<xsl:copy>
					<xsl:apply-templates select="@* | node()"/>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>