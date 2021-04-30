<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:aid="http://ns.adobe.com/AdobeInDesign/4.0/"
    xmlns:ast="http://www.astkorea.net/"
    exclude-result-prefixes="xs ast aid"
    version="2.0">

    <xsl:output method="xml" encoding="UTF-8" indent="no" />
    <xsl:preserve-space elements="*"/>
    <xsl:strip-space elements="Roots Root Story Callout stories Empty Empty-Indent Table Cell"/>

	<xsl:template match="@*">
	</xsl:template>

	<xsl:template match="*">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@* | node()"/>
		</xsl:element>
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
		<xsl:choose>
			<xsl:when test="starts-with(name(), 'Empty')">
				<xsl:choose>
					<xsl:when test="string(.)">
						<xsl:call-template name="hashing"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:variable name="Str">
							<xsl:value-of select="concat(ast:getLastToken(.//*/@href, '/'), @nested, @paragraphBreakType)"/>
						</xsl:variable>	
						<xsl:copy>
							<xsl:attribute name="hash" select="$Str"/>
							<xsl:apply-templates select="@* | node()"/>
						</xsl:copy>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="hashing"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="*[parent::Cell][string()]">
		<xsl:call-template name="hashing"/>
	</xsl:template>

	<xsl:template match="*[matches(name(), '^MMI')]">
		<xsl:variable name="Str">
			<xsl:value-of select="preceding-sibling::node()"/>
		</xsl:variable>
		<xsl:element name="{local-name()}">
			<xsl:attribute name="hash" select="concat($Str, .)"/>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:element>
	</xsl:template>

	<xsl:template name="hashing">
		<xsl:variable name="Str">
			<xsl:value-of select="concat(normalize-space(.), @nested, @paragraphBreakType)"/>
		</xsl:variable>
		<xsl:element name="{local-name()}">
			<xsl:if test="$Str!=''">
				<xsl:attribute name="hash" select="$Str"/>
			</xsl:if>
			<xsl:apply-templates select="node()"/>
		</xsl:element>
	</xsl:template>

	<xsl:function name="ast:getLastToken">
		<xsl:param name="str"/>
		<xsl:param name="char"/>
		<xsl:value-of select="tokenize($str, $char)[last()]" />
	</xsl:function>

</xsl:stylesheet>